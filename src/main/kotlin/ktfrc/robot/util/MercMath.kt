package ktfrc.robot.util

import ktfrc.robot.OI
import ktfrc.robot.subsystems.DriveTrain
import ktfrc.robot.Robot
import ktfrc.robot.subsystems.DriveTrainMCConfig

/**
 * Clamps a value between a minimum and maximum, inclusive.
 *
 * @param val the value to clamp
 * @param min the minimum value
 * @param max the maximum value
 * @return {@code val}, if {@code val} is between [{@code min}, {@code max}]
 * {@code min}, if {@code val} is <= {@code min}
 * {@code min}, if {@code val} is >= {@code min}
 */
fun clamp(value: Double, min: Double, max: Double): Double =
        if(value < min) min else (if(value > max) max else value)

/**
 * Rounds a floating-point value to a certain number of places past the decimals
 *
 * @param value  the number to round
 * @param places the number of places to round to
 * @return the value, truncated to the specified amount of places
 */
fun roundFloat(value: Double, places: Int): Double {
    val intForm = value * Math.pow(10.0, places.toDouble())
    var truncated = intForm.toInt()
    truncated = if (truncated % 10 >= 5) truncated + 1 else truncated
    return truncated.toDouble() / Math.pow(10.0, places.toDouble())
}

/**
 * Interpolates a value between a minimum and maximum value via a percentage
 *
 * @param percent percent to use to interpolate between a and b
 * @param a       some value
 * @param b       some other value
 * @return a value between a and b given a percent, with 0 being min, and 1 being max
 */
fun lerp(percent: Double, a: Double, b: Double): Double {
    val alpha = clamp(percent, 0.0, 1.0)
    return alpha * b - alpha * a + a
}

/**
 * Does the law of cosines to find the third line length of a triangle
 * based off of the other two sides and the angle between those two
 *
 * @return the length of the third side, already square rooted
 */
fun lawOfCosines(x: Double, y: Double, theta: Double): Double =
        Math.sqrt(x * x + y * y - 2.0 * x * y * Math.cos(theta))

/**
 * Does the law of sines to find an angle based off its opposite
 * side, and another angle and its opposite side
 *
 * @param x     a known sidelength
 * @param theta x's opposite angle  "Y DIS ! θ" - Aidan Sharpe
 * @param y     the side opposite to the angle you want to know
 * @return the unknown angle
 */
fun lawOfSinesAngle(x: Double, y: Double, theta: Double): Double =
        Math.asin(y * Math.sin(theta) / x)

fun applyDeadzone(value: Double, deadzone: Double = OI.DEADZONE): Double =
        if (Math.abs(value) > deadzone) value else 0.0

fun centimetersToInches(value: Double): Double = value / 2.54

fun inchesToCentimeters(value: Double): Double = value * 0.393700787

fun secondsToMinutes(value: Double): Double = value / 60

fun minutesToSeconds(value: Double): Double = value * 60

fun minutesToHours(value: Double): Double = value / 60

fun hoursToMinutes(value: Double): Double = value * 60

fun feetToMeters(value: Double): Double = 100 * inchesToCentimeters(value / 12)

fun metersToFeet(value: Double): Double = 1 / (100 * inchesToCentimeters(value / 12))

fun ticksToMeters(ticks: Double): Double = inchesToCentimeters(encoderTicksToInches(ticks)) * 100

fun getEncPosition(ticks: Double): Double {
    return Math.PI * DriveTrain.WHEEL_DIAMETER_INCHES / ((
            if (Robot.driveTrain.mCLayout !== DriveTrainMCConfig.SPARKS)
                DriveTrain.MAG_ENCODER_TICKS_PER_REVOLUTION
            else
                DriveTrain.NEO_ENCODER_TICKS_PER_REVOLUTION)
            * DriveTrain.GEAR_RATIO) * ticks / 12
}

/**
 * Returns a value in ticks based on a certain value in feet using
 * the Magnetic Encoder.
 *
 * @param feet The value in feet
 * @return The value in ticks
 */
fun feetToEncoderTicks(feet: Double): Double = inchesToEncoderTicks(feet * 12)

fun inchesToEncoderTicks(inches: Double): Double {
    return inches / (Math.PI * DriveTrain.WHEEL_DIAMETER_INCHES) *
            if (Robot.driveTrain.mCLayout !== DriveTrainMCConfig.SPARKS)
                DriveTrain.MAG_ENCODER_TICKS_PER_REVOLUTION
            else
                DriveTrain.NEO_ENCODER_TICKS_PER_REVOLUTION
}

fun encoderTicksToInches(ticks: Double): Double {
    return (ticks / (
            if (Robot.driveTrain.mCLayout !== DriveTrainMCConfig.SPARKS)
                DriveTrain.MAG_ENCODER_TICKS_PER_REVOLUTION
            else
                DriveTrain.NEO_ENCODER_TICKS_PER_REVOLUTION)
            * (Math.PI * DriveTrain.WHEEL_DIAMETER_INCHES))
}

fun degreesToPigeonUnits(degrees: Double): Double =
        DriveTrain.PIGEON_NATIVE_UNITS_PER_ROTATION * degrees / 360

fun pigeonUnitsToDegrees(pigeonUnits: Double): Double =
        pigeonUnits * 360 / DriveTrain.PIGEON_NATIVE_UNITS_PER_ROTATION

fun radiansToPigeonUnits(radians: Double): Double =
        DriveTrain.PIGEON_NATIVE_UNITS_PER_ROTATION * Math.toDegrees(radians) / 360

fun encoderTicksToRevs(ticks: Double): Double {
    return ticks / if (Robot.driveTrain.mCLayout !== DriveTrainMCConfig.SPARKS)
        DriveTrain.MAG_ENCODER_TICKS_PER_REVOLUTION
    else
        DriveTrain.NEO_ENCODER_TICKS_PER_REVOLUTION
}

fun revsToEncoderTicks(revs: Double): Double {
    return revs * if (Robot.driveTrain.mCLayout !== DriveTrainMCConfig.SPARKS)
        DriveTrain.MAG_ENCODER_TICKS_PER_REVOLUTION
    else
        DriveTrain.NEO_ENCODER_TICKS_PER_REVOLUTION
}

/**
 * <pre>
 * public double ticksPerTenthToRevsPerMinute(double ticksPerTenthSecond)
</pre> *
 * Returns value in revolutions per minute given ticks per tenth of a second.
 *
 * @param ticksPerTenthSecond
 * @return Revs per minute
 */
fun ticksPerTenthToRevsPerMinute(ticksPerTenthSecond: Double): Double {
    return ticksPerTenthSecond / if (Robot.driveTrain.mCLayout !== DriveTrainMCConfig.SPARKS)
        DriveTrain.MAG_ENCODER_TICKS_PER_REVOLUTION
    else
        DriveTrain.NEO_ENCODER_TICKS_PER_REVOLUTION * 600
}


fun revsPerMinuteToTicksPerTenth(revsPerMinute: Double): Double {
    return revsPerMinute * if (Robot.driveTrain.mCLayout !== DriveTrainMCConfig.SPARKS)
        DriveTrain.MAG_ENCODER_TICKS_PER_REVOLUTION
    else
        DriveTrain.NEO_ENCODER_TICKS_PER_REVOLUTION / 600
}

fun revsPerMinuteToMetersPerSecond(revsPerMinute: Double): Double =
    revsPerMinute * feetToMeters(DriveTrain.WHEEL_DIAMETER_INCHES * Math.PI / 12) / 60

fun ticksPerTenthToMetersPerSecond(ticksPerTenth: Double): Double =
    revsPerMinuteToMetersPerSecond(ticksPerTenthToRevsPerMinute(ticksPerTenth))


fun calculateFeedForward(rpm: Double): Double {
    val maxMotorOutput = 1023.0
    val nativeUnitsPer100 = rpm / 600 * DriveTrain.MAG_ENCODER_TICKS_PER_REVOLUTION
    return maxMotorOutput / nativeUnitsPer100
}