package com.example.vignesh.textrecognitionimage.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.vignesh.textrecognitionimage.Activities.FreeUserDashboardActivity;
import com.example.vignesh.textrecognitionimage.Activities.LoginActivity;
import com.example.vignesh.textrecognitionimage.Activities.PremiumUserDashboardActivity;
import com.example.vignesh.textrecognitionimage.Controllers.UserController;
import com.example.vignesh.textrecognitionimage.R;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Registration page
 */
public class RegistrationActivity extends AppCompatActivity {

    private EditText mFirstNameView, mLastNameView, mEmailIDView, mPhoneNumberView, mAddressView, mPasswordView;
    private CheckBox isPremiumUser;
    public static Context applicationContext;
    public static Map<Integer, ArrayList<String>> receiptMap = new HashMap<>();

    /**
     * Creates Registration UI
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Button Register = (Button) findViewById(R.id.register_button);
        applicationContext = getApplicationContext();

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { ;
                try {
                    if(!attemptRegistration()) {
                        if((isPremiumUser.isChecked()==true)) {
                            Intent myIntent = new Intent(view.getContext(), PremiumUserDashboardActivity.class);
                            startActivityForResult(myIntent, 0);
                        }
                        else{
                            Intent myIntent = new Intent(view.getContext(), FreeUserDashboardActivity.class);
                            startActivityForResult(myIntent, 0);
                            receiptMap.put(LoginActivity.user.getId(), new ArrayList<String>());
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        mFirstNameView = (EditText) findViewById(R.id.first_name);
        mLastNameView = (EditText) findViewById(R.id.last_name);
        mEmailIDView = (EditText) findViewById(R.id.email_id);
        mPhoneNumberView = (EditText) findViewById(R.id.phone_number);
        mAddressView = (EditText) findViewById(R.id.address);
        mPasswordView = (EditText) findViewById(R.id.password);
        isPremiumUser = (CheckBox)findViewById(R.id.checkBox);

    }


    /**
     * Attempts a registration and login with new user
     * @return true if registration successful
     * @throws JSONException
     */
    private boolean attemptRegistration() throws JSONException {
        //canLogin will login unless a parameter is incorrect
        boolean canLogin = true;
        //These fields cannot be empty
        mFirstNameView.setError(null);
        mLastNameView.setError(null);
        mEmailIDView.setError(null);
        mPhoneNumberView.setError(null);
        mAddressView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String First_Name = mFirstNameView.getText().toString();
        String Last_Name = mLastNameView.getText().toString();
        String Email_Id = mEmailIDView.getText().toString();
        String Phone_Number = mPhoneNumberView.getText().toString();
        String Address = mAddressView.getText().toString();
        String Password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid First Name, if the user entered one.
        if(TextUtils.isEmpty(First_Name)){
            mFirstNameView.setError("This field is required");
            focusView = mFirstNameView;
            cancel = true;
        }
        if (!TextUtils.isEmpty(First_Name) && !isNameValid(First_Name)) {
            mFirstNameView.setError("First name is not long enough");
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid Last Name, if the user entered one.
        if(TextUtils.isEmpty(Last_Name)){
            mLastNameView.setError("This field is required");
            focusView = mLastNameView;
            cancel = true;
        }
        if (!TextUtils.isEmpty(Last_Name) && !isNameValid(Last_Name)) {
            mFirstNameView.setError("Last name is not long enough");
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(Email_Id)){
            mEmailIDView.setError("This field is required");
            focusView = mEmailIDView;
            cancel = true;
        } else if (!isEmailValid(Email_Id)) {
            mEmailIDView.setError("This email address is required");
            focusView = mEmailIDView;
            cancel = true;
        }

        if(TextUtils.isEmpty(Phone_Number)){
            mPhoneNumberView.setError("This field is required");
            focusView = mPhoneNumberView;
            cancel = true;
        } else if (!isPhoneNumberValid(Phone_Number)){
            mPhoneNumberView.setError("This Phone Number is not valid");
            focusView = mPhoneNumberView;
            cancel = true;
        }

        if(TextUtils.isEmpty(Address)){
            mAddressView.setError("This field is required");
            focusView = mAddressView;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(Password) || !isPasswordValid(Password)) {
            mPasswordView.setError("This Password is too short");
            focusView = mPasswordView;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }
        else {
            LoginActivity.user.setFirstName(First_Name);
            LoginActivity.user.setLastName(Last_Name);
            LoginActivity.user.setAddress(Address);
            LoginActivity.user.setPhoneNumber(Phone_Number);
            LoginActivity.user.setPassword(Password);
            LoginActivity.user.setEmail(Email_Id);
            UserController.makeRegistrationRequest();
        }
        return cancel;
    }

    /**
     * Checks if email contains @ symbol
     * @param email email from registration
     * @return true if valid
     */
    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    /**
     * Checks if password is greater than 1 character
     * @param password password from registration
     * @return true if valid
     */
    private boolean isPasswordValid(String password) {
        return password.length() > 1;
    }

    /**
     * Checks if name is valid length
     * @param name first and last names from registration
     * @return true if valid
     */
    private boolean isNameValid(String name) {
        return name.length() > 0;
    }

    /**
     * Checks if phone number is 10 digits
     * @param phone_number phone number from registration
     * @return
     */
    private boolean isPhoneNumberValid(String phone_number){
        if (phone_number.length() == 10) {
            return true;
        }
        return false;
    }

}
