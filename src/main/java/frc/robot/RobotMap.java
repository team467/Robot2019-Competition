package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotMap {

  // Global robot constants
  public static RobotId robotId;

  public static final int ITERATION_TIME_MS = 20;

  // 0 is non-blocking (i.e. it doesn't wait for a response before going to the
  // next statement)
  public static final int TALON_TIMEOUT = 0;

  public static final int PID_SLOT_DRIVE = 0;
  public static final int PID_SLOT_TURN = 1;

  // Sensors
  public static boolean HAS_GYRO = true;

  // Turret angle offsets
  public static final double ON_TARGET = 1.0;
  public static final double ANGLE_OFFSET_LEVEL_ONE = 5.0;
  public static final double ANGLE_OFFSET_LEVEL_TWO = 10.0;
  public static final double ANGLE_OFFSET_LEVEL_THREE = 15.0;
  public static final double ANGLE_OFFSET_LEVEL_FOUR = 20.0;
  public static final int PID_SLOT_TURRET = 0; // Slot will need to be determined and set

  // Steering motor ids
  public static boolean HAS_WHEELS;
  public static int DRIVEMOTOR_NUM;
  public static int AUTONOMOUS_DRIVE_TIMEOUT_MS = 200;
  public static int AUTONOMOUS_TURN_TIMEOUT_MS = 300;

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

  public static double CLOSED_LOOP_RAMP_RATE;
  public static double OPEN_LOOP_RAMP_RATE;

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

  public static boolean useSimulator = false;
  public static final double MIN_DRIVE_SPEED = 0.1;

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
   * Used to ensure that all Talon SRX outputs are relative to a fixed value. If
   * the available voltage is below the nominal and a value about that is
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
    ROBOT_2020, KITBOT

  }

  /**
   * Initialize robot map.
   * 
   * @param id the robot identifier.
   */
  public static void init(RobotId id) {

    robotId = id;
    useSimulator = false;

    switch (id) {

    case ROBOT_2020:
      HAS_WHEELS = true;
      DRIVEMOTOR_NUM = 4;
      WHEEL_CIRCUMFERENCE = 18.50;

      CONTROLS_INVERTED_FB = false;
      CONTROLS_INVERTED_TURN = false;

      USE_VELOCITY_SPEED_CONTROL_FOR_TELOP = true;
      VELOCITY_MULTIPLIER_RIGHT = 5700;
      VELOCITY_MULTIPLIER_LEFT = 5700;

      NORMAL_DRIVE_SPEED_MULTIPLIER = 0.8;
      SLOW_DRIVE_SPEED_MULTIPLIER = 0.6;

      NORMAL_VELOCITY_SPEED_MULTIPLIER = 0.9;
      SLOW_VELOCITY_SPEED_MULTIPLIER = 0.7;

      LEFT_LEAD_CHANNEL = 1;
      LEFT_FOLLOWER_1_CHANNEL = 2;
      LEFT_DRIVE_SENSOR_IS_INVERTED = true;
      LEFT_DRIVE_MOTOR_IS_INVERTED = false;

      RIGHT_LEAD_CHANNEL = 3;
      RIGHT_FOLLOWER_1_CHANNEL = 4;
      RIGHT_DRIVE_SENSOR_IS_INVERTED = true;
      RIGHT_DRIVE_MOTOR_IS_INVERTED = false;

      // Linear PIDS
      LEFT_DRIVE_PID_P = 0.0002;
      LEFT_DRIVE_PID_I = 0.0;
      LEFT_DRIVE_PID_D = 0.0;
      LEFT_DRIVE_PID_F = 0.0;

      RIGHT_DRIVE_PID_P = 0.0002;
      RIGHT_DRIVE_PID_I = 0.0;
      RIGHT_DRIVE_PID_D = 0.0;
      RIGHT_DRIVE_PID_F = 0.0;

      // Turn PIDs
      LEFT_TURN_PID_P = 0.00025;
      LEFT_TURN_PID_I = 0.0;
      LEFT_TURN_PID_D = 0.0;
      LEFT_TURN_PID_F = 0.0;

      RIGHT_TURN_PID_P = 0.00025;
      RIGHT_TURN_PID_I = 0.0;
      RIGHT_TURN_PID_D = 0.0;
      RIGHT_TURN_PID_F = 0.0;

      CLOSED_LOOP_RAMP_RATE = 0.5;
      OPEN_LOOP_RAMP_RATE = 0.0;

      FORWARD_CAMERA_INDEX = 0;
      BACKWARD_CAMERA_INDEX = 2;
      HAS_CAMERA = true;
      AUTO_CAMERA = true;

      // Game Pieces
      HAS_SHOOTER = true;
      HAS_INDEXER = true;
      HAS_INTAKE = true;
      HAS_CLIMBER = true;

      // Shooter
      // TODO:change the values of these later
      SHOOTER_FOLLOWER = false;
      SHOOTER_MOTOR_CHANNEL = 5;
      SHOOTER_MOTOR_INVERTED = true;
      SHOOTER_MOTOR_FOLLOWER_CHANNEL = 6;
      SHOOTER_MOTOR_FOLLOWER_INVERTED = true;
      SHOOTER_SMART_SHOT = true;

      SHOOTER_SENSOR_INVERTED = true;

      TRIGGER_MOTOR_CHANNEL = 6;
      TRIGGER_MOTOR_INVERTED = false;


      SHOOTER_P = 2;
      SHOOTER_I = 0.0;
      SHOOTER_D = 0.0;
      SHOOTER_F = 0.0;

      VELOCITY_MULTIPLIER_SHOOTER = 8200;
      SHOOTER_SPEED_TOLERANCE = 0.25;
      MANUAL_MODE_SHOOTER_SPEED = 1.0; //TODO determine the speed
      

      //Intake
      // TODO: change the values of these later.
      ARM_MOTOR_CHANNEL = 0;
      ARM_MOTOR_INVERTED = false;
      ROLLER_MOTOR_CHANNEL = 1;
      ROLLER_MOTOR_INVERTED = false;

      //Indexer
      // TODO: change the values of these later. 
      FIRST_MAGAZINE_FEED_MOTOR_CHANNEL = 1;
      FIRST_MAGAZINE_FEED_MOTOR_INVERTED = false;
      SECOND_MAGAZINE_FEED_MOTOR_CHANNEL = 1;
      SECOND_MAGAZINE_FEED_MOTOR_INVERTED = false;
      INDEX_FOLLOWER_MOTOR = false;
      
      INDEXER_MOVE_TIMER = 0.2;

      //Climber
      CLIMBER_MOTOR_INVERTED = false;
      CLIMBER_SENSOR = false;
      HAS_CLIMBFOLLOWER = false;
      CLIMB_MOTER_LEADER = 0;
      CLIMB_MOTER_FOLLOWER = 1;

      CLIMBER_P = 2;
      CLIMBER_I = 0.0;
      CLIMBER_D = 0.0;
      CLIMBER_F = 0.0;

      VELOCITY_MULTIPLIER_CLIMBER = 8200;
      CLIMBER_PID_SLOT = 0;

      HAS_CLIMB_TOP_SENSOR = true;
      HAS_CLIMB_BOTTOM_SENSOR = true;
      CLIMB_TOP_SENSOR_INVERTED = false;
      CLIMB_BOTTOM_SENSOR_INVERTED = false;
      CLIMB_TOP_SENSOR_CHANNEL = 1;
      CLIMB_BOTTOM_SENSOR_CHANNEL = 2;
      HAS_CLIMB_POT = true;
      CLIMB_POT_CHANNEL = 1;

      HAS_CLIMB_TILT_SWITCH = true;
      CLIMB_TILT_SWITCH_INVERTED = false;
      CLIMB_TILT_SWITCH_CHANNEL = 3;

      HAS_CLIMBLOCK = false;
      CLIMB_LOCK_CHANNEL = 0;
      CLIMB_LOCK_INVERTED = false;
      break;

    case KITBOT:
    default:
      HAS_WHEELS = true;
      DRIVEMOTOR_NUM = 4;
      WHEEL_CIRCUMFERENCE = 18.50;

      // CONTROLS_INVERTED_FB = false;
      // CONTROLS_INVERTED_TURN = false;

      USE_VELOCITY_SPEED_CONTROL_FOR_TELOP = true;
      VELOCITY_MULTIPLIER_RIGHT = 5700;
      VELOCITY_MULTIPLIER_LEFT = 5700;

      NORMAL_DRIVE_SPEED_MULTIPLIER = 0.8;
      SLOW_DRIVE_SPEED_MULTIPLIER = 0.6;

      NORMAL_VELOCITY_SPEED_MULTIPLIER = 0.9;
      SLOW_VELOCITY_SPEED_MULTIPLIER = 0.7;

      LEFT_LEAD_CHANNEL = 4;
      LEFT_FOLLOWER_1_CHANNEL = 6;
      LEFT_DRIVE_SENSOR_IS_INVERTED = true;
      LEFT_DRIVE_MOTOR_IS_INVERTED = false;

      RIGHT_LEAD_CHANNEL = 3;
      RIGHT_FOLLOWER_1_CHANNEL = 4;
      RIGHT_DRIVE_SENSOR_IS_INVERTED = true;
      RIGHT_DRIVE_MOTOR_IS_INVERTED = false;

      // Linear PIDS
      LEFT_DRIVE_PID_P = 0.00015;
      LEFT_DRIVE_PID_I = 0.0;
      LEFT_DRIVE_PID_D = 0.0;
      LEFT_DRIVE_PID_F = 0.0;

      RIGHT_DRIVE_PID_P = 0.00015;
      RIGHT_DRIVE_PID_I = 0.0;
      RIGHT_DRIVE_PID_D = 0.0;
      RIGHT_DRIVE_PID_F = 0.0;

      // Turn PIDs
      LEFT_TURN_PID_P = 0.00015;
      LEFT_TURN_PID_I = 0.0;
      LEFT_TURN_PID_D = 0.0;
      LEFT_TURN_PID_F = 0.0;

      RIGHT_TURN_PID_P = 0.00015;
      RIGHT_TURN_PID_I = 0.0;
      RIGHT_TURN_PID_D = 0.0;
      RIGHT_TURN_PID_F = 0.0;

      CLOSED_LOOP_RAMP_RATE = 0.5;
      OPEN_LOOP_RAMP_RATE = 0.0;

      // Cameras
      FORWARD_CAMERA_INDEX = 0;
      BACKWARD_CAMERA_INDEX = 1;
      HAS_CAMERA = false;
      AUTO_CAMERA = false;

      // Game Pieces
      HAS_SHOOTER = false;
      HAS_INDEXER = true;
      HAS_INTAKE = false;
      HAS_CLIMBER = false;

      IS_IN_TEST_MODE = true;

      // Shooter
      // TODO:change the values of these later
      SHOOTER_FOLLOWER = false;
      SHOOTER_MOTOR_CHANNEL = 5;
      SHOOTER_MOTOR_INVERTED = false;
      SHOOTER_MOTOR_FOLLOWER_INVERTED = false;
      SHOOTER_SMART_SHOT = true;

      TRIGGER_MOTOR_CHANNEL = 6;
      HAS_TRIGGER = true;
      HAS_SHOOTER_TRIGGER = true;

      HAS_SHOOTER_HOOD = true;
      HOOD_INVERTED = false;
      HOOD_PWM_PORT = 0;
      HOOD_FOLLOWER = true;
      HOOD_FOLLOWER_PWM_PORT = 1;
      HOOD_FOLLOWER_INVERTED = true;
      HOOD_MAX_ANGLE = 165;


      HAS_SHOOTER_LEDS = false;
      SHOOTER_LED_AMOUNT = 12;
      SHOOTER_LED_CHANNEL = 9;
      SHOOTER_DOUBLESIDE_LED = true;

      SHOOTER_SENSOR_INVERTED = true;

      SHOOTER_P = 2;
      SHOOTER_I = 0.0;
      SHOOTER_D = 0.0;
      SHOOTER_F = 0.0;

      VELOCITY_MULTIPLIER_SHOOTER = 8200;
      SHOOTER_SPEED_TOLERANCE = 0.25;
      MANUAL_MODE_SHOOTER_SPEED = 1.0;

      //Climber
      CLIMBER_MOTOR_INVERTED = false;
      CLIMBER_SENSOR = false;
      HAS_CLIMBFOLLOWER = false;
      CLIMB_MOTER_LEADER = 1;
      CLIMB_MOTER_FOLLOWER = 2;

      CLIMBER_P = 2;
      CLIMBER_I = 0.0;
      CLIMBER_D = 0.0;
      CLIMBER_F = 0.0;

      VELOCITY_MULTIPLIER_CLIMBER = 8200;
      CLIMBER_PID_SLOT = 0;

      HAS_CLIMB_TOP_SENSOR = false;
      HAS_CLIMB_BOTTOM_SENSOR = false;
      CLIMB_TOP_SENSOR_INVERTED = false;
      CLIMB_BOTTOM_SENSOR_INVERTED = false;
      CLIMB_TOP_SENSOR_CHANNEL = 1;
      CLIMB_BOTTOM_SENSOR_CHANNEL = 2;
      HAS_CLIMB_POT = true;
      CLIMB_POT_CHANNEL = 1;

      HAS_CLIMB_TILT_SWITCH = false;
      CLIMB_TILT_SWITCH_INVERTED = false;
      CLIMB_TILT_SWITCH_CHANNEL = 3;

      HAS_CLIMBLOCK = false;
      CLIMB_LOCK_CHANNEL = 0;
      CLIMB_LOCK_INVERTED = false;

      //Indexer 

      FIRST_MAGAZINE_FEED_MOTOR_CHANNEL = 6;
      FIRST_MAGAZINE_FEED_MOTOR_INVERTED = false;
      SECOND_MAGAZINE_FEED_MOTOR_CHANNEL = 5;
      SECOND_MAGAZINE_FEED_MOTOR_INVERTED = false;
      INDEX_FOLLOWER_MOTOR = true;
      
      INDEXER_MOVE_TIMER = 0.2;
      INDEXER_TOF_THRESHOLD = 100;

      break;
    }

    // These calculations can be made after the robot-specific constants are set.
    POSITION_ALLOWED_ERROR = ALLOWED_ERROR_INCHES / RobotMap.WHEEL_CIRCUMFERENCE;

    // This is in encoder ticks
    POSITION_ALLOWABLE_CLOSED_LOOP_ERROR = (int) (POSITION_ALLOWED_ERROR * WHEEL_ENCODER_CODES_PER_REVOLUTION * 0.95);
  }

  /**
   * Overrides normal values with values required for simulator.
   */
  static void setSimulator() {

    useSimulator = true;

    // Linear PIDS
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

  // Speed Controlsclimer
  public static double NORMAL_DRIVE_SPEED_MULTIPLIER;
  public static double SLOW_DRIVE_SPEED_MULTIPLIER;
  public static double NORMAL_VELOCITY_SPEED_MULTIPLIER;
  public static double SLOW_VELOCITY_SPEED_MULTIPLIER;
  public static boolean USE_VELOCITY_SPEED_CONTROL_FOR_TELOP = false;
  public static double VELOCITY_MULTIPLIER_LEFT;
  public static double VELOCITY_MULTIPLIER_RIGHT;
  public static boolean CONTROLS_INVERTED_FB;
  public static boolean CONTROLS_INVERTED_TURN;

  // Driver Cameras
  public static int FORWARD_CAMERA_INDEX;
  public static int BACKWARD_CAMERA_INDEX;
  public static boolean HAS_CAMERA = false;
  public static boolean AUTO_CAMERA = false;

  // Telemetry Enables
  public static boolean ENABLE_TELEMETRY = true;
  public static boolean ENABLE_DRIVER_STATION_TELEMETRY = true;
  public static int TELEMETRY_TIMER_MS = 20;

  // Game Pieces

  // climber
  public static boolean HAS_CLIMBER = false;
  public static boolean CLIMBER_MOTOR_INVERTED = false;
  public static boolean CLIMBER_SENSOR = false;

  public static int CLIMB_MOTER_LEADER;
  public static int CLIMB_MOTER_FOLLOWER;

  public static boolean HAS_CLIMBFOLLOWER = false;
  public static boolean HAS_CLIMBLOCK = false;
  public static int CLIMB_LOCK_CHANNEL;
  public static boolean CLIMB_LOCK_INVERTED = false;
  public static boolean HAS_CLIMB_TOP_SENSOR = false;
  public static boolean HAS_CLIMB_BOTTOM_SENSOR = false;
  public static boolean CLIMB_TOP_SENSOR_INVERTED = false;
  public static boolean CLIMB_BOTTOM_SENSOR_INVERTED = false;
  public static int CLIMB_TOP_SENSOR_CHANNEL;
  public static int CLIMB_BOTTOM_SENSOR_CHANNEL;
  public static boolean HAS_CLIMB_POT = false;
  public static int CLIMB_POT_CHANNEL;
  public static boolean HAS_CLIMB_TILT_SWITCH = false;
  public static boolean CLIMB_TILT_SWITCH_INVERTED = false;
  public static int CLIMB_TILT_SWITCH_CHANNEL;
  public static int CLIMBER_PID_SLOT;
  public static double CLIMBER_P;
  public static double CLIMBER_I;
  public static double CLIMBER_D;
  public static double CLIMBER_F;
  public static double VELOCITY_MULTIPLIER_CLIMBER;
  
  // Shooter
  public static boolean HAS_SHOOTER = false;
  public static boolean HAS_TRIGGER = false;
  public static boolean HAS_SHOOTERLEDS = false;
  public static boolean SHOOTER_FOLLOWER;
  public static int SHOOTER_MOTOR_CHANNEL;
  public static boolean SHOOTER_MOTOR_INVERTED;
  public static int SHOOTER_MOTOR_FOLLOWER_CHANNEL;
  public static boolean SHOOTER_MOTOR_FOLLOWER_INVERTED;
  public static boolean SHOOTER_SMART_SHOT;

  public static boolean HAS_SHOOTER_TRIGGER = false;
  public static int TRIGGER_MOTOR_CHANNEL;
  public static boolean TRIGGER_MOTOR_INVERTED = false;

  public static boolean HAS_SHOOTER_HOOD = false;
  public static boolean HOOD_INVERTED = false;
  public static int HOOD_PWM_PORT;
  public static boolean HOOD_FOLLOWER = false;
  public static int HOOD_FOLLOWER_PWM_PORT;
  public static boolean HOOD_FOLLOWER_INVERTED = false;
  public static int HOOD_MAX_ANGLE;

  public static boolean SHOOTER_SENSOR_INVERTED;
  public static double SHOOTER_P;
  public static double SHOOTER_I;
  public static double SHOOTER_D;
  public static double SHOOTER_F;
  public static double VELOCITY_MULTIPLIER_SHOOTER;
  public static double SHOOTER_SPEED_TOLERANCE;
  public static int SHOOTER_PID_SLOT_DRIVE;
  public static double MANUAL_MODE_SHOOTER_SPEED;

  public static boolean HAS_SHOOTER_LEDS = false;
  public static int SHOOTER_LED_AMOUNT;
  public static int SHOOTER_LED_CHANNEL;
  public static boolean SHOOTER_DOUBLESIDE_LED = false;

  // Intake
  public static boolean HAS_INTAKE = false;
  public static int ARM_MOTOR_CHANNEL;
  public static boolean ARM_MOTOR_INVERTED = false;
  public static int ROLLER_MOTOR_CHANNEL;
  public static boolean ROLLER_MOTOR_INVERTED;

  //INDEXER 
  public static boolean HAS_INDEXER = false;
  public static boolean INDEXER_FOLLOWER = false;
  public static int FIRST_MAGAZINE_FEED_MOTOR_CHANNEL;
  public static boolean FIRST_MAGAZINE_FEED_MOTOR_INVERTED = false;
  public static int SECOND_MAGAZINE_FEED_MOTOR_CHANNEL;
  public static boolean SECOND_MAGAZINE_FEED_MOTOR_INVERTED = false;
  public static boolean INDEX_FOLLOWER_MOTOR = false;
  public static boolean INDEXER_SENSOR_INVERTED = false;
  public static boolean INDEXER_MOTOR_INVERTED = false;
  public static boolean HAS_INDEXER_TOF_SENSORS = false;


  public static boolean IS_IN_TEST_MODE = false;

  public static double INDEXER_MOVE_TIMER;
  //distance threshold in mm for detecting a ball
  public static double INDEXER_TOF_THRESHOLD;
}