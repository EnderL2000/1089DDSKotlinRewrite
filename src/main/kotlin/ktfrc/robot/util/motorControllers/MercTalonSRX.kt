package ktfrc.robot.util.motorControllers

import com.ctre.phoenix.ParamEnum
import com.ctre.phoenix.motorcontrol.*
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import ktfrc.robot.util.PIDGain
import ktfrc.robot.RobotMap

class MercTalonSRX(private val port: Int) : IMercMotorController {

    private val talonsrx: WPI_TalonSRX = WPI_TalonSRX(port)

    init {
        talonsrx.configFactoryDefault()
    }

    override fun setPosition(position: Double) {
        talonsrx.set(ControlMode.Position, position)
    }

    override fun getSpeed(): Double {
        return talonsrx.get()
    }

    override fun setSpeed(speed: Double) {
        talonsrx.set(ControlMode.PercentOutput, speed)
    }

    override fun setInverted(invert: Boolean) {
        talonsrx.inverted = invert
    }

    override fun getPort(): Int {
        return port
    }

    override fun stop() {
        talonsrx.stopMotor()
    }

    override fun getEncTicks(): Double {
        return talonsrx.getSelectedSensorPosition(0).toDouble()
    }

    override fun getEncVelocity(): Double {
        return talonsrx.getSelectedSensorVelocity(0).toDouble()
    }

    override fun resetEncoder() {
        talonsrx.sensorCollection.setQuadraturePosition(0, RobotMap.CTRE_TIMEOUT)
    }

    override fun configPID(slot: Int, gain: PIDGain) {
        talonsrx.config_kP(slot, gain.kP, 10)
        talonsrx.config_kI(slot, gain.kI, 10)
        talonsrx.config_kD(slot, gain.kD, 10)
        talonsrx.config_kF(slot, gain.kF, 10)
        talonsrx.configClosedLoopPeakOutput(slot, gain.closedLoopMaxOutput, 10)
    }

    override fun configVoltage(nominalOutput: Double, peakOutput: Double) {
        talonsrx.configNominalOutputForward(nominalOutput, RobotMap.CTRE_TIMEOUT)
        talonsrx.configNominalOutputReverse(-nominalOutput, RobotMap.CTRE_TIMEOUT)
        talonsrx.configPeakOutputForward(peakOutput, RobotMap.CTRE_TIMEOUT)
        talonsrx.configPeakOutputReverse(-peakOutput, RobotMap.CTRE_TIMEOUT)
    }

    override fun setNeutralMode(neutralMode: NeutralMode) {
        talonsrx.setNeutralMode(neutralMode)
    }

    override fun setSensorPhase(phase: Boolean) {
        talonsrx.setSensorPhase(phase)
    }

    override fun configAllowableClosedLoopError(slotIdx: Int, allowableClosedLoopError: Int) {
        talonsrx.configAllowableClosedloopError(slotIdx, allowableClosedLoopError, RobotMap.CTRE_TIMEOUT)
    }

    override fun configSelectedFeedbackSensor(feedbackDevice: FeedbackDevice, pidIdx: Int) {
        talonsrx.configSelectedFeedbackSensor(feedbackDevice, pidIdx, RobotMap.CTRE_TIMEOUT)
    }

    override fun configSetParameter(param: ParamEnum, value: Double, subValue: Int, ordinal: Int) {
        talonsrx.configSetParameter(param, value, subValue, ordinal, RobotMap.CTRE_TIMEOUT)
    }

    override fun isLimitSwitchClosed(polarity: LimitSwitchPolarity): Boolean {
        return when(polarity) {
            LimitSwitchPolarity.FORWARD -> talonsrx.sensorCollection.isFwdLimitSwitchClosed
            LimitSwitchPolarity.REVERSE -> talonsrx.sensorCollection.isRevLimitSwitchClosed
        }
    }

    override fun setForwardSoftLimit(limitTicks: Int) {
        talonsrx.configForwardSoftLimitThreshold(limitTicks, RobotMap.CTRE_TIMEOUT)
    }

    override fun enableForwardSoftLimit() {
        talonsrx.configForwardSoftLimitEnable(true, RobotMap.CTRE_TIMEOUT)
    }

    override fun disableForwardSoftLimit() {
        talonsrx.configForwardSoftLimitEnable(false, RobotMap.CTRE_TIMEOUT)
    }

    override fun setReverseSoftLimit(limitTicks: Int) {
        talonsrx.configReverseSoftLimitThreshold(limitTicks, RobotMap.CTRE_TIMEOUT)
    }

    override fun enableReverseSoftLimit() {
        talonsrx.configReverseSoftLimitEnable(true, RobotMap.CTRE_TIMEOUT)
    }

    override fun disableReverseSoftLimit() {
        talonsrx.configReverseSoftLimitEnable(false, RobotMap.CTRE_TIMEOUT)
    }

    override fun configSensorTerm(term: SensorTerm, feedbackDevice: FeedbackDevice) {
        talonsrx.configSensorTerm(term, feedbackDevice, RobotMap.CTRE_TIMEOUT)
    }

    override fun configRemoteFeedbackFilter(deviceID: Int, source: RemoteSensorSource, remoteSlotIdx: Int) {
        talonsrx.configRemoteFeedbackFilter(deviceID, source, remoteSlotIdx)
    }

    override fun configSelectedFeedbackCoefficient(feedbackScale: Double, pidIdx: Int) {
        talonsrx.configSelectedFeedbackCoefficient(feedbackScale, pidIdx, RobotMap.CTRE_TIMEOUT)
    }

    override fun setStatusFramePeriod(statusFrame: StatusFrame, statusMs: Int) {
        talonsrx.setStatusFramePeriod(statusFrame, statusMs, RobotMap.CTRE_TIMEOUT)
    }

    override fun selectProfileSlot(slotIdx: Int, pidIdx: Int) {
        talonsrx.selectProfileSlot(slotIdx, pidIdx)
    }

    override fun configClosedLoopPeakOutput(slotIdx: Int, peakOutput: Double) {
        talonsrx.configClosedLoopPeakOutput(slotIdx, peakOutput)
    }

    override fun set(controlMode: ControlMode, demand0: Double, demand1Type: DemandType, demand1: Double) {
        talonsrx.set(controlMode, demand0, demand1Type, demand1)
    }

    override fun configClosedLoopPeriod(slotIdx: Int, closedLoopTimeMs: Int) {
        talonsrx.configClosedLoopPeriod(slotIdx, closedLoopTimeMs, RobotMap.CTRE_TIMEOUT)
    }

    override fun configAuxPIDPolarity(invert: Boolean) {
        talonsrx.configAuxPIDPolarity(invert, RobotMap.CTRE_TIMEOUT)
    }

    override fun configMotionAcceleration(sensorUnitsPer100msPerSec: Int) {
        talonsrx.configMotionAcceleration(sensorUnitsPer100msPerSec, RobotMap.CTRE_TIMEOUT)
    }

    override fun configMotionCruiseVelocity(sensorUnitsPer100ms: Int) {
        talonsrx.configMotionCruiseVelocity(sensorUnitsPer100ms, RobotMap.CTRE_TIMEOUT)
    }

    override fun follow(leader: IMercMotorController, followerType: FollowerType) {
        if (leader is MercTalonSRX) {
            talonsrx.follow(leader.get(), followerType)
        } else if (leader is MercVictorSPX) {
            talonsrx.follow(leader.get(), followerType)
        }
    }

    override fun getClosedLoopError(slotIdx: Int): Double {
        return talonsrx.getClosedLoopError(slotIdx).toDouble()
    }

    override fun configFactoryReset() {
        talonsrx.configFactoryDefault()
    }

    /**
     * Get the TalonSRX tied to this class
     *
     * @return the Talon
     */
    fun get(): WPI_TalonSRX {
        return talonsrx
    }
}
