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
import com.example.vignesh.textrecognitionimage.Activities.FreeUserDashboardActivity;
import com.example.vignesh.textrecognitionimage.Activities.GalleryActivity;
import com.example.vignesh.textrecognitionimage.Activities.PremiumUserDashboardActivity;
import com.example.vignesh.textrecognitionimage.Activities.ReceiptCategoryActivity;
import com.example.vignesh.textrecognitionimage.Activities.UploadActivity;
import com.example.vignesh.textrecognitionimage.Communications.AppController;
import com.example.vignesh.textrecognitionimage.Activities.LoginActivity;
import com.example.vignesh.textrecognitionimage.Activities.ReceiptInputActivity;
import com.example.vignesh.textrecognitionimage.Model.ReceiptItems;
import com.example.vignesh.textrecognitionimage.Activities.ReceiptItemsActivity;
import com.example.vignesh.textrecognitionimage.Utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Controller for receipt item requests
 */
public class ReceiptItemsController {

    private static String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req",
            errorToast = "Null error", URL = "", TAG = ReceiptItemsController.class.getSimpleName();

    private static JSONObject json = new JSONObject();
    private static JSONObject object;

    private static Context appContext;
    public static ArrayList<ReceiptItems> items = new ArrayList<>();

    /**
     * GET all the receipt items from the database for the current (logged in) user
     * @throws JSONException
     */
    public static void getAllReceiptsItems() throws JSONException {
        URL = Const.URL_GET_ALL_RECEIPT_ITEMS_FOR_USER + LoginActivity.user.getId();
        JsonArrayRequest jsonArrReq = new JsonArrayRequest(Request.Method.GET,
                URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            ArrayList<ReceiptItems> temp_list = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                ReceiptItems temp = new ReceiptItems();
                                object = response.getJSONObject(i);
                                temp.setItemName(object.getString("itemName"));
                                temp.setItemPrice(Double.parseDouble(object.getString("itemPrice")));
                                temp.setReceiptId(Integer.parseInt(object.getString("receiptId")));
                                temp.setUserId(Integer.parseInt(object.getString("userId")));
                                temp_list.add(temp);
                                items = temp_list;

                            }


                            if (FreeUserDashboardActivity.galleryPressed) {
                                FreeUserDashboardActivity.galleryPressed = false;
                                Intent myIntent = new Intent(FreeUserDashboardActivity.appContext, ReceiptCategoryActivity.class);
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(FreeUserDashboardActivity.appContext, myIntent, null);
                            }
                            else if (PremiumUserDashboardActivity.galleryPressed) {
                                PremiumUserDashboardActivity.galleryPressed = false;
                                Intent myIntent = new Intent(PremiumUserDashboardActivity.appContext, ReceiptCategoryActivity.class);
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(PremiumUserDashboardActivity.appContext, myIntent, null);
                            }}catch (JSONException e) {
                            e.printStackTrace();
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
                tag_json_arry);
    }

    /**
     * POST receipt items to the database
     * @throws JSONException
     */
    public static void postReceiptItems() throws JSONException {
        // final JSONArray items_arr = buildReceiptItemsJson();
        //buildReceiptItemsJson();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                URL, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

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
     * Parse Receipt Item into a JSON object to be posted into the database
     * @throws JSONException
     */
    public static void buildReceiptItemsJson() throws JSONException {
        ArrayList<ReceiptItems> itemsArray = ReceiptItemsActivity.getItemsArray() ;
        URL = Const.URL_POST_RECEIPT_ITEMS;
        errorToast = "Couldn't send receipt items";
        appContext = ReceiptInputActivity.applicationContext;
        // JSONArray itemJSON = new JSONArray();

        for(int i = 0; i < itemsArray.size(); i++){
            JSONObject params = new JSONObject();
            params.put("userId", itemsArray.get(i).getUserId());
            params.put("receiptId", itemsArray.get(i).getReceiptId());
            params.put("itemName", itemsArray.get(i).getItemName());
            params.put("itemPrice", itemsArray.get(i).getItemPrice());
            json = params;
            postReceiptItems();
            // itemJSON.put(json);
        }
        if (LoginActivity.user.getPremium().equals("true")) {
            Intent input = new Intent(ReceiptItemsController.appContext, PremiumUserDashboardActivity.class);
            ReceiptItemsController.appContext.startActivity(input);
        }
        else {
            Intent input = new Intent(ReceiptItemsController.appContext, FreeUserDashboardActivity.class);
            ReceiptItemsController.appContext.startActivity(input);
        }
        //  return itemJSON;
    }
}
