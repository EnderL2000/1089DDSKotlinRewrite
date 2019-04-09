package ktfrc.robot.commands.elevator

import edu.wpi.first.wpilibj.command.Command
import ktfrc.robot.Robot

class HoldElevatorPosition: Command() {

    init {
        requires(Robot.elevator)
    }

    override fun initialize() {
        Robot.elevator.elevatorLeader.setPosition(Robot.elevator.elevatorLeader.getEncTicks())
    }

    override fun isFinished(): Boolean = false
}