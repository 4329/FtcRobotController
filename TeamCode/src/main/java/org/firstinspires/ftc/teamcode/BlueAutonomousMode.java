package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

//Extends Autonomous Mode Class for Blue Team
public abstract class BlueAutonomousMode extends AutonomousModeBase {

    @Override
    protected RobotDirection getStrafeDirection(RobotDirection direction) {
        return direction;
    }
}
