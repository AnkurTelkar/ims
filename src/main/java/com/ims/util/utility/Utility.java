package com.ims.util.utility;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.ims.ApplicationConstants;

@Component("utility")
public class Utility {

	public String getStatusStr( int status ) {
		String statusStr = "";
		switch( status ) {
		case ApplicationConstants.STATUS_DRAFT:
			statusStr = ApplicationConstants.STATUS_DRAFT_STR;
			break;
		case ApplicationConstants.STATUS_VOID:
			statusStr = ApplicationConstants.STATUS_VOID_STR;
			break;
		case ApplicationConstants.STATUS_FINALIZE:
			statusStr = ApplicationConstants.STATUS_FINALIZE_STR;
			break;
		case ApplicationConstants.STATUS_REFUND:
			statusStr = ApplicationConstants.STATUS_REFUND_STR;
			break;
		case ApplicationConstants.STATUS_RETURN:
			statusStr = ApplicationConstants.STATUS_RETURN_STR;
			break;
		}
		return statusStr;
	}

	public static int getValidInt( Integer num ) {
		if( num == null ) {
			return 0;
		}
		return num.intValue();
	}

	public static String getValidString( String value ) {
		if( value == null || value.trim().length() <= 0 ) {
			return "";
		}
		return value;
	}

	public static double roundUp( double value, int precision ) {
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(precision, BigDecimal.ROUND_HALF_EVEN);
		return bd.doubleValue();
	}
}
