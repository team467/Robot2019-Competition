package frc.robot.drive;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.IMotorControllerEnhanced;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonSRXPIDSetConfiguration;

import edu.wpi.first.wpilibj.MotorSafety;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SpeedController;

public interface WpiTalonSrxInterface 
    extends IMotorController, IMotorControllerEnhanced,
    SpeedController, Sendable, MotorSafety {

	// ---------Intercept CTRE calls for motor safety ---------//
	public void set(ControlMode mode, double value);

	public void set(ControlMode mode, double demand0, double demand1);

	/**
	 * Free the resources used by this object.
	 */
  public void free();

	/**
	 * @return object that can get/set individual raw sensor values.
	 */
	public SensorCollection getSensorCollection();

	/**
	 * Sets the period of the given status frame.
	 *
	 * User ensure CAN Bus utilization is not high.
	 *
	 * This setting is not persistent and is lost when device is reset.
	 * If this is a concern, calling application can use HasReset()
	 * to determine if the status frame needs to be reconfigured.
	 *
	 * @param frame
	 *            Frame whose period is to be changed.
	 * @param periodMs
	 *            Period in ms for the given frame.
	 * @param timeoutMs
	 *            Timeout value in ms. If nonzero, function will wait for
	 *            config success and report an error if it times out.
	 *            If zero, no blocking or checking is performed.
	 * @return Error Code generated by function. 0 indicates no error.
	 */
  public ErrorCode setStatusFramePeriod(StatusFrameEnhanced frame, int periodMs, int timeoutMs);
  
  public ErrorCode setStatusFramePeriod(StatusFrameEnhanced frame, int periodMs);
  
	/**
	 * Gets the period of the given status frame.
	 *
	 * @param frame
	 *            Frame to get the period of.
	 * @param timeoutMs
	 *            Timeout value in ms. If nonzero, function will wait for
	 *            config success and report an error if it times out.
	 *            If zero, no blocking or checking is performed.
	 * @return Period of the given status frame.
	 */
	public int getStatusFramePeriod(StatusFrameEnhanced frame, int timeoutMs);
  
	public int getStatusFramePeriod(StatusFrameEnhanced frame);
  
  /**
	 * Configures the period of each velocity sample.
	 * Every 1ms a position value is sampled, and the delta between that sample
	 * and the position sampled kPeriod ms ago is inserted into a filter.
	 * kPeriod is configured with this function.
	 *
	 * @param period
	 *            Desired period for the velocity measurement. @see
	 *            #VelocityMeasPeriod
	 * @param timeoutMs
	 *            Timeout value in ms. If nonzero, function will wait for
	 *            config success and report an error if it times out.
	 *            If zero, no blocking or checking is performed.
	 * @return Error Code generated by function. 0 indicates no error.
	 */
  public ErrorCode configVelocityMeasurementPeriod(VelocityMeasPeriod period, int timeoutMs);
  
  public ErrorCode configVelocityMeasurementPeriod(VelocityMeasPeriod period);
  
	/**
	 * Sets the number of velocity samples used in the rolling average velocity
	 * measurement.
	 *
	 * @param windowSize
	 *            Number of samples in the rolling average of velocity
	 *            measurement. Valid values are 1,2,4,8,16,32. If another
	 *            value is specified, it will truncate to nearest support value.
	 * @param timeoutMs
	 *            Timeout value in ms. If nonzero, function will wait for
	 *            config success and report an error if it times out.
	 *            If zero, no blocking or checking is performed.
	 * @return Error Code generated by function. 0 indicates no error.
	 */
  public ErrorCode configVelocityMeasurementWindow(int windowSize, int timeoutMs);
  
  public ErrorCode configVelocityMeasurementWindow(int windowSize);
  
	/**
	 * Configures a limit switch for a local/remote source.
	 *
	 * For example, a CAN motor controller may need to monitor the Limit-R pin
	 * of another Talon, CANifier, or local Gadgeteer feedback connector.
	 *
	 * If the sensor is remote, a device ID of zero is assumed.
	 * If that's not desired, use the four parameter version of this function.
	 *
	 * @param type
	 *            Limit switch source.
	 *            User can choose between the feedback connector, remote Talon SRX, CANifier, or deactivate the feature.
	 * @param normalOpenOrClose
	 *            Setting for normally open, normally closed, or disabled. This setting
	 *            matches the web-based configuration drop down.
	 * @param timeoutMs
	 *            Timeout value in ms. If nonzero, function will wait for
	 *            config success and report an error if it times out.
	 *            If zero, no blocking or checking is performed.
	 * @return Error Code generated by function. 0 indicates no error.
	 */
	public ErrorCode configForwardLimitSwitchSource(LimitSwitchSource type, LimitSwitchNormal normalOpenOrClose,
			int timeoutMs);
  
  public ErrorCode configForwardLimitSwitchSource(LimitSwitchSource type, LimitSwitchNormal normalOpenOrClose);
  
	/**
	 * Configures a limit switch for a local/remote source.
	 *
	 * For example, a CAN motor controller may need to monitor the Limit-R pin
	 * of another Talon, CANifier, or local Gadgeteer feedback connector.
	 *
	 * If the sensor is remote, a device ID of zero is assumed. If that's not
	 * desired, use the four parameter version of this function.
	 *
	 * @param type
	 *            Limit switch source. @see #LimitSwitchSource User can choose
	 *            between the feedback connector, remote Talon SRX, CANifier, or
	 *            deactivate the feature.
	 * @param normalOpenOrClose
	 *            Setting for normally open, normally closed, or disabled. This
	 *            setting matches the web-based configuration drop down.
	 * @param timeoutMs
	 *            Timeout value in ms. If nonzero, function will wait for config
	 *            success and report an error if it times out. If zero, no
	 *            blocking or checking is performed.
	 * @return Error Code generated by function. 0 indicates no error.
	 */
	public ErrorCode configReverseLimitSwitchSource(LimitSwitchSource type, LimitSwitchNormal normalOpenOrClose,
      int timeoutMs);
      
  public ErrorCode configReverseLimitSwitchSource(LimitSwitchSource type, LimitSwitchNormal normalOpenOrClose);
  
	// ------ Current Lim ----------//
	/**
	 * Configure the peak allowable current (when current limit is enabled).
	 * 
	 * Current limit is activated when current exceeds the peak limit for longer
	 * than the peak duration. Then software will limit to the continuous limit.
	 * This ensures current limiting while allowing for momentary excess current
	 * events.
	 *
	 * For simpler current-limiting (single threshold) use
	 * ConfigContinuousCurrentLimit() and set the peak to zero:
	 * ConfigPeakCurrentLimit(0).
	 * 
	 * @param amps
	 *            Amperes to limit.
	 * @param timeoutMs
	 *            Timeout value in ms. If nonzero, function will wait for config
	 *            success and report an error if it times out. If zero, no
	 *            blocking or checking is performed.
	 */
  public ErrorCode configPeakCurrentLimit(int amps, int timeoutMs);
  
	public ErrorCode configPeakCurrentLimit(int amps);

	/**
	 * Configure the peak allowable duration (when current limit is enabled).
	 *
	 * Current limit is activated when current exceeds the peak limit for longer
	 * than the peak duration. Then software will limit to the continuous limit.
	 * This ensures current limiting while allowing for momentary excess current
	 * events.
	 *
	 * For simpler current-limiting (single threshold) use
	 * ConfigContinuousCurrentLimit() and set the peak to zero:
	 * ConfigPeakCurrentLimit(0).
	 * 
	 * @param milliseconds
	 *            How long to allow current-draw past peak limit.
	 * @param timeoutMs
	 *            Timeout value in ms. If nonzero, function will wait for config
	 *            success and report an error if it times out. If zero, no
	 *            blocking or checking is performed.
	 */
  public ErrorCode configPeakCurrentDuration(int milliseconds, int timeoutMs);
  
	public ErrorCode configPeakCurrentDuration(int milliseconds);

	/**
	 * Configure the continuous allowable current-draw (when current limit is
	 * enabled).
	 *
	 * Current limit is activated when current exceeds the peak limit for longer
	 * than the peak duration. Then software will limit to the continuous limit.
	 * This ensures current limiting while allowing for momentary excess current
	 * events.
	 *
	 * For simpler current-limiting (single threshold) use
	 * ConfigContinuousCurrentLimit() and set the peak to zero:
	 * ConfigPeakCurrentLimit(0).
	 * 
	 * @param amps
	 *            Amperes to limit.
	 * @param timeoutMs
	 *            Timeout value in ms. If nonzero, function will wait for config
	 *            success and report an error if it times out. If zero, no
	 *            blocking or checking is performed.
	 */
	public ErrorCode configContinuousCurrentLimit(int amps, int timeoutMs);

	/**
	 * Enable or disable Current Limit.
	 * 
	 * @param enable
	 *            Enable state of current limit.
	 * @see configPeakCurrentLimit, configPeakCurrentDuration,
	 *      configContinuousCurrentLimit
	 */
	public void enableCurrentLimit(boolean enable);
	
    /**
     * Configures all PID set peristant settings (overloaded so timeoutMs is 50 ms
     * and pidIdx is 0).
     *
	 * @param pid               Object with all of the PID set persistant settings
	 * @param pidIdx            0 for Primary closed-loop. 1 for auxiliary closed-loop.
     * @param timeoutMs
     *              Timeout value in ms. If nonzero, function will wait for
     *              config success and report an error if it times out.
     *              If zero, no blocking or checking is performed.
     *
     * @return Error Code generated by function. 0 indicates no error. 
     */
  public ErrorCode configurePID(TalonSRXPIDSetConfiguration pid, int pidIdx, int timeoutMs);
  
    /**
     * Configures all PID set peristant settings (overloaded so timeoutMs is 50 ms
     * and pidIdx is 0).
     *
	 * @param pid               Object with all of the PID set persistant settings
     *
     * @return Error Code generated by function. 0 indicates no error. 
     */
	public ErrorCode configurePID(TalonSRXPIDSetConfiguration pid);

    /**
     * Gets all PID set persistant settings.
     *
	 * @param pid               Object with all of the PID set persistant settings
	 * @param pidIdx            0 for Primary closed-loop. 1 for auxiliary closed-loop.
     * @param timeoutMs
     *              Timeout value in ms. If nonzero, function will wait for
     *              config success and report an error if it times out.
     *              If zero, no blocking or checking is performed.
     */
    public void getPIDConfigs(TalonSRXPIDSetConfiguration pid, int pidIdx, int timeoutMs);

    /**
     * Gets all PID set persistant settings (overloaded so timeoutMs is 50 ms
     * and pidIdx is 0).
     *
	 * @param pid               Object with all of the PID set persistant settings
     */
	public void getPIDConfigs(TalonSRXPIDSetConfiguration pid);
	


    /**
     * Configures all peristant settings.
     *
	   * @param allConfigs        Object with all of the persistant settings
     * @param timeoutMs
     *              Timeout value in ms. If nonzero, function will wait for
     *              config success and report an error if it times out.
     *              If zero, no blocking or checking is performed.
     *
     * @return Error Code generated by function. 0 indicates no error. 
     */
	public ErrorCode configAllSettings(TalonSRXConfiguration allConfigs, int timeoutMs);
	
    /**
     * Configures all peristant settings (overloaded so timeoutMs is 50 ms).
     *
	   * @param allConfigs        Object with all of the persistant settings
     *
     * @return Error Code generated by function. 0 indicates no error. 
     */
	public ErrorCode configAllSettings(TalonSRXConfiguration allConfigs);

    /**
     * Gets all persistant settings.
     *
	 * @param allConfigs        Object with all of the persistant settings
     * @param timeoutMs
     *              Timeout value in ms. If nonzero, function will wait for
     *              config success and report an error if it times out.
     *              If zero, no blocking or checking is performed.
     */
    public void getAllConfigs(TalonSRXConfiguration allConfigs, int timeoutMs);

    /**
     * Gets all persistant settings (overloaded so timeoutMs is 50 ms).
     *
	 * @param allConfigs        Object with all of the persistant settings
     */
    public void getAllConfigs(TalonSRXConfiguration allConfigs);


}