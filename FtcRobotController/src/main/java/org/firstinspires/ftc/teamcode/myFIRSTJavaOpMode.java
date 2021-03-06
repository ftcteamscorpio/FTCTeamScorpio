package org.firstinspires.ftc.teamcode;
//R CHANGE
//Import stuff
import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class myFIRSTJavaOpMode extends LinearOpMode{
    //Create Variables
    //private Gyroscope imu;
    private DcMotor leftmotor;
    private DcMotor rightmotor;
    private DcMotor leftmotor2;
    private DcMotor rightmotor2;
    private DcMotor motor3;
    double tgtPower = 0;
    double tgtpower2 = 0;
    /*private DigitalChannel digitalTouch;
    private DistanceSensor sensorColorRange;
    private Servo servoTest;*/

    @Override

    public void runOpMode () {
        //Map the sensors
        //imu = hardwareMap.get(Gyroscope.class, "imu");

        //Initalize Motors
        leftmotor = hardwareMap.get(DcMotor.class, "leftmotor");
        rightmotor = hardwareMap.get(DcMotor.class, "rightmotor");
        leftmotor2 = hardwareMap.get(DcMotor.class, "leftmotor2"); //MECANUM
        rightmotor2 = hardwareMap.get(DcMotor.class, "rightmotor2"); //MECANUM
        motor3 = hardwareMap.get(DcMotor.class, "atach");
        /*digitalTouch = hardwareMap.get(DigitalChannel.class, "digitalTouch");
        sensorColorRange = hardwareMap.get(DistanceSensor.class, "sensorColorRange");
        servoTest = hardwareMap.get(Servo.class, "servoTest");*/

        //Wait for Start
        waitForStart();
        while (opModeIsActive()) {
            newmotor(); //Control the basic drivetrain using the controller (Refer to newmotor function)/good
            //servoMotor(); //Do the servos
            landing(); //good
            arm(); //for testing
            mecanum(); //good
            spinner();
            armmove();
            servoforbox();
        }
    }
    public void servoforbox () {
        Servo servo1 = hardwareMap.get(Servo.class,"servo1");
        Servo servo2 = hardwareMap.get(Servo.class,"servo2");

        if (gamepad2.x) {
            servo1.setPosition(0);
            servo2.setPosition(1);
        }
        if (gamepad2.b) {
            servo1.setPosition(0.5);
            servo2.setPosition(0.5);
        }
    }
    public void armmove () {
        DcMotor atach2 = hardwareMap.get(DcMotor.class, "atach2");
        if (gamepad2.right_stick_y < 0) {
            atach2.setPower(0.75);
        }
        else if (gamepad2.right_stick_y > 0) {
            atach2.setPower(-0.75);
        }
        else {
            atach2.setPower(0);
        }
    }
    public void spinner () {
        CRServo spinner = hardwareMap.get(CRServo.class, "spinner");
        if (gamepad2.right_trigger != 0) {
            spinner.setPower(1);
        }
        else if (gamepad2.left_trigger != 0) {
            spinner.setPower(-1);
        }
        else if (gamepad2.right_bumper) {
            spinner.setPower(0);
        }
    }
    public void oldmotor () {
        tgtPower = this.gamepad1.right_stick_y;
        tgtpower2 = -this.gamepad1.right_stick_y;
        //Set the motor according to the gamepad
        leftmotor.setPower(tgtPower);
        rightmotor.setPower(tgtpower2);
        //Display the target motor power
        telemetry.addData("Target Power", tgtPower);
        telemetry.addData("X Axis", this.gamepad1.right_stick_x);
        //Display the actual motor power
        telemetry.addData("Motor Power", leftmotor.getPower());
        //Display that the status is running
        telemetry.addData("Status", "Running");
        //Update the screen
        telemetry.update();
    }
    int cool = 0;
    boolean slowmode = false;
    public void newmotor () {
        //Set the base to the y of the speed joystick (add 1.5 because range is -1 to 0) which is speed
        //not accounting for the turn
        double base = Range.scale((-0.8*-1) + 1.3,0.3,1.3, 0,0.8);
        //if slow mode
        if (gamepad1.a) {
            slowmode = true;
        }
        //if deactivate slow mode
        if (gamepad1.y) {
            slowmode = false;
        }
        if (slowmode) {
            base = Range.scale((-0.5*-1) + 1.3,0.3,1.3, 0,0.8);
        }
        else {
            base = Range.scale((-0.8*-1) + 1.3,0.3,1.3, 0,0.8);
        }
        //If left joystick forward
        if (gamepad1.right_stick_y > 0) {
            //Create rightspeed based on base and scale it appropriately
            double leftspeed =  Range.scale(base /*+ gamepad1.right_stick_x*/,-2.3,2.3,-1,1);
            //Create rightspeed based on base and scale it appropriately
            double rightspeed = Range.scale(base * -1,-2.3,2.3,-1,1);
            if (gamepad1.right_stick_x < 0) {
                //If gamepad is left make the right wheel move in the opposite direction, creating a spinning turn
                rightspeed *= -1;
            }
            else if (gamepad1.right_stick_x > 0) {
                //If gamepad is right make the left wheel move in the opposite direction, creating a spinning turn
                leftspeed *= -1;
            }
            //Apply rightspeed and leftspeed appropriately
            rightmotor.setPower(rightspeed);
            rightmotor2.setPower(rightspeed); //MECANUM
            leftmotor.setPower(leftspeed);
            leftmotor2.setPower(leftspeed); //MECANUM

        }
        //If left joystick back
        else if (gamepad1.right_stick_y < 0) {
            //Create rightspeed based on base and sacale it appropriately
            double leftspeed = Range.scale((base /*+ gamepad1.right_stick_x*/) * -1,-2.3,2.3,-1,1);
            //Create leftspeed based on base and scale it appropriately
            double rightspeed = Range.scale(base * -1 * -1, -2.3,2.3,-1,1);
            if (gamepad1.right_stick_x < 0) {
                //If gamepad is left make the right wheel move in the opposite direction, creating a spinning turn
                rightspeed *= -1;
            }
            else if (gamepad1.right_stick_x > 0) {
                //If gamepad is right make the left wheel move in the opposite direction, creating a spinning turn
                leftspeed *= -1;
            }
            //Apply rightspeed and leftspeed approriately
            leftmotor.setPower(leftspeed);
            leftmotor2.setPower(leftspeed); //MECANUM
            rightmotor.setPower(rightspeed);
            rightmotor2.setPower(rightspeed); //MECANUM
        }
        double b;
        if (slowmode) {
            b = 0.5;
        }
        else {
            b = 1;
        }
        //If left joystick backward
        if (gamepad1.left_stick_y > 0) {
            //Move backward
            leftmotor.setPower(-b);
            rightmotor.setPower(b); //MECANUM
            leftmotor2.setPower(-b);
            rightmotor2.setPower(b); //MECANUM
        }
        //If left joystick forward
        else if (gamepad1.left_stick_y < 0) {
            //Move forwards
            leftmotor.setPower(b);
            leftmotor2.setPower(b); //MECANUM
            rightmotor.setPower(-b);
            rightmotor2.setPower(-b); //MECANUM
        }
        //If nothing pressed
        else {
            //Stop
            leftmotor.setPower(0);
            leftmotor2.setPower(0); //MECANUM
            rightmotor.setPower(0);
            rightmotor2.setPower(0); //MECANUM
        }
        //Print everything
        telemetry.addData("Left Joystick X", gamepad1.left_stick_x);
        telemetry.addData("Left Joystick Y", gamepad1.left_stick_y);
        telemetry.addData("Right Joystick X", gamepad1.right_stick_x);
        telemetry.addData("Right Joystick Y", base);
        telemetry.addData("Left Motor Speed", leftmotor.getPower());
        telemetry.addData("Right Motor Speed", rightmotor.getPower());
        telemetry.update();
    }
    /*public void servoMotor () {
        //
        Servo servo1 = hardwareMap.get(Servo.class, "servo1");
        servo1.setPosition(Range.scale(gamepad2.left_stick_x,-1,1,0,1));
        telemetry.addData("DA ONE (SERVO POS)", Range.scale(gamepad2.left_stick_x,-1,1,0.5,1));
        telemetry.update();
    }*/
    public void landing () {
        //If "a" (gamepad 2) pressed
        if (gamepad2.a) {
            //move landing gear down
            motor3.setPower(1);
        }
        //If "y" (gamepad 2) pressed
        else if (gamepad2.y) {
            //move landing gear up
            motor3.setPower(-1);
        }
        //If none pressed
        else {
            //stop landing gear
            motor3.setPower(0);
        }
    }
    public void arm () {
        //Create DcMotor arm and arm2
        DcMotor arm = hardwareMap.get(DcMotor.class, "arm");
        DcMotor arm2 = hardwareMap.get(DcMotor.class, "arm2");
        //If left joystick down
        if (gamepad2.left_stick_y > 0) {
            //Move arm forwards
            arm.setPower(0.75);
            arm2.setPower(-0.75);
        }
        //If left joystick up
        else if (gamepad2.left_stick_y < 0) {
            //Move arm forwards
            arm.setPower(-0.75);
            arm2.setPower(0.75);
        }
        //If none of the above pressed
        else {
            //Stop the arm
            arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            arm.setPower(0);
            arm2.setPower(0);
        }
    }
    public void mecanum () {
         if (gamepad1.left_trigger != 0) {
            //Move sideways left
            leftmotor.setPower(1 * -1);
            rightmotor.setPower(1 * -1);
            leftmotor2.setPower(1);
            rightmotor2.setPower(1);
        }
        //If right trigger pressed
        else if (gamepad1.right_trigger != 0) {
            //Move sideways right
            leftmotor.setPower(1);
            rightmotor.setPower(1);
            leftmotor2.setPower(1 * -1);
            rightmotor2.setPower(1 * -1);
        }
    }
}
