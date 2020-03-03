package com.example.vignesh.textrecognitionimage.Activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.vignesh.textrecognitionimage.Communications.AppController;
import com.example.vignesh.textrecognitionimage.Model.Receipt;
import com.example.vignesh.textrecognitionimage.R;
import com.example.vignesh.textrecognitionimage.Utils.Const;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.vision.text.Line;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Graphs for users to see spenditures
 */
public class GraphsActivity extends AppCompatActivity {

    private static Context appContext;
    private static String URL = "";
    private static String errorToast = "Null error";
    private static String tag_json_arry = "jarray_req";
    private static String TAG = GraphsActivity.class.getSimpleName();
    private static List<Receipt> currentReceipts = new ArrayList<>();
    public JSONArray responseArr;
    public static boolean beenHere = false;

    /**
     *
     * @param savedInstanceState
     * creates the Graph activity page
     * Shows interactive graphs on user spending practices
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphs);
        appContext = getApplicationContext();

        try {

            if(beenHere == false){
                buildGraphs();
                beenHere = true;
            } else {
                printTitle();
                createSpendingByCategoryGraph();
                printThreeGreatestExpenditures();
                printTotalAmount();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * @throws JSONException
     * gets the last receipt stored
     */
    public void buildGraphs() throws JSONException {
        buildCurrentMonthJSON();
        final JsonArrayRequest jsonArrReq = new JsonArrayRequest(Request.Method.GET,
                URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(final JSONArray response) {

                        responseArr = response;
                        for (int i = 0; i < responseArr.length(); i++) {
                            JSONObject object = null;
                            try {
                                object = responseArr.getJSONObject(i);
                                if (object != null) {currentReceipts.add(parseReceiptJSON(object));}
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        printTitle();
                        createSpendingByCategoryGraph();
                        printThreeGreatestExpenditures();
                        printTotalAmount();

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

    private void printTitle() {
        TextView title = (TextView) findViewById(R.id.title);
        title.append("Hey " + LoginActivity.user.getFirstName() + "," + "\n\n");
        title.append("Take a look at how you've spent your money this month");
    }

    private void createSpendingByCategoryGraph() {

        HashMap<String, Double> map = getReceiptByCategory();
        PieChart chart = (PieChart) findViewById(R.id.piechart);
        Description description = new Description();
        description.setText("");
        chart.setDescription(description);

        List<PieEntry> entries = new ArrayList<PieEntry>();

        for(Map.Entry<String, Double> entry: map.entrySet()){
            float total = (float) entry.getValue().floatValue();
            PieEntry pieEntry = new PieEntry(total, entry.getKey());
            entries.add(pieEntry);
        }

        PieDataSet set = new PieDataSet(entries, "Breakin' down your spending");
        PieData data = new PieData(set);
        chart.setCenterText("Breakin' down your spending");
        chart.setData(data);
        chart.invalidate(); //refresh

        PieDataSet firstDataSet = new PieDataSet(entries, "");

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(249, 231, 159));
        colors.add(Color.rgb(187, 143, 206));
        colors.add(Color.rgb(133, 193, 233));
        colors.add(Color.rgb(229, 152, 102));
        colors.add(Color.rgb(125, 206, 160));

        firstDataSet.setColors(colors);
        PieData pieData = new PieData(firstDataSet);
        chart.setData(pieData);
    }


    private static HashMap<String, Double> getReceiptByCategory(){

        HashMap<String, Double> map = new HashMap<>();

        if(currentReceipts.size() < 2){
            throw new Error("Unable to get receipts by Category. Need at least 2 receipts");
        }

        for(Receipt receipt: currentReceipts){
            if(map.containsKey(receipt.getCategory())){
                map.put(receipt.getCategory(), map.get(receipt.getCategory()) + receipt.getCompleteTotal());
            } else {
                map.put(receipt.getCategory(), receipt.getCompleteTotal());
            }
        }

        return map;
    }

    private void printTotalAmount(){

        TextView monthlyTotal = findViewById(R.id.totalAmount);
        double total = 0;

        for(Receipt receipt: currentReceipts){
            total += receipt.getCompleteTotal();
        }

        monthlyTotal.append("Looks like you've spent a total of $" +total + " this month");

    }

    private void printThreeGreatestExpenditures(){

        TextView greatestExpenditures = findViewById(R.id.topThreePurchases);
        TextView totals = findViewById(R.id.totals);

        HashMap<String, String> greatest = getThreeGreatestExpenditures();
        HashMap<String, Double> receipts =  getReceiptByStore();

        greatestExpenditures.append("1." + "     " + greatest.get("first")
                + "                                    " + "\n" + "\n");
        totals.append("$" + receipts.get(greatest.get("first")) + "\n" + "\n");
        greatestExpenditures.append("2." + "     " + greatest.get("second")
                + "                                    " + "\n" + "\n");
        totals.append("$" + receipts.get(greatest.get("second"))+ "\n" + "\n");
        greatestExpenditures.append("3." + "     " + greatest.get("third")
                + "                                    " + "\n"+ "\n");
        totals.append("$" + receipts.get(greatest.get("third")) + "\n"+ "\n");
    }

    private static HashMap<String, Double> getReceiptByStore(){

        HashMap<String, Double> map = new HashMap<>();

        if(currentReceipts.size() == 0){
            throw new Error("Unable to get any receipts. Size of receipts is 0");
        }

        for(Receipt receipt: currentReceipts){
            if(map.containsKey(receipt.getStoreName())){
                map.put(receipt.getStoreName(), map.get(receipt.getStoreName()) + receipt.getCompleteTotal());
            } else {
                map.put(receipt.getStoreName(), receipt.getCompleteTotal());
            }
        }

        return map;
    }

    /**
     *
     * @return top three receipts with the most spending
     * Provide results based on user spending practices
     */
    private static HashMap<String, String> getThreeGreatestExpenditures(){

        HashMap<String, Double> receipts = getReceiptByStore();

        if(receipts.size() < 3){
            throw new Error("Need at least three receipts with different store names");
        }



        //keeps track of the indexes of the 3 greatest store purchases
        HashMap<String, String> map = new HashMap<>();
        map.put("first", "");
        map.put("second", "");
        map.put("third", "");

        double first = 0, second = 0, third = 0;
        int index = 0;

        for(Map.Entry<String, Double> entry: receipts.entrySet()){
            if(entry.getValue() > first) {
                third = second;
                map.put("third", map.get("second"));

                second = first;
                map.put("second", map.get("first"));

                first = entry.getValue();
                map.put("first", entry.getKey());
            } else if(entry.getValue() > second){
                third = second;
                map.put("third", map.get("second"));

                second = entry.getValue();
                map.put("second", entry.getKey());
            } else if (entry.getValue() > third) {
                third = entry.getValue();
                map.put("third", entry.getKey());
            }
        }

        return map;
    }

    /**
     *
     * @param object JSON object to be parsed into Receipt object
     * @return a Receipt object
     * Parse a JSON object into Receipt object
     */
    private static Receipt parseReceiptJSON(JSONObject object){

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


    private static void buildCurrentMonthJSON(){
        URL = Const.URL_GET_CURRENT_RECEIPTS + LoginActivity.user.getId();
        errorToast = "Could not get the previous month's receipts by userId";
    }


}
