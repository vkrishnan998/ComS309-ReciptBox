package com.example.vignesh.textrecognitionimage.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.vignesh.textrecognitionimage.Controllers.ReceiptController;
import com.example.vignesh.textrecognitionimage.Model.Receipt;
import com.example.vignesh.textrecognitionimage.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

/**
 * Gallery for users to see their receipts
 */
public class GalleryActivity extends AppCompatActivity {
    private Button btn;
    private static ArrayList<Receipt> receipts_in  = new ArrayList<>();
    private LinearLayout lnrDynamicEditTextHolder;
    private LinearLayout itemAndPrice;
    public static int btnId;
    private ArrayList<Integer> receipt_ids_in = ReceiptController.receiptIds;
    private LinearLayout categ_layout;
    ArrayList<Receipt> receipts = new ArrayList<>();

    /**
     * Creates GalleryActivity UI
     * User can view their receipts
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        receipts_in = ReceiptController.receipts;
        Intent intent = getIntent();
//        try {
//            //JsonRequest.getAllUsers();
//            JsonRequest.getAllReceipts();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        boolean showAll = false;

        lnrDynamicEditTextHolder = (LinearLayout) findViewById(R.id.lnrDynamicEditTextHolder);
        itemAndPrice = (LinearLayout) findViewById(R.id.layout);
        itemAndPrice.setVisibility(View.GONE);
        categ_layout = (LinearLayout) findViewById(R.id.layout1);
        Button byDate = (Button)findViewById(R.id.by);
        byDate.setText("by Date");

        Button byName = (Button)findViewById(R.id.by1);
        byName.setText("by Name");

        Button byAmount = (Button)findViewById(R.id.by2);
        byAmount.setText("by Amount");

        //LinearLayout m = new LinearLayout(GalleryActivity.this);
//        m.addView(byDate);
//        m.addView(byName);
//        m.addView(byAmount);
        //lnrDynamicEditTextHolder.addView(m);
        if(!ReceiptController.sort_receipts){
            String category_name = ReceiptCategoryActivity.btnId;
            receipts = ReceiptCategoryActivity.getCategories().get(category_name);
        }
        else{
            receipts = receipts_in;
        }


        //receipt_ids = receipt_ids_in;
        byDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ReceiptController.sort_receipts){
                    String category_name = ReceiptCategoryActivity.btnId;
                    receipts = ReceiptCategoryActivity.getCategories().get(category_name);
                }
                else{
                    Collections.sort(receipts, Receipt.idComparator);
                    Collections.reverse(receipts);
                }
               // ReceiptCategoryActivity.sort = true;
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

        byName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ReceiptController.sort_receipts){
                    String category_name = ReceiptCategoryActivity.btnId;
                    receipts = ReceiptCategoryActivity.getCategories().get(category_name);
                }
                Collections.sort(receipts, Receipt.nameComparator);
                //ReceiptCategoryActivity.sort = true;
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

        byAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ReceiptController.sort_receipts){
                    String category_name = ReceiptCategoryActivity.btnId;
                    receipts = ReceiptCategoryActivity.getCategories().get(category_name);
                }
                Collections.sort(receipts, Receipt.amountComparator);
                //ReceiptCategoryActivity.sort = true;
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
        showAllReceipts(receipts);
        //showCategorizedReceipts(receipts);
    }

    /**
     * Shows all receipts of a certain user
     * @param receipts List of receipts that a user has
     */
    void showAllReceipts(final ArrayList<Receipt> receipts) {

            for (int i = 0; i < (receipts.size()); i += 2) {
                LinearLayout l = new LinearLayout(GalleryActivity.this);
                l.setLayoutParams(itemAndPrice.getLayoutParams());
                l.setOrientation(LinearLayout.HORIZONTAL);

                final Button btn = (Button) new Button(GalleryActivity.this);
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                p.weight = 1;
                btn.setLayoutParams(p);
                btn.setText(receipts.get(i).getReceiptName());
                btn.setBackground(getDrawable(R.drawable.login_button));
                btn.setTextColor(getResources().getColor(R.color.white));
                final int j = i;
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ;
                        btnId = receipts.get(j).getId();
                        Intent myIntent = new Intent(view.getContext(), ReceiptItemsDisplayActivity.class);
                        myIntent.putExtra("buttonNumber", btnId);
                        startActivityForResult(myIntent, 0);
                    }
                });

                btn.setId(receipts.get(j).getId());
                l.addView(btn);
                if (i + 1 < receipts.size()) {
                    final Button btn2 = (Button) new Button(GalleryActivity.this);
                    btn2.setLayoutParams(p);
                    btn2.setText(receipts.get(i + 1).getReceiptName());
                    btn2.setBackground(getDrawable(R.drawable.login_button));
                    btn2.setWidth(150);
                    btn2.setTextColor(getResources().getColor(R.color.white));
                    btn2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ;
                            btnId = receipts.get(j + 1).getId();
                            Intent myIntent = new Intent(view.getContext(), ReceiptItemsDisplayActivity.class);
                            myIntent.putExtra("buttonNumber", btnId);
                            startActivityForResult(myIntent, 0);
                        }
                    });

                    btn2.setId(receipts.get(i + 1).getId());
                    l.addView(btn2);
                }
                l.setId(i + 1);
                lnrDynamicEditTextHolder.addView(l);

            }
        }

    void showCategorizedReceipts(final ArrayList<Receipt> receipts) {

        for (int i = 0; i < receipts.size(); i++) {
            LinearLayout l = new LinearLayout(GalleryActivity.this);
            l.setLayoutParams(itemAndPrice.getLayoutParams());
            l.setOrientation(LinearLayout.HORIZONTAL);
            l.setPadding(30, 40, 0, 20);
                final Button btn = (Button) new Button(GalleryActivity.this);
                btn.setText(receipts.get(i).getReceiptName());
                btn.setBackground(getDrawable(R.drawable.login_button));
                btn.setTextColor(getResources().getColor(R.color.white));
                btn.setPadding(30, 0, 20, 0);
                final int j = i;
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ;
                        btnId = receipts.get(j).getId();
                        Intent myIntent = new Intent(view.getContext(), ReceiptItemsDisplayActivity.class);
                        myIntent.putExtra("buttonNumber", j);
                        startActivityForResult(myIntent, 0);
                    }
                });

                btn.setId(receipts.get(j).getId());
                l.addView(btn);

                if (i + 1 < receipts.size()) {
                        final Button btn2 = (Button) new Button(GalleryActivity.this);
                        btn2.setText(receipts.get(i + 1).getReceiptName());
                        btn2.setBackground(getDrawable(R.drawable.login_button));
                        btn2.setPadding(30, 0, 20, 20);
                        btn2.setTextColor(getResources().getColor(R.color.white));
                        btn2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ;
                                btnId = receipts.get(j + 1).getId();
                                Intent myIntent = new Intent(view.getContext(), ReceiptItemsDisplayActivity.class);
                                myIntent.putExtra("buttonNumber", j + 1);
                                startActivityForResult(myIntent, 0);
                            }
                        });

                        btn2.setId(receipts.get(i + 1).getId());
                        l.addView(btn2);
                    }
                    l.setId(i + 1);
                    lnrDynamicEditTextHolder.addView(l);
                }

        }

}
