
package com.example.vignesh.textrecognitionimage.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.vignesh.textrecognitionimage.Communications.AppController;
import com.example.vignesh.textrecognitionimage.Model.Receipt;
import com.example.vignesh.textrecognitionimage.R;
import com.example.vignesh.textrecognitionimage.Utils.Const;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import android.widget.ProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Shows receipt that user wants to scan before processing
 */
public class UploadActivity extends AppCompatActivity {

    ImageView imageView;
    Button btnProcess;
    public static String dataString;
    public static String imageBase64Encoded;
    private static Context appContext = null;
    private static String TAG = UploadActivity.class.getSimpleName();
    private static String tag_json_obj = "jobj_req";
    public static JSONObject scanData;
    private static Activity activity;
    public static boolean receipt_from_scan = false;
    public static boolean receipt_from_upload = false;
    private ProgressBar spinner;
    /**
     * creates the receipt upload activity UI
     * User can upload a receipt and process it to store the information
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        appContext = this;

        imageView = (ImageView) findViewById(R.id.image_view);
        btnProcess = (Button) findViewById(R.id.button_process);
        spinner = (ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);

        imageView.setImageBitmap(ScanActivity.notScaled);

        if (receipt_from_scan == true) {
            imageView.setRotation(90);
        }


        btnProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            imageBase64Encoded = createBase64FromBmap();
            //spinner.setVisibility(View.VISIBLE);
            try {
                spinner.setVisibility(View.VISIBLE);
                postReceiptScan();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            }
        });


    }

    private String createBase64FromBmap() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ScanActivity.bmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return encoded;
    }

    private static JSONObject buildReceiptScanJSON() throws JSONException {
        JSONObject params = new JSONObject();
        params.put("image", imageBase64Encoded);
        params.put("filename", "upload.jpg");
        params.put("contentType", "image/jpeg");
        return params;
    }


    public static void postReceiptScan() throws JSONException {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Const.URL_POST_SCAN_API, buildReceiptScanJSON(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        scanData = response;
                        Intent input = new Intent(UploadActivity.appContext, ReceiptInputActivity.class);
                        UploadActivity.appContext.startActivity(input);
                        Toast.makeText(appContext, "Receipt Scanned", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, response.toString());

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(appContext, "Could not scan receipt", Toast.LENGTH_SHORT).show();
            }
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("apikey", " a4273e70f50f11e8a3bae1edc4be3a66");
                return headers;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq,
                tag_json_obj);

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
    }

}