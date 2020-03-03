package com.example.vignesh.textrecognitionimage.Controllers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.vignesh.textrecognitionimage.Activities.ChatActivity;
import com.example.vignesh.textrecognitionimage.Communications.AppController;
import com.example.vignesh.textrecognitionimage.Activities.FreeUserDashboardActivity;
import com.example.vignesh.textrecognitionimage.Activities.LoginActivity;
import com.example.vignesh.textrecognitionimage.Model.ReceiptItems;
import com.example.vignesh.textrecognitionimage.Model.User;
import com.example.vignesh.textrecognitionimage.Activities.RegistrationActivity;
import com.example.vignesh.textrecognitionimage.Utils.Const;
import com.example.vignesh.textrecognitionimage.Activities.PremiumUserDashboardActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.support.v4.content.ContextCompat.startActivity;
import static com.example.vignesh.textrecognitionimage.Activities.LoginActivity.user;

/**
 * Controller for user requests
 */
public class UserController {


    private static String TAG = UserController.class.getSimpleName();

    // These tags will be used to cancel the requests
    private static String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";
    private static int userId = 0;

    private static JSONObject json = new JSONObject();
    private static String URL = "";
    private static String tag_string_req = "string_req";
    private static String errorToast = "Null error";
    private static Context appContext = null;
    private static JSONObject object;
    private static Boolean loginSuccess;
    private static User temp_user = new User();

    public static ArrayList<ReceiptItems> items = new ArrayList<>();

    /**
     * Builds Json to get user info
     * @return true if the user object is found, false otherwise
     * @throws JSONException
     */
    public static boolean buildGetUserByUserIdJSON() throws JSONException {

        if(userId == 0){
            return false;
        }

        URL = Const.URL_GET_USER_BY_ID + userId;
        errorToast = "Could not get user object by userID";
        appContext = ChatActivity.applicationContext;
        return true;
    }

    /**
     * builds JSON to be send for a login request
     * @throws JSONException
     */
    public static void buildLoginJSON() throws JSONException {
        URL = Const.URL_LOGIN + user.getEmail() + "?" + "password" + "=" +
                user.getPassword();
        errorToast = "Could not log in user";
        appContext = LoginActivity.applicationContext;
    }

    /**
     * a boolean to detect a successful login
     * @param userID
     */
    public static void setLoginSuccess(int userID) {
        if (userId != 0 && userId != -1) {
            loginSuccess = true;
        }
        else {
            loginSuccess = false;
        }
    }

    /**
     * Build JSON object to be posted into the database for the registration information
     * @throws JSONException
     */
    public static void buildRegistrationJSON() throws JSONException {
        JSONObject params = new JSONObject();
        params.put("firstName", user.getFirstName());
        params.put("lastName", user.getLastName());
        params.put("password", user.getPassword());
        params.put("emailId", user.getEmail());
        params.put("address", user.getAddress());
        params.put("phoneNumber", user.getPhoneNumber());
        params.put("premium", user.getPremium());
        params.put("username", user.getUsername());

        URL = Const.URL_REGISTRATION;
        errorToast = "Couldn't register user";
        appContext = RegistrationActivity.applicationContext;
        json = params;
    }

    /**
     * method to make a login request
     * @throws JSONException
     */
    public static void makeLoginRequest() throws JSONException {
        buildLoginJSON();
        StringRequest strReq = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);
                userId = Integer.parseInt(response);
                LoginActivity.user.setId(userId);
                setLoginSuccess(userId);
                if (getLoginSuccess()) {
                    try {
                        getUserObject();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(appContext, "Incorrect email or password", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(appContext, errorToast, Toast.LENGTH_SHORT).show();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

    /**
     * Returns true if login is successful
     * @return boolean that states if a login was successful
     */
    public static Boolean getLoginSuccess() {
        return loginSuccess;
    }

    /**
     * get user id from User JSON object
     * @param object
     */
    public static void parseUserId(JSONObject object) {

        if (object == null) {
            return;
        }

        try {
            temp_user.setId(Integer.parseInt(object.getString(Const.USER_ID)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * parse JSON object into a User Object
     * @param object
     */
    public static void parseJSONObject(JSONObject object) {

        if (object == null) {
            return;
        }

        try {

            LoginActivity.user.setId(Integer.parseInt(object.getString(Const.USER_ID)));
            LoginActivity.user.setFirstName(object.getString(Const.FIRST_NAME));
            LoginActivity.user.setLastName(object.getString(Const.LAST_NAME));
            LoginActivity.user.setAddress(object.getString(Const.ADDRESS));
            LoginActivity.user.setEmail(object.getString(Const.EMAIL_ID));
            LoginActivity.user.setPremium(object.getString(Const.PREMIUM));
            LoginActivity.user.setPhoneNumber(object.getString(Const.PHONE_NUMBER));
            LoginActivity.user.setPassword(object.getString(Const.PASSWORD));
            LoginActivity.user.setUsername(object.getString(Const.USERNAME));



        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(LoginActivity.user.getPremium().equals("true")){
            Intent myIntent = new Intent(LoginActivity.applicationContext, PremiumUserDashboardActivity.class);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(LoginActivity.applicationContext, myIntent, null);
        } else {
            Intent myIntent = new Intent(LoginActivity.applicationContext, FreeUserDashboardActivity.class);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(LoginActivity.applicationContext, myIntent, null);
        }
    }

    /**
     * get all the Users from the database for statistics
     * @throws JSONException
     */
    public static void getAllUsers() throws JSONException {
        JsonArrayRequest jsonArrReq = new JsonArrayRequest(Request.Method.GET,
                Const.URL_JSON_ARRAY, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                object = response.getJSONObject(i);
                                parseUserId(object);
                                RegistrationActivity.receiptMap.put(temp_user.getId(), new ArrayList<String>());
                                Log.d(TAG, response.getJSONObject(i).toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(appContext, errorToast, Toast.LENGTH_SHORT).show();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrReq,
                tag_json_obj);

    }

    /**
     * makes a registration request
     * @throws JSONException
     */
    public static void makeRegistrationRequest() throws JSONException {
        buildRegistrationJSON();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                URL, json,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            makeLoginRequest();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(appContext, "Registration Successful", Toast.LENGTH_SHORT).show();
                        RegistrationActivity.receiptMap.put(LoginActivity.user.getId(), new ArrayList<String>());
                        Log.d(TAG, response.toString());
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(appContext, errorToast, Toast.LENGTH_SHORT).show();
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq,
                tag_json_obj);

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
    }

    /**
     * GET a User along with their current saved receipts from database
     * @throws JSONException
     */
    public static void getUserObject() throws JSONException {
        buildGetUserByUserIdJSON();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                URL, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        object = response;
                        parseJSONObject(object);
                        try {
                            ReceiptController.getAllReceipts();
                            ReceiptItemsController.getAllReceiptsItems();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d(TAG, response.toString());
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(appContext, errorToast, Toast.LENGTH_SHORT).show();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq,
                tag_json_obj);
    }


}


