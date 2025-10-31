package org.firstinspires.ftc.teamcode.LegionTeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="LegionTeleOp")
public class LegionTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor leftFront  = hardwareMap.dcMotor.get("leftFront");
        DcMotor leftBack   = hardwareMap.dcMotor.get("leftBack");
        DcMotor rightFront = hardwareMap.dcMotor.get("rightFront");
        DcMotor rightBack  = hardwareMap.dcMotor.get("rightBack");
        DcMotor intake     = hardwareMap.dcMotor.get("intake");
        DcMotor shooter    = hardwareMap.dcMotor.get("shooter");

        leftFront.setDirection(DcMotor.Direction.FORWARD);
        leftBack.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setDirection(DcMotor.Direction.REVERSE);
        shooter.setDirection(DcMotor.Direction.FORWARD);

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double DPI = gamepad1.left_stick_button ? 0.1 : 1.0;
            double DPI2 = gamepad1.right_stick_button ? 0.1 : 1.0;

            //MOTORS
            double lfPower = (gamepad1.left_stick_y * DPI * DPI2) + (-gamepad1.left_stick_x * DPI * DPI2) + (-gamepad1.right_stick_x * DPI * DPI2);
            double lbPower = (gamepad1.left_stick_y * DPI * DPI2) + (-gamepad1.left_stick_x * DPI * DPI2) + (gamepad1.right_stick_x * DPI * DPI2);
            double rfPower = (gamepad1.left_stick_y * DPI * DPI2) + (gamepad1.left_stick_x * DPI * DPI2) + (gamepad1.right_stick_x * DPI * DPI2);
            double rbPower = (gamepad1.left_stick_y * DPI * DPI2) + (gamepad1.left_stick_x * DPI * DPI2) + (-gamepad1.right_stick_x * DPI * DPI2);

            leftFront.setPower(lfPower);
            leftBack.setPower(lbPower);
            rightFront.setPower(rfPower);
            rightBack.setPower(rbPower);

            //INTAKE
            if (gamepad1.left_bumper ) {
                intake.setPower(-1.0);
            } else {
                intake.setPower(0.0);
            }

            if (gamepad1.right_bumper){
                intake.setPower(1.0);
            } else {
                intake.setPower(0.0);
            }

            if (gamepad1.dpad_up ) {
                shooter.setPower(-2.0);
            }

            if (gamepad1.dpad_down) {
                shooter.setPower(0);
            }

            telemetry.addData("LF", lfPower);
            telemetry.addData("LB", lbPower);
            telemetry.addData("RF", rfPower);
            telemetry.addData("RB", rbPower);
            telemetry.addData("intake", intake);
            telemetry.addData("shooter", shooter);


            telemetry.update();
        }
    }
}