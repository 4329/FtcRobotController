/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;

/**
 * This file illustrates the concept of driving a path based on encoder counts.
 * It uses the common Pushbot hardware class to define the drive on the robot.
 * The code is structured as a LinearOpMode
 *
 * The code REQUIRES that you DO have encoders on the wheels,
 *   otherwise you would use: PushbotAutoDriveByTime;
 *
 *  This code ALSO requires that the drive Motors have been configured such that a positive
 *  power command moves them forwards, and causes the encoders to count UP.
 *
 *   The desired path in this example is:
 *   - Drive forward for 48 inches
 *   - Spin right for 12 Inches
 *   - Drive Backwards for 24 inches
 *   - Stop and close the claw.
 *
 *  The code is written using a method called: encoderDrive(speed, leftInches, rightInches, timeoutS)
 *  that performs the actual movement.
 *  This methods assumes that each movement is relative to the last stopping place.
 *  There are other ways to perform encoder based moves, but this method is probably the simplest.
 *  This code uses the RUN_TO_POSITION mode to enable the Motor controllers to generate the run profile
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

public abstract class AutonomousModeBase extends LinearOpMode {


    /* Declare OpMode members. */
    RobotHardware robot   = new RobotHardware();   //declaration
    RobotController robotController = new RobotController(robot);
    private ElapsedTime     runtime = new ElapsedTime();

    static final double     COUNTS_PER_MOTOR_REV    = 1120 ;
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                                                      (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 1.0;
    static final double     TURN_SPEED              = 0.5;
    private static final double STRAFE_SPEED = 0.2;
    static final double     STRAFE_BONUS_EXTRA_K    = 1.09; // when we ask the robot to move 48 it only moves 44 (11:12)

    @Override
    public void runOpMode() {

        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.update();

        robot.leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0",  "Starting at %7d :%7d, %7d :%7d",
                          robot.leftFrontDrive.getCurrentPosition(),
                          robot.rightFrontDrive.getCurrentPosition(),
                          robot.leftBackDrive.getCurrentPosition(),
                          robot.rightBackDrive.getCurrentPosition());
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)
        moveToScan();
        RingStack ringStack = scanRings();
            telemetry.addData("Ring_Stack", ringStack);

        telemetry.update();

        if(ringStack.equals(RingStack.NONE)) {
            moveToZoneAlpha();
        }
        else if(ringStack.equals(RingStack.ONE)) {
            moveToZoneBravo();
        }
        else {
            moveToZoneCharlie();

        }


    }

    protected void moveToZoneAlpha() {
        encoderDrive(DRIVE_SPEED, 18, getStrafeDirection(RobotDirection.STRAFE_LEFT), 3.33);
        encoderDrive(DRIVE_SPEED, 60, RobotDirection.BACKWARD, 5.75);
        encoderDrive(DRIVE_SPEED, 28, getStrafeDirection(RobotDirection.STRAFE_RIGHT), 4);
        robotController.releaseWobble();
        sleep(750);
        //robotController.ringBearerDown();
        encoderDrive(DRIVE_SPEED, 51.33, RobotDirection.BACKWARD, 5);
        encoderDrive(DRIVE_SPEED, 10, getStrafeDirection(RobotDirection.STRAFE_LEFT), 3.33);
        robotController.ringBearerUp();
        sleep(3000);
        robotController.ringBearerDown();
        encoderDrive(DRIVE_SPEED,  21, getStrafeDirection(RobotDirection.STRAFE_LEFT), 5);
        encoderDrive(DRIVE_SPEED, 108, RobotDirection.FORWARD, 5.75);
    }

    protected void moveToZoneBravo() {
        encoderDrive(DRIVE_SPEED, 18, getStrafeDirection(RobotDirection.STRAFE_LEFT), 3.33);
        encoderDrive(DRIVE_SPEED, 84, RobotDirection.BACKWARD, 6.50);
        encoderDrive(DRIVE_SPEED, 4, getStrafeDirection(RobotDirection.STRAFE_RIGHT), 1);
        robotController.releaseWobble();
        sleep(1000);
        //robotController.ringBearerDown();
        encoderDrive(DRIVE_SPEED, 27.50, RobotDirection.BACKWARD, 3.50);
        encoderDrive(DRIVE_SPEED, 19, getStrafeDirection(RobotDirection.STRAFE_RIGHT), 5);
        robotController.ringBearerUp();
        sleep(3000);
        robotController.ringBearerDown();
        encoderDrive(DRIVE_SPEED,  23, getStrafeDirection(RobotDirection.STRAFE_LEFT), 5);
        encoderDrive(DRIVE_SPEED, 108, RobotDirection.FORWARD, 5.75);
    }

    protected void moveToZoneCharlie() {
        encoderDrive(DRIVE_SPEED, 18,getStrafeDirection(RobotDirection.STRAFE_LEFT), 3.33);
        encoderDrive(DRIVE_SPEED, 106, RobotDirection.BACKWARD, 6.50);
        encoderDrive(DRIVE_SPEED,28, getStrafeDirection(RobotDirection.STRAFE_RIGHT), 4);
        robotController.releaseWobble();
        sleep(1000);
        //robotController.ringBearerDown();
        encoderDrive(DRIVE_SPEED, 5.5, RobotDirection.BACKWARD, 1);
        encoderDrive(DRIVE_SPEED, 10, getStrafeDirection(RobotDirection.STRAFE_LEFT), 3.33);
        robotController.ringBearerUp();
        sleep(3000);
        robotController.ringBearerDown();
        encoderDrive(DRIVE_SPEED,  21, getStrafeDirection(RobotDirection.STRAFE_LEFT), 5);
        encoderDrive(DRIVE_SPEED, 108, RobotDirection.FORWARD, 5.75);
    }

    protected void moveToScan(){
        encoderDrive(DRIVE_SPEED, 12, RobotDirection.BACKWARD, 2);
        encoderDrive(STRAFE_SPEED, 12, getScanStrafeDirection(RobotDirection.STRAFE_RIGHT), 2);
        sleep(250);
    }


    protected RingStack scanRings() {

        List<Recognition> recognitions = robotController.getRecognitions();
            if (recognitions == null){
                return RingStack.NONE;
            }
            else {
                for (Recognition re : recognitions) {
                    if (re.getLabel().equals("Single")){
                        return RingStack.ONE;
                    }
                    else if (re.getLabel().equals("Quad")){
                        return RingStack.FOUR;
                    }
                }
            }
        return RingStack.NONE;
    }


    /*
     *  Method to perform a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
     *  Move will stop if any of three conditions occur:
     *  1) Move gets to the desired position
     *  2) Move runs out of time
     *  3) Driver stops the opmode running.
     */
    public void encoderDrive(double speed,
                             double inches, RobotDirection direction,
                             double timeoutS) {
        int newLeftFrontTarget = 0;
        int newRightFrontTarget = 0;
        int newLeftBackTarget = 0;
        int newRightBackTarget = 0;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {
         int distance = (int) (inches * COUNTS_PER_INCH);
            if (direction.equals(RobotDirection.STRAFE_RIGHT)) {
                distance *= STRAFE_BONUS_EXTRA_K;
                newRightFrontTarget = robot.rightFrontDrive.getCurrentPosition()+(distance * -1);
                newLeftBackTarget = robot.leftBackDrive.getCurrentPosition()+(distance * -1);
                newRightBackTarget = robot.rightBackDrive.getCurrentPosition()+(distance);
                newLeftFrontTarget = robot.leftFrontDrive.getCurrentPosition()+(distance);
            }
            else if (direction.equals(RobotDirection.STRAFE_LEFT)) {
                distance *= STRAFE_BONUS_EXTRA_K;
                newRightFrontTarget = robot.rightFrontDrive.getCurrentPosition()+(distance);
                newLeftBackTarget = robot.leftBackDrive.getCurrentPosition()+(distance);
                newRightBackTarget = robot.rightBackDrive.getCurrentPosition()+(distance * -1);
                newLeftFrontTarget = robot.leftFrontDrive.getCurrentPosition()+(distance * -1);
            }
            else if (direction.equals(RobotDirection.TURN_LEFT)) {
                newRightFrontTarget = robot.rightFrontDrive.getCurrentPosition()+(distance);
                newLeftBackTarget = robot.leftBackDrive.getCurrentPosition()+(distance * -1);
                newRightBackTarget = robot.rightBackDrive.getCurrentPosition()+(distance);
                newLeftFrontTarget = robot.leftFrontDrive.getCurrentPosition()+(distance * -1);
            }
            else if (direction.equals(RobotDirection.FORWARD_RIGHT)) {
                newRightFrontTarget = robot.rightFrontDrive.getCurrentPosition();
                newLeftBackTarget = robot.leftBackDrive.getCurrentPosition();
                newRightBackTarget = robot.rightBackDrive.getCurrentPosition()+(distance);
                newLeftFrontTarget = robot.leftFrontDrive.getCurrentPosition()+(distance);
            }
            else if (direction.equals(RobotDirection.BACKWARD)) {
                newRightFrontTarget = robot.rightFrontDrive.getCurrentPosition()+(distance * -1);
                newLeftBackTarget = robot.leftBackDrive.getCurrentPosition()+(distance * -1);
                newRightBackTarget = robot.rightBackDrive.getCurrentPosition()+(distance * -1);
                newLeftFrontTarget = robot.leftFrontDrive.getCurrentPosition()+(distance * -1);
            }
            else if (direction.equals(RobotDirection.FORWARD)) {
                newLeftFrontTarget = robot.leftFrontDrive.getCurrentPosition() + distance;
                newRightFrontTarget = robot.rightFrontDrive.getCurrentPosition() + distance;
                newLeftBackTarget = robot.leftBackDrive.getCurrentPosition() + distance;
                newRightBackTarget = robot.rightBackDrive.getCurrentPosition() + distance;
            }
            robot.leftFrontDrive.setTargetPosition(newLeftFrontTarget);
            robot.rightFrontDrive.setTargetPosition(newRightFrontTarget);
            robot.leftBackDrive.setTargetPosition(newLeftBackTarget);
            robot.rightBackDrive.setTargetPosition(newRightBackTarget);
            // Turn On RUN_TO_POSITION
            robot.leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.leftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.leftFrontDrive.setPower(Math.abs(speed));
            robot.rightFrontDrive.setPower(Math.abs(speed));
            robot.leftBackDrive.setPower(Math.abs(speed));
            robot.rightBackDrive.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                   (runtime.seconds() < timeoutS) &&
                   (robot.leftFrontDrive.isBusy() && robot.rightFrontDrive.isBusy()
                           && robot.leftBackDrive.isBusy() && robot.rightBackDrive.isBusy())) {

//                // Display it for the driver.
                telemetry.addData("Targets",  "Running to %7d , %7d , %7d , %7d", newLeftFrontTarget,  newRightFrontTarget, newLeftBackTarget, newRightBackTarget);
                telemetry.addData("Currant",  "Running to %7d , %7d , %7d , %7d",
                                            robot.leftFrontDrive.getCurrentPosition(),
                                            robot.rightFrontDrive.getCurrentPosition(),robot.leftBackDrive.getCurrentPosition(),robot.rightBackDrive.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            robot.leftFrontDrive.setPower(0);
            robot.rightFrontDrive.setPower(0);
            robot.leftBackDrive.setPower(0);
            robot.rightBackDrive.setPower(0);
            // Turn off RUN_TO_POSITION
            robot.leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }
    protected abstract RobotDirection getStrafeDirection (RobotDirection direction);
    protected abstract RobotDirection getScanStrafeDirection(RobotDirection direction);


}
