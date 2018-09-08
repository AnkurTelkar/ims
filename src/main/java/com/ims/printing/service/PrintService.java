package com.ims.printing.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PrintService {
	static Logger logger = LoggerFactory.getLogger( PrintService.class.getName() );
	static StringBuilder sb = new StringBuilder();
	static {
		try {
			InputStream is = PrintService.class.getClassLoader().getResourceAsStream( "/com/ims/printing/file/receipt.txt" ); 
			BufferedReader buf = new BufferedReader(new InputStreamReader(is)); 
			String line = buf.readLine(); 
			sb = new StringBuilder(); 
			while(line != null){
				sb.append(line).append("\n"); 
				line = buf.readLine(); 
			} 
			buf.close();
		} catch( Exception e ) {
			logger.error( e.getMessage(), e );
		}
	}
	
	public boolean printReceipt( String receiptText ) {
		boolean success = false;
		try {
			logger.info( "Print Content:" + receiptText );
			print( receiptText );
			success = true;
		} catch( Exception e ) {
			logger.error( e.getMessage(), e );
		}
		return success;
	}
	
	public String getReceipt() {
		return sb.toString();
	}
	
	private void print( String fileAsString ) throws Exception {
	    
		
		fileAsString += "\f";
		logger.debug(fileAsString);
		System.out.println( fileAsString );
		InputStream is = new ByteArrayInputStream( fileAsString.getBytes("UTF8"));
		DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
	    Doc doc = new SimpleDoc(is,flavor,null);
		
	    //javax.print.PrintService receiptPrinter = findReceiptPrintService();//get chit printer
	    javax.print.PrintService hpOfficejetPrinter = findHpOfficejetPrintService();
	    DocPrintJob job = hpOfficejetPrinter.createPrintJob();//service.createPrintJob();
	    job.print(doc, null);
	    is.close();
	    

	    
		
		/*DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
		byte[] bytes = fileAsString.getBytes();
		javax.print.PrintService receiptPrinter = findReceiptPrintService();//get chit printer
	    DocPrintJob job = receiptPrinter .createPrintJob();//service.createPrintJob();
	    Doc doc = new SimpleDoc(bytes,flavor,null);
	    job.print(doc, null);*/
	}
	
	private javax.print.PrintService findReceiptPrintService() throws Exception {
		return findPrintService( "receipt" );
	}
	
	private javax.print.PrintService findHpOfficejetPrintService() throws Exception {
		return findPrintService( "Brother HL-L2320D series" );
	}
	
	private javax.print.PrintService findPrintService( String printServiceName ) throws Exception {
		DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
	    PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		javax.print.PrintService printServices[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
		 javax.print.PrintService receiptPrintService = null;
		 if( printServices == null || printServices.length <= 0 ) {
			 throw new Exception( "No Printer defined!" );
		 }
		 
		 for( int index = 0; index < printServices.length; index++ ) {
			 javax.print.PrintService printService = printServices[index];
			 if( printService.getName().equalsIgnoreCase( printServiceName ) ) {
				 receiptPrintService = printService;
				 break;
			 }
		 }
		 
		 if( receiptPrintService == null ) {
			 throw new Exception( "No Receipt Printer defined!" );
		 } else {
			 return receiptPrintService;
		 }
	}
	
	
}
