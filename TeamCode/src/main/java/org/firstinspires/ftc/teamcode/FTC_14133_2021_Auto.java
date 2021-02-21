package org.firstinspires.ftc.teamcode;// https://first-tech-challenge.github.io/SkyStone/  This is the link to ALL metered of FTC
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import java.util.concurrent.TimeUnit;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;
import org.firstinspires.ftc.robotcore.internal.network.RobotCoreCommandList;

@Autonomous(name="FTC 14133 2021 Auto", group="Auto")
    public class FTC_14133_2021_Auto extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftback = null;        // Sets the variables of the mecanum wheels
    private DcMotor rightback = null;
    private DcMotor lf = null;
    private DcMotor rightfront = null;
    static final double MOTOR_TICK_COUNT = 2800;        //
    private DcMotor Shooter = null;         // Sets the variable of the shooter
    private DcMotor LongArm = null;         // Sets the variable of the arm that is long but there is not a arm that is short
    private DcMotor intake = null;          // Sets the variable of the intake
    private DcMotor conveyor = null;          // Sets the variable of the conveyor
    //   DigitalChannel LimitSwitchLongArm;          // Sets the variable of the LimitSwitchLongArm
    DigitalChannel beamBreak;          // Sets the variable of the beamBreak
    Servo Claw = null;          // Sets the variable of the Claw
    boolean clawstate = false;          // Sets the variable of the clawstate
    boolean toggle = true;          // Sets the variable of the toggle
    int count = 0;
    //double distance = 0;
    //  double turn = 0;

    void ForwardorBackwards(double distance, double speed) {
        lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightfront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftback.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightback.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //Driving forward/backwards
        //  double distance= 5; //(in)
        double encodercounts = distance * 60.3686819388;//(1/(75*(1/25.4)))*560;
        int encodercountsint = (int) encodercounts;
        lf.setTargetPosition(encodercountsint);
        lf.setPower(speed);        //Sets the power for the Long arm
        rightfront.setTargetPosition(encodercountsint);


        rightfront.setPower(speed);        //Sets the power for the Long arm
        leftback.setTargetPosition(encodercountsint);
        leftback.setPower(speed);        //Sets the power for the Long arm
        rightback.setTargetPosition(encodercountsint);
        rightback.setPower(speed);        //Sets the power for the Long arm
        leftback.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightfront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightback.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (lf.isBusy() || rightfront.isBusy() || leftback.isBusy() || rightback.isBusy()) {

        }
    }


    void Rotate(double turn, double speed) {
        lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightfront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftback.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightback.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //Driving left/right
        //NOT DONE
        double encodercounts = turn * 1.4142135623730950488016887242097; // test iteratively
        int encodercountsint = (int) encodercounts;
        lf.setTargetPosition(-encodercountsint);
        lf.setPower(speed);        //
        rightfront.setTargetPosition(encodercountsint);
        rightfront.setPower(speed);        //Sets the power for the Long arm
        leftback.setTargetPosition(-encodercountsint);
        leftback.setPower(speed);        //Sets the power for the Long arm
        rightback.setTargetPosition(encodercountsint);
        rightback.setPower(speed);        //Sets the power for the Long arm
        leftback.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightfront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightback.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (lf.isBusy() || rightfront.isBusy() || leftback.isBusy() || rightback.isBusy()) {
        }
    }

    void Strafing(double Strafe, double speed) {
        lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightfront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftback.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightback.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //Driving left/right
        //Positive is Strafing left negative is Strafing right
        double encodercounts = Strafe * 60.3686819388 * 1.4142135623730950488016887242097;
        int encodercountsint = (int) encodercounts;
        lf.setTargetPosition(-encodercountsint);
        lf.setPower(speed);        //
        rightfront.setTargetPosition(encodercountsint);
        rightfront.setPower(speed);        //Sets the power for the Long arm
        leftback.setTargetPosition(encodercountsint);
        leftback.setPower(speed);        //Sets the power for the Long arm
        rightback.setTargetPosition(-encodercountsint);
        rightback.setPower(speed);        //Sets the power for the Long arm
        leftback.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightfront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightback.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (lf.isBusy() || rightfront.isBusy() || leftback.isBusy() || rightback.isBusy()) {
        }


    }

    boolean IntakeFunction(double speed) {
        intake.setPower(speed);
        if (beamBreak.getState()) {
            conveyor.setPower(speed);
        }
        if (speed == 0) {
            return false;
        } else {
            conveyor.setPower(0);
            return true;
        }

    }

    void ConveyorFunction(double speed) {
     //   Shooter.setPower(speed);
        conveyor.setPower(speed);
        intake.setPower(speed);
    }

    void LongArmFunctionDown() {
        double armrotation = MOTOR_TICK_COUNT * (0.375);
        LongArm.setPower(0.3);        //Sets the power for the Long arm
        LongArm.setTargetPosition((int) armrotation);        //Tell the motor to go to 90 degrees when told to
        LongArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (LongArm.isBusy()){
        }
    }

    void LongArmFunctionUP() {
        LongArm.setPower(0.3);        //Sets the power for the Long arm
        LongArm.setTargetPosition(0);        //Tell the motor to go to 90 degrees when told to
        LongArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (LongArm.isBusy()){
        }
    }

    public void waitForStart() {
    }

    public void runOpMode() {
        lf = hardwareMap.get(DcMotor.class, "lf");       //sets the names of the motors on the hardware map
        rightfront = hardwareMap.get(DcMotor.class, "rightfront");
        leftback = hardwareMap.get(DcMotor.class, "leftback");
        rightback = hardwareMap.get(DcMotor.class, "rightback");
        LongArm = hardwareMap.get(DcMotor.class, "LongArm");
        Shooter = hardwareMap.get(DcMotor.class, "Shooter");
        intake = hardwareMap.get(DcMotor.class, "intake");
        conveyor = hardwareMap.get(DcMotor.class, "conveyor");
        beamBreak = hardwareMap.get(DigitalChannel.class, "beamBreak");
        Claw = hardwareMap.get(Servo.class, "Claw");
        telemetry.addData("count", count);
        telemetry.addData("beamBreak", beamBreak.getState());
        telemetry.update();

        lf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightfront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftback.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightback.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LongArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Shooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        conveyor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        Shooter.setDirection(DcMotor.Direction.REVERSE);            //sets the directions of the motors
        lf.setDirection(DcMotor.Direction.FORWARD);
        rightfront.setDirection(DcMotor.Direction.REVERSE);
        leftback.setDirection(DcMotor.Direction.FORWARD);
        rightback.setDirection(DcMotor.Direction.REVERSE);
        //LimitSwitchLongArm.setMode(DigitalChannel.Mode.INPUT);
        beamBreak.setMode(DigitalChannel.Mode.INPUT); // set the digital channel to input.
        Claw.setPosition(1);
        LongArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);        //Since this is the first time using the encoder we start it up
        rightfront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftback.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightback.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Shooter.setPower(1);


   //     boolean intakeon = IntakeFunction(0.5);
     //   while (intakeon == true) {
     //       telemetry.update();
      //      if (!beamBreak.getState()) {
     //           conveyor.setPower(1);
      //      }

        while (toggle)
        {
            if (beamBreak.getState()) {
                count = count + 1;
                toggle = false;

            }
        }


        ForwardorBackwards(57.5, 0.75);

        Strafing(-18, -0.5);   // scoot left until aligned with top goal

        ConveyorFunction(1);

        sleep(4000);
        ForwardorBackwards(-26, 0.2);    // move forward at rings



       // ForwardorBackwards(-26, 0.2);    // move forward at rings

        ConveyorFunction(0);

        ForwardorBackwards(-6, 0.3);    // move forward at rings
        toggle = true;

        ForwardorBackwards(-2, 0.3);    // move forward at rings
        toggle = true;

        ForwardorBackwards(-2, 0.3);    // move forward at rings
        toggle = true;

        telemetry.addData("count", count);    //
        telemetry.update();

        ForwardorBackwards(35, 0.5);

        ConveyorFunction(1);

        sleep(3000);

        ConveyorFunction(0);
        if (count == 0) {       // if one ring is picked up, do this portion of code

       //     ShooterFunction(1);

       //     sleep(3000);

     //       IntakeFunction(0);

            Strafing(12, -0.75);

            LongArmFunctionDown();

            sleep(250);

            Claw.setPosition(0);

            sleep(250);

            LongArm.setTargetPosition(0);
            LongArm.setPower(0.3);        //Sets the power for the Long arm
            LongArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            Strafing(-12, 0.75);

            ForwardorBackwards(6,0.5);
        }
        if (count == 1) {

            ForwardorBackwards(16, 0.75);

        //    ShooterFunction(1);

            Strafing(-6,0.75);

            LongArmFunctionDown();

            sleep(250);

            Claw.setPosition(0);

            sleep(250);

            LongArm.setTargetPosition(0);
            LongArm.setPower(0.3);        //Sets the power for the Long arm
            LongArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            //ForwardorBackwards(-4,0.75);
        }
        if (count == 3) {
            ForwardorBackwards(40, 0.75);

     //       ShooterFunction(1);

            Strafing(12, -1);

            LongArmFunctionDown();

            sleep(250);

            Claw.setPosition(0);

            sleep(250);

            LongArm.setTargetPosition(0);
            LongArm.setPower(0.3);        //Sets the power for the Long arm
            LongArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            ForwardorBackwards(-32,0.75);
            }
        }
    }