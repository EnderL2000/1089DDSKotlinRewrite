package ktfrc.robot

object RobotMap {

    val CTRE_TIMEOUT = 10

    object CAN {
        val DRIVETRAIN_ML = 1
        val DRIVETRAIN_MR = 2
        val DRIVETRAIN_SL = 3
        val DRIVETRAIN_SR = 4
        val ELEVATOR_TALON = 5
        val CLAW_LEFT = 6
        val CLAW_RIGHT = 7
        val MOUTH_INTAKE = 8
        val CANIFIER = 9
        val PIGEON = 10
        val FANGS = 11
        val HATCH_EJECTOR = 12
        val SCREW_BL = 14
        val SCREW_BR = 15
        val SCREW_FRONT = 16
        val SCREW_DRIVE = 17
        val PCM = 18
    }

    object PCM {
        val HAB_ACTUATE = 0
        val HAB_RETRACT = 1
        val MOUTH_ACTUATE = 2
        val MOUTH_RETRACT = 3
    }

    object AIO {
        val LEFT_ULTRASONIC = 0
        val RIGHT_ULTRASONIC = 1
    }

    object PID {
        val PRIMARY_PID_LOOP = 0
    }

    object CANIFIER_PWM {
        val HATCH_LIDAR = 0
        val CARGO_LIDAR = 1
    }

    object PWM {
        val LIMELIGHT_SERVO = 1
    }

    object DIGITAL_INPUT {
        val ELEVATOR_LIMIT_SWITCH = 0
    }

    object DS_USB {
        val LEFT_STICK = 0
        val RIGHT_STICK = 1
        val GAMEPAD = 2
    }

    object GAMEPAD_AXIS {
        val leftX = 0
        val leftY = 1
        val leftTrigger = 2
        val rightTrigger = 3
        val rightX = 4
        val rightY = 5
    }

    object GAMEPAD_BUTTONS {
        val A = 1
        val B = 2
        val X = 3
        val Y = 4
        val LB = 5
        val RB = 6
        val BACK = 7
        val START = 8
        val L3 = 9
        val R3 = 10
    }

    object JOYSTICK_BUTTONS {
        val BTN1 = 1
        val BTN2 = 2
        val BTN3 = 3
        val BTN4 = 4
        val BTN5 = 5
        val BTN6 = 6
        val BTN7 = 7
        val BTN8 = 8
        val BTN9 = 9
        val BTN10 = 10
        val BTN11 = 11
    }
}