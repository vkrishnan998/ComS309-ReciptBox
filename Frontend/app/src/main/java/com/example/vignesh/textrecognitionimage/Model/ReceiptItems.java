package com.example.vignesh.textrecognitionimage.Model;

/**
 * Receipt item object
 */
public class ReceiptItems {
    String itemName;
    Double itemPrice;
    int receiptId;
    int userId;

    /**
     * Creates receipt item object
     */
    public ReceiptItems() {

    }

    /**
     * Get name of item
     * @return
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Set name of item
     * @param itemName
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * Gets price of item
     * @return
     */
    public Double getItemPrice() {
        return itemPrice;
    }

    /**
     * Sets price of item
     * @param itemPrice
     */
    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    /**
     * Get receiptID of item's receipt
     * @return
     */
    public int getReceiptId() {
        return receiptId;
    }

    /**
     * Set receiptID of item's receipt
     * @param rid
     */
    public void setReceiptId(int rid) {
        receiptId = rid;
    }

    /**
     * Get userID of item's user
     * @return
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Set userID of item's user
     * @param uid
     */
    public void setUserId(int uid) {
        userId = uid;
    }


}
