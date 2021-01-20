package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

//Extends Autonomous Mode Class for Blue Team
@Autonomous(name = "Blue Autonomous Mode")
public class BlueAutonomousMode extends AutonomousModeBase {

    @Override
    protected RobotDirection getStrafeDirection(RobotDirection direction) {
        return direction;
    }
}
