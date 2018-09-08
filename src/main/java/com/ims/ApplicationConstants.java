package com.ims;

public class ApplicationConstants {
	
	public static final Integer ACTIVE = 1;
	public static final Integer INACTIVE = 0;

	public static final int TOTAL = 1;
	public static final int CASH_DISCOUNT = 2;
	public static final int DISPLAY_DISCOUNT = 3;
	public static final int CGST = 4;
	public static final int SGST = 5;
	
	public static final int STATUS_VOID = 0;
	public static final int STATUS_DRAFT = 1;
	public static final int STATUS_FINALIZE = 2;
	public static final int STATUS_REFUND = 3;
	public static final int STATUS_RETURN = 4;
	
	public static final String STATUS_VOID_STR = "Void";
	public static final String STATUS_DRAFT_STR = "Draft";
	public static final String STATUS_FINALIZE_STR = "Finalize";
	public static final String STATUS_REFUND_STR = "Credit Note";
	public static final String STATUS_RETURN_STR = "Debit Note";
	
	public static final int INFO_ID = 0;
	public static final int INFO_PHONE = 1;
	public static final int INFO_EMAIL = 2;
	public static final int INFO_CONTACT_PERSON = 3;
	
	public static final int PAYMENT_TYPE_CASH = 1;
	public static final int PAYMENT_TYPE_CHECK = 2;
	public static final int PAYMENT_TYPE_EWALLET = 3;
	public static final int PAYMENT_TYPE_ACCOUNT = 4;
	public static final int PAYMENT_TYPE_DEBIT_CARD = 5;
	public static final int PAYMENT_TYPE_CREDIT_CARD = 6;

}
