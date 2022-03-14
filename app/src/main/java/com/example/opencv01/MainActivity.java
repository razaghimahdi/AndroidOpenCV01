package com.example.opencv01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.example.opencv01.color_blob_detection.ColorBlobDetectionActivity;
import com.example.opencv01.example.Example01Activity;
import com.example.opencv01.face_detection.FdActivity;
import com.example.opencv01.puzzle15.Puzzle15Activity;

import org.opencv.android.OpenCVLoader;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "AppDebug";

    static {
        if (OpenCVLoader.initDebug()){
            Log.i(TAG, "static initializer successfully ");
        }else{
            Log.i(TAG, "static initializer is not installed ");
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnExample01).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Example01Activity.class));
            }
        });
        findViewById(R.id.btnFace).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FdActivity.class));
            }
        });
        findViewById(R.id.btnColorBlob).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ColorBlobDetectionActivity.class));
            }
        });

        findViewById(R.id.btnPuzzle15Activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Puzzle15Activity.class));
            }
        });

    }
}