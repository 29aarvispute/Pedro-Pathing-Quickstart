package WebCam_Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="Auto1")
public class auto1 extends LinearOpMode {
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

        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
        sleep(500);

        leftFront.setPower(1);
        leftBack.setPower(1);
        rightFront.setPower(1);
        rightBack.setPower(1);
        sleep(2000);

        leftFront.setPower(1);
        leftBack.setPower(1);
        rightFront.setPower(-1);
        rightBack.setPower(-1);
        sleep(1000);

        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
    }
}