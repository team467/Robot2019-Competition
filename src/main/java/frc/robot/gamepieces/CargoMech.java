package frc.robot.gamepieces;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

import frc.robot.RobotMap;
import frc.robot.drive.TalonProxy;
import frc.robot.drive.WpiTalonSrxInterface;
import frc.robot.logging.RobotLogManager;
import frc.robot.logging.TelemetryBuilder;

import org.apache.logging.log4j.Logger;

public class CargoMech extends GamePieceBase implements GamePiece {

  private static CargoMech instance = null; // set to null

  private static final Logger LOGGER = RobotLogManager.getMainLogger(CargoMech.class.getName());

  // Actuators
  private CargoMechClaw claw;
  private CargoMechWrist wrist; // stores desired height

  // State
  private CargoMechWristState armState;
  private static boolean onManualControl = true;

  private static final int TALON_SENSOR_ID = 0;
  private static final int TALON_PID_SLOT_ID = 0;

  public enum CargoMechWrist {

    // height values measured empirically
    CARGO_BIN(RobotMap.CARGO_MECH_CARGO_BIN), 
    LOW_ROCKET(RobotMap.CARGO_MECH_LOW_ROCKET),
    CARGO_SHIP(RobotMap.CARGO_MECH_CARGO_SHIP), 
    SAFE_TURRET(RobotMap.CARGO_MECH_SAFE_TURRET);

    // Height in sensor units
    public double height;
    private static WpiTalonSrxInterface talon;

    private CargoMechWrist(double heightProportion) {
      height = heightTicksFromProportion(heightProportion);
    }

    private static void initialize() {
      if (RobotMap.HAS_CARGO_MECHANISM) {
        talon = TalonProxy.create(RobotMap.CARGO_MECH_WRIST_MOTOR_CHANNEL);
        talon.setName("Telemetry", "Cargo Wrist Motor");
        talon.setInverted(RobotMap.CARGO_MECH_WRIST_MOTOR_INVERTED);
        talon.setSensorPhase(RobotMap.CARGO_MECH_WRIST_SENSOR_INVERTED);
        talon.selectProfileSlot(TALON_PID_SLOT_ID, TALON_SENSOR_ID);
        talon.config_kP(TALON_PID_SLOT_ID, RobotMap.CARGO_MECH_WRIST_P, RobotMap.TALON_TIMEOUT);
        talon.config_kI(TALON_PID_SLOT_ID, RobotMap.CARGO_MECH_WRIST_I, RobotMap.TALON_TIMEOUT);
        talon.config_kD(TALON_PID_SLOT_ID, RobotMap.CARGO_MECH_WRIST_D, RobotMap.TALON_TIMEOUT);
        talon.config_kF(TALON_PID_SLOT_ID, RobotMap.CARGO_MECH_WRIST_F, RobotMap.TALON_TIMEOUT);
        // talon.configForwardSoftLimitThreshold(
        //     RobotMap.CARGO_WRIST_UP_LIMIT_TICKS, RobotMap.TALON_TIMEOUT);
        // talon.configForwardSoftLimitEnable(true, RobotMap.TALON_TIMEOUT);
        // talon.configReverseSoftLimitThreshold(
        //     RobotMap.CARGO_WRIST_DOWN_LIMIT_TICKS, RobotMap.TALON_TIMEOUT);
       // talon.configReverseSoftLimitEnable(true, RobotMap.TALON_TIMEOUT);
        // talon.configForwardSoftLimitEnable(false, RobotMap.TALON_TIMEOUT);
        // talon.configReverseSoftLimitEnable(false, RobotMap.TALON_TIMEOUT);
        talon.configAllowableClosedloopError(TALON_PID_SLOT_ID,
            RobotMap.CARGO_MECH_WRIST_ALLOWABLE_ERROR_TICKS, RobotMap.TALON_TIMEOUT);
      } else {
        talon = null;
      }
    }

    // 0.0 is the bottom, 1.0 is the top (proportion)
    // takes proportion and converts it to ticks
    private static double heightTicksFromProportion(double proportion) {
      return ((proportion) * RobotMap.CARGO_MECH_WRIST_TOP_TICKS
          + (1.0 - proportion) * RobotMap.CARGO_MECH_WRIST_BOTTOM_TICKS);
    }

    private static void override(double speed) {
      if (!RobotMap.useSimulator && RobotMap.HAS_CARGO_MECHANISM) {
        onManualControl = true;
        talon.set(ControlMode.PercentOutput, speed);
        LOGGER.error("Manual override cargo mech arm Speed: {}, Channel: {}, talon speed = {}, Control mode: {}", speed, talon.getDeviceID(), talon.getMotorOutputPercent(), talon.getControlMode());
      }
    }

    public static int cargoMechWristTickValueIn(){
      return talon.getSensorCollection().getAnalogIn();
    }

    public static int cargoMechWristTickValueInRaw(){
      return talon.getSensorCollection().getAnalogInRaw();
    }

    /**
     * Moves the arm based on the requested command.
     */
    private void actuate() {
      LOGGER.debug("Actuating cargo mechanism arm: {}", name());
      if (!RobotMap.useSimulator && RobotMap.HAS_CARGO_MECHANISM) {
        talon.set(ControlMode.Position, height);
      } 
    }
  }

  public enum CargoMechWristState {
    CARGO_BIN, 
    MOVING_DOWN_TO_CARGO_BIN, 
    MOVING_UP_TO_LOW_ROCKET, 
    LOW_ROCKET, 
    MOVING_DOWN_TO_LOW_ROCKET,
    MOVING_UP_TO_CARGO_SHIP, 
    CARGO_SHIP, 
    UNKNOWN;

    private static CargoMechWristState previousState = UNKNOWN;
    private static double simulatedReading = 0.0;
    private static double height = 0.0;

    private static CargoMechWristState read() {
      height = simulatedReading;
      if (!RobotMap.useSimulator && RobotMap.HAS_CARGO_MECHANISM) {
        height = CargoMechWrist.talon.getSelectedSensorPosition(TALON_SENSOR_ID);
      }
      height *= (RobotMap.CARGO_MECH_WRIST_SENSOR_INVERTED) ? -1.0 : 1.0;

      CargoMechWristState state;
      if (height >= (CargoMechWrist.CARGO_BIN.height 
            - RobotMap.CARGO_MECH_WRIST_ALLOWABLE_ERROR_TICKS)
          && height <= (CargoMechWrist.CARGO_BIN.height 
            + RobotMap.CARGO_MECH_WRIST_ALLOWABLE_ERROR_TICKS)) {
        state = CARGO_BIN;
      } else if (height > (CargoMechWrist.CARGO_BIN.height 
            + RobotMap.CARGO_MECH_WRIST_ALLOWABLE_ERROR_TICKS)
          && height < (CargoMechWrist.LOW_ROCKET.height 
            - RobotMap.CARGO_MECH_WRIST_ALLOWABLE_ERROR_TICKS)) {
        if (previousState == CARGO_BIN || previousState == MOVING_UP_TO_LOW_ROCKET) {
          state = MOVING_UP_TO_LOW_ROCKET;
        } else {
          state = MOVING_DOWN_TO_CARGO_BIN;
        }
      } else if (height >= (CargoMechWrist.LOW_ROCKET.height 
            - RobotMap.CARGO_MECH_WRIST_ALLOWABLE_ERROR_TICKS)
          && height <= (CargoMechWrist.LOW_ROCKET.height 
            + RobotMap.CARGO_MECH_WRIST_ALLOWABLE_ERROR_TICKS)) {
        state = LOW_ROCKET;
      } else if (height > (CargoMechWrist.LOW_ROCKET.height 
            + RobotMap.CARGO_MECH_WRIST_ALLOWABLE_ERROR_TICKS)
          && height < (CargoMechWrist.CARGO_SHIP.height 
            - RobotMap.CARGO_MECH_WRIST_ALLOWABLE_ERROR_TICKS)) {
        if (previousState == LOW_ROCKET || previousState == MOVING_UP_TO_CARGO_SHIP) {
          state = MOVING_UP_TO_CARGO_SHIP;
        } else {
          state = MOVING_DOWN_TO_LOW_ROCKET;
        }
      } else if (height >= (CargoMechWrist.CARGO_SHIP.height 
            - RobotMap.CARGO_MECH_WRIST_ALLOWABLE_ERROR_TICKS)
          && height <= (CargoMechWrist.CARGO_SHIP.height 
            + RobotMap.CARGO_MECH_WRIST_ALLOWABLE_ERROR_TICKS)) {
        state = CARGO_SHIP;
      } else {
        state = UNKNOWN;
      }
      LOGGER.debug("Current state: {} at height in ticks {}", state.name(), height);
      previousState = state;
      return state;
    }

  }

  /**
   * Forward means the roller is spinning inwards, essentially pulling balls in.
   * Reverse means the roller is spinning outwards not letting the ball in.
   * 
   */

  public enum CargoMechClaw {
    FORWARD, REVERSE, STOP;

    private static Spark motorLeader;
    private static Spark motorFollower;

    private static void initialize() {
      // Create the roller object. No sensors
      LOGGER.trace("Initializing Claw");
      if (RobotMap.HAS_CARGO_MECHANISM) {
        motorLeader = new Spark(RobotMap.CARGO_MECH_CLAW_LEFT_MOTOR_CHANNEL);
        motorLeader.setInverted(RobotMap.CARGO_MECH_CLAW_LEFT_MOTOR_INVERTED);
        
        motorFollower = new Spark(RobotMap.CARGO_MECH_CLAW_RIGHT_MOTOR_CHANNEL);
        motorFollower.setInverted(RobotMap.CARGO_MECH_CLAW_RIGHT_MOTOR_INVERTED);
        LOGGER.debug("spark channels: {}, {}, spark speed: {}, ", motorLeader.getChannel(), motorFollower.getChannel(), motorLeader.getSpeed(), motorFollower.getSpeed());
      }

    }

    /**
     * Moves the belts of the claw forward or backward based on the requested
     * command.
     */
    private void actuate() {
      // LOGGER.debug("Actuating cargo mech claw");
      // if (RobotMap.useSimulator || !RobotMap.HAS_CARGO_MECHANISM) {
      //   return;
      // }
      
      LOGGER.debug("Calling Claw Actuate state: {}", this);
      switch (this) {
        case FORWARD:
          if (RobotMap.HAS_CARGO_MECHANISM) {
            motorLeader.set(1.0);
            motorFollower.set(1.0);
            LOGGER.debug("going forward");
          }
          break;

        case REVERSE:
          if (RobotMap.HAS_CARGO_MECHANISM) {
            motorLeader.set(-1.0);
            motorFollower.set(-1.0);
            LOGGER.debug("going backward");
          }
          break;

        case STOP:
        default:
          if (RobotMap.HAS_CARGO_MECHANISM) {
            motorLeader.set(0.0);
            motorFollower.set(0.0);
            LOGGER.debug("stopping");
          }
      }
    }

  }

  /**
   * Returns a singleton instance of the telemery builder.
   * 
   * @return TelemetryBuilder the telemetry builder instance
   */
  public static CargoMech getInstance() {
    if (instance == null) {
      instance = new CargoMech();
    }
    return instance;
  }

  private CargoMech() {
    super("Telemetry", "CargoMech");
    // Initialize the sensors and actuators
    CargoMechWrist.initialize();
    CargoMechClaw.initialize();

    claw = CargoMechClaw.STOP;
    wrist = CargoMechWrist.CARGO_BIN;
    armState = CargoMechWristState.read();

    initSendable(TelemetryBuilder.getInstance());
    LOGGER.trace("Created Ball Mech game piece.");
  }

  /**
   * Checks to see if the cargo mechanism arm at or above a safe distance to turn
   * the turret.
   * 
   * @return boolean true if safe to turn.
   */
  public boolean isSafeToMoveTurret() {
    if (CargoMechWristState.height >= CargoMechWrist.SAFE_TURRET.height) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Moves the claw arm up or down.
   * 
   * @param command which way to move the arm.
   */
  public void wrist(CargoMechWrist command) {
    onManualControl = false;
    wrist = command;
  }

  /**
   * Moves the claw arm up or down. The String version sets the command from the
   * Smart Dashboard.
   * 
   * @param command which way to move the arm.
   */
  public void wrist(String command) {
    wrist = CargoMechWrist.valueOf(command);
  }

  /**
   * Reads the arm state from the sensors.
   * 
   * @return the state of the arm, including if unknown or moving.
   */
  public CargoMechWristState wrist() {
    return armState;
  }

  /**
   * Sets the belt based on the given command.
   * 
   * @param command the belt command
   */
  public void claw(CargoMechClaw command) {
    claw = command;
  }

  /**
   * Sets the belt based on the given command. The String version is used for
   * setting the command from the SmartDashboard.
   * 
   * @param command the belt command
   */
  public void claw(String command) {
    claw = CargoMechClaw.valueOf(command);
  }

  /**
   * Returns the current belt command. There is no external sensor on this motor.
   */
  public CargoMechClaw claw() {
    return claw;
  }

  public void overrideArm(double speed) {
    CargoMechWrist.override(speed);
  }

  /**
   * Tries to handle commands and update state.
   */
  public void periodic() { // In progress
    // Take Actions
    //LOGGER.debug("Mech Periodic Called");
    if (!onManualControl) {
      claw.actuate();
      wrist.actuate();
    }
    // TODO onManualControl = false;

    // Update state
    armState = CargoMechWristState.read();
  }

  static void simulatedSensorData(double reading) {
    CargoMechWristState.simulatedReading = reading;
  }

  @Override
  public void initSendable(SendableBuilder builder) {
    builder.addStringProperty("CargoMechClaw", claw::name, (command) -> claw(command));
    builder.addStringProperty("CargoMechArm", wrist::name, (command) -> wrist(command));
    builder.addStringProperty("CargoMechArmState", armState::name, null);
    if (RobotMap.HAS_CARGO_MECHANISM) {
      CargoMechClaw.motorLeader.initSendable(builder);
      CargoMechWrist.talon.initSendable(builder);
    }
  }
}