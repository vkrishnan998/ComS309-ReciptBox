package com.example.vignesh.textrecognitionimage.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.vignesh.textrecognitionimage.Controllers.ReceiptController;
import com.example.vignesh.textrecognitionimage.Controllers.ReceiptItemsController;
import com.example.vignesh.textrecognitionimage.Model.ReceiptItems;
import com.example.vignesh.textrecognitionimage.R;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Let's users enter items for receipts
 */
public class ReceiptItemsActivity extends AppCompatActivity {


    private LinearLayout lnrDynamicEditTextHolder;
    private EditText edtNoCreate;
    private Button btnCreate;
    private EditText itemBox;
    private EditText priceBox;
    private Button btnSave;
    private Context appContext;
    private LinearLayout itemAndPrice;
    private ArrayList<EditText> itemET = new ArrayList<>();
    private ArrayList<EditText> priceET = new ArrayList<>();
    private Double lineItemsTotal = 0.00;
    public static final ArrayList<ReceiptItems> itemsArray = new ArrayList<>();

    /**
     * Creates ReceiptItemsInput UI for entering receipt items
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_items);
        appContext = this;

        lnrDynamicEditTextHolder = (LinearLayout) findViewById(R.id.lnrDynamicEditTextHolder);
        edtNoCreate = (EditText) findViewById(R.id.edtNoCreate);
        itemBox = (EditText) findViewById(R.id.items);
        priceBox = (EditText) findViewById(R.id.price);
        btnCreate = (Button) findViewById(R.id.btnCreate);
        btnSave = (Button) findViewById(R.id.btnSave);
        itemAndPrice = (LinearLayout) findViewById(R.id.layout);
        itemAndPrice.setVisibility(View.GONE);

        if (UploadActivity.receipt_from_scan || UploadActivity.receipt_from_upload) {
            lineItemsTotal = 0.0;
            try {
                lnrDynamicEditTextHolder.removeAllViews();
            } catch (Throwable e) {
                e.printStackTrace();
            }

            int length = 0;
            try {
                length = UploadActivity.scanData.getJSONArray("lineAmounts").length();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            for (int i = 0; i < length; i++) {
                LinearLayout l = new LinearLayout(ReceiptItemsActivity.this);
                l.setLayoutParams(itemAndPrice.getLayoutParams());
                l.setOrientation(LinearLayout.HORIZONTAL);

                EditText itemsText = new EditText(ReceiptItemsActivity.this);
                EditText priceText = new EditText(ReceiptItemsActivity.this);

                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(800, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(200, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);

                itemsText.setLayoutParams(param);
                priceText.setLayoutParams(param2);
                itemsText.setHint("Item " + (i + 1));
                priceText.setHint("$.$$");
                try {
                    Double price = UploadActivity.scanData.getJSONArray("lineAmounts").getJSONObject(i).getDouble("data");
                    lineItemsTotal = lineItemsTotal + price;
                    priceText.setText(price.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                priceText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                priceText.addTextChangedListener(new NumberTextWatcher(priceText, "#####"));

                itemsText.setError(null);
                itemsText.setId(i + 1);
                priceText.setId(i + 1);
                l.setId(i + 1);

                itemET.add(itemsText);
                priceET.add(priceText);

                l.addView(itemsText);
                l.addView(priceText);
                lnrDynamicEditTextHolder.addView(l);
                if (i == length) {
                    lnrDynamicEditTextHolder.addView(btnSave);
                }
            }
        }



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    postItems();
                    if (lineItemsTotal > ReceiptInputActivity.receipt.getCompleteTotal()) {
                        String formatCompleteTotal = String.format("%.2f", ReceiptInputActivity.receipt.getCompleteTotal());
                        String formatLineTotal = String.format("%.2f", lineItemsTotal - ReceiptInputActivity.receipt.getCompleteTotal());
                        Toast.makeText(appContext, "Line items total is greater than full total: $" + formatCompleteTotal + "\nDifference of $" + formatLineTotal, Toast.LENGTH_LONG).show();
                    }
                    else {
                        ReceiptItemsController.buildReceiptItemsJson();
//                        ReceiptController.getAllReceipts();
//                        ReceiptItemsController.getAllReceiptsItems();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });



        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtNoCreate.getText().toString().length()>0) {
                    try {
                        lnrDynamicEditTextHolder.removeAllViews();
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }

                    int length = Integer.parseInt(edtNoCreate.getText().toString());
                    //int length = 6;

                    for (int i=0;i<length;i++){
                        LinearLayout l = new LinearLayout(ReceiptItemsActivity.this);
                        l.setLayoutParams(itemAndPrice.getLayoutParams());
                        l.setOrientation(LinearLayout.HORIZONTAL);

                        EditText itemsText = new EditText(ReceiptItemsActivity.this);
                        EditText priceText = new EditText(ReceiptItemsActivity.this);

                        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(800, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                        LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(200, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);

                        itemsText.setLayoutParams(param);
                        priceText.setLayoutParams(param2);
                        itemsText.setHint("Item "+(i+1));
                        priceText.setHint("$.$$");
                        priceText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        priceText.addTextChangedListener(new NumberTextWatcher(priceText, "#####"));

                        itemsText.setError(null);
                        itemsText.setId(i+1);
                        priceText.setId(i+1);
                        l.setId(i+1);

                        itemET.add(itemsText);
                        priceET.add(priceText);

                        l.addView(itemsText);
                        l.addView(priceText);
                        lnrDynamicEditTextHolder.addView(l);
                        if(i == length){
                            lnrDynamicEditTextHolder.addView(btnSave);
                        }
                    }
                }
            }
        });
    }

    /**
     * Prepares receipt items into an array to be sent to database
     * @throws JSONException
     */
    public void postItems() throws JSONException{

        lineItemsTotal = 0.0;
        for(int i = 0; i < itemET.size(); i++){
            ReceiptItems item = new ReceiptItems();
            String itemName = itemET.get(i).getText().toString();
            if (itemName.length() == 0) {
                itemName = itemET.get(i).getHint().toString();
            }
            String priceString = "";
            if (priceET.get(i).getText().toString().charAt(0) != '$') {
                priceString = priceET.get(i).getText().toString();
                lineItemsTotal = lineItemsTotal + Double.parseDouble(priceString);
            }
            else {
                priceString = priceET.get(i).getText().toString().substring(1);
                lineItemsTotal = lineItemsTotal + Double.parseDouble(priceString);
            }


            boolean cancel = false;
            View focusView = null;
            double price;
            int userId = LoginActivity.user.getId();
            int receiptId = ReceiptInputActivity.receipt.getId();
            // Check for a valid item Name, if the user entered one.
            if(TextUtils.isEmpty(itemName)){
                itemET.get(i).setError("This field is required");
                focusView = itemET.get(i);
                cancel = true;
            }
            if(TextUtils.isEmpty(priceString)){
                price = 0.00;
                focusView = priceET.get(i);
            }
            else{
                price = Double.parseDouble(priceString);
            }

//                    if (cancel) {
//                        // There was an error; don't attempt login and focus the first
//                        // form field with an error.
//                        focusView.requestFocus();
//                    }
            //  else{
            item.setItemName(itemName);
            item.setItemPrice(price);
            item.setReceiptId(receiptId);
            item.setUserId(userId);
            itemsArray.add(item);
            // }
        }
    }

    /**
     * Returns array of receipt items for a receipt
     * @return
     */
    public static ArrayList<ReceiptItems> getItemsArray(){
        return itemsArray;
    }
}
