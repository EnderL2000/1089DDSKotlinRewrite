package ktfrc.robot.util

import ktfrc.robot.util.motorControllers.IMercMotorController
import org.apache.logging.log4j.LogManager

class DriveAssist(private val leftController: IMercMotorController,
                  private val rightController: IMercMotorController,
                  var driveDirection: DriveDirection) {
    private val LOG = LogManager.getLogger(DriveAssist::class.java)
    var maxOutput = 1.0

    fun arcadeDrive(moveVal: Double, rotateVal: Double, shouldSquareInputs: Boolean = false) {
        var mov = clamp(moveVal, -1.0, 1.0)
        var rot = clamp(rotateVal, -1.0, 1.0)

        if(shouldSquareInputs) {
            mov = Math.copySign(mov * mov, mov)
            rot = Math.copySign(rot * rot, rot)
        }

        var leftPercent: Double
        var rightPercent: Double

        if(mov > 0) {
            if(rot > 0) {
                leftPercent = Math.max(mov, rot)
                rightPercent = mov - rot
            }
            else {
                leftPercent = mov + rot
                rightPercent = Math.max(mov, -rot)
            }
        }
        else {
            if(rot > 0) {
                leftPercent = mov + rot
                rightPercent = -Math.max(-mov, rot)
            }
            else {
                leftPercent = -Math.max(-mov, -rot)
                rightPercent = mov - rot
            }
        }

        leftPercent = clamp(leftPercent, 1.0, 1.0)
        rightPercent = clamp(rightPercent, 1.0, 1.0)

        leftPercent = applyDeadzone(leftPercent)
        rightPercent = applyDeadzone(rightPercent)

        leftController.setSpeed(leftPercent * maxOutput)
        rightController.setSpeed(rightPercent * maxOutput)
    }

    fun tankDrive(leftVal: Double, rightVal: Double) {
        leftController.setSpeed(leftVal * maxOutput)
        rightController.setSpeed(rightVal * maxOutput)
    }
}

enum class DriveDirection(val dir: Double) {
    HATCH(1.0),
    CARGO(-1.0)
}