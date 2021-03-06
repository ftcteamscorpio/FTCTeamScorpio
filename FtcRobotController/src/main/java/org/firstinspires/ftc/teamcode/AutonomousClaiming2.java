package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
@Disabled
@Autonomous
public class AutonomousClaiming2 extends LinearOpMode{
    private DcMotor motorTest;
    private DcMotor motor2;
    private DcMotor leftmotor;
    private DcMotor rightmotor;
    public void runOpMode() {
        waitForStart();
        //Init
        motorTest = hardwareMap.get(DcMotor.class, "leftmotor");
        leftmotor = motorTest;
        motor2 = hardwareMap.get(DcMotor.class, "rightmotor");
        rightmotor = motor2;
        //Straight
        leftmotor.setPower(-0.5);
        rightmotor.setPower(0.5);
        sleep(3000);
        //Turn
        sleep(1000);
        leftmotor.setPower(-0.25);
        rightmotor.setPower(-0.25);
        sleep(1300);
        //Straight
        leftmotor.setPower(-0.5);
        rightmotor.setPower(0.5);
        sleep(5000);
        //CLAIM
        leftmotor.setPower(0);
        rightmotor.setPower(0);
        Servo servo0 = hardwareMap.get(Servo.class, "servo0");
        servo0.setPosition(1);
        sleep(1000);
        leftmotor.setPower(0.5);
        rightmotor.setPower(-0.5);
        sleep(1000);
        /*//Backwards
        leftmotor.setPower(0.5);
        rightmotor.setPower(-0.5);
        sleep(5000);
        //Turn
        sleep(1000);
        leftmotor.setPower(-0.25);
        rightmotor.setPower(-0.25);
        sleep(1300);
        //Straight
        leftmotor.setPower(0.25);
        rightmotor.setPower(-0.25);
        sleep(1000);
        //DE END
        Servo servo1 = hardwareMap.get(Servo.class, "servo1");
        servo1.setPosition(0.7);
        sleep(1000);*/
    }
}
