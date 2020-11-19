package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

public class FTC_14133_2021 extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftback = null;
    private DcMotor rightback = null;
    private DcMotor leftfront = null;
    private DcMotor rightfront = null;
    HardwarePushbot robot = new HardwarePushbot();


    public void init() {
        leftfront = hardwareMap.get(DcMotor.class, "left_drive");
        rightfront = hardwareMap.get(DcMotor.class, "right_drive");
        leftback  = hardwareMap.get(DcMotor.class, "left_drive");
        rightback = hardwareMap.get(DcMotor.class, "right_drive");

        leftfront.setDirection(DcMotor.Direction.FORWARD);
        rightfront.setDirection(DcMotor.Direction.REVERSE);
        leftback.setDirection(DcMotor.Direction.FORWARD);
        rightback.setDirection(DcMotor.Direction.REVERSE);
    }


    public void init_loop() {
    }


    public void start() {
    }


    public void loop() {
        double leftPowerY;
        double leftPowerX;
        double rightPowerX;
        double NormScaling;
        double leftfront;
        double rightback;
        double leftback;
        double rightfront;

        leftPowerY  = -gamepad1.left_stick_y ;
        leftPowerX  = gamepad1.left_stick_x ;
        rightPowerX = gamepad1.right_stick_x ;

        leftfront = leftPowerY + leftPowerX + rightPowerX;
        rightfront = leftPowerY - leftPowerX - rightPowerX;
        leftback = leftPowerY + leftPowerX - rightPowerX;
        rightback = leftPowerY - leftPowerX + rightPowerX;

        NormScaling = Math.max(Math.max(leftfront, rightfront), Math.max(leftback, rightback));

        leftfront = leftfront/=NormScaling;
        rightfront = rightfront/=NormScaling;
        leftback = leftback/=NormScaling;
        rightback = rightback/=NormScaling;

        Servo Claw = null;

        if(gamepad1.y) {
            // move to 0 degrees.
            Claw.setPosition(0);
        } else if (gamepad1.x) {
            // move to 90 degrees.
            Claw.setPosition(0.5);
        } else if (gamepad1.a) {
            // move to 180 degrees.
            Claw.setPosition(1);
        }
    }
}