package frc.robot.tuning;

import frc.robot.RobotMap;
import frc.robot.drive.Drive;
import frc.robot.logging.RobotLogManager;

import org.apache.logging.log4j.Logger;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AdvancedTuner implements Tuner {

  private static final Logger LOGGER
      = RobotLogManager.getMainLogger(TuneController.class.getName());

  Drive drive;

  AdvancedTuner() {
    drive = Drive.getInstance();
  }

    public void init() {
      SmartDashboard.putNumber("Speed", 0);
      
      SmartDashboard.putNumber("Left Current", 0);
      SmartDashboard.putNumber("Left Distance", 0);
      SmartDashboard.putNumber("Left Speed", 0);
      SmartDashboard.putNumber("Left Temperature", 0);
      SmartDashboard.putNumber("Left CPR", 0);
      SmartDashboard.putBoolean("Using Left", false);

      SmartDashboard.putNumber("Right Current", 0);
      SmartDashboard.putNumber("Right Distance", 0);
      SmartDashboard.putNumber("Right Speed", 0);
      SmartDashboard.putNumber("Right Temperature", 0);
      SmartDashboard.putNumber("Right CPR", 0);
      SmartDashboard.putBoolean("Using Right", false);
      drive.zero();
    }

    public void periodic() {
      double speed = SmartDashboard.getNumber("Speed", 0);
      boolean left = SmartDashboard.getBoolean("Using Left", false);
      boolean right = SmartDashboard.getBoolean("Using Right", false);
      
      drive.tankDrive(left ? speed: 0, right ? speed: 0);

      SmartDashboard.putNumber("Left Current", drive.getLeftCurrent());
      SmartDashboard.putNumber("Left Distance", drive.getLeftDistance());
      SmartDashboard.putNumber("Left Speed", drive.getLeftVelocity());
      SmartDashboard.putNumber("Left Temperature", drive.getLeftTemperature());
      SmartDashboard.putNumber("Left CPR", drive.getLeftCPR());

      SmartDashboard.putNumber("Right Current", drive.getRightCurrent());
      SmartDashboard.putNumber("Right Distance", -drive.getRightDistance());
      SmartDashboard.putNumber("Right Speed", -drive.getRightVelocity());
      SmartDashboard.putNumber("Right Temperature", drive.getRightTemperature());
      SmartDashboard.putNumber("Right CPR", drive.getRightCPR());
    }
}