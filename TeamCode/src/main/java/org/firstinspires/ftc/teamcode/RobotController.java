package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;

public class RobotController {
    private final RobotHardware robot;

    double position0 = 0;
    double position1 = 0;

    public RobotController(RobotHardware poop) {
        this.robot = poop;
    }
    public void ringBearerUp() {
        position0 = 0.45;
        position1 = 0.55;
        moveRingBearer();

    }

    private void moveRingBearer() {
        robot.ringBearer0.setPosition(position0);
        robot.ringBearer1.setPosition(position1);
    }

    public void ringBearerDown() {
        position0 = 0.0;
        position1 = 1.0;
        moveRingBearer();
    }
    public void ringBearerHalfway() {
        position0 = 0.1;
        position1 = 0.9;
        moveRingBearer();
    }
    public void ringBearerLower() {
    position0 = Math.max(position0 - .01, 0.0);
    position1 = Math.min(position1 + .01, 1.0);
        moveRingBearer();
    }
    public void ringBearerRaise() {
        position0 = Math.min(position0 + .01, 1.0);
        position1 = Math.max(position1 - .01, 0.0);
        moveRingBearer();
    }

    public void secureWobble() {
        robot.wobbleRelease.setPosition(0.77);

    }

    public void releaseWobble() {
        robot.wobbleRelease.setPosition(1);
    }


    public List<Recognition> getRecognitions() {
        return robot.tfod.getUpdatedRecognitions();
    }

    public void nomNomRingTime() {
        robot.hungryHungryCaterpillar.setPower(1);
    }

    public void noNomNoms() {
        robot.hungryHungryCaterpillar.setPower(0);
    }
}
