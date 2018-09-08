package com.ims.inventory;

public class InventoryConstants {

	public static final int INV_TRANSACTION_CREATE_UPDATE_SKU = 1;
	public static final int INV_TRANSACTION_RECEIVE_SKU = 2;
	public static final int INV_TRANSACTION_SALE_SKU = 3;
	public static final int INV_TRANSACTION_ADJUST_SKU = 4;
	
	public static final int INV_ADJUSTMENT_SALE = 1;
	public static final int INV_ADJUSTMENT_REFUND_SALE = 2;
	public static final int INV_ADJUSTMENT_RECEIVING = 3;
	public static final int INV_ADJUSTMENT_RETURN_RECEIVING = 4;
	public static final int INV_ADJUSTMENT_SPOILAGE = 5;
}
