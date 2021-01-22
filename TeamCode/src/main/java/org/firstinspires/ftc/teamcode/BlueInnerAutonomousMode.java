package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Blue Inner Autonomous Mode", group = "Blue")
public class BlueInnerAutonomousMode extends BlueAutonomousMode {

    @Override
    protected RobotDirection getScanStrafeDirection(RobotDirection direction) {
        return getStrafeDirection(direction);
    }
}
