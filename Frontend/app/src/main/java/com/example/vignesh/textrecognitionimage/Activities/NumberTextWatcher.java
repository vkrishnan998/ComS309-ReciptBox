package com.example.vignesh.textrecognitionimage.Activities;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Class for number formatting in input fields
 */
public class NumberTextWatcher implements TextWatcher {

    private final DecimalFormat df;
    private final DecimalFormat dfnd;
    private final EditText et;
    private boolean hasFractionalPart;
    private int trailingZeroCount;

    /**
     * Number text watcher is text input format in the form $##.## for price amounts
     * constructor to instantiate variables
     * @param editText
     * @param pattern
     */
    public NumberTextWatcher(EditText editText, String pattern) {
        df = new DecimalFormat(pattern);
        df.setDecimalSeparatorAlwaysShown(true);
        dfnd = new DecimalFormat("#.##");
        this.et = editText;
        hasFractionalPart = false;
    }

    private String current = "";

    /**
     * Parse the input on the go as the user inputs numbers in the form $##.##
     * @param s
     * @param start
     * @param before
     * @param count
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(!s.toString().equals(current)){
            et.removeTextChangedListener(this);

            String cleanString = s.toString().replaceAll("[$,.]", "");

            double parsed = Double.parseDouble(cleanString);
            String formatted = NumberFormat.getCurrencyInstance().format((parsed/100));

            current = formatted;
            et.setText(formatted);
            et.setSelection(formatted.length());

            et.addTextChangedListener(this);
        }
    }

    /**
     * auto-generated method not used
     * @param s
     */
    @Override
    public void afterTextChanged(Editable s) {

    }

    /**
     * auto-genrated method not used
     * @param s
     * @param start
     * @param count
     * @param after
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

}
