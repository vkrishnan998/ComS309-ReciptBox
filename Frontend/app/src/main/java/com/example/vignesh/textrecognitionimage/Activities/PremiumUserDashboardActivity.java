package com.example.vignesh.textrecognitionimage.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.Intent;
import android.widget.Button;

import com.example.vignesh.textrecognitionimage.Controllers.ReceiptController;
import com.example.vignesh.textrecognitionimage.Controllers.ReceiptItemsController;
import com.example.vignesh.textrecognitionimage.R;

import org.json.JSONException;

/**
 * A dashboard for Premium users that allows the user to be able to access all other app features
 */
public class PremiumUserDashboardActivity extends AppCompatActivity {

    public static boolean galleryPressed = false;
    public static Context appContext;
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    /**
     * This method is accessed through the login or registration chat activities. If the user checks
     * that they are a premium user than the are able to access this page.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium_user_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appContext = this;
        Intent intent = getIntent();

        Button scanner = (Button) findViewById(R.id.scanner);
        scanner.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) { ;
                Intent myIntent = new Intent(view.getContext(), ScanActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        Button userProfile = (Button) findViewById(R.id.userProfile);
        userProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), UserProfileActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        Button manual_input = (Button) findViewById(R.id.manual_input);
        manual_input.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), ReceiptInputActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        Button gallery = (Button) findViewById(R.id.gallery);
        gallery.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                galleryPressed = true;
                try {
                    ReceiptController.getAllReceipts();
                    ReceiptItemsController.getAllReceiptsItems();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Button graphs = (Button) findViewById(R.id.graphs);
        graphs.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), GraphsActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        Button chat = (Button) findViewById(R.id.chat);
        chat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), ChatActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

    }

}
