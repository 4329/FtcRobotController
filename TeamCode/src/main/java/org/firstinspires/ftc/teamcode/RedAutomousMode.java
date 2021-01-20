package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Red Autonomous Mode")
public class RedAutomousMode extends AutonomousModeBase{
    @Override
    protected RobotDirection getStrafeDirection(RobotDirection direction) {
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
