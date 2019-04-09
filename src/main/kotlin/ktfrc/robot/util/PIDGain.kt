package ktfrc.robot.util

data class PIDGain(val kP: Double,
                   val kI: Double,
                   val kD: Double,
                   val kF: Double,
                   val closedLoopMaxOutput: Double = 1.0)