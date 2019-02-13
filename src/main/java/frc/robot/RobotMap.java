package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotMap {

  // Global robot constants
  public static RobotId robotId;

  public static final int ITERATION_TIME_MS = 20;

  // 0 is non-blocking (i.e. it doesn't wait for a response before going to the next statement)
  public static final int TALON_TIMEOUT = 0; 

  public static final int PID_SLOT_DRIVE = 0;
  public static final int PID_SLOT_TURN = 1;
  
  // Steering motor ids
  public static boolean HAS_WHEELS;
  public static int DRIVEMOTOR_NUM;
  public static int AUTONOMOUS_DRIVE_TIMEOUT_MS;
  public static int AUTONOMOUS_TURN_TIMEOUT_MS;


  public static int LEFT_LEAD_CHANNEL;
  public static int LEFT_FOLLOWER_1_CHANNEL;
  public static int LEFT_FOLLOWER_2_CHANNEL;
  public static boolean LEFT_DRIVE_SENSOR_IS_INVERTED;
  public static boolean LEFT_DRIVE_MOTOR_IS_INVERTED;

  public static double LEFT_TURN_PID_P;
  public static double LEFT_TURN_PID_I;
  public static double LEFT_TURN_PID_D;
  public static double LEFT_TURN_PID_F;

  public static double LEFT_DRIVE_PID_P;
  public static double LEFT_DRIVE_PID_I;
  public static double LEFT_DRIVE_PID_D;
  public static double LEFT_DRIVE_PID_F;

  public static int RIGHT_LEAD_CHANNEL;
  public static int RIGHT_FOLLOWER_1_CHANNEL;
  public static int RIGHT_FOLLOWER_2_CHANNEL;
  public static boolean RIGHT_DRIVE_SENSOR_IS_INVERTED;
  public static boolean RIGHT_DRIVE_MOTOR_IS_INVERTED;
    
  public static double RIGHT_TURN_PID_P;
  public static double RIGHT_TURN_PID_I;
  public static double RIGHT_TURN_PID_D;
  public static double RIGHT_TURN_PID_F;

  public static double RIGHT_DRIVE_PID_P;
  public static double RIGHT_DRIVE_PID_I;
  public static double RIGHT_DRIVE_PID_D;
  public static double RIGHT_DRIVE_PID_F;

  public static boolean useSpeedControllers;


  public static double ALLOWED_ERROR_INCHES = 0.5;

  public static double POSITION_ALLOWED_ERROR;

  public static int POSITION_ALLOWABLE_CLOSED_LOOP_ERROR;

  public static final double FAST_MAX_SPEED = 1.0;
  public static final double NORMAL_MAX_SPEED = 0.8;
  public static final double SLOW_MAX_SPEED = 0.5;

  public static final double NORMAL_TURN_MAX_SPEED = 1.0;
  public static final double SLOW_TURN_MAX_SPEED = 0.8;

  public static boolean useSimulator = true;
  public static final double MIN_DRIVE_SPEED = 0.1;
  public static final double CLIMB_MIN_DRIVE_SPEED = 0.3;

  // How far the sensor speeds can be and still be considered turning in place,
  // in sensor units per 100 ms
  public static final int TURN_IN_PLACE_DETECT_TOLERANCE = 150;

  // Robot Dimensions
  // TODO: Measure robot dimensions
  public static double WHEEL_BASE_LENGTH = 3.33;
  public static double WHEEL_BASE_WIDTH = 1.99; 
  public static double BUMPER_LENGTH = 3.33;
  public static double BUMPER_WIDTH = 2.92;

  /**
   * Used to ensure that all Talon SRX outputs are relative to a fixed value.
   * If the available voltage is below the nominal and a value about that is
   * requested, the output will be 100%.
   */
  public static final double NOMINAL_BATTERY_VOLTAGE = 12.0;

  // The circumference of the wheels for use in determining distance in
  // position mode
  public static double WHEEL_CIRCUMFERENCE;

  // The number of encoder ticks per one revolution of the wheel. This is used
  // for correctly determining RPM and position.
  public static final int WHEEL_ENCODER_CODES_PER_REVOLUTION = 1024;

  // Set to true to use LSM9DS1 IMU on Raspberry Pi
  // Set to false to use the local ADIS16448 IMU on the Robo Rio
  public static final boolean useRemoteImu = false;

  public enum RobotId {
    MiniBot, Robot2018, Robot2019
  }

  // Initialize robot map. 
  public static void init(RobotId id) {

    robotId = id;
    useSimulator = false;

    switch (id) {
      
      case Robot2018:
        HAS_WHEELS = true;
        DRIVEMOTOR_NUM = 4;
        WHEEL_CIRCUMFERENCE = 18.50;
        // useSpeedControllers = true;

        LEFT_LEAD_CHANNEL = 1;
        LEFT_FOLLOWER_1_CHANNEL = 2;
        LEFT_FOLLOWER_2_CHANNEL = 3;
        LEFT_DRIVE_SENSOR_IS_INVERTED = true;
        LEFT_DRIVE_MOTOR_IS_INVERTED = false;

        RIGHT_LEAD_CHANNEL = 4;
        RIGHT_FOLLOWER_1_CHANNEL = 5;
        RIGHT_FOLLOWER_2_CHANNEL = 6;
        RIGHT_DRIVE_SENSOR_IS_INVERTED = true;
        RIGHT_DRIVE_MOTOR_IS_INVERTED = false;
        
        //Linear PIDS
        LEFT_DRIVE_PID_P = 1.0;
        LEFT_DRIVE_PID_I = 0.0;
        LEFT_DRIVE_PID_D = 450.0;
        LEFT_DRIVE_PID_F = 0.0;

        RIGHT_DRIVE_PID_P = 1.0;
        RIGHT_DRIVE_PID_I = 0.0;
        RIGHT_DRIVE_PID_D = 450.0;
        RIGHT_DRIVE_PID_F = 0.0;

        // Turn PIDs
        LEFT_TURN_PID_P = 1.0;
        LEFT_TURN_PID_I = 0.0;
        LEFT_TURN_PID_D = 450.0;
        LEFT_TURN_PID_F = 0.0;

        RIGHT_TURN_PID_P = 1.0;
        RIGHT_TURN_PID_I = 0.0;
        RIGHT_TURN_PID_D = 450.0;
        RIGHT_TURN_PID_F = 0.0;
  
        AUTONOMOUS_DRIVE_TIMEOUT_MS = 200;
        AUTONOMOUS_TURN_TIMEOUT_MS = 300;
        break;


    }

    //These calculations can be made after the robot-specific constants are set. 
    POSITION_ALLOWED_ERROR = ALLOWED_ERROR_INCHES / RobotMap.WHEEL_CIRCUMFERENCE;

    // This is in encoder ticks
    POSITION_ALLOWABLE_CLOSED_LOOP_ERROR 
        = (int) (POSITION_ALLOWED_ERROR * WHEEL_ENCODER_CODES_PER_REVOLUTION * 0.95);
  }

  /**
   * Overrides normal values with values required for simulator.
   */
  static void setSimulator() {

    useSimulator = true;

    //Linear PIDS
    LEFT_DRIVE_PID_P = 0.00033;
    LEFT_DRIVE_PID_I = 0.0;
    LEFT_DRIVE_PID_D = 0.0;
    LEFT_DRIVE_PID_F = 0.0;

    RIGHT_DRIVE_PID_P = 0.00033;
    RIGHT_DRIVE_PID_I = 0.0;
    RIGHT_DRIVE_PID_D = 0.0;
    RIGHT_DRIVE_PID_F = 0.0;

    // Turn PIDs
    LEFT_TURN_PID_P = 0.00051;
    LEFT_TURN_PID_I = 0.0;
    LEFT_TURN_PID_D = 0.0;
    LEFT_TURN_PID_F = 0.0;

    RIGHT_TURN_PID_P = 0.00051;
    RIGHT_TURN_PID_I = 0.0;
    RIGHT_TURN_PID_D = 0.0;
    RIGHT_TURN_PID_F = 0.0;  
  }

  /**
   * Used to load Robot Map PID values onto the Smart Dashboard for tuning.
   * 
   * @param pidSlot 0 for drive, 1 for turn PIDs
   */
  public static void loadPidsOntoSmartDashboard(int pidSlot) {
    if (pidSlot == 0) {
      SmartDashboard.putString("DB/String 0", "10.0"); // Tune Distance
      SmartDashboard.putString("DB/String 1", String.valueOf(LEFT_DRIVE_PID_P)); // P Left
      SmartDashboard.putString("DB/String 2", String.valueOf(LEFT_DRIVE_PID_I)); // I Left
      SmartDashboard.putString("DB/String 3", String.valueOf(LEFT_DRIVE_PID_D)); // D Left
      SmartDashboard.putString("DB/String 4", String.valueOf(LEFT_DRIVE_PID_F)); // F Left
      SmartDashboard.putString("DB/String 5", "0"); // PID Slot
      SmartDashboard.putString("DB/String 6", String.valueOf(RIGHT_DRIVE_PID_P)); // P Right
      SmartDashboard.putString("DB/String 7", String.valueOf(RIGHT_DRIVE_PID_I)); // I Right
      SmartDashboard.putString("DB/String 8", String.valueOf(RIGHT_DRIVE_PID_D)); // D Right
      SmartDashboard.putString("DB/String 9", String.valueOf(RIGHT_DRIVE_PID_F)); // F Right
    } else {
      SmartDashboard.putString("DB/String 0", "90.0"); // Tune Distance
      SmartDashboard.putString("DB/String 1", String.valueOf(LEFT_TURN_PID_P)); // P Left
      SmartDashboard.putString("DB/String 2", String.valueOf(LEFT_TURN_PID_I)); // I Left
      SmartDashboard.putString("DB/String 3", String.valueOf(LEFT_TURN_PID_D)); // D Left
      SmartDashboard.putString("DB/String 4", String.valueOf(LEFT_TURN_PID_F)); // F Left
      SmartDashboard.putString("DB/String 5", "1"); // PID Slot
      SmartDashboard.putString("DB/String 6", String.valueOf(RIGHT_TURN_PID_P)); // P Right
      SmartDashboard.putString("DB/String 7", String.valueOf(RIGHT_TURN_PID_I)); // I Right
      SmartDashboard.putString("DB/String 8", String.valueOf(RIGHT_TURN_PID_D)); // D Right
      SmartDashboard.putString("DB/String 9", String.valueOf(RIGHT_TURN_PID_F)); // F Right
    }
  }


  // Game Pieces
  public static boolean HAS_HATCH_MECHANISM;
  public static int HATCH_S1_FORWARD_CHANNEL;
  public static int HATCH_S1_REVERSE_CHANNEL;
  public static int HATCH_S2_FORWARD_CHANNEL;
  public static int HATCH_S2_REVERSE_CHANNEL;
  public static int HATCH_S3_FORWARD_CHANNEL;
  public static int HATCH_S3_REVERSE_CHANNEL;

  // Cargo Intake
  public static boolean HAS_CARGO_INTAKE;
  public static int ROLLER_ARM_UP_SOLINOID_CHANNEL = 0;
  public static int ROLLER_ARM_DOWN_SOLINOID_CHANNEL = 0;
  public static int ROLLER_MOTOR_CHANNEL = 0;
  public static boolean ROLLER_MOTOR_INVERTED = false;

  // Cargo Arm
  private static boolean HAS_CARGO_MECHANISM;

  public static int CARGO_MECH_ARM_SENSOR_CHANNEL = 0;
  public static int CARGO_MECH_ARM_MOTOR_CHANNEL = 0;
  public static boolean CARGO_MECH_ARM_MOTOR_INVERTED = false;
  public static boolean CARGO_MECH_ARM_SENSOR_INVERTED = false; 
  public static double CARGO_MECH_ARM_P = 1.0;
  public static double CARGO_MECH_ARM_I = 0.0;
  public static double CARGO_MECH_ARM_D = 0.0;
  public static double CARGO_MECH_ARM_F = 0.0;

  public static int CARGO_MECH_ARM_TOP_TICKS; // TODO
  public static int CARGO_MECH_ARM_BOTTOM_TICKS; // TODO
  public static int CARGO_MECH_ARM_ALLOWABLE_ERROR_TICKS = 10;

  // Relative
  public static double CARGO_CARGO_BIN = 0; // TODO
  public static double CARGO_MECH_ROCKET_LOW = 0.3; // TODO
  public static double CARGO_MECH_CARGO_SHIP = 0.7; // TODO


  public static int CARGO_MECH_CLAW_MOTOR_CHANNEL = 0;
  public static boolean CARGO_MECH_MOTOR_INVERTED = false;

  

}