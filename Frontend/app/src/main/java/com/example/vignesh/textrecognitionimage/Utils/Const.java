package com.example.vignesh.textrecognitionimage.Utils;

/**
 * This class contains the urls that allows the the user to be able to access specific database entries based on the user
 * There is also variables associated with users and receipts listed below.
 */
public class Const {

	public static final String URL_REGISTRATION = "http://proj309-pp-08.misc.iastate.edu:8080/user/new";
	public static final String URL_JSON_ARRAY = "http://proj309-pp-08.misc.iastate.edu:8080/users";
	public static final String URL_LOGIN = "http://proj309-pp-08.misc.iastate.edu:8080/validate/";
	public static final String URL_STRING_REQ = "http://proj309-pp-08.misc.iastate.edu:8080/users";
	public static final String URL_IMAGE = "http://proj309-pp-08.misc.iastate.edu:8080/users";
	public static final String URL_POST_RECEIPT = "http://proj309-pp-08.misc.iastate.edu:8080/receipt/saveReceipt";
	public static final String URL_GET_ALL_RECEIPTS_FOR_USER = "http://proj309-pp-08.misc.iastate.edu:8080/receipt/getAllReceiptsForUser/";
	public static final String URL_POST_RECEIPT_ITEMS = "http://proj309-pp-08.misc.iastate.edu:8080/receiptItems/saveItems";
	public static final String URL_GET_ALL_RECEIPT_ITEMS_FOR_USER = "http://proj309-pp-08.misc.iastate.edu:8080/receiptItems/getAllItems/";
	public static String URL_GET_USER_BY_ID = "http://proj309-pp-08.misc.iastate.edu:8080/getUserById/";
	public static String URL_GET_RECEIPT_ID = "http://proj309-pp-08.misc.iastate.edu:8080/getReceiptId/";
	public static String URL_GET_LATEST_RECEIPTS = "http://proj309-pp-08.misc.iastate.edu:8080/receipt/getReceiptsForMostRecentMonth/";
	public static String URL_POST_SCAN_API = "https://api.taggun.io/api/receipt/v1/verbose/encoded";
	public static String URL_GET_CURRENT_RECEIPTS = "http://proj309-pp-08.misc.iastate.edu:8080/receipt/getReceiptsForMostRecentMonth/";
	//User Object Params
	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String USER_ID = "userId";
	public static final String PHONE_NUMBER = "phoneNumber";
	public static final String EMAIL_ID = "emailId";
	public static final String USERNAME = "username";
	public static final String PREMIUM = "premium";
	public static final String PASSWORD = "password";
	public static final String ADDRESS = "address";

	//Receipt obj params
	public static final String RECEIPT_ID = "receiptId";
	public static final String STORE_NAME = "storeName";
	public static final String RECEIPT_NAME = "receiptName";
	public static final String SUB_TOTAL = "subtotal";
	public static final String TAX = "tax";
	public static final String COMPLETE_TOTAL = "completetotal";
	public static final String DATE = "date";
	public static final String CATEGORY = "category";



}

