package frc.robot.gamepieces.states;

import frc.robot.RobotMap;
import frc.robot.logging.RobotLogManager;
import org.apache.logging.log4j.Logger;

public class SuperStructStates {
    private static final Logger LOGGER = RobotLogManager.getMainLogger(SuperStructStates.class.getName());
    public double turnTicks = RobotMap.TURRET_HOME_TICKS;
    public double wristTicks = RobotMap.CARGO_MECH_WRIST_BOTTOM_TICKS;

    public SuperStructStates(double turretAngle, double wristHeight){
        this.turnTicks = turretAngle;
        this.wristTicks = wristHeight;
    }

    public SuperStructStates(){
        this(RobotMap.TURRET_HOME_TICKS, RobotMap.CARGO_MECH_CARGO_BIN_PROPORTION);
    }

    public SuperStructStates(SuperStructStates alternate){
        this.turnTicks = alternate.turnTicks;
    }

    public boolean isTurretHome() {
        double distanceToHome = turnTicks - RobotMap.TURRET_HOME_TICKS;
        if (Math.abs(distanceToHome) <= RobotMap.TURRET_ALLOWABLE_ERROR_TICKS) {
          LOGGER.debug("Turret is home at distance: {}, ticks {}, angle: {}", 
              turnTicks, distanceToHome);
          return true;
        }
        LOGGER.debug("Turret is NOT home at distance: {}, ticks {}, angle: {}", 
            turnTicks);
        return false;
      }

    public boolean fubarTurretState() {
        //Figure out which way the logic goes
        return (turnTicks - RobotMap.TURRET_ALLOWABLE_ERROR_TICKS) > RobotMap.TURRET_LEFT_LIMIT_TICKS || (turnTicks + RobotMap.TURRET_ALLOWABLE_ERROR_TICKS) < RobotMap.TURRET_RIGHT_LIMIT_TICKS;
    }

    public void fatalReset() {
        turnTicks = RobotMap.TURRET_HOME_TICKS;
        wristTicks = RobotMap.CARGO_MECH_WRIST_BOTTOM_TICKS;
        LOGGER.error("Robot has encountered fatal state going to neutral state");
    }

    public boolean isInRange(SuperStructStates state, double turretThreshold, double wristThreshold) {
        boolean turretInRange = (state.turnTicks + turretThreshold) >= turnTicks && (state.turnTicks - turretThreshold) <= turnTicks;
        boolean wristInRange = (state.wristTicks + wristThreshold) >= wristThreshold && (state.wristTicks + wristThreshold) <= wristTicks;
        return turretInRange && wristInRange;

    }

    public String stateSitRep() {
        return "turnticks: " + turnTicks;
    }

}