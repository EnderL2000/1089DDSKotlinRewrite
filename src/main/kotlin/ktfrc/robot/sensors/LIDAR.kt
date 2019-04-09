package ktfrc.robot.sensors

import com.ctre.phoenix.CANifier
import edu.wpi.first.wpilibj.PIDSource
import edu.wpi.first.wpilibj.PIDSourceType
import edu.wpi.first.wpilibj.filters.LinearDigitalFilter
import frc.robot.util.MercMath

class LIDAR(val cfier: CANifier,
            val channel: CANifier.PWMChannel = CANifier.PWMChannel.PWMChannel0,
            val offset: LidarPWMOffset = LidarPWMOffset.DEFAULT): PIDSource {

    private var pwmInput = doubleArrayOf()
    private var linearDigitalFilter = LinearDigitalFilter.movingAverage(this, 5)

    @Synchronized
    fun updatePWMInput() = cfier.getPWMInput(channel, pwmInput)

    @Synchronized
    override fun pidGet(): Double = offset.apply(getRawDistance())

    @Synchronized
    fun getRawDistance(): Double {
        val centimeters = pwmInput[0] / 10.0
        return MercMath.centimetersToInches(centimeters)
    }

    @Synchronized
    fun getDistance(): Double = linearDigitalFilter.pidGet()

    @Synchronized
    fun getPeriod(): Double = pwmInput[1]

    override fun getPIDSourceType(): PIDSourceType = PIDSourceType.kDisplacement

    override fun setPIDSourceType(pidSource: PIDSourceType?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

enum class LidarPWMOffset(private val constant: Double, private val coefficient: Double) {
    EQUATION_A(-5.55, 1.0),
    EQUATION_B(-4.67, 1.02),
    EQUATION_C(-0.756, 0.996),
    EQUATION_D(-1.33, 1.02),
    DEFAULT(0.0, 0.0);

    fun apply(value: Double): Double {
        return coefficient * value + constant
    }
}