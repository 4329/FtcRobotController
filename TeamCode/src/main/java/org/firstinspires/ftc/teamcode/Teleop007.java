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

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This file contains an example of an iterative (Non-Linear) "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all iterative OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="T-E-L-E-O-P")
public class Teleop007 extends OpMode
{
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();


    private static final double MINIMUM_STICK_VALUE = .2;
    private static final double STRAFE_CORRECTION_FACTOR = 1.5;
    private RobotHardware robotHardware = new RobotHardware();
    private RobotController robotController = new RobotController(robotHardware);
    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        robotHardware.init(hardwareMap);


        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        // Setup a variable for each drive wheel to save power level for telemetry
        double leftPower;
        double rightPower;

        // Choose to drive using either Tank Mode, or POV Mode
        // Comment out the method that's not used.  The default below is POV.

        // POV Mode uses left stick to go forward, and right stick to turn.
        // - This uses basic math to combine motions and is easier to drive straight.
        EngineValues engineValues = determineEnginePower(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x);

        if (gamepad2.y) {
                robotController.releaseWobble();
        }
        if (gamepad2.dpad_up) {
                robotController.endgameWobbleSnatcher();
        }
        if (gamepad2.x) {
                robotController.secureWobble();
        }
        if (gamepad2.left_stick_y > 0.0){
            robotController.ringBearerLower();
        }
        if (gamepad2.left_stick_y < 0.0){
            robotController.ringBearerRaise();
        }
        if (gamepad2.right_trigger > 0.0){
            robotController.nomNomRingTime();
        } else {
            robotController.noNomNoms();

        }
        if (gamepad2.right_stick_y > 0.3){
            robotController.slideCaterpillarDown();
        }
        else if (gamepad2.right_stick_y < -0.3){
            robotController.slideCaterpillarUp();
        }
        else {
            robotController.slideCaterpillarStop();
        }
        if (gamepad2.dpad_down) {
            robotController.wobbleSnatcherToggle();
        }
        if (gamepad2.left_bumper) {
            robotController.toggleFlipper();
        }
        if (gamepad2.right_bumper) {
            robotController.toggleFlipperClaw();
        }
        telemetry.addData("Left Stick y",  gamepad2.left_stick_y);
        // Tank Mode uses one stick to control each wheel.
        // - This requires no math, but it is hard to drive forward slowly and keep straight.
        // leftPower  = -gamepad1.left_stick_y ;
        // rightPower = -gamepad1.right_stick_y ;

        // Send calculated power to wheels
        robotHardware.leftFrontDrive.setPower(engineValues.leftFrontPower);
        robotHardware.leftBackDrive.setPower(engineValues.leftBackPower);
        robotHardware.rightFrontDrive.setPower(engineValues.rightFrontPower);
        robotHardware.rightBackDrive.setPower(engineValues.rightBackPower);

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Stick values", "left.x: (%.2f), left.y: (%.2f), right.x: (%.2f), right.y: (%.2f)", gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.right_stick_y);
//        telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

    public EngineValues determineEnginePower(double leftStickX, double leftStickY, double rightStickX) {
        EngineValues engineValues = new EngineValues();
        if (Math.abs(leftStickX) < MINIMUM_STICK_VALUE) {
            leftStickX = 0;
        }
        if (Math.abs(leftStickY) < MINIMUM_STICK_VALUE) {
            leftStickY = 0;
        }
        if (Math.abs(rightStickX) < MINIMUM_STICK_VALUE) {
            rightStickX = 0;
        }

        leftStickX = leftStickX * STRAFE_CORRECTION_FACTOR;

        engineValues.leftFrontPower = leftStickY + leftStickX + rightStickX;
        engineValues.leftBackPower = leftStickY - leftStickX + rightStickX;
        engineValues.rightFrontPower = leftStickY - leftStickX - rightStickX;
        engineValues.rightBackPower = leftStickY + leftStickX - rightStickX;

        if (Math.abs(engineValues.leftFrontPower) > 1 || Math.abs(engineValues.leftBackPower) > 1 ||
                Math.abs(engineValues.rightFrontPower) > 1 || Math.abs(engineValues.rightBackPower) > 1 ) {
            // Find the largest power
            double max = 0;
            max = Math.max(Math.abs(engineValues.leftFrontPower), Math.abs(engineValues.leftBackPower));
            max = Math.max(Math.abs(engineValues.rightFrontPower), max);
            max = Math.max(Math.abs(engineValues.rightBackPower), max);

            // Divide everything by max (it's positive so we don't need to worry
            // about signs)
            engineValues.leftFrontPower /= max;
            engineValues.leftBackPower /= max;
            engineValues.rightFrontPower /= max;
            engineValues.rightBackPower /= max;
        }

        return engineValues;
    }
}
