package com.ims.util.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;

public class SmsService {
	private static Logger logger = LoggerFactory.getLogger( SmsService.class.getName() ) ;
	private final static String ACCOUNT_SID = "AC69fb3a4ed5ca461a4b827a7a6267678e"; 
	private final static String AUTH_TOKEN = "e2589ebf7825c9eaf50304e4fa0000a4";
	private final static PhoneNumber phoneNumber = new PhoneNumber("+14125047990");
	static {
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
	}
	
	public static void sendSms( String sendToNumber, String body ) {
		try {
			logger.debug( "Message sending to:" + sendToNumber + "\nmessage Body:" + body );
			MessageCreator messageCreator = Message.creator(new PhoneNumber( sendToNumber ), phoneNumber, body);
			Message message = messageCreator.create();
			logger.debug( "Message sent to:" + sendToNumber + "\nmessage Sid:" + message.getSid() );
		} catch( Exception e ) {
			logger.error(e.getMessage(), e);
		}
	}
}
