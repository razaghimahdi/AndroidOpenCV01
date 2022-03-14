package com.example.opencv01.example;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.opencv01.R;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class Example01Activity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener {


    private static String TAG = "AppDebug Example01Activity";

    Mat mat1, mat2, mat3;

    CameraBridgeViewBase cameraBridgeViewBase;

    BaseLoaderCallback baseLoaderCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example01);
        cameraBridgeViewBase = (JavaCameraView) findViewById(R.id.cameraView);
        cameraBridgeViewBase.setVisibility(View.VISIBLE);
        cameraBridgeViewBase.setCvCameraViewListener(this);

        baseLoaderCallback = new BaseLoaderCallback(this) {
            @Override
            public void onManagerConnected(int status) {
                switch (status) {
                    case BaseLoaderCallback.SUCCESS:
                        cameraBridgeViewBase.enableView();
                        break;
                    default:
                        super.onManagerConnected(status);
                        break;
                }
            }
        };

    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        mat1 = new Mat(width, height, CvType.CV_8UC4);
        mat2 = new Mat(width, height, CvType.CV_8UC4);
        mat3 = new Mat(width, height, CvType.CV_8UC4);

        Log.i(TAG, "onCameraViewStarted mat1: " + mat1.toString());
    }

    @Override
    public void onCameraViewStopped() {
        mat1.release();
    }

    @Override
    public Mat onCameraFrame(Mat inputFrame) {

        mat1 = inputFrame;

        Log.i(TAG, "onCameraFrame mat1: " + mat1.toString());

        //  return null;

        // rotate frame to 90 degree
        Core.transpose(mat1, mat2);
        // Imgproc.resize(mat2, mat3, mat3.size(), 0, 0, 0);
        Core.flip(mat3, mat1, 1);

        return mat1;
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (cameraBridgeViewBase != null) {
            cameraBridgeViewBase.disableView();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Toast.makeText(getApplicationContext(), "There is a problem in openCv", Toast.LENGTH_SHORT).show();
        } else {
            baseLoaderCallback.onManagerConnected(BaseLoaderCallback.SUCCESS);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cameraBridgeViewBase != null) {
            cameraBridgeViewBase.disableView();
        }
    }

}