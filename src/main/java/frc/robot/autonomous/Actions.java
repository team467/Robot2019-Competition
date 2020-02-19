package frc.robot.autonomous;

import frc.robot.RobotMap;
import frc.robot.drive.Drive;
import frc.robot.gamepieces.GamePieceController;
import frc.robot.logging.RobotLogManager;
import frc.robot.gamepieces.AbstractLayers.IndexerAL;

import java.text.DecimalFormat;

import org.apache.logging.log4j.Logger;

public class Actions {

  private static final Logger LOGGER = RobotLogManager.getMainLogger(Actions.class.getName());

  private static final DecimalFormat df = new DecimalFormat("####0.00");

  private static Drive drive = Drive.getInstance();

  private static GamePieceController gamePieceController = GamePieceController.getInstance();

  private static double mirrorTurns = 1.0;

  public static void startOnLeft() {
    mirrorTurns = -1.0;
  }

  public static void startOnRight() {
    mirrorTurns = 1.0;
  }

  public static void startInCenter() {
    mirrorTurns = 1.0;
  }

  public static final Action nothing() {
    String actionText = "Do Nothing";
    return new Action(actionText, () -> drive.isStopped(), () -> drive.arcadeDrive(0, 0, false));
  }

  public static final Action drive(double duration) {
    String actionText = "Do Nothing";
    return new Action(actionText, new ActionGroup.Duration(duration), () -> drive.arcadeDrive(1, 0, false));
  }

  public static final Action Shoot() {
    String actionText = "shoot";

    IndexerAL indexer = IndexerAL.getInstance();
    return new Action(actionText, () -> indexer.isBallInChamber(),
        () -> gamePieceController.setAutomousFireWhenReady(true));
  }

  public static Action wait(double duration) {
    String actionText = "Do Nothing";
    return new Action(actionText, new ActionGroup.Duration(duration), () -> drive.arcadeDrive(0, 0));
  }

  public static final Action nothingForever() {
    String actionText = "Do Nothing";
    return new Action(actionText, () -> false, () -> drive.moveLinearFeet(0));
  }

  public static ActionGroup doNothing() {
    ActionGroup mode = new ActionGroup("none");
    mode.addAction(nothing());
    return mode;
  }

  public static Action print(String message) {
    return new Action("Print custom message", new ActionGroup.RunOnce(() -> LOGGER.info(message)));
  }

  public static Action zeroDistance() {
    return new Action("Zeroing the distance", new ActionGroup.RunOnce(() -> drive.zero()));
  }

  /**
   * 
   * @param distance moves robot in feet.
   * @return
   */
  public static Action moveDistanceForward(double distance) {
    String actionText = "Move forward " + distance + " feet";
    // TODO determine the mult values
    double timeAmt = (distance - 0.112) / 10.3596;
    return new Action(actionText, new ActionGroup.Duration(timeAmt), () -> drive.arcadeDrive(0.8, 0));
  }

  /**
   * 
   * @param rotationInDegrees Rotates robot in radians. Enter rotation amount in
   *                          Degrees.
   * 
   */
  public static Action moveturn(double rotationInDegrees) {
    String actionText = "Rotate " + rotationInDegrees + " degrees.";
    return new Action(actionText, new ActionGroup.ReachAngle(rotationInDegrees),
        // reach distance was here instead of reachAngle
        () -> drive.rotateByAngle(rotationInDegrees));
  }

  public static boolean moveDistanceComplete(double distance) {
    double distanceMoved = drive.absoluteDistanceMoved();

    LOGGER.debug("Distances - Target: {} Moved: {}", df.format(Math.abs(distance)), df.format(distanceMoved));
    if (distanceMoved >= (Math.abs(distance) - RobotMap.POSITION_ALLOWED_ERROR)) {
      LOGGER.info("Finished moving {} feet", df.format(distanceMoved));
      return true;
    } else {
      LOGGER.info("Still moving {} feet", df.format(distanceMoved));
      return false;
    }
  }

  /**
   * 
   * @param distance distance to move in feet
   */
  public static ActionGroup move(double distance) {
    String actionGroupText = "Move forward " + distance + " feet";
    ActionGroup mode = new ActionGroup(actionGroupText);
    mode.addAction(zeroDistance());
    mode.addAction(moveDistanceForward(distance));
    return mode;
  }

  /**
   * 
   * @param degrees enter positive degrees for left turn and enter negative
   *                degrees for right turn.
   */
  public static ActionGroup turn(double degrees) {
    String actionGroupText = "Turn " + degrees + " degrees";
    ActionGroup mode = new ActionGroup(actionGroupText);
    mode.addAction(zeroDistance());
    mode.addAction(moveturn(mirrorTurns * degrees));
    return mode;
  }

  public static ActionGroup start() {
    String actionGroupText = "Lower grabber down and move elevator to safe height";
    ActionGroup mode = new ActionGroup(actionGroupText);
    return mode;
  }

  public static ActionGroup DoNothing() {
    String actionGroupText = "doing nothing";
    ActionGroup mode = new ActionGroup(actionGroupText);
    mode.addAction(nothing());
    return mode;
  }

  public static ActionGroup shootGroup() {
    String actionGroupText = "doing nothing";
    ActionGroup mode = new ActionGroup(actionGroupText);
    mode.addAction(Shoot());
    mode.addAction(moveDistanceForward(10));
    return mode;
  }

  public static ActionGroup crossAutoLine() {
    String actionGroupText = "Go straight to cross the auto line.";
    ActionGroup mode = new ActionGroup(actionGroupText);
    mode.addActions(start());
    mode.addActions(move(10.0));
    return mode;
  }

  public static Action getAngleFromTarget() {
    String actionText = "sense angle from target";
    return new Action(actionText, //
        () -> true, //if an angle has been sensed
        () -> {
        }//sense for an angle
    );
  }

  // TEST ACTIONS

  public static ActionGroup fourFootSquare() {
    String actionGroupText = "Move in 4 foot square.";
    ActionGroup mode = new ActionGroup(actionGroupText);
    mode.addActions(move(4.0));
    mode.addActions(turn(90));
    mode.addActions(move(4.0));
    mode.addActions(turn(90));
    mode.addActions(move(4.0));
    mode.addActions(turn(90));
    mode.addActions(move(4.0));
    mode.addActions(turn(90));
    return mode;
  }

  //
  public static ActionGroup collectAndShoot() {
    // stuff
    String actionGroupText = "align with trench and go back to score";
    ActionGroup mode = new ActionGroup(actionGroupText);
    mode.addActions(move(11));
    mode.addActions(turn(90));
    // TODO create this
    mode.addAction(getAngleFromTarget());
    mode.addActions(turn(-90));
    // mode.addActions(modeForwardByTheAmountINeedPlus5.66Feet()); TODO
    //formula is tan(theta)*10.16+5.66
    mode.addActions(turn(-90));
    mode.addActions(move(16.06));
    mode.addActions(turn(-160.59));
    mode.addActions(move(17.03));
    mode.addActions(turn(-19.41));
    mode.addActions(shootGroup());

    return mode;
  }

}
