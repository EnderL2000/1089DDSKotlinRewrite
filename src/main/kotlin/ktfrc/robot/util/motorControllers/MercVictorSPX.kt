package ktfrc.robot.util.motorControllers

import com.ctre.phoenix.ParamEnum
import com.ctre.phoenix.motorcontrol.*
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX
import ktfrc.robot.util.PIDGain
import ktfrc.robot.RobotMap

class MercVictorSPX(private val port: Int) : IMercMotorController {
    private val victorspx: WPI_VictorSPX = WPI_VictorSPX(port)

    init {
        victorspx.configFactoryDefault()
    }

    override fun setPosition(position: Double) {
        victorspx.set(ControlMode.Position, position)
    }

    override fun getSpeed(): Double {
        return victorspx.get()
    }

    override fun setSpeed(speed: Double) {
        victorspx.set(ControlMode.PercentOutput, speed)
    }

    override fun setInverted(invert: Boolean) {
        victorspx.inverted = invert
    }

    override fun getPort(): Int {
        return port
    }

    override fun stop() {
        victorspx.stopMotor()
    }

    override fun getEncTicks(): Double {
        return 0.0
    }

    override fun getEncVelocity(): Double {
        return 0.0
    }

    override fun resetEncoder() {
        return
    }

    override fun configPID(slot: Int, gain: PIDGain) {
        victorspx.config_kP(slot, gain.kP, 10)
        victorspx.config_kI(slot, gain.kI, 10)
        victorspx.config_kD(slot, gain.kD, 10)
        victorspx.config_kF(slot, gain.kF, 10)
        victorspx.configClosedLoopPeakOutput(slot, gain.closedLoopMaxOutput, 10)
    }

    override fun configVoltage(nominalOutput: Double, peakOutput: Double) {
        victorspx.configNominalOutputForward(nominalOutput, RobotMap.CTRE_TIMEOUT)
        victorspx.configNominalOutputReverse(-nominalOutput, RobotMap.CTRE_TIMEOUT)
        victorspx.configPeakOutputForward(peakOutput, RobotMap.CTRE_TIMEOUT)
        victorspx.configPeakOutputReverse(-peakOutput, RobotMap.CTRE_TIMEOUT)
    }

    override fun setNeutralMode(neutralMode: NeutralMode) {
        victorspx.setNeutralMode(neutralMode)
    }

    override fun setSensorPhase(phase: Boolean) {
        victorspx.setSensorPhase(phase)
    }

    override fun configAllowableClosedLoopError(slotIdx: Int, allowableClosedLoopError: Int) {
        victorspx.configAllowableClosedloopError(slotIdx, allowableClosedLoopError, RobotMap.CTRE_TIMEOUT)
    }

    override fun configSelectedFeedbackSensor(feedbackDevice: FeedbackDevice, pidIdx: Int) {
        victorspx.configSelectedFeedbackSensor(feedbackDevice, pidIdx, RobotMap.CTRE_TIMEOUT)
    }

    override fun configSetParameter(param: ParamEnum, value: Double, subValue: Int, ordinal: Int) {
        victorspx.configSetParameter(param, value, subValue, ordinal, RobotMap.CTRE_TIMEOUT)
    }

    override fun isLimitSwitchClosed(polarity: LimitSwitchPolarity): Boolean {
        return false
    }

    override fun setForwardSoftLimit(limitTicks: Int) {
        victorspx.configForwardSoftLimitThreshold(limitTicks, RobotMap.CTRE_TIMEOUT)
    }

    override fun enableForwardSoftLimit() {
        victorspx.configForwardSoftLimitEnable(true, RobotMap.CTRE_TIMEOUT)
    }

    override fun disableForwardSoftLimit() {
        victorspx.configForwardSoftLimitEnable(false, RobotMap.CTRE_TIMEOUT)
    }

    override fun setReverseSoftLimit(limitTicks: Int) {
        victorspx.configReverseSoftLimitThreshold(limitTicks, RobotMap.CTRE_TIMEOUT)
    }

    override fun enableReverseSoftLimit() {
        victorspx.configReverseSoftLimitEnable(true, RobotMap.CTRE_TIMEOUT)
    }

    override fun disableReverseSoftLimit() {
        victorspx.configReverseSoftLimitEnable(false, RobotMap.CTRE_TIMEOUT)
    }

    override fun configSensorTerm(term: SensorTerm, feedbackDevice: FeedbackDevice) {
        victorspx.configSensorTerm(term, feedbackDevice, RobotMap.CTRE_TIMEOUT)
    }

    override fun configRemoteFeedbackFilter(deviceID: Int, source: RemoteSensorSource, remoteSlotIdx: Int) {
        victorspx.configRemoteFeedbackFilter(deviceID, source, remoteSlotIdx)
    }

    override fun configSelectedFeedbackCoefficient(feedbackScale: Double, pidIdx: Int) {
        victorspx.configSelectedFeedbackCoefficient(feedbackScale, pidIdx, RobotMap.CTRE_TIMEOUT)
    }

    override fun setStatusFramePeriod(statusFrame: StatusFrame, statusMs: Int) {
        victorspx.setStatusFramePeriod(statusFrame, statusMs, RobotMap.CTRE_TIMEOUT)
    }

    override fun selectProfileSlot(slotIdx: Int, pidIdx: Int) {
        victorspx.selectProfileSlot(slotIdx, pidIdx)
    }

    override fun configClosedLoopPeakOutput(slotIdx: Int, peakOutput: Double) {
        victorspx.configClosedLoopPeakOutput(slotIdx, peakOutput)
    }

    override fun set(controlMode: ControlMode, demand0: Double, demand1Type: DemandType, demand1: Double) {
        victorspx.set(controlMode, demand0, demand1Type, demand1)
    }

    override fun configClosedLoopPeriod(slotIdx: Int, closedLoopTimeMs: Int) {
        victorspx.configClosedLoopPeriod(slotIdx, closedLoopTimeMs, RobotMap.CTRE_TIMEOUT)
    }

    override fun configAuxPIDPolarity(invert: Boolean) {
        victorspx.configAuxPIDPolarity(invert, RobotMap.CTRE_TIMEOUT)
    }

    override fun configMotionAcceleration(sensorUnitsPer100msPerSec: Int) {
        victorspx.configMotionAcceleration(sensorUnitsPer100msPerSec, RobotMap.CTRE_TIMEOUT)
    }

    override fun configMotionCruiseVelocity(sensorUnitsPer100ms: Int) {
        victorspx.configMotionCruiseVelocity(sensorUnitsPer100ms, RobotMap.CTRE_TIMEOUT)
    }

    override fun follow(leader: IMercMotorController, followerType: FollowerType) {
        if (leader is MercTalonSRX) {
            victorspx.follow(leader.get(), followerType)
        } else if (leader is MercVictorSPX) {
            victorspx.follow(leader.get(), followerType)
        }
    }

    //_________________________________________________________________________________

    /**
     * Get the VictorSPX tied to this class
     *
     * @return the Victor
     */
    fun get(): WPI_VictorSPX {
        return victorspx
    }

    override fun getClosedLoopError(slotIdx: Int): Double {
        return victorspx.getClosedLoopError(slotIdx).toDouble()
    }

    override fun configFactoryReset() {
        victorspx.configFactoryDefault()
    }
}