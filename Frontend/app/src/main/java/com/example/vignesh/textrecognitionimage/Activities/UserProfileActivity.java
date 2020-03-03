package com.example.vignesh.textrecognitionimage.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.vignesh.textrecognitionimage.Activities.LoginActivity;
import com.example.vignesh.textrecognitionimage.Controllers.ReceiptController;
import com.example.vignesh.textrecognitionimage.R;

/**
 * User's profile with info on their receipts
 */
public class UserProfileActivity extends AppCompatActivity {

    TextView userName;
    TextView userEmail;
    TextView userAddress;
    TextView userPhoneNumber;
    TextView numReceipts;

    /**
     * creates user profile activity UI
     * Displays user info for the logged in user
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        userName = findViewById(R.id.userName);

        String text = "\n\n\n\n" + "       " + "Name: "+ LoginActivity.user.getFirstName() + " " + LoginActivity.user.getLastName() + "\n\n" +
                "       " + "Email: " + LoginActivity.user.getEmail() + "\n\n" + "       " + "Address: " + LoginActivity.user.getAddress()
                + "\n\n" + "       " + "Phone: " + LoginActivity.user.getPhoneNumber() + "\n\n" + "       " + "Receipts Stored: " +
                ReceiptController.getNumberOfReceipts();

        userName.setText(text);
    }

    //input the credentials associated with the userID here

}
