package ims;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;

public class TwilioAppTest {
	private final static String ACCOUNT_SID = "AC69fb3a4ed5ca461a4b827a7a6267678e"; 
	private final static String AUTH_TOKEN = "e2589ebf7825c9eaf50304e4fa0000a4";


	// TODO Auto-generated method stub


	public static void main(String[] args) { 
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		
		/*MessageCreator messageCreator = Message.creator( 
	            "AC69fb3a4ed5ca461a4b827a7a6267678e", 
	            new PhoneNumber("+917772863424"), new PhoneNumber("+14125047990")  
	        );*/
		MessageCreator messageCreator = Message.creator(new PhoneNumber("+919479568962"), new PhoneNumber("+14125047990"), "\nHi Arpit. This message is sent to you from Hardeep for testing :)");
		Message message = messageCreator.create();
		/*Message message = Message.creator( 
				"AC69fb3a4ed5ca461a4b827a7a6267678e", 
				
				); */

		//Message message = Message.creator
		/*Message message = Message.creator(new PhoneNumber("+14125047990"),
				new PhoneNumber("+917772863424"),
		        "This is the ship that made the Kessel Run in fourteen parsecs?").create();*/

		    System.out.println(message.getSid()); 
	} 




}
