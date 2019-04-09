package ktfrc.robot

import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.buttons.JoystickButton
import frc.robot.commands.hatchpanel.RunStinger
import ktfrc.robot.commands.cargo.ArticulateMouth
import ktfrc.robot.commands.cargo.IntakeCargo
import ktfrc.robot.commands.cargo.RunClaw
import ktfrc.robot.commands.climber.ArticulateFangs
import ktfrc.robot.commands.climber.ManualFangsControl
import ktfrc.robot.commands.climber.MoveLegs
import ktfrc.robot.commands.conditionals.ConditionalLevel2Elevator
import ktfrc.robot.commands.conditionals.ConditionalLevel3Elevator
import ktfrc.robot.commands.conditionals.UseElevator
import ktfrc.robot.commands.drivetrain.DriveWithJoysticks
import ktfrc.robot.commands.elevator.ManualElevator
import ktfrc.robot.subsystems.*
import ktfrc.robot.util.ShuffleDash

class OI {
    private val shuffleDash = ShuffleDash()

    private val leftJoystick = Joystick(RobotMap.DS_USB.LEFT_STICK)
    private val rightJoystick = Joystick(RobotMap.DS_USB.RIGHT_STICK)
    private val gamepad = Joystick(RobotMap.DS_USB.GAMEPAD)

    //region Joystick Buttons

    private val left1 = JoystickButton(leftJoystick, RobotMap.JOYSTICK_BUTTONS.BTN1)
    private val left2 = JoystickButton(leftJoystick, RobotMap.JOYSTICK_BUTTONS.BTN2)
    private val left3 = JoystickButton(leftJoystick, RobotMap.JOYSTICK_BUTTONS.BTN3)
    private val left4 = JoystickButton(leftJoystick, RobotMap.JOYSTICK_BUTTONS.BTN4)
    private val left5 = JoystickButton(leftJoystick, RobotMap.JOYSTICK_BUTTONS.BTN5)
    private val left6 = JoystickButton(leftJoystick, RobotMap.JOYSTICK_BUTTONS.BTN6)
    private val left7 = JoystickButton(leftJoystick, RobotMap.JOYSTICK_BUTTONS.BTN7)
    private val left8 = JoystickButton(leftJoystick, RobotMap.JOYSTICK_BUTTONS.BTN8)
    private val left9 = JoystickButton(leftJoystick, RobotMap.JOYSTICK_BUTTONS.BTN9)
    private val left10 = JoystickButton(leftJoystick, RobotMap.JOYSTICK_BUTTONS.BTN10)
    private val left11 = JoystickButton(leftJoystick, RobotMap.JOYSTICK_BUTTONS.BTN11)

    private val right1 = JoystickButton(rightJoystick, RobotMap.JOYSTICK_BUTTONS.BTN1)
    private val right2 = JoystickButton(rightJoystick, RobotMap.JOYSTICK_BUTTONS.BTN2)
    private val right3 = JoystickButton(rightJoystick, RobotMap.JOYSTICK_BUTTONS.BTN3)
    private val right4 = JoystickButton(rightJoystick, RobotMap.JOYSTICK_BUTTONS.BTN4)
    private val right5 = JoystickButton(rightJoystick, RobotMap.JOYSTICK_BUTTONS.BTN5)
    private val right6 = JoystickButton(rightJoystick, RobotMap.JOYSTICK_BUTTONS.BTN6)
    private val right7 = JoystickButton(rightJoystick, RobotMap.JOYSTICK_BUTTONS.BTN7)
    private val right8 = JoystickButton(rightJoystick, RobotMap.JOYSTICK_BUTTONS.BTN8)
    private val right9 = JoystickButton(rightJoystick, RobotMap.JOYSTICK_BUTTONS.BTN9)
    private val right10 = JoystickButton(rightJoystick, RobotMap.JOYSTICK_BUTTONS.BTN10)
    private val right11 = JoystickButton(rightJoystick, RobotMap.JOYSTICK_BUTTONS.BTN11)

    private val gamepadA = JoystickButton(gamepad, RobotMap.GAMEPAD_BUTTONS.A)
    private val gamepadB = JoystickButton(gamepad, RobotMap.GAMEPAD_BUTTONS.B)
    private val gamepadX = JoystickButton(gamepad, RobotMap.GAMEPAD_BUTTONS.X)
    private val gamepadY = JoystickButton(gamepad, RobotMap.GAMEPAD_BUTTONS.Y)
    private val gamepadRB = JoystickButton(gamepad, RobotMap.GAMEPAD_BUTTONS.RB)
    private val gamepadLB = JoystickButton(gamepad, RobotMap.GAMEPAD_BUTTONS.LB)
    private val gamepadBack = JoystickButton(gamepad, RobotMap.GAMEPAD_BUTTONS.BACK)
    private val gamepadStart = JoystickButton(gamepad, RobotMap.GAMEPAD_BUTTONS.START)
    private val gamepadLeftStickButton = JoystickButton(gamepad, RobotMap.GAMEPAD_BUTTONS.L3)
    private val gamepadRightStickButton = JoystickButton(gamepad, RobotMap.GAMEPAD_BUTTONS.R3)

    //endregion

    init {
        left1.whenPressed(IntakeCargo())
        //left3.whenPressed() Track Target
        left4.whenPressed(DriveWithJoysticks())
        left5.whenPressed(ArticulateFangs(FangsPosition.IN_BOT))

        right1.whenPressed(RunStinger())
        right2.whenPressed(RunClaw(ClawState.EJECTING))
        right3.whenPressed(ArticulateMouth(MouthArticulatorPosition.OUT))
        right4.whenPressed(ArticulateMouth(MouthArticulatorPosition.IN))

        gamepadA.whenPressed(UseElevator(ElevatorPosition.BOTTOM))
        gamepadB.whenPressed(ManualElevator())
        gamepadX.whenPressed(UseElevator(ElevatorPosition.CARGOSHIP_C))
        gamepadY.whenPressed(UseElevator(ElevatorPosition.ROCKET_1_C))
        gamepadLB.whenPressed(ConditionalLevel2Elevator())
        gamepadRB.whenPressed(ConditionalLevel3Elevator())
        gamepadBack.whenPressed(MoveLegs(LegsPosition.OUT))
        gamepadStart.whenPressed(MoveLegs(LegsPosition.IN))
        gamepadLeftStickButton.whenPressed(ManualFangsControl())
    }

    fun getJoystickX(port: Int): Double { //TODO Enum instead of int port
        return when(port) {
            RobotMap.DS_USB.LEFT_STICK -> leftJoystick.x
            RobotMap.DS_USB.RIGHT_STICK -> rightJoystick.x
            else -> 0.0
        }
    }

    fun getJoystickY(port: Int): Double { //Enum instead of int port
        return when(port) {
            RobotMap.DS_USB.LEFT_STICK -> leftJoystick.y
            RobotMap.DS_USB.RIGHT_STICK -> rightJoystick.y
            else -> 0.0
        }
    }

    fun getJoystickZ(port: Int): Double { //Enum instead of int port
        return when(port) {
            RobotMap.DS_USB.LEFT_STICK -> leftJoystick.z
            RobotMap.DS_USB.RIGHT_STICK -> rightJoystick.z
            else -> 0.0
        }
    }

    fun getGamepadAxis(axis: Int): Double = //TODO Enum instead of int for axis
            (if(axis % 2 != 0 && axis != 3) -1.0 else 1.0) * gamepad.getRawAxis(axis)

    fun updateDash() = shuffleDash.updateDash()

    companion object {
        const val DEADZONE = 0.08
    }
}