package com.example.vignesh.textrecognitionimage.Activities;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.vignesh.textrecognitionimage.Controllers.ReceiptController;
import com.example.vignesh.textrecognitionimage.Model.Receipt;
import com.example.vignesh.textrecognitionimage.Model.User;
import com.example.vignesh.textrecognitionimage.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

/**
 * Let's users input receipts manually or auto populate from scanning
 */
public class ReceiptInputActivity extends AppCompatActivity {

    private static EditText storeNameText;
    EditText receiptNameText;
    private static EditText addressText;
    EditText phoneNumberText;
    private static EditText subTotalText;
    private static EditText taxText;
    private static EditText dateText;
    private static EditText completeTotalText;
    private static EditText categoryText;
    private static Double completeTotal;
    private static Double tax;
    private static String storeName;
    private static String date;
    private static String address;
    private static String category;
    private static Double subTotal;
    public static Receipt receipt = new Receipt();
    public static Context applicationContext;
    public static ArrayList<String> receiptNames = new ArrayList<>();
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Spinner spinner;

    /**
     * Creates ReceiptInput UI
     * For manual input and scanned receipts auto populate
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_input);

        storeNameText = findViewById(R.id.storeText);
        receiptNameText = findViewById(R.id.receiptText);
        addressText = findViewById(R.id.addressText);
        phoneNumberText = findViewById(R.id.phoneText);
        subTotalText = findViewById(R.id.subTotalText);
        taxText = findViewById(R.id.taxText);
        completeTotalText = findViewById(R.id.completeTotalText);
        dateText = (EditText) findViewById(R.id.dateText);
        categoryText = (EditText) findViewById(R.id.categoryText) ;
        spinner = findViewById(R.id.spinner2);
        categoryText.setVisibility(View.GONE);

        applicationContext = this;

        subTotalText.setHint("Sub Total");
        subTotalText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        subTotalText.addTextChangedListener(new NumberTextWatcher(subTotalText, "#####"));

        taxText.setHint("Tax");
        taxText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        taxText.addTextChangedListener(new NumberTextWatcher(taxText, "#####"));

        completeTotalText.setHint("Complete Total");
        completeTotalText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        completeTotalText.addTextChangedListener(new NumberTextWatcher(completeTotalText, "#####"));

        if(UploadActivity.receipt_from_scan || UploadActivity.receipt_from_upload) {
            try {
                processDataString(UploadActivity.scanData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            updateTextFields();
        }

        List<User> userList = new ArrayList<>();
        // Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<User> adapter = new ArrayAdapter<User>(this,
//                android.R.layout.simple_spinner_item, userList);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        View.OnTouchListener spinnerOnTouch = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if(spinner.getSelectedItem().toString().equals("None of the Above")){
                        categoryText.setVisibility(View.VISIBLE);
                    }
                    else{
                        categoryText.setVisibility(View.GONE);
                    }
                }
                return false;
            }
        };
        spinner.setOnTouchListener(spinnerOnTouch);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                              @Override
                                              public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                                  if (spinner.getSelectedItem().toString().equals("None of the Above")) {
                                                      categoryText.setVisibility(View.VISIBLE);
                                                  } else {
                                                      categoryText.setVisibility(View.GONE);
                                                  }
                                              }

                                              @Override
                                              public void onNothingSelected(AdapterView<?> parent) {

                                              }
                                          });
//        View.OnKeyListener spinnerOnKey = new View.OnKeyListener() {
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
//                    if(spinner.getSelectedItem().toString().equals("None of the Above")){
//                        categoryText.setVisibility(View.VISIBLE);
//                    }
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//        };

//        spinner.setOnKeyListener(spinnerOnKey);


        Button next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) { ;
                receipt.setStoreName(storeNameText.getText().toString());
                receipt.setReceiptName(receiptNameText.getText().toString());
                receipt.setAddress(addressText.getText().toString());
                receipt.setPhoneNumber(phoneNumberText.getText().toString());
                receipt.setSubTotal(Double.parseDouble(subTotalText.getText().toString().substring(1)));
                receipt.setTax(Double.parseDouble(taxText.getText().toString().substring(1)));
                receipt.setDateOfPurchase(convertDateFormat(dateText.getText().toString()));
                receipt.setCompleteTotal(Double.parseDouble(completeTotalText.getText().toString().substring(1)));
                category = spinner.getSelectedItem().toString();
                if(category.equals("None of the above")){
                    receipt.setCategory(categoryText.getText().toString());
                }
                else{
                    receipt.setCategory(category);
                }

                try {
                    ReceiptController.postManualReceipt();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent myIntent = new Intent(view.getContext(), ReceiptItemsActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });


        dateText.setOnClickListener(new View.OnClickListener(){
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(ReceiptInputActivity.this,
                        android.R.style.Theme_Holo_NoActionBar,
                        mDateSetListener,
                        year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = "" + year + month + dayOfMonth;
                String dayLeadingZero = "";
                String monthLeadingZero = "";
                if (dayOfMonth < 10) {
                    dayLeadingZero = "0";
                }
                if(month + 1 < 10) {
                    monthLeadingZero = "0";
                }
                month = month + 1;
                dateText.setText(monthLeadingZero + "" + month + "/" + dayLeadingZero + "" +  dayOfMonth + "/" +
                    year);
            }
        };

    }

    private String convertDateFormat(String date) {

        date = date.substring(6,10) + "-" + date.substring(0,2) +  "-"  + date.substring(3,5);
        return date;
    }

    /**
     * Updates totalCost in receipt based on scan
     */
    public static void processDataString(JSONObject data) throws JSONException {
        completeTotal = findTotalCost(data);
        tax = findTax(data);
        storeName = findMerchantName(data);
        address = findMerchantAddress(data);
        date = findDate(data);
        subTotal = completeTotal - tax;

    }

    private static void updateTextFields() {
        storeNameText.setText(storeName);
        completeTotalText.setText(completeTotal.toString());
        taxText.setText(tax.toString());
        addressText.setText(address);
        dateText.setText(date);
        subTotalText.setText(subTotal.toString());

    }

    /**
     * Finds total cost in receipt scan
     * @param data String that contains all text from receipt
     * @return
     */
    private static Double findTotalCost(JSONObject data)  {
        try {
            return data.getJSONObject("totalAmount").getDouble("data");
        }
        catch (JSONException e) {
            e.printStackTrace();
            return 0.00;
        }
    }


    private static Double findTax(JSONObject data) throws JSONException {
        try {
            return data.getJSONObject("taxAmount").getDouble("data");
        }
        catch (JSONException e) {
            e.printStackTrace();
            return 0.00;
        }
    }

    private static String findDate(JSONObject data) throws JSONException {
        try {
            String dateString = data.getJSONObject("date").getString("data");
            dateString = dateString.substring(0, 10);
            dateString = dateString.substring(5, 7) + "/" + dateString.substring(8, 10) + "/" + dateString.substring(0, 4);
            return dateString;
        }
        catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String findMerchantName(JSONObject data) throws JSONException {
        try {
            return data.getJSONObject("merchantName").getString("data");
        }
        catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String findMerchantAddress(JSONObject data) throws JSONException {
        try {
            String address = data.getJSONObject("merchantAddress").getString("data") + ", " +
                    data.getJSONObject("merchantCity").getString("data") + ", " + data.getJSONObject("merchantState").getString("data");
            return address;
        }
        catch (JSONException e) {
            e.printStackTrace();
            return "";
        }

    }


}
