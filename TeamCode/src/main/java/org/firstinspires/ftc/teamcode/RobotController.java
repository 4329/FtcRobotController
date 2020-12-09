package org.firstinspires.ftc.teamcode;

public class RobotController {
    private final RobotHardware robot;

    public RobotController(RobotHardware poop) {
        this.robot = poop;
    }
    public void ringBearerUp() {
        robot.ringBearer0.setPosition(0.3);
        robot.ringBearer1.setPosition(0.7);
    }

    public void ringBearerDown() {
        robot.ringBearer0.setPosition(0);
        robot.ringBearer1.setPosition(1);
    }
}
