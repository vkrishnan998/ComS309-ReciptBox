package com.example.vignesh.textrecognitionimage.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vignesh.textrecognitionimage.Controllers.ReceiptController;
import com.example.vignesh.textrecognitionimage.Model.Receipt;
import com.example.vignesh.textrecognitionimage.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ReceiptCategoryActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private Button btn;
    private static ArrayList<Receipt> receipts_in = new ArrayList<>();
    private LinearLayout lnrDynamicEditTextHolder;
    private LinearLayout itemAndPrice;
    private LinearLayout categ_layout;


    public static String btnId;

    //public static boolean sort = false;

    private ArrayList<Integer> receipt_ids_in = ReceiptController.receiptIds;
    ArrayList<Receipt> receipts = new ArrayList<>();
    private static HashMap<String, ArrayList<Receipt>> categories = new HashMap<>();

//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
//                    return true;
//                case R.id.navigation_dashboard:
//                    mTextMessage.setText(R.string.title_dashboard);
//                    return true;
//                case R.id.navigation_notifications:
//                    mTextMessage.setText(R.string.title_notifications);
//                    return true;
//            }
//            return false;
//        }
//    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_category);
        receipts_in = ReceiptController.receipts;
        categories = splitCategories(receipts_in);
//        mTextMessage = (TextView) findViewById(R.id.message);
//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        
            Intent intent = getIntent();
//        try {
//            //JsonRequest.getAllUsers();
//            JsonRequest.getAllReceipts();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        lnrDynamicEditTextHolder = (LinearLayout) findViewById(R.id.lnrDynamicEditTextHolder);
        itemAndPrice = (LinearLayout) findViewById(R.id.layout);
        itemAndPrice.setVisibility(View.GONE);
        categ_layout = (LinearLayout) findViewById(R.id.layout1);
//        categ_layout.setVisibility(View.GONE);
        btn = findViewById(R.id.categ);
        btn.setVisibility(View.GONE);

        Button byDate = (Button)findViewById(R.id.by);
        byDate.setText("by Date");

        Button byName = (Button)findViewById(R.id.by1);
        byName.setText("by Name");

        Button byAmount = (Button)findViewById(R.id.by2);
        byAmount.setText("by Most Receipts");
//        LinearLayout m = new LinearLayout(ReceiptCategoryActivity.this);
//        m.setLayoutParams(categ_layout.getLayoutParams());
//        categ_layout.addView(byDate);
//        categ_layout.addView(byName);
//        categ_layout.addView(byAmount);
        //lnrDynamicEditTextHolder.addView(categ_layout);

        receipts = receipts_in;
            if(!ReceiptController.sort_category){
                ReceiptController.uniqueCategories = removeDuplicates(receipts);

            }

            //receipt_ids = receipt_ids_in;
            byDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Collections.sort(receipts, Receipt.idComparator);
                    Collections.reverse(receipts);
                    ReceiptController.uniqueCategories = removeDuplicates(receipts);
                    ReceiptController.sort_category = true;
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            });

            byName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TreeMap<String, ArrayList<Receipt>> nameSorted = new TreeMap<>();
                    nameSorted.putAll(categories);
                    ArrayList<String> temp = new ArrayList<String>(nameSorted.keySet());
                    ReceiptController.uniqueCategories = temp;
                    ReceiptController.sort_category = true;
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            });

            byAmount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    List<Map.Entry<String,ArrayList<Receipt>>> entries = new ArrayList<>(
                            categories.entrySet()
                    );
                    Collections.sort(
                            entries
                            ,   new Comparator<Map.Entry<String,ArrayList<Receipt>>>() {
                                public int compare(Map.Entry<String,ArrayList<Receipt>> a, Map.Entry<String,ArrayList<Receipt>> b) {
                                    return Integer.compare(b.getValue().size(), a.getValue().size());
                                }
                            }
                    );
                    ReceiptController.uniqueCategories.clear();
                    for (Map.Entry<String,ArrayList<Receipt>> e : entries){
                        ReceiptController.uniqueCategories.add(e.getKey());
                    }
                    ReceiptController.sort_category = true;
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            });

            showCategories(ReceiptController.uniqueCategories);
        }

        /**
         * Shows all receipts of a certain user
         * @param categ List of receipts that a user has
         */
        void showCategories(final ArrayList<String> categ) {

            for (int i = 0; i < categ.size(); i ++) {
                LinearLayout l = new LinearLayout(ReceiptCategoryActivity.this);
                l.setLayoutParams(itemAndPrice.getLayoutParams());
                l.setOrientation(LinearLayout.VERTICAL);
                //l.setPadding(30, 40, 0, 0);

                final Button btn = (Button) new Button(ReceiptCategoryActivity.this);
                btn.setLayoutParams(findViewById(R.id.categ).getLayoutParams());
                String title = categ.get(i);
                int titleLength = title.length();
                String subTitle = "Receipts: " + categories.get(categ.get(i)).size();
                int subtitleLength = subTitle.length();
                Spannable span = new SpannableString(title + "\n" + subTitle);
                span.setSpan(new RelativeSizeSpan(0.8f), titleLength, (titleLength + subtitleLength + 1), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                btn.setText(span);
                btn.setBackground(getDrawable(R.drawable.login_button));
                btn.setTextColor(getResources().getColor(R.color.white));
                //btn.setPadding(30, 0, 20, 0);
                final int j = i;
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ;
                        btnId = categ.get(j);
                        Intent myIntent = new Intent(view.getContext(), GalleryActivity.class);
                        myIntent.putExtra("buttonName", categ.get(j));
                        startActivityForResult(myIntent, 0);
                    }
                });

                btn.setId(j);
                l.addView(btn);
//                if (i + 1 < categ.size()) {
//                    final Button btn2 = (Button) new Button(ReceiptCategoryActivity.this);
//                    btn2.setLayoutParams(findViewById(R.id.categ).getLayoutParams());
//                    String title1 = categ.get(j+1);
//                    int titleLength1 = title1.length();
//                    String subTitle1 = "Receipts: " + categories.get(categ.get(j+1)).size();
//                    int subtitleLength1 = subTitle1.length();
//                    Spannable span1 = new SpannableString(title1 + "\n" + subTitle1);
//                    span1.setSpan(new RelativeSizeSpan(0.8f), titleLength1, (titleLength1 + subtitleLength1 + 1), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    btn2.setText(span1);
//                    btn2.setBackground(getDrawable(R.drawable.login_button));
//                    //btn2.setPadding(30, 0, 20, 0);
//                    //btn2.setWidth(150);
//                    btn2.setTextColor(getResources().getColor(R.color.white));
//
//                    btn2.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            ;
//                            btnId = categ.get(j+1);
//                            Intent myIntent = new Intent(view.getContext(), GalleryActivity.class);
//                            myIntent.putExtra("buttonName", categ.get(j + 1));
//                            startActivityForResult(myIntent, 0);
//                        }
//                    });
//
//                    btn2.setId(j+1);
//                    l.addView(btn2);
//                }
                l.setId(i + 1);
                lnrDynamicEditTextHolder.addView(l);

            }
        }

    public static HashMap<String, ArrayList<Receipt>> splitCategories(ArrayList<Receipt> list){
        HashMap<String, ArrayList<Receipt>> categories = new HashMap<>();
        for (Receipt i : list) {
            if(categories.containsKey(i.getCategory())){
                categories.get(i.getCategory()).add(i);
            }
            else {
                categories.put(i.getCategory(), new ArrayList<Receipt>());
                categories.get(i.getCategory()).add(i);
            }
        }
            return categories;
    }

    public ArrayList<String> getCategoryList(){
        ArrayList<String> cats = new ArrayList<>();
        for (int i = 0; i < receipts_in.size(); i++){
            cats.add(receipts_in.get(i).getCategory());
        }
        return cats;
    }


    public ArrayList<String> removeDuplicates(ArrayList<Receipt> list){
        ArrayList<String> new_cats = new ArrayList<>();
        for (int i = 0; i < list.size(); i++){
            if(!new_cats.contains(list.get(i).getCategory()))
            new_cats.add(list.get(i).getCategory());
        }
        return new_cats;
    }



    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > list =
                new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    public static HashMap<String, ArrayList<Receipt>> getCategories(){
            return categories;
    }


}


