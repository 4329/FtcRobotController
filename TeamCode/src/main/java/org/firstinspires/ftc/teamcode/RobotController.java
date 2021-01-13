package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

public class RobotController {
    private final RobotHardware robot;
    private TFObjectDetector tfod;

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
    public void ringBearerLower() {
        double position0 = robot.ringBearer0.getPosition();
        double position1 = robot.ringBearer1.getPosition();
    position0 = Math.max(position0-.01,0);
    position1 = Math.min(position1+.01,1);
        robot.ringBearer0.setPosition(position0);
        robot.ringBearer1.setPosition(position1);
    }
    public void ringBearerRaise() {
        double position0 = robot.ringBearer0.getPosition();
        double position1 = robot.ringBearer1.getPosition();
        position0 = Math.min(position0+.01,0);
        position1 = Math.max(position1-.01,1);
        robot.ringBearer0.setPosition(position0);
        robot.ringBearer1.setPosition(position1);
    }

    public void secureWobble() {
        robot.wobbleRelease.setPosition(0.77);

    }

    public void releaseWobble() {
        robot.wobbleRelease.setPosition(1);
    }

    public void setTfod(TFObjectDetector tfod) {
        this.tfod = tfod;
    }

    public List<Recognition> getRecognitions() {
        return tfod.getUpdatedRecognitions();
    }

}
