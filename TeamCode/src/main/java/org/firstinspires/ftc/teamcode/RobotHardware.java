package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

public class RobotHardware {

    /* Public OpMode members. */
    public DcMotor leftFrontDrive = null;
    public DcMotor rightFrontDrive = null;
    public DcMotor leftBackDrive = null;
    public DcMotor rightBackDrive = null;
    public DcMotor hungryHungryCaterpillar = null;
    public DcMotor caterpillarSlider = null;


    public Servo ringBearer0 = null;
    public Servo ringBearer1 = null;
    public Servo wobbleRelease = null;
    public Servo wobbleSnatcher = null;
public Servo flipperFlipLeft= null;
public Servo flipperFlipRight= null;
    public Servo flipperClawLeft= null;
public Servo flipperClawRight= null;
public Servo ringbouncer= null;



    private static final String VUFORIA_KEY =
        "AT65lYD/////AAABmYLSlMYJ50sZuYO3mRZFMtApeNGp82g4hf/Trb2fI6/FfDhf6CoeqAyeZLBQgpLF9rYRV4krMK5JW/TiqGulVw0fDMOfOQjc03Qs8YkFrIT6rWLRVlvS2NoZgDAGDHEEZvf/S1c34clJmw45b7uzcJYgVxdlRoSM7uU/u4ne8+aikzB4MWu4xybCUVsFl44lW/2acUNJmJ1XFjkspO/TP1M/s42NTOBNPeMA+6sy8wIbvtn3BRrQnklOnIGYSaRNC/Yl2UTuUCBzM5fPB2eRBS+e9hgJNORHm29YKfprz0dM6Ah0ubrrQdI2HLlGGUiqO6JnKtASw0SYNvdSKIZG4VU5fI7MTvjHenteZOeLbHl3";

    private static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Quad";
    private static final String LABEL_SECOND_ELEMENT = "Single";
    private VuforiaLocalizer vuforia;
    protected TFObjectDetector tfod;



    /* local OpMode members. */
    HardwareMap hardwareMap =  null;
    private ElapsedTime period  = new ElapsedTime();

    private RobotController robotController = new RobotController(this);

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hardwareMap = ahwMap;

        // Define and Initialize Motors
        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        leftFrontDrive = hardwareMap.get(DcMotor.class, "left_front_drive");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "right_front_drive");
        leftBackDrive = hardwareMap.get(DcMotor.class, "left_back_drive");
        rightBackDrive = hardwareMap.get(DcMotor.class, "right_back_drive");
        ringBearer0 = hardwareMap.get(Servo.class, "ringBearer0");
        ringBearer1 = hardwareMap.get(Servo.class, "ringBearer1");
        //wobbleRelease = hardwareMap.get(Servo.class, "wobbleRelease");
        hungryHungryCaterpillar = hardwareMap.get(DcMotor.class, "hungry_hungry_caterpillar");
        caterpillarSlider = hardwareMap.get(DcMotor.class, "caterpillar_slider");
        wobbleSnatcher = hardwareMap.get(Servo.class, "wobble_snatcher");
flipperFlipRight = hardwareMap.get(Servo.class,"flipperFlipRight");
flipperFlipLeft = hardwareMap.get(Servo.class, "flipperFlipLeft");
flipperClawLeft= hardwareMap.get (Servo.class, "flipperClawLeft");
flipperClawRight = hardwareMap.get(Servo.class,"flipperClawRight");
ringbouncer= hardwareMap.get(Servo.class,"ringbouncer");
        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);
        hungryHungryCaterpillar.setDirection(DcMotor.Direction.FORWARD);
        caterpillarSlider.setDirection(DcMotor.Direction.FORWARD);
        // Set all motors to zero power
        leftFrontDrive.setPower(0);
        rightFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightBackDrive.setPower(0);
        hungryHungryCaterpillar.setPower(0);
        caterpillarSlider.setPower(0);
//
//        // Set all motors to run without encoders.
//        // May want to use RUN_USING_ENCODERS if encoders are installed.
//        leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        leftArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //wobbleRelease.setDirection(Servo.Direction.REVERSE);
        robotController.ringBearerDown();
        robotController.secureWobble();
        robotController.wobbleSnatcherClose();
        robotController.flipperClawsClose();
        robotController.flipperHalfway();
        robotController.ringbouncerDown();
        initVuforia();
        initTfod();

    }
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "goldenEye");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.8f;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
        if (tfod != null) {
            tfod.activate();
            tfod.setZoom(1.25, 1.77);
        }
    }
}

