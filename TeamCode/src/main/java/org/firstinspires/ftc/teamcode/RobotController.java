package org.firstinspires.ftc.teamcode;

public class RobotController {
    private final RobotHardware robot;

    public RobotController(RobotHardware poop) {
        this.robot = poop;
    }
    public void ringBearerUp() {
        robot.ringBearer0.setPosition(0.45);
        robot.ringBearer1.setPosition(0.55);
    }

    public void ringBearerDown() {
        robot.ringBearer0.setPosition(0);
        robot.ringBearer1.setPosition(1);
    }
    public void ringBearerHalfway() {
        robot.ringBearer0.setPosition(0.1);
        robot.ringBearer1.setPosition(0.9);
    }
    public void secureWobble() {
        robot.wobbleRelease.setPosition(0);


    }

    public void releaseWobble() {
        robot.wobbleRelease.setPosition(0.15);
    }
}
