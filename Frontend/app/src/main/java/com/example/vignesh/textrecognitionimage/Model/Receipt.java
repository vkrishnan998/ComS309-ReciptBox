package com.example.vignesh.textrecognitionimage.Model;

import android.support.annotation.NonNull;

import com.example.vignesh.textrecognitionimage.Activities.ReceiptCategoryActivity;

import java.sql.Date;
import java.util.Comparator;

/**
 * Receipt object
 */
public class Receipt implements Comparable{

    private String storeName;
    private String receiptName;
    private String address;
    private String phoneNumber;
    private Double subTotal;
    private Double tax;
    private String category;
    private Double completetotal;
    private String dateOfPurchase;
    private byte[] image;
    private int rid;
    private int userId;

    /**
     * Constructs a new receipt
     */
    public Receipt() {

    }

    /**
     * Get store name
     * @return Store name
     */
    public String getStoreName() {
        return this.storeName;
    }

    /**
     * Sets store name
     * @param storeName
     */
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    /**
     * Gets receipt name
     * @return
     */
    public String getReceiptName() {
        return this.receiptName;
    }

    /**
     * Sets receipt name
     * @param receiptName
     */
    public void setReceiptName(String receiptName) {
        this.receiptName = receiptName;
    }

    /**
     * Get address
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set address
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Get phone number
     * @return
     */
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
     * Set phone number
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Get sub total
     * @return
     */
    public Double getSubTotal() {
        return this.subTotal;
    }

    /**
     * Set sub total
     * @param subTotal
     */
    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    /**
     * Get tax
     * @return
     */
    public Double getTax() {
        return this.tax;
    }

    /**
     * Set tax
     * @param tax
     */
    public void setTax(Double tax) {
        this.tax = tax;
    }

    /**
     * Get complete total
     * @return
     */
    public Double getCompleteTotal() {
        return this.completetotal;
    }

    /**
     * Set complete total
     * @param completetotal
     */
    public void setCompleteTotal(Double completetotal) {
        this.completetotal = completetotal;
    }

    /**
     * Get date
     * @return
     */
    public int getId() {
        return this.rid;
    }

    /**
     * Set receipt Id
     * @param id
     */
    public void setId(int id) {
        this.rid = id;
    }

    /**
     * Get user of receipt's ID
     * @return
     */
    public int getUserId() {return this.userId; }

    /**
     * Set user of receipt's ID
     * @param id
     */
    public void setUserId(int id) {this.userId = id;}

    /**
     * Get category of receipt
     * @return
     */
    public String getCategory() {return category; }

    /**
     * Set category of receipt
     * @param category
     */
    public void setCategory(String category) {this.category = category; }


    public String getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(String dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }

    /**
     * Compares receipt names
     * @param compareto Receipt object to compare
     * @return
     */
    @Override
    public int compareTo(@NonNull Object compareto) {
        Receipt r = (Receipt) compareto;
        String name = r.getReceiptName();
        return this.receiptName.compareTo(name);
    }

    /**
     * Comparator used to compare receipt names for sorting
     */
    public static Comparator<Receipt> nameComparator = new Comparator<Receipt>() {

        /**
         * Compares two receipts by name
         * @param r1 first receipt
         * @param r2 second receipt
         * @return
         */
        public int compare(Receipt r1, Receipt r2) {
            String ReceiptName1 = r1.getReceiptName().toUpperCase();
            String ReceiptName2 = r2.getReceiptName().toUpperCase();

            //ascending order
            return ReceiptName1.compareTo(ReceiptName2);

            //descending order
            //return ReceiptName2.compareTo(ReceiptName1);
        }};

    /**
     * Comparator for sorting receipts by receipt ID
     */
    public static Comparator<Receipt> idComparator = new Comparator<Receipt>() {

        public int compare(Receipt r1, Receipt r2) {

            int rid1 = r1.getId();
            int rid2 = r2.getId();

            /*For ascending order*/
            return rid1-rid2;

            /*For descending order*/
            //rollno2-rollno1;
        }};

    /**
     * Comparator for sorting receipts by amount
     */
    public static Comparator<Receipt> amountComparator = new Comparator<Receipt>() {

        public int compare(Receipt r1, Receipt r2) {

            double a1 = r1.getCompleteTotal();
            double a2 = r2.getCompleteTotal();

            /*For ascending order*/
            //return rid1-rid2;

            /*For descending order*/
            double diff = a2 - a1;
            int diff2 = (int) diff;
            return diff2;
        }};

}
