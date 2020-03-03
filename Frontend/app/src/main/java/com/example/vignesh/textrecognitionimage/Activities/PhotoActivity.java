package com.example.vignesh.textrecognitionimage.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.content.Intent;

import com.example.vignesh.textrecognitionimage.Model.ReceiptItems;
import com.example.vignesh.textrecognitionimage.R;

import java.util.ArrayList;

public class PhotoActivity extends AppCompatActivity {

    private LinearLayout lnrDynamicEditTextHolder;
    private ImageView image1, image2;
    private LinearLayout image1and2;
    private ArrayList<ImageView> image1ET = new ArrayList<>();
    private ArrayList<ImageView> image2ET = new ArrayList<>();
    public static final ArrayList<ReceiptItems> imagesArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//      setContentView(R.layout.activity_photo2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setContentView(R.layout.activity_photo);
        Intent intent = getIntent();
        lnrDynamicEditTextHolder = (LinearLayout) findViewById(R.id.lnrDynamicEditTextHolder);

//        image1 = (ImageView) findViewById(R.id.image1);
//        image2 = (ImageView) findViewById(R.id.image2);

        image1and2 = (LinearLayout) findViewById(R.id.layout);

        int length = 6;
        for (int i = 0; i < length; i++) {
            LinearLayout l = new LinearLayout(PhotoActivity.this);
            l.setLayoutParams(image1and2.getLayoutParams());
            l.setOrientation(LinearLayout.HORIZONTAL);

            ImageView image1 = new ImageView(PhotoActivity.this);
            ImageView image2 = new ImageView(PhotoActivity.this);

            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(800, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
            LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(200, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);

            image1.setLayoutParams(param);
            image2.setLayoutParams(param2);
//            itemsTex.setHint("Item "+(i+1));
//            priceText.setHint("$.$$");
//            image2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
//            image2.addTextChangedListener(new NumberTextWatcher(priceText, "#####"));

            //image1.setError(null);
            image1.setId(i + 1);
            image2.setId(i + 1);
            l.setId(i + 1);

            image1ET.add(image1);
            image2ET.add(image2);

            l.addView(image1);
            l.addView(image2);
            lnrDynamicEditTextHolder.addView(l);
//            if (i == length) {
//                lnrDynamicEditTextHolder.addView(btnSave);
//            }
//        lnrDynamicEditTextHolder = (LinearLayout) findViewById(R.id.lnrDynamicEditTextHolder);
//        image1 = (ImageView) findViewById(R.id.image1);
//        image2 = (ImageView) findViewById(R.id.image2);
//        image1and2 = (LinearLayout) findViewById(R.id.layout);
//
//
//        int length = 6;
//        for (int i = 0; i < length; i++) {
//            LinearLayout l = new LinearLayout(PhotoActivity.this);
//            l.setLayoutParams(image1and2.getLayoutParams());
//            l.setOrientation(LinearLayout.HORIZONTAL);
//
//            ImageView image1 = new ImageView(PhotoActivity.this);
//            ImageView image2 = new ImageView(PhotoActivity.this);
//
//            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(800, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
//            LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(200, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
//
//            image1.setLayoutParams(param);
//            image2.setLayoutParams(param2);
////            itemsTex.setHint("Item "+(i+1));
////            priceText.setHint("$.$$");
////            image2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
////            image2.addTextChangedListener(new NumberTextWatcher(priceText, "#####"));
//
//            //image1.setError(null);
//            image1.setId(i + 1);
//            image2.setId(i + 1);
//            l.setId(i + 1);
//
//            image1ET.add(image1);
//            image2ET.add(image2);
//
//            l.addView(image1);
//            l.addView(image2);
//            lnrDynamicEditTextHolder.addView(l);
////            if (i == length) {
////                lnrDynamicEditTextHolder.addView(btnSave);
////            }



        }

    }}


