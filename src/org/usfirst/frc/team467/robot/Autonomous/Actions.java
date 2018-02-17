package org.usfirst.frc.team467.robot.Autonomous;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.usfirst.frc.team467.robot.Drive;
import org.usfirst.frc.team467.robot.RobotMap;
import org.usfirst.frc.team467.robot.simulator.DriveSimulator;

import edu.wpi.first.wpilibj.Timer;

public class Actions {
	private static final Logger LOGGER = Logger.getLogger(Actions.class);

	public static final Action nothing(){
		Drive drive = Drive.getInstance();
		String actionText = "Do Nothing";
		return new Action(actionText,
				() -> drive.isStopped(),
				() -> drive.tankDrive(0, 0));
	}

	public static Action wait(double duration) {
		Drive drive = Drive.getInstance();
		String actionText = "Do Nothing";
		return new Action(actionText,
				new ActionGroup.Duration(duration),
				() -> drive.tankDrive(0, 0, false));
	}

	public static final Action nothingForever(){
		Drive drive = Drive.getInstance();
		String actionText = "Do Nothing";
		return new Action(actionText,
				() -> false,
				() -> drive.tankDrive(0, 0, false));
	}

	public static final Action moveForward = new Action(
			"Move Forward 2 seconds",
			new ActionGroup.Duration(1),
			() -> Drive.getInstance().tankDrive(0.5, 0.5));

	public static Action print(String message) {
		return new Action(
				"Print custom message",
				new ActionGroup.RunOnce(() -> LOGGER.info(message)));
	}

	public static final Action goForward(double seconds){
		Drive drive = Drive.getInstance();
		String actionText = "Move Forward " + seconds + "seconds";
		return new Action(actionText,
				new ActionGroup.Duration(seconds),
				() -> drive.tankDrive(0.7, 0.7));
	}

	public static Action zeroDistance() {
//		Drive drive = Drive.getInstance();
		DriveSimulator drive = DriveSimulator.getInstance();
		return new Action(
				"Zeroing the distance",
//				new ActionGroup.RunOnce(() -> drive.zeroPosition()));
				new ActionGroup.RunOnce(() -> drive.zero()));
	}
	
	
	/**
	 * 
	 * @param Distance moves robot in feet.
	 * @return
	 */

	public static Action moveDistanceForward(double distance) {
//		Drive drive = Drive.getInstance();
		DriveSimulator drive = DriveSimulator.getInstance();
		String actionText = "Move forward " + distance + " feet";
		return new Action(actionText,
				new ActionGroup.ReachDistance(distance),
				() -> drive.moveFeet(distance));
//				() -> drive.moveFeet(distance));
	}
	
	
	/**
	 * 
	 * @param rotationInDegrees Rotates robot in radians. Enter rotation amount in Degrees.
	 * @return
	 */
	public static Action moveturn(double rotationInDegrees) {
	    double rotation = rotationInDegrees;
	    
//		Drive drive = Drive.getInstance();
		DriveSimulator drive = DriveSimulator.getInstance();
		String actionText = "Rotate " + rotationInDegrees + " degrees.";
		return new Action(actionText,
				new ActionGroup.ReachDistance(rotation),
				() -> drive.rotateByAngle(rotation));
//				() -> drive.rotateDegrees(rotation));
	}

	public static boolean moveDistanceComplete(double distance) {
		Drive drive = Drive.getInstance();
		double distanceMoved = drive.absoluteDistanceMoved();
		LOGGER.debug("Distances - Target: " + Math.abs(distance) + " Moved: " + distanceMoved);
		if (distanceMoved >= (Math.abs(distance) - RobotMap.POSITION_ALLOWED_ERROR)) {
			LOGGER.info("Finished moving " + distanceMoved + " feet");
			return true;
		} else {
			LOGGER.info("Still moving " + distanceMoved + " feet");
			return false;
		}
	}

//	public static boolean turnDistanceComplete() {
//		Drive drive = Drive.getInstance();
//		double turnError = drive.getTurnError();
//		LOGGER.debug("Error: " + turnError);
//		if (turnError <= RobotMap.POSITION_ALLOWED_ERROR) {
//			LOGGER.info("Error: " + turnError);
//			return true;
//		} else {
//			LOGGER.debug("Error" + turnError);
//			return false;
//		}
//	}

	public static ActionGroup moveDistanceForwardProcess3X(double distance) {
		String actionGroupText = "Move forward 3X " + distance + " feet";
		ActionGroup mode = new ActionGroup(actionGroupText);
		mode.addAction(zeroDistance());
		mode.addAction(moveDistanceForward(distance));
		mode.addAction(zeroDistance());
		mode.addAction(moveDistanceForward(distance));
		mode.addAction(zeroDistance());
		mode.addAction(moveDistanceForward(distance));
		mode.enable();
		return mode;
	}

	public static ActionGroup moveDistance(double distance) {
		String actionGroupText = "Move forward 3X " + distance + " feet";
		ActionGroup mode = new ActionGroup(actionGroupText);
		mode.addAction(zeroDistance());
		mode.addAction(moveDistanceForward(distance));
		return mode;
	}
	
	//SWITCH
	
	
	public static ActionGroup leftBasicSwitch() {
		String actionGroupText = "Start on left side, put cube on switch.";
		ActionGroup mode = new ActionGroup(actionGroupText);
		mode.addAction(zeroDistance());
		mode.addAction(moveDistanceForward(12.33)); 
		mode.addAction(zeroDistance());
		mode.addAction(moveturn(90));
		mode.addAction(zeroDistance());
		mode.addAction(moveDistanceForward(1.479));
		mode.addAction(zeroDistance());
		return mode;
	} 



	public static ActionGroup centerBasicSwitchLeft() {
		String actionGroupText = "Start in center, put cube on left switch.";
		ActionGroup mode = new ActionGroup(actionGroupText);
		mode.addAction(zeroDistance());
		mode.addAction(moveDistanceForward(4.0)); 
		mode.addAction(zeroDistance());
		mode.addAction(moveturn(-90));
		mode.addAction(zeroDistance());
		mode.addAction(moveDistanceForward(5.27)); 
		mode.addAction(zeroDistance());
		mode.addAction(moveturn(90));
		mode.addAction(zeroDistance());
		mode.addAction(moveDistanceForward(4.34)); 
		return mode;
	}

	public static ActionGroup centerBasicSwitchRight() {
		String actionGroupText = "Start in center, put cube on right switch.";
		ActionGroup mode = new ActionGroup(actionGroupText);
		mode.addAction(zeroDistance());
		mode.addAction(moveDistanceForward(4.0));
		mode.addAction(zeroDistance());
		mode.addAction(moveturn(90));
		mode.addAction(zeroDistance());
		mode.addAction(moveDistanceForward(4.27)); 
		mode.addAction(zeroDistance());
		mode.addAction(moveturn(-90));
		mode.addAction(zeroDistance());
		mode.addAction(moveDistanceForward(4.34));
		return mode;
	}

	
	public static ActionGroup rightBasicSwitch() {
		String actionGroupText = "Start on right, put cube on switch.";
		ActionGroup mode = new ActionGroup(actionGroupText);
		mode.addAction(zeroDistance());
		mode.addAction(moveDistanceForward(12.33)); // 12' 4"
		mode.addAction(zeroDistance());
		mode.addAction(moveturn(-90));
		mode.addAction(zeroDistance());
		mode.addAction(moveDistanceForward(1.479)); // 1' 5.75"
		return mode; //works
	}

//	
//	public static ActionGroup switch3A() {
//		String actionGroupText = "Start on side 3A, put cube on switch.";
//		ActionGroup mode = new ActionGroup(actionGroupText);
//		mode.addAction(zeroDistance());
//		mode.addAction(moveDistanceForward(4.0)); // 4'
//		mode.addAction(zeroDistance());
//		mode.addAction(moveturn(90));
//		mode.addAction(zeroDistance());
//		mode.addAction(moveDistanceForward(19.0833)); // 19' 1"
//		mode.addAction(zeroDistance());
//		mode.addAction(moveturn(90));
//		mode.addAction(moveDistanceForward(8.33)); // 8' 4"
//		mode.addAction(zeroDistance());
//		mode.addAction(moveturn(90));
//		mode.addAction(zeroDistance());
//		mode.addAction(moveDistanceForward(1.479)); // 1' 5.75"
//		mode.addAction(zeroDistance());
//		mode.addAction(moveDistanceForward(-2.0)); // 2' backwards
//		return mode;
//	}
//	
//	public static ActionGroup switch1B() {
//		String actionGroupText = "Start on side 1B, put cube on switch.";
//		ActionGroup mode = new ActionGroup(actionGroupText);
//		mode.addAction(zeroDistance());
//		mode.addAction(moveDistanceForward(16.33));
//		mode.addAction(zeroDistance());
//		mode.addAction(moveturn(90));
//		mode.addAction(zeroDistance());
//		mode.addAction(moveDistanceForward(17.0));
//		mode.addAction(zeroDistance());
//		mode.addAction(moveturn(90));
//		mode.addAction(zeroDistance());
//		mode.addAction(moveDistanceForward(2.55));
//		mode.addAction(zeroDistance());
//		mode.addAction(moveturn(90));
//		mode.addAction(zeroDistance());
//		mode.addAction(moveDistanceForward(1.2));
//		mode.addAction(zeroDistance());
//		return mode;
//	} 
	
	
	
	//SCALE
	
	public static ActionGroup leftBasicScaleLeft() {
		String actionGroupText = "Start on left, put cube on left scale";
		ActionGroup mode = new ActionGroup(actionGroupText);
		mode.addAction(zeroDistance());
		mode.addAction(moveDistanceForward(25.33));
		mode.addAction(zeroDistance());
		mode.addAction(moveturn(90));
		mode.addAction(zeroDistance());
		mode.addAction(moveDistanceForward(0.375));
		return mode;
	}
	
	public static ActionGroup leftBasicScaleRight(){
		String actionGroupText = "Start on left, put cube on right scale";
		ActionGroup mode = new ActionGroup(actionGroupText);
		mode.addAction(zeroDistance());
		mode.addAction(moveDistanceForward(18.14));
		mode.addAction(zeroDistance());
		mode.addAction(moveturn(90));
		mode.addAction(zeroDistance());
		mode.addAction(moveDistanceForward(19.08));
		mode.addAction(zeroDistance());
		mode.addAction(moveturn(-90));
		mode.addAction(zeroDistance());
		mode.addAction(moveDistanceForward(7.19));
		mode.addAction(zeroDistance());
		mode.addAction(moveturn(-90));
		mode.addAction(zeroDistance());
		mode.addAction(moveDistanceForward(0.375));
		return mode;
	}
	
	public static ActionGroup centerBasicScaleLeft(){
		String actionGroupText = "Start in center, put cube on left scale";
		ActionGroup mode = new ActionGroup(actionGroupText);
		mode.addAction(zeroDistance());
		mode.addAction(moveDistanceForward(4.0));
		mode.addAction(zeroDistance());
		mode.addAction(moveturn(-90));
		mode.addAction(zeroDistance());
		mode.addAction(moveDistanceForward(10.0));
		mode.addAction(zeroDistance());
		mode.addAction(moveturn(90));
		mode.addAction(zeroDistance());
		mode.addAction(moveDistanceForward(21.33));
		mode.addAction(zeroDistance());
		mode.addAction(moveturn(90));
		mode.addAction(zeroDistance());
		mode.addAction(moveDistanceForward(0.375));
		return mode;
	}
	
	public static ActionGroup centerBasicScaleRight(){
		String actionGroupText = "Start in center, put cube on right scale";
		ActionGroup mode = new ActionGroup(actionGroupText);
		mode.addAction(zeroDistance());
		mode.addAction(moveDistanceForward(4.0));
		mode.addAction(zeroDistance());
		mode.addAction(moveturn(90));
		mode.addAction(zeroDistance());
		mode.addAction(moveDistanceForward(9.08));
		mode.addAction(zeroDistance());
		mode.addAction(moveturn(-90));
		mode.addAction(zeroDistance());
		mode.addAction(moveDistanceForward(21.33));
		mode.addAction(zeroDistance());
		mode.addAction(moveturn(-90));
		mode.addAction(zeroDistance());
		mode.addAction(moveDistanceForward(0.375));
		return mode;
	}
	
	public static ActionGroup rightBasicScaleLeft(){
		String actionGroupText = "Start on right, put cube on left scale";
		ActionGroup mode = new ActionGroup(actionGroupText);
		mode.addAction(zeroDistance());
		mode.addAction(moveDistanceForward(18.14));
		mode.addAction(zeroDistance());
		mode.addAction(moveturn(-90));
		mode.addAction(zeroDistance());
		mode.addAction(moveDistanceForward(19.08));
		mode.addAction(zeroDistance());
		mode.addAction(moveturn(90));
		mode.addAction(zeroDistance());
		mode.addAction(moveDistanceForward(7.19));
		mode.addAction(zeroDistance());
		mode.addAction(moveturn(90));
		mode.addAction(zeroDistance());
		mode.addAction(moveDistanceForward(0.375));
		return mode;
	}
	
	public static ActionGroup rightBasicScaleRight(){
		String actionGroupText = "Start on right, put cube on right scale";
		ActionGroup mode = new ActionGroup(actionGroupText);
		mode.addAction(zeroDistance());
		mode.addAction(moveDistanceForward(25.33));
		mode.addAction(zeroDistance());
		mode.addAction(moveturn(-90));
		mode.addAction(zeroDistance());
		mode.addAction(moveDistanceForward(0.375));
		return mode;
	}

//	public static Action aim(double angle) {
//		Drive drive = Drive.getInstance();
//		Action aim =  new Action(
//				"Aim",
//				new ActionGroup.OnTarget(5),
//				() -> drive.turnToAngle(angle));
//		return aim;
//	}

//	public static Action disableAiming() {
//		Drive drive = Drive.getInstance();
//		Action disableAimingAction = new Action(
//				"Disable",
//				() -> true,
//				() -> drive.aiming.disable());
//		return disableAimingAction;
//	}


	public static ActionGroup doNothing(){
		ActionGroup mode = new ActionGroup("none");
		mode.addAction(nothing());
		return mode;
	}


	public static ActionGroup newDriveSquareProcess() {
		ActionGroup mode = new ActionGroup("Drive in a 2x2 square clockwise from bottom left");
//		mode.addAction(turnAndMoveDistance(0, 2));
//		mode.addAction(turnAndMoveDistance(90, 2));
//		mode.addAction(turnAndMoveDistance(180, 2));
//		mode.addAction(turnAndMoveDistance(270, 2));
		return mode;
	}


}
