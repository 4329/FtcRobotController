package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Red Outer Autonomous Mode", group = "Red")

public class RedOuterAutonomousMode extends RedAutomousMode {
    @Override
    protected RobotDirection getScanStrafeDirection(RobotDirection direction) {
        return direction;
    }
}
