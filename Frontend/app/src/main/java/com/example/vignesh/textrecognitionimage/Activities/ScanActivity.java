package com.example.vignesh.textrecognitionimage.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.util.Log;
import android.net.Uri;

import com.example.vignesh.textrecognitionimage.R;
import com.example.vignesh.textrecognitionimage.Utils.CameraPreview;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Scanner page for users to scan receipts
 */
public class ScanActivity extends AppCompatActivity {

    SurfaceView cameraView;
    CameraSource cameraSource;
    Button btnProcess;
    Button btnUpload;
    TextView txtResult;
    Camera mCamera;
    public static final int PICK_IMAGE = 100;
    private static final int requestCameraPermissionID = 1001;
    public String dataString;
    private View globalView;
    private Uri imageUri;
    public static Bitmap bmap;
    public static Bitmap notScaled;
    private static Context applicationContext;

    /**
     * Requests camera permission
     * @param requestCode
     * @param permissions
     * @param grantResults
     */


    /**
     * Creates scanner UI
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        applicationContext = this;
        btnProcess = (Button) findViewById(R.id.button_process);
        btnUpload = findViewById(R.id.button_upload);

            mCamera = getCameraInstance();
            mCamera.setDisplayOrientation(90);
            mCamera.getParameters().setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            CameraPreview mPreview = new CameraPreview(this, mCamera);
            FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
            preview.addView(mPreview);


            final Camera.PictureCallback mPicture = new Camera.PictureCallback() {
                @Override
                public void onPictureTaken(byte[] data, Camera camera) {
                    bmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    notScaled = bmap;
                    if (UploadActivity.receipt_from_scan) {
                        bmap = bmap.createScaledBitmap(bmap, 700, 700, false);
                    }
                    Intent input = new Intent(applicationContext, UploadActivity.class);
                    UploadActivity.receipt_from_scan = true;
                    applicationContext.startActivity(input);
                }
            };

            // process button
            btnProcess.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCamera.takePicture(null, null, mPicture);
                    UploadActivity.receipt_from_scan = true;
                }
            });


            // upload button
            btnUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    globalView = view;
                    openGallery();
                    UploadActivity.receipt_from_upload = true;
                }
            });
        }


    /**
     * Opens users gallery to select image
     */
    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    /**
     * Takes image to upload page after selection
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            final InputStream imageStream;
            try {
                imageStream = getContentResolver().openInputStream(imageUri);
                bmap = BitmapFactory.decodeStream(imageStream);
                notScaled = bmap;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Intent myIntent = new Intent(this, UploadActivity.class);
            startActivityForResult(myIntent, 0);
        }
    }

}
