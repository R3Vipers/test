/*
 * Copyright (c) 2018 Craig MacFarlane
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * (subject to the limitations in the disclaimer below) provided that the following conditions are
 * met:
 *
 * Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions
 * and the following disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of Craig MacFarlane nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS LICENSE. THIS
 * SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF
 * THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.system.Deadline;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import java.util.concurrent.TimeUnit;

/*
 * Display patterns of a REV Robotics Blinkin LED Driver.
 * AUTO mode cycles through all of the patterns.
 * MANUAL mode allows the user to manually change patterns using the
 * left and right bumpers of a gamepad.
 *
 * Configure the driver on a servo port, and name it "blinkin".
 *
 * Displays the first pattern upon init.
 */
@TeleOp(name="Light Test")
public class Arm_Test extends OpMode {

    RevBlinkinLedDriver blinkinLedDriver;
    RevBlinkinLedDriver.BlinkinPattern pattern;

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotorEx lb = null;        // Sets the variables of the mecanum wheels
    private DcMotorEx rb = null;
    private DcMotorEx lf = null;
    private DcMotorEx rf = null;

    // COMMENTED OUT THINGS ARE NOT TO BE DELETED
    static final double MOTOR_TICK_COUNT = 2800;
    private DcMotorEx shooter = null;         // Sets the variable of the shooter
    private DcMotor arm = null;         // Sets the variable of the arm that is long but there is not a arm that is short
    private DcMotorEx intake = null;          // Sets the variable of the intake
    private DcMotorEx conveyor = null;          // Sets the variable of the conveyor
    DigitalChannel beambreak;          // Sets the variable of the beamBreak
    Servo leftclaw = null;          // Sets the variable of the Claw
    Servo rightclaw = null;          // Sets the variable of the Claw
    boolean clawstate = false;          // Sets the variable of the clawstate
    boolean toggle = true;          // Sets the variable of the toggle
    double shooterpower = 2500;             // mayhaps
    //Servo light = null;
    DigitalChannel limitup;
    DigitalChannel limitdown;

    @Override
    public void init()
    {

        blinkinLedDriver = hardwareMap.get(RevBlinkinLedDriver.class, "blinkin");
        pattern = RevBlinkinLedDriver.BlinkinPattern.LAWN_GREEN;
        blinkinLedDriver.setPattern(pattern);

        lf = (DcMotorEx)hardwareMap.get(DcMotorEx.class, "lf");       //sets the names of the motors on the hardware map
        rf = (DcMotorEx)hardwareMap.get(DcMotorEx.class, "rf");
        lb = (DcMotorEx)hardwareMap.get(DcMotorEx.class, "lb");
        rb = (DcMotorEx)hardwareMap.get(DcMotorEx.class, "rb");
        arm = hardwareMap.get(DcMotor.class, "arm");
        shooter = (DcMotorEx)hardwareMap.get(DcMotorEx.class, "shooter");
        intake = (DcMotorEx)hardwareMap.get(DcMotorEx.class, "intake");
        conveyor = (DcMotorEx)hardwareMap.get(DcMotorEx.class, "conveyor");
        beambreak = hardwareMap.get(DigitalChannel.class, "beambreak");
        leftclaw = hardwareMap.get(Servo.class, "leftclaw");
        rightclaw = hardwareMap.get(Servo.class, "rightclaw");
        limitup = hardwareMap.get(DigitalChannel.class, "limitup");
        limitdown = hardwareMap.get(DigitalChannel.class, "limitdown");

        shooter.setDirection(DcMotorEx.Direction.REVERSE);            //sets the directions of the motors
        lf.setDirection(DcMotorEx.Direction.FORWARD);
        rf.setDirection(DcMotorEx.Direction.REVERSE);
        lb.setDirection(DcMotorEx.Direction.FORWARD);
        rb.setDirection(DcMotorEx.Direction.REVERSE);
        beambreak.setMode(DigitalChannel.Mode.INPUT); // set the digital channel to input.
        leftclaw.setPosition(1);
        rightclaw.setPosition(0);

        final double driveP = 2.5;        //PID values will change, these are filler values
        final double driveI = 0.1;
        final double driveD = 0.2;
        PIDCoefficients drivePID = new PIDCoefficients(driveP, driveI, driveD);

        //Since this is the first time using the encoder we start it up
        shooter.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        shooter.setVelocity(shooterpower);
        arm.setDirection(DcMotor.Direction.FORWARD);
        intake.setDirection(DcMotor.Direction.FORWARD);
        conveyor.setDirection(DcMotor.Direction.FORWARD);
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
        double leftfrontpower;      //Power level for leftfront
        double rightbackpower;      //Power level for rightback
        double leftbackpower;       //Power level for leftback
        double rightfrontpower;     //Power level for rightfront
        //double armrotation = MOTOR_TICK_COUNT * (0.375);
        // double armrotationmiddle = MOTOR_TICK_COUNT * (0.25);

        telemetry.addData("Up Is pressed? ", limitup.getState());
        telemetry.addData(" Down Is pressed? ", limitdown.getState());
        telemetry.addData("Shooter Speed ", shooterpower);
        telemetry.update();

        //Mecanum Wheels

        leftPowerY = -gamepad1.left_stick_y;      //find the value of y axis on the left joystick
        leftPowerX = gamepad1.left_stick_x;      //find the value of x axis on the left joystick
        rightPowerX = gamepad1.right_stick_x;      //find the value of x axis on the right joystick

        leftfrontpower = leftPowerY + leftPowerX - rightPowerX;     //Power of Mecanum wheels
        rightfrontpower = leftPowerY - leftPowerX + rightPowerX;
        leftbackpower = leftPowerY - leftPowerX - rightPowerX;
        rightbackpower = leftPowerY + leftPowerX + rightPowerX;

        NormScaling = Math.max(Math.max(Math.abs(leftfrontpower), Math.abs(rightfrontpower)), Math.max(Math.abs(leftbackpower), Math.abs(rightbackpower)));     //This line of code gets the max of the the absolute values of the power of the wheels. WARNING DO NOT TRY THIS AT HOME

        if (NormScaling > 1) {
            leftfrontpower /= NormScaling;      //If the max of the the absolute values of the power of the wheels is greater than 1 it will divide all of the powers of the wheels by the max of the the absolute values of the power of the wheels
            rightfrontpower /= NormScaling;
            leftbackpower /= NormScaling;
            rightbackpower /= NormScaling;
        }
        lf.setPower(leftfrontpower);     //Sets the power of the wheels
        lb.setPower(leftbackpower);
        rf.setPower(rightfrontpower);
        rb.setPower(rightbackpower);

        //Claw

        if (toggle && gamepad2.y) {  // Only execute once per Button push
            toggle = false;  // Prevents this section of code from being called again until the Button is released and re-pressed
            if (clawstate) {  // Decide which way to set the motor this time through (or use this as a motor value instead)
                clawstate = false;
                leftclaw.setPosition(1);
                rightclaw.setPosition(0);
            } else {
                clawstate = true;
                leftclaw.setPosition(0);
                rightclaw.setPosition(1);
            }
        } else if (!gamepad2.y) {
            toggle = true; // Button has been released, so this allows a re-press to activate the code above.
        }



        //Long Arm

        if (limitup.getState()) {
            arm.setPower(0);
        }

        if (limitdown.getState()) {
            arm.setPower(0);
        }

        if (gamepad2.dpad_up && limitup.getState()) {            //turns the arm that is long but there is not a arm that is short
            arm.setPower(-0.5);        //Sets the power for the Long arm
        }

        if (gamepad2.dpad_down && limitdown.getState()) {      //rotates the arm that is long but there is not a arm that is short
            arm.setPower(0.5);        //Sets the power for the Long arm
        }


        //Shooter and Shooter Power

        if (gamepad2.left_stick_y < -0.25) {
            shooterpower += 50;
            pattern = RevBlinkinLedDriver.BlinkinPattern.RED;
            blinkinLedDriver.setPattern(pattern);
        }

        if (gamepad2.left_stick_y > 0.25) {
            shooterpower -= 50;
            pattern = RevBlinkinLedDriver.BlinkinPattern.BLUE;
            blinkinLedDriver.setPattern(pattern);
        }

        if (shooterpower < 700) {
            shooterpower = 700;
        }

        if (shooterpower > 2500) {
            shooterpower = 2500;
        }

        if (gamepad2.b) {
            shooter.setVelocity(shooterpower);
            intake.setPower(1);
            conveyor.setPower(1);
            pattern = RevBlinkinLedDriver.BlinkinPattern.WHITE;
            blinkinLedDriver.setPattern(pattern);
        }

        //Intake and Conveyor and Conveyor Detection System

        if (gamepad2.right_trigger > 0) {       //runs the intake forward
            intake.setPower(1);
            if(pattern== RevBlinkinLedDriver.BlinkinPattern.ORANGE){

            }
            else {
                pattern = RevBlinkinLedDriver.BlinkinPattern.ORANGE;
                blinkinLedDriver.setPattern(pattern);
            }
        }

        if (gamepad2.left_trigger > 0) {        //runs the intake backwards
            intake.setPower(-1);
            conveyor.setPower(-1);
            if(pattern== RevBlinkinLedDriver.BlinkinPattern.ORANGE){

            }
            else {
                pattern = RevBlinkinLedDriver.BlinkinPattern.ORANGE;
                blinkinLedDriver.setPattern(pattern);
            }
        }

        if (!beambreak.getState()) {
            if (gamepad2.left_trigger > 0) {        //BEAM BREAK
                conveyor.setPower(-1);
                if(pattern== RevBlinkinLedDriver.BlinkinPattern.ORANGE){

                }
                else {
                    pattern = RevBlinkinLedDriver.BlinkinPattern.ORANGE;
                    blinkinLedDriver.setPattern(pattern);
                }
            } else {
                conveyor.setPower(1);
                if(pattern== RevBlinkinLedDriver.BlinkinPattern.ORANGE){

                }
                else {
                    pattern = RevBlinkinLedDriver.BlinkinPattern.ORANGE;
                    blinkinLedDriver.setPattern(pattern);
                }
            }
        }

        // Color Reset

        if (gamepad2.x) {
            pattern = RevBlinkinLedDriver.BlinkinPattern.LAWN_GREEN;
            blinkinLedDriver.setPattern(pattern);
        }

        // Else Statement
        else {
            conveyor.setPower(0);        // This tells the program to set the Intake, Long Arm, and Shooter
            intake.setPower(0);        //to turn them off when not being used
            //pattern = RevBlinkinLedDriver.BlinkinPattern.LAWN_GREEN;
            //blinkinLedDriver.setPattern(pattern);
        }
    }



}