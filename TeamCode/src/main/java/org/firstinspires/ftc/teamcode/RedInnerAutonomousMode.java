package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Red Inner Autonomous Mode", group = "Red")
public class RedInnerAutonomousMode extends RedAutomousMode {
    @Override
    protected RobotDirection getScanStrafeDirection(RobotDirection direction) {
        return getStrafeDirection(direction);
    }
}
