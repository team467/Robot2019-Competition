/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.sensors;

import com.revrobotics.Rev2mDistanceSensor;
import com.revrobotics.Rev2mDistanceSensor.Port;
import com.revrobotics.Rev2mDistanceSensor.Unit;

/**
 * Add your docs here.
 */
public class TOFSensor {
    private static TOFSensor instance = null;
    private Rev2mDistanceSensor chamberSensor; 
    private Rev2mDistanceSensor mouthSensor; 
    private TOFSensor(){
        chamberSensor = new Rev2mDistanceSensor(Port.kOnboard);
        mouthSensor = new Rev2mDistanceSensor(Port.kOnboard);
        chamberSensor.setDistanceUnits(Unit.kMillimeters);
        mouthSensor.setDistanceUnits(Unit.kMillimeters);
    }
    public void enable(){
        chamberSensor.setAutomaticMode(true);
        mouthSensor.setAutomaticMode(true);
    }
    public void disable(){
        chamberSensor.setAutomaticMode(false);
        mouthSensor.setAutomaticMode(false);
    }
    public static TOFSensor getInstance(){
        if(instance == null){
            instance = new TOFSensor();
        }
        return instance;
    }

    public double getMouthDistance(){
        return mouthSensor.getRange();
    }
    public double getChamberDistance(){
        return chamberSensor.getRange();
    }

}
