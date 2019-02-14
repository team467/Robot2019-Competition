package frc.robot.gamepieces;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import frc.robot.RobotMap;
import frc.robot.logging.RobotLogManager;
import frc.robot.logging.TelemetryBuilder;

import org.apache.logging.log4j.Logger;

public class CargoMech extends GamePieceBase implements GamePiece {

  private static CargoMech instance  = null; // set to null

  private static final Logger LOGGER = RobotLogManager.getMainLogger(CargoMech.class.getName());

  // Actuators
  private CargoMechClaw claw;
  private CargoMechArm arm; // stores desired height

  // State
  private CargoMechArmState armState;

  public enum CargoMechArm {

    // height values measured empirically
    CARGO_BIN(RobotMap.CARGO_MECH_CARGO_BIN), 
    LOW_ROCKET(RobotMap.CARGO_MECH_LOW_ROCKET),
    CARGO_SHIP(RobotMap.CARGO_MECH_CARGO_SHIP);

    // Height in sensor units
    public double height;
    private static PIDController arm;
    private static Spark motor;
  
    private CargoMechArm(double heightProportion) {
      height = heightTicksFromProportion(heightProportion);
    }

    private static void initialize() {
      motor = new Spark(RobotMap.CARGO_MECH_ARM_MOTOR_CHANNEL);
      motor.setInverted(RobotMap.CARGO_MECH_ARM_MOTOR_INVERTED);
      motor.setName("Telemetry", "CargoMechArmMotor");
      arm = new PIDController(
        RobotMap.CARGO_MECH_ARM_P,
        RobotMap.CARGO_MECH_ARM_I,
        RobotMap.CARGO_MECH_ARM_D,
        RobotMap.CARGO_MECH_ARM_F,
        CargoMechArmState.sensor, motor);
      arm.setInputRange(CARGO_BIN.height, CARGO_SHIP.height);
      arm.setOutputRange(-1.0, 1.0);
      arm.setAbsoluteTolerance(RobotMap.CARGO_MECH_ARM_ALLOWABLE_ERROR_TICKS);
      arm.setContinuous(false);
      arm.enable();
    }

    // 0.0 is the bottom, 1.0 is the top (proportion)
    // takes proportion and converts it to ticks
    private static double heightTicksFromProportion(double proportion) {
      return ((proportion) * RobotMap.CARGO_MECH_ARM_TOP_TICKS 
          + (1.0 - proportion) * RobotMap.CARGO_MECH_ARM_BOTTOM_TICKS);
    }

    private static void override(double speed) {
      LOGGER.debug("Manual override cargo mech arm: {}", speed);
      if (!RobotMap.useSimulator) {
        motor.set(speed);
      }
    }

    /**
     * Moves the arm based on the requested command.
     */
    private void actuate() {
      LOGGER.debug("Actuating cargo mechanism arm: {}", name());
      if (RobotMap.useSimulator || !RobotMap.HAS_CARGO_MECHANISM) {
        return;
      }
      arm.setSetpoint(height);
    }
  }

  public enum CargoMechArmState {
    CARGO_BIN, 
    MOVING_DOWN_TO_CARGO_BIN, 
    MOVING_UP_TO_LOW_ROCKET, 
    LOW_ROCKET, 
    MOVING_DOWN_TO_LOW_ROCKET,
    MOVING_UP_TO_CARGO_SHIP, 
    CARGO_SHIP, 
    UNKNOWN;

    private static AnalogPotentiometer sensor = null;
    private static CargoMechArmState previousState = UNKNOWN;
    private static double simulatedReading = 0.0;

    private static void initialize() {
      // Config arm sensors
      sensor = new AnalogPotentiometer(RobotMap.CARGO_MECH_ARM_SENSOR_CHANNEL);
      sensor.setName("Telemetry", "CargoMechArmSensor");
    }

    private static CargoMechArmState read() {
      double value = simulatedReading;
      if (!RobotMap.useSimulator) {
        value = sensor.get();
      }
      value *= (RobotMap.CARGO_MECH_ARM_SENSOR_INVERTED) ? -1.0 : 1.0;

      CargoMechArmState state;
      if (value >= (CargoMechArm.CARGO_BIN.height 
          - RobotMap.CARGO_MECH_ARM_ALLOWABLE_ERROR_TICKS)
          && value <= (CargoMechArm.CARGO_BIN.height 
          + RobotMap.CARGO_MECH_ARM_ALLOWABLE_ERROR_TICKS)) {
        state = CARGO_BIN;
      } else if (value > (CargoMechArm.CARGO_BIN.height 
          + RobotMap.CARGO_MECH_ARM_ALLOWABLE_ERROR_TICKS) 
          && value < (CargoMechArm.LOW_ROCKET.height 
          - RobotMap.CARGO_MECH_ARM_ALLOWABLE_ERROR_TICKS)) {
        if (previousState == CARGO_BIN || previousState == MOVING_UP_TO_LOW_ROCKET) {
          state = MOVING_UP_TO_LOW_ROCKET;
        } else {
          state = MOVING_DOWN_TO_CARGO_BIN;
        }
      } else if (
          value >= (CargoMechArm.LOW_ROCKET.height 
          - RobotMap.CARGO_MECH_ARM_ALLOWABLE_ERROR_TICKS)
          && value <= (CargoMechArm.LOW_ROCKET.height 
          + RobotMap.CARGO_MECH_ARM_ALLOWABLE_ERROR_TICKS)) {
        state = LOW_ROCKET;
      } else if (value > (CargoMechArm.LOW_ROCKET.height 
          + RobotMap.CARGO_MECH_ARM_ALLOWABLE_ERROR_TICKS)
          && value < (CargoMechArm.CARGO_SHIP.height 
          - RobotMap.CARGO_MECH_ARM_ALLOWABLE_ERROR_TICKS)) {
        if (previousState == LOW_ROCKET || previousState == MOVING_UP_TO_CARGO_SHIP) {
          state = MOVING_UP_TO_CARGO_SHIP;
        } else {
          state = MOVING_DOWN_TO_LOW_ROCKET;
        }
      } else if (
          value >= (CargoMechArm.CARGO_SHIP.height 
          - RobotMap.CARGO_MECH_ARM_ALLOWABLE_ERROR_TICKS)
          && value <= (CargoMechArm.CARGO_SHIP.height 
          + RobotMap.CARGO_MECH_ARM_ALLOWABLE_ERROR_TICKS)) {
        state = CARGO_SHIP;
      } else {
        state = UNKNOWN;
      }
      LOGGER.debug("Current state: {}", state.name());
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
    FORWARD,
    REVERSE,
    STOP;

    private static Spark motor;

    private static void initialize() {
      // Create the roller object. No sensors
      LOGGER.trace("Initializing Claw");
      motor = new Spark(RobotMap.CARGO_MECH_CLAW_MOTOR_CHANNEL);
      motor.setInverted(RobotMap.CARGO_MECH_MOTOR_INVERTED);
    }

    /**
     * Moves the belts of the claw forward or backward based on the requested command.
     */
    private void actuate() {
      LOGGER.debug("Actuating cargo mech claw: {}", name());
      if (RobotMap.useSimulator) {
        return;
      }
      switch (this) {

        case FORWARD:
          motor.set(1.0);
          break;
        
        case REVERSE:
          motor.set(-1.0);
          break;

        case STOP:
        default:
          motor.set(0.0);
      }
      
    }
      
  }

  /**
   * Returns a singleton instance of the telemery builder.
   * 
   * @return TelemetryBuilder the telemetry builder instance
   */
  public static CargoMech getInstance()  {
    if (instance == null) {
      instance = new CargoMech();
    }
    return instance;
  }

  private CargoMech() {
    super("Telemetry" , "CargoMech");

    // Initialize the sensors and actuators
    CargoMechArmState.initialize();
    CargoMechArm.initialize();
    CargoMechClaw.initialize();

    claw = CargoMechClaw.STOP;
    arm = CargoMechArm.CARGO_BIN;
    armState = CargoMechArmState.read();

    initSendable(TelemetryBuilder.getInstance());
    LOGGER.trace("Created Ball Mech game piece.");
  }

  /**
   * Moves the claw arm up or down.
   * 
   * @param command which way to move the arm.
   */
  public void arm(CargoMechArm command) {
    arm = command;
  }

  /**
   * Moves the claw arm up or down. The String version sets the 
   * command from the Smart Dashboard.
   * 
   * @param command which way to move the arm.
   */
  public void arm(String command) {
    arm = CargoMechArm.valueOf(command);
  }

  /**
   * Reads the arm state from the sensors.
   * 
   * @return the state of the arm, including if unknown or moving.
   */
  public CargoMechArmState arm() {
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
   * Sets the belt based on the given command. The String version
   * is used for setting the command from the SmartDashboard.
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
    CargoMechArm.override(speed);
  }

  /**
   * Tries to handle commands and update state.
   */
  public void periodic() { // In progress
    // Take Actions
    if (enabled) {
      claw.actuate();
      arm.actuate();
    }

    // Update state
    armState = CargoMechArmState.read();
  }

  static void simulatedSensorData(double reading) {
    CargoMechArmState.simulatedReading = reading;
  }

  @Override
  public void initSendable(SendableBuilder builder) {
    builder.addStringProperty("CargoMechClaw", claw::name, (command) -> claw(command));
    builder.addStringProperty("CargoMechArm", arm::name, (command) -> arm(command));
    builder.addStringProperty("CargoMechArmState", armState::name, null);
    CargoMechClaw.motor.initSendable(builder);
    CargoMechArm.motor.initSendable(builder);
    CargoMechArmState.sensor.initSendable(builder);
  }
}