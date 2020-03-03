package com.example.vignesh.textrecognitionimage.Controllers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.vignesh.textrecognitionimage.Communications.AppController;
import com.example.vignesh.textrecognitionimage.Activities.LoginActivity;
import com.example.vignesh.textrecognitionimage.Activities.ReceiptInputActivity;
import com.example.vignesh.textrecognitionimage.Model.Receipt;
import com.example.vignesh.textrecognitionimage.Utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller for receipt requests
 */
public class ReceiptController {

    private static String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req",
            errorToast = "Null error", URL = "", TAG = ReceiptController.class.getSimpleName();

    private static Receipt temp_receipt = new Receipt();

    private static Context appContext = null;

    private static JSONObject json = new JSONObject();
    private static JSONObject object;

    public static ArrayList<String> receipt_names = new ArrayList<>();
    public static ArrayList<Integer> receiptIds = new ArrayList<>();
    public static HashMap<Integer, String> rMap = new HashMap<>();
    public static ArrayList<Receipt> receipts = new ArrayList<>();
    public static ArrayList<String> uniqueCategories = new ArrayList<>();
    public static boolean sort_category = false;
    public static boolean sort_receipts = false;


    /**
     * Post receipt to the database that has been entered manually by the user.
     * @throws JSONException
     */
    public static void postManualReceipt() throws JSONException {
        buildReceiptJson();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                URL, json,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            parseReceiptJSON(response);
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
        }) {

            /**
             * Passing some request headers
             */
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
     * GET all the receipts from the database for the current (logged in) user
     * @throws JSONException
     */
    public static void getAllReceipts() throws JSONException {
        URL = Const.URL_GET_ALL_RECEIPTS_FOR_USER + LoginActivity.user.getId();
        JsonArrayRequest jsonArrReq = new JsonArrayRequest(Request.Method.GET,
                URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            ArrayList<Receipt> temp = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                object = response.getJSONObject(i);
//                                String name = object.getString("receiptName");
//                                receipt_names.add(name);
//                                receiptIds.add(Integer.parseInt(object.getString("receiptId")));
//                                rMap.put(Integer.parseInt(object.getString("receiptId")), name);
                                Receipt r = new Receipt();
                                parseReceiptJSON_new(object, r);
                                temp.add(r);
                                receipts = temp;
                            } }catch (JSONException e) {
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
     * Parse a JSON object into a current user's Receipt object
     * @param object JSON object to be parsed
     * @throws JSONException
     */
    private static void parseReceiptJSON(JSONObject object) throws JSONException {
        ReceiptInputActivity.receipt.setId(Integer.parseInt(object.getString(Const.RECEIPT_ID)));
        ReceiptInputActivity.receipt.setStoreName(object.getString(Const.STORE_NAME));
        ReceiptInputActivity.receipt.setReceiptName(object.getString(Const.RECEIPT_NAME));
        ReceiptInputActivity.receipt.setAddress(object.getString(Const.ADDRESS));
        ReceiptInputActivity.receipt.setPhoneNumber(object.getString(Const.PHONE_NUMBER));
        ReceiptInputActivity.receipt.setSubTotal(Double.parseDouble(object.getString(Const.SUB_TOTAL)));
        ReceiptInputActivity.receipt.setTax(Double.parseDouble(object.getString(Const.TAX)));
        ReceiptInputActivity.receipt.setCompleteTotal((object.getDouble(Const.COMPLETE_TOTAL)));
        ReceiptInputActivity.receipt.setDateOfPurchase(object.getString(Const.DATE));
        ReceiptInputActivity.receipt.setCategory(object.getString(Const.CATEGORY));
    }

    /**
     * Parse a fetched JSON object into a general Receipt object
     * @param object JSON Object
     * @param receipt an empty Receipt Object
     * @throws JSONException
     */
    private static void parseReceiptJSON_new(JSONObject object , Receipt receipt) throws JSONException {
        receipt.setId(Integer.parseInt(object.getString(Const.RECEIPT_ID)));
        receipt.setStoreName(object.getString(Const.STORE_NAME));
        receipt.setReceiptName(object.getString(Const.RECEIPT_NAME));
        receipt.setAddress(object.getString(Const.ADDRESS));
        receipt.setPhoneNumber(object.getString(Const.PHONE_NUMBER));
        receipt.setSubTotal(Double.parseDouble(object.getString(Const.SUB_TOTAL)));
        receipt.setTax(Double.parseDouble(object.getString(Const.TAX)));
        receipt.setCompleteTotal((object.getDouble(Const.COMPLETE_TOTAL)));
        receipt.setDateOfPurchase(object.getString(Const.DATE));
        receipt.setCategory(object.getString(Const.CATEGORY));
    }

    /**
     * Parse a Receipt object into a JSON object to be posted into the database
     * @throws JSONException
     */
    public static void buildReceiptJson() throws JSONException {

        JSONObject params = new JSONObject();
        params.put("userId", LoginActivity.user.getId());
        params.put("storeName", ReceiptInputActivity.receipt.getStoreName());
        params.put("receiptName", ReceiptInputActivity.receipt.getReceiptName());
        params.put("address", ReceiptInputActivity.receipt.getAddress());
        params.put("phoneNumber", ReceiptInputActivity.receipt.getPhoneNumber());
        params.put("subtotal", ReceiptInputActivity.receipt.getSubTotal());
        params.put("tax", ReceiptInputActivity.receipt.getTax());
        params.put("date", ReceiptInputActivity.receipt.getDateOfPurchase());
        params.put("completetotal", ReceiptInputActivity.receipt.getCompleteTotal());
        params.put("category", ReceiptInputActivity.receipt.getCategory());


        URL = Const.URL_POST_RECEIPT;
        errorToast = "Couldn't send receipt";
        appContext = ReceiptInputActivity.applicationContext;
        json = params;
    }

    /**
     * get the receipt name from a fetched JSON object
     * @param object JSON object
     */
    public static void parseReceiptName(JSONObject object) {

        if (object == null) {
            return;
        }

        try {
            temp_receipt.setReceiptName(object.getString(Const.RECEIPT_NAME));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the number of total receipts that the user has
     * @return number of total receipts for the current (logged in) user
     */
    public static int getNumberOfReceipts(){
        return receipts.size();
    }

    /**
     * Parse the latest (receipt) JSON object into a Receipt object
     * @param object JSON object
     * @return a Receipt object
     */
    private static Receipt parseLatestReceiptsJSON(JSONObject object){

        Receipt receipt = new Receipt();

        try {

            receipt.setId(object.getInt(Const.RECEIPT_ID));
            receipt.setUserId(object.getInt(Const.USER_ID));
            receipt.setStoreName(object.getString(Const.STORE_NAME));
            receipt.setPhoneNumber(object.getString(Const.PHONE_NUMBER));
            receipt.setAddress(object.getString(Const.ADDRESS));
            receipt.setSubTotal(object.getDouble(Const.SUB_TOTAL));
            receipt.setReceiptName(object.getString(Const.RECEIPT_NAME));
            receipt.setTax(object.getDouble(Const.TAX));
            receipt.setCompleteTotal(object.getDouble(Const.COMPLETE_TOTAL));
            receipt.setDateOfPurchase(object.getString(Const.DATE));
            receipt.setCategory(object.getString(Const.CATEGORY));

        } catch (JSONException e){
            e.printStackTrace();
        }

        return receipt;
    }

    /**
     * build the URL for getting the latest receipt from the database
     */
    private static void buildLatestReceiptsJSON(){
        URL = Const.URL_GET_CURRENT_RECEIPTS + LoginActivity.user.getId();
        errorToast = "Could not get latest receipts by userId";
    }


}
