package ktfrc.robot.util.motorControllers

import com.ctre.phoenix.ParamEnum
import com.ctre.phoenix.motorcontrol.*
import ktfrc.robot.util.PIDGain

interface IMercMotorController {

    //region Motor Control

    fun setPosition(position: Double)

    fun getEncTicks(): Double

    fun getEncVelocity(): Double

    fun setSpeed(speed: Double)

    fun getSpeed(): Double

    fun set(controlMode: ControlMode, demand0: Double, demand1Type: DemandType = DemandType.Neutral, demand1: Double = 0.0) //enums

    fun stop()

    //endregion

    //region Basic Motor Config

    fun configVoltage(nominalOutput: Double, peakOutput: Double)

    fun setInverted(invert: Boolean)

    fun setNeutralMode(neutralMode: NeutralMode) //Should be an enum (brake/coast)

    fun follow(leader: IMercMotorController, followerType: FollowerType = FollowerType.PercentOutput) //Make enum for follower type

    fun getPort(): Int

    fun configFactoryReset()

    //endregion

    //region Encoders/Limit Switches

    fun resetEncoder()

    fun setSensorPhase(phase: Boolean)

    fun isLimitSwitchClosed(polarity: LimitSwitchPolarity): Boolean

    //endregion

    //region Soft limit switches

    fun setForwardSoftLimit(limitTicks: Int)

    fun enableForwardSoftLimit()

    fun disableForwardSoftLimit()

    fun setReverseSoftLimit(limitTicks: Int)

    fun enableReverseSoftLimit()

    fun disableReverseSoftLimit()

    //endregion

    //region Closed Loop / PID

    fun configPID(slot: Int, gain: PIDGain)

    fun getClosedLoopError(slotIdx: Int = 0): Double

    fun configAllowableClosedLoopError(slotIdx: Int, allowableClosedLoopError: Int)

    fun configClosedLoopPeakOutput(slotIdx: Int, peakOutput: Double)

    fun configClosedLoopPeriod(slotIdx: Int, closedLoopTimeMs: Int)

    fun configAuxPIDPolarity(invert: Boolean)

    fun configSelectedFeedbackSensor(feedbackDevice: FeedbackDevice, pidIdx: Int) //enum

    fun configSetParameter(param: ParamEnum, value: Double, subValue: Int = 0, ordinal: Int = 0) //enum

    fun configSensorTerm(term: SensorTerm, feedbackDevice: FeedbackDevice)

    fun configRemoteFeedbackFilter(deviceID: Int, source: RemoteSensorSource, remoteSlotIdx: Int)

    fun configSelectedFeedbackCoefficient(feedbackScale: Double, pidIdx: Int)

    fun setStatusFramePeriod(statusFrame: StatusFrame, statusMs: Int)

    fun selectProfileSlot(slotIdx: Int, pidIdx: Int)

    //endregion

    //region Motion Magic

    fun configMotionAcceleration(sensorUnitsPer100msPerSec: Int)

    fun configMotionCruiseVelocity(sensorUnitsPer100ms: Int)

    //endregion
}

enum class LimitSwitchPolarity {
    FORWARD,
    REVERSE
}