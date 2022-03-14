package com.example.opencv01.example;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;

import com.example.opencv01.R;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;

public class Example02Activity extends AppCompatActivity {

    ImageView imageView;
    Uri imageUri;

    Bitmap grayBitmap, imageBitmap;

    Mat sampleImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example02);
        imageView = findViewById(R.id.imageView);
        OpenCVLoader.initDebug();
    }

    public void openGallery(View v) {
        Intent myIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(myIntent, 100);
    }


    public void convertToGray(View v) {

        Mat rgba = new Mat();
        Mat grayMat = new Mat();

        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inDither = false;
        o.inSampleSize = 4;

        int width = imageBitmap.getWidth();
        int height = imageBitmap.getHeight();

        grayBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

        // bitmap to MAT

        Utils.bitmapToMat(imageBitmap, rgba);

        Imgproc.cvtColor(rgba, grayMat, Imgproc.COLOR_RGB2GRAY);

        Utils.matToBitmap(grayMat, grayBitmap);

        imageView.setImageBitmap(grayBitmap);


    }


    private void displayImage(Mat mat){
        Bitmap bitmap = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.RGB_565);

        // convert mat to bitmap

        Utils.matToBitmap(mat, bitmap);

        imageView.setImageBitmap(bitmap);
    }


    public void loadImageByMat(String path) {
        Mat originalImage = Imgcodecs.imread(path); // image will be in BGR format
        Mat rgbImg = new Mat();

        // convert BGR to RGB
        Imgproc.cvtColor(originalImage, rgbImg, Imgproc.COLOR_BGR2RGB);

        Display display = getWindowManager().getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);

        int mobile_width = size.x;
        int mobile_height = size.y;

        sampleImg = new Mat();


        double downSampleRatio = calculateSubSimpleSize(rgbImg, mobile_width, mobile_height);

        Imgproc.resize(rgbImg, sampleImg,new Size(), downSampleRatio, downSampleRatio, Imgproc.INTER_AREA);


        try {
            ExifInterface exif = new ExifInterface(path);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            switch (orientation){
                case ExifInterface.ORIENTATION_ROTATE_90:
                        sampleImg = sampleImg.t();
                    Core.flip(sampleImg, sampleImg, 1);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    sampleImg = sampleImg.t();
                    Core.flip(sampleImg, sampleImg, 0);
                    break;



            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public double calculateSubSimpleSize(Mat src, int mobile_width, int mobile_height){
        final int width = src.width();
        final int height = src.height();
        double inSampleSize = 1;

        if (height > mobile_height || width > mobile_width){
            // calculate the ratio

            final  double heightRatio = (double) mobile_height / (double)height;
            final  double widthRatio = (double) mobile_width / (double)width;

            inSampleSize = heightRatio < widthRatio ? height : width;
        }

        return inSampleSize;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


}