package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Blue Outer Autonomous Mode", group = "Blue")
public class BlueOuterAutonomousMode extends BlueAutonomousMode {
    @Override
    protected RobotDirection getScanStrafeDirection(RobotDirection direction) {
        if(direction.equals(RobotDirection.STRAFE_LEFT)){
            return RobotDirection.STRAFE_RIGHT;
        }
        else if(direction.equals(RobotDirection.STRAFE_RIGHT)){
            return RobotDirection.STRAFE_LEFT;
        }
        else {
            return direction;
        }
    }
}
