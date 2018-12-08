package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.Range;
@Autonomous
public class AutonomousClaiming extends LinearOpMode{
    private DcMotor motorTest;
    private DcMotor motor2;
    private DcMotor leftmotor;
    private DcMotor rightmotor;
    public void runOpMode() {
        waitForStart();
        //Init
        motorTest = hardwareMap.get(DcMotor.class, "motorTest");
        leftmotor = motorTest;
        motor2 = hardwareMap.get(DcMotor.class, "motor2");
        rightmotor = motor2;
        //Straight
        leftmotor.setPower(-0.5);
        rightmotor.setPower(0.5);
        sleep(4000);
        //CLAIM
        leftmotor.setPower(0);
        rightmotor.setPower(0);
        Servo servo0 = hardwareMap.get(Servo.class, "servo0");
        servo0.setPosition(180);
        //Turn
        sleep(1000);
        leftmotor.setPower(0.5);
        rightmotor.setPower(0.5);
        sleep(600);
        //Backwards
        leftmotor.setPower(0.5);
        rightmotor.setPower(-0.5);
        sleep(7000);
        //DE END
        Servo servo1 = hardwareMap.get(Servo.class, "servo1");
        servo1.setPosition(180);
        servo1.setPosition(180);
    }
}
