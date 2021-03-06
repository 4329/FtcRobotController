package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;

import static java.lang.Thread.sleep;

public class RobotController {
    private final RobotHardware robot;

    double position0 = 0;
    double position1 = 0;
    boolean wobbleSnatcherClosed = false;
    boolean flipperDeployed = false;
    boolean flipperClawsClosed = false;
    boolean clawsOnMat = false;
    boolean ringBouncerDown= false;

    public RobotController(RobotHardware poop) {
        this.robot = poop;
    }
    public void ringBearerUp() {
        position0 = 0.95;
        position1 = 0.05;
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
    public void  ringBearerSpasm() {

       long sleeptime = 100;
        position0 = 0.55;
        position1 = 0.45;
        moveRingBearer();
        sleep(200);
        for (int i = 0; i < 5; i++) {
            position0 = 0.5;
            position1 = 0.5;
            moveRingBearer();
            sleep(sleeptime);
            position0 = 0.55;
            position1 = 0.45;
            moveRingBearer();
            sleep(sleeptime);
        }
       moveRingBearer();
       sleep(150);
        position0 = 0.60;
        position1 = 0.40;



    }
    public void ringBearerHalfway() {
        position0 = 0.15;
        position1 = 0.85;
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
        //robot.wobbleRelease.setPosition(1);
        ringBearerDown();
    }

    public void releaseWobble() {
        //robot.wobbleRelease.setPosition(0.77);
        ringBearerHalfway();
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

    public void slideCaterpillarDown() {
        robot.caterpillarSlider.setPower(0.5);
    }

    public void slideCaterpillarUp() {
        robot.caterpillarSlider.setPower(-0.5);
    }

    public void slideCaterpillarStop() {
        robot.caterpillarSlider.setPower(0);
    }

    public void wobbleSnatcherToggle() {
        if (wobbleSnatcherClosed == true) {
            wobbleSnatcherOpen();
        }
        else {
            wobbleSnatcherClose ();
        }
        sleep (200);
    }

    public void wobbleSnatcherClose() {
        robot.wobbleSnatcher.setPosition(0);
        wobbleSnatcherClosed = true;
    }
    public void wobbleSnatcherOpen() {
        robot.wobbleSnatcher.setPosition(0.7);
        wobbleSnatcherClosed = false;
    }
    public final void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void flipperClawsClose() {
        robot.flipperClawLeft.setPosition(0);
        robot.flipperClawRight.setPosition(1);
        flipperClawsClosed = true;

    }

    public void flipperRetract() {
        robot.flipperFlipLeft.setPosition(1);
        robot.flipperFlipRight.setPosition(0);
        flipperDeployed = false;
    }
    public void flipperClawsOpen() {
        robot.flipperClawLeft.setPosition(.25);
        robot.flipperClawRight.setPosition(.75);
        flipperClawsClosed = false;
    }

    public void flipperDeploy() {
        robot.flipperFlipLeft.setPosition(.4);
        robot.flipperFlipRight.setPosition(.6);
        flipperDeployed = true;
        clawsOnMat = true;
    }
    public void flipperFloat() {
        robot.flipperFlipLeft.setPosition(.44);
        robot.flipperFlipRight.setPosition(.56);
        flipperDeployed = true;
        clawsOnMat = false;
    }

    public void toggleFlipperClaw() {
        if (flipperClawsClosed) {
            flipperClawsOpen();
        }
        else {
            if (!clawsOnMat) {
                flipperDeploy();
                sleep(35);
            }
            flipperClawsClose();
            sleep(70);
            flipperFloat();
        }
        sleep (200);
    }

    public void toggleFlipper() {
        if (flipperDeployed) {
            flipperRetract();
        }
        else {
            flipperFloat();
            flipperClawsOpen();
        }
        sleep (200);
    }
    public void flipperHalfway () {
        flipperDeployed = false;
        robot.flipperFlipLeft.setPosition(.85);
        robot.flipperFlipRight.setPosition(.15);
    }

    public void endgameWobbleSnatcher() {
        position0 = 0.85;
        position1 = 0.15;
        moveRingBearer();
        wobbleSnatcherOpen();
    }
    public void wobbleDrive() {
        position0 = 0.75;
        position1 = 0.25;
        moveRingBearer();
    }

    public void ringbouncerDown() {
        robot.ringbouncer.setPosition(1);
        ringBouncerDown= true;
    }
    public void ringbouncerUp() {
        robot.ringbouncer.setPosition(.75);
        ringBouncerDown= false;
    }

    public void toggleRingBouncer() {
        if (ringBouncerDown) {
            if (flipperDeployed) {
                ringbouncerUp();
            }
        }
        else {
            if (flipperDeployed) {
                ringbouncerDown();
            }
        }
        sleep(200);
    }
}
