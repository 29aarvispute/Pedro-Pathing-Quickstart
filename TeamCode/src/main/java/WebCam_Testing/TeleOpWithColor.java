package WebCam_Testing;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.*;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

@TeleOp(name = "TeleOp with Color Detection")
public class TeleOpWithColor extends LinearOpMode {

    OpenCvCamera camera;
    String detectedColor = "None";

    @Override
    public void runOpMode() {
        // --- Setup webcam ---
        FtcDashboard.getInstance().startCameraStream(camera, 30);

        camera = OpenCvCameraFactory.getInstance().createWebcam(
                hardwareMap.get(WebcamName.class, "WebCam") // Name in configuration
        );

        // --- Color detection pipeline ---
        camera.setPipeline(new OpenCvPipeline() {
            @Override
            public Mat processFrame(Mat input) {
                Mat hsv = new Mat();
                Imgproc.cvtColor(input, hsv, Imgproc.COLOR_RGB2HSV);

                // --- Detect purple ---
                Scalar lowerPurple = new Scalar(125, 50, 50);
                Scalar upperPurple = new Scalar(155, 255, 255);
                Mat maskPurple = new Mat();
                Core.inRange(hsv, lowerPurple, upperPurple, maskPurple);
                double purplePixels = Core.countNonZero(maskPurple);

                // --- Detect green ---
                Scalar lowerGreen = new Scalar(35, 50, 50);
                Scalar upperGreen = new Scalar(85, 255, 255);
                Mat maskGreen = new Mat();
                Core.inRange(hsv, lowerGreen, upperGreen, maskGreen);
                double greenPixels = Core.countNonZero(maskGreen);

                // --- Determine dominant color ---
                if (purplePixels > greenPixels && purplePixels > 1000) {
                    detectedColor = "Purple";
                } else if (greenPixels > purplePixels && greenPixels > 1000) {
                    detectedColor = "Green";
                } else {
                    detectedColor = "None";
                }

                hsv.release();
                maskPurple.release();
                maskGreen.release();

                return input;
            }
        });

        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
                camera.showFpsMeterOnViewport(true);
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("Camera Error", errorCode);
                telemetry.update();
            }
        });
        telemetry.addLine("Camera Initialized. Press Play to start.");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Detected Color", detectedColor);
            telemetry.update();
        }

        camera.stopStreaming();
    }
}

