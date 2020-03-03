package com.example.vignesh.textrecognitionimage.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vignesh.textrecognitionimage.Controllers.ReceiptController;
import com.example.vignesh.textrecognitionimage.Controllers.ReceiptItemsController;
import com.example.vignesh.textrecognitionimage.Model.ReceiptItems;
import com.example.vignesh.textrecognitionimage.R;

import java.util.ArrayList;

/**
 * This activity displays receipts off the gallery page
 */
public class ReceiptItemsDisplayActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private TextView item;
    private TextView itemPrice;
    private LinearLayout lnrDynamicEditTextHolder;
    private LinearLayout itemAndPrice;
    private int pressedButtonNumber;
    private ArrayList<ReceiptItems> allItems = ReceiptItemsController.items;
    private boolean atLeastOne;
    private TextView tv;

//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//            switch (item.getItemId()) {
//                case R.id.navigation_home:
//
//                    Button navigation_home = (Button) findViewById(R.id.navigation_home);
//
//
//
//                    mTextMessage.setText(R.string.title_home);
//                    Intent myIntent = new Intent(view.getContext(), Rec.class);
//                    startActivityForResult(myIntent, 0);
//                    return true;
//                case R.id.navigation_dashboard:
//                    mTextMessage.setText(R.string.title_dashboard);
//                    if(LoginActivity.user.getPremium().equals("true")){
//                        setContentView(R.layout.activity_premium_user_dashboard);
//                    } else {
//                        setContentView(R.layout.activity_dashboard);
//                    }
//                    return true;
//                case R.id.navigation_notifications:
//                    mTextMessage.setText("Enter new receipt");
//                    setContentView(R.layout.activity_manual_input);
//                    return true;
//            }
//            return false;
//        }
//    };

    /**
     * When this app is access from the gallery page, it displays the receipts on a separate page
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_items_display);
        atLeastOne = false;
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        lnrDynamicEditTextHolder = (LinearLayout) findViewById(R.id.lnrDynamicEditTextHolder);
        itemAndPrice = (LinearLayout) findViewById(R.id.layout);
        itemAndPrice.setVisibility(View.GONE);
        tv = (TextView) findViewById(R.id.receipt_items);
        tv.setVisibility(View.GONE);

        int receiptId = getIntent().getExtras().getInt("buttonNumber");

//        int receiptId = ReceiptController.receipts.get(pressedButtonNumber).getId();
        LinearLayout ll = new LinearLayout(ReceiptItemsDisplayActivity.this);
        ll.setLayoutParams(itemAndPrice.getLayoutParams());
        ll.setOrientation(LinearLayout.HORIZONTAL);
        //l.setPadding(30,40, 0,0);

        final TextView vv = new TextView(ReceiptItemsDisplayActivity.this);
        vv.setText("ITEM NAME");
        vv.setTextColor(getResources().getColor(R.color.black));
        vv.setTypeface(null, Typeface.BOLD);
        vv.setTextSize(22);
        vv.setLayoutParams(tv.getLayoutParams());


        final TextView vv1 = new TextView(ReceiptItemsDisplayActivity.this);
        vv1.setText("ITEM PRICE");
        vv1.setTextColor(getResources().getColor(R.color.black));
        vv1.setTextSize(22);
        vv1.setTypeface(null, Typeface.BOLD);
        vv1.setLayoutParams(tv.getLayoutParams());



        ll.addView(vv);
        ll.addView(vv1);

        lnrDynamicEditTextHolder.addView(ll);

        for(int i = 0; i < allItems.size(); i++){
            if(receiptId == allItems.get(i).getReceiptId()){
                LinearLayout l = new LinearLayout(ReceiptItemsDisplayActivity.this);
                l.setLayoutParams(itemAndPrice.getLayoutParams());
                l.setOrientation(LinearLayout.HORIZONTAL);
                //l.setPadding(30,40, 0,0);

                final TextView v = new TextView(ReceiptItemsDisplayActivity.this);
                v.setText(allItems.get(i).getItemName());
                v.setTextColor(getResources().getColor(R.color.black));
                v.setTextSize(20);
                v.setLayoutParams(tv.getLayoutParams());

                final TextView v1 = new TextView(ReceiptItemsDisplayActivity.this);
                v1.setText("$" + Double.toString(allItems.get(i).getItemPrice()));
                v1.setTextColor(getResources().getColor(R.color.black));
                v1.setTextSize(20);
                v1.setLayoutParams(tv.getLayoutParams());

                l.addView(v);
                l.addView(v1);

                lnrDynamicEditTextHolder.addView(l);
                atLeastOne = true;
            }

        }
        if(!atLeastOne) {


            final TextView v = new TextView(ReceiptItemsDisplayActivity.this);
            v.setText("No Items");
            v.setTextColor(getResources().getColor(R.color.black));
            v.setTextSize(35);

            ll.addView(v);

            //lnrDynamicEditTextHolder.addView(ll);
        }


    }

}
