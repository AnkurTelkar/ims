package com.ims.inventory.sales.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ims.inventory.InventoryConstants;
import com.ims.inventory.sales.dto.BillDto;
import com.ims.inventory.sales.service.BillService;
import com.ims.inventory.sales.utility.BillUtility;
import com.ims.inventory.service.SkuService;

@Controller
@SessionAttributes("posUserDto")
@Scope("session")
@RequestMapping(value = "/inventory/sales/pointOfSale")
public class BillController {


	org.slf4j.Logger logger = LoggerFactory.getLogger( BillController.class.getName() );
	
	@Autowired
	private BillService billService;
	
	@Autowired
	private SkuService skuService;
	
	@Autowired
	private BillUtility billUtility;
	
	@ResponseBody
	@RequestMapping(value = "/getItemSkuDetailsByDesc.htm", method = RequestMethod.GET)
	public String getItemSkuDetailsByDesc(@RequestParam("input") String input, HttpServletRequest request,
			HttpServletResponse response) {
		JSONArray jsonArray = skuService.findSkuDetails(input);
		return jsonArray.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/getItemSkuDetailsByBarcode.htm", method = RequestMethod.GET)
	public String getItemSkuDetailsByBarcode(@RequestParam("input") String input, HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jsonObject = skuService.findSkuDetailsByBarcode(input);
		return jsonObject.toString();
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateBill.htm", method = RequestMethod.GET)
	public String updateBill(@RequestParam("input") String input, HttpServletRequest request,
			HttpServletResponse response) {
		BillDto billDto = billUtility.populateBillDtoBeforeUpdate( input, skuService, billService );
		JSONObject jsonObject = new JSONObject();
		String message = "";
		String printerMessage = "";
		try {
			BillDto billDtoFromDB = billService.save( billDto, InventoryConstants.INV_TRANSACTION_SALE_SKU );
			/*if( billDtoFromDB.getStatus() == ApplicationConstants.STATUS_FINALIZE ) {
				JSONObject printerResponseJson = billService.printBill(billDtoFromDB);
				printerMessage = printerResponseJson.getString( "message" );
				billUtility.sendSms( billDtoFromDB );
			}*/
			message = "Bill " + billDtoFromDB.getStatusStr() + " successfully!";
			message += "\n" + printerMessage;
			jsonObject.put( "message", message );
			jsonObject.put( "status", billDtoFromDB.getStatus());
			
		} catch( Exception e ) {
			logger.error(e.getMessage(),e);
			jsonObject.put( "message", "Unable to update bill due to: " + e.getMessage());
		}
		
		return jsonObject.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/printBill.htm", method = RequestMethod.GET)
	public String printBill(@RequestParam("id") int billId, HttpServletRequest request,
			HttpServletResponse response) {
		return billService.printBill( billId ).toString();
	}
}
