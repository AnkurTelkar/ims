package com.ims.inventory.sales.utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.ims.ApplicationConstants;
import com.ims.customer.dto.CustomerDetailDto;
import com.ims.customer.dto.CustomerDto;
import com.ims.customer.service.CustomerService;
import com.ims.inventory.dto.SkuDto;
import com.ims.inventory.sales.dto.BillAmountDetailDto;
import com.ims.inventory.sales.dto.BillDto;
import com.ims.inventory.sales.dto.BillItemDto;
import com.ims.inventory.sales.dto.BillPaymentDto;
import com.ims.inventory.sales.service.BillService;
import com.ims.inventory.service.SkuService;
import com.ims.user.dto.UserDto;
import com.ims.user.service.UserService;
import com.ims.util.service.SmsService;
import com.ims.util.utility.Utility;

@Component("billUtility")
public class BillUtility {

	@Autowired
	private Gson gson;
	
	@Autowired
	private UserService userService;
	
	@Autowired 
	private CustomerService customerService;
	
	public CustomerDto getOrSaveCustomer(Map<String, String> requestMap, CustomerService customerService) {
		
		CustomerDto customerDto = null;
		String customerInfo = "";
		int infoTypeInt = 0;
		if( requestMap != null ) {
			customerInfo = requestMap.get( "customerInfoSubmit" );
			String infoType = requestMap.get( "infoType" );	
			infoTypeInt = Utility.getValidInt( Integer.valueOf( infoType ) );
			if( infoTypeInt == ApplicationConstants.INFO_ID ) {
				customerDto = customerService.findById( Integer.parseInt(customerInfo) );
			} else if( infoTypeInt == ApplicationConstants.INFO_PHONE ) {
				customerDto = customerService.findByPhoneNo(customerInfo);
			} else if( infoTypeInt == ApplicationConstants.INFO_EMAIL ) {
				customerDto = customerService.findByEmail(customerInfo);
			} else {
				customerDto = customerService.findByCustomerName( customerInfo );
			}
		
		}

		if( customerDto == null ) {
			customerDto = new CustomerDto();
			if( StringUtils.isEmpty(customerInfo) ) {
				customerDto.setCustomerName( "Guest" );	
			} else {
				customerDto.setCustomerName( customerInfo );
				customerDto.setDescription( customerInfo );
				CustomerDetailDto customerDetailDto = new CustomerDetailDto();
				customerDetailDto.setContactPerson(customerInfo);
				customerDetailDto.setCustomer(customerDto);
				if( infoTypeInt == ApplicationConstants.INFO_PHONE ) {
					//append country code
					customerDetailDto.setPhoneNo( customerInfo);
					
				} else if( infoTypeInt == ApplicationConstants.INFO_EMAIL ) {
					customerDetailDto.setEmail(customerInfo);
				}
				
				customerDto.setCustomerDetails( new ArrayList<CustomerDetailDto>() );
				customerDto.getCustomerDetails().add(customerDetailDto);
				Date date = new Date();
				customerDto.setCustomerCode( customerService.getNextCode() );
				customerDto.setCreatedAt( date );
				customerDto.setUpdatedAt( date );
				
				customerDto = customerService.save(customerDto);
			}
		}
		return customerDto;
	}
	
	public ModelAndView openBill(BillDto billDto, HttpSession session) {

		ModelAndView mv = new ModelAndView();
		String url = "/inventory/sales/pointOfSale/login";

		if( session.getAttribute( "posUserDto" ) == null ) {
			mv.addObject("message", "Staff Member is not logged in.");
			mv.setViewName( url );
			return mv;
		}

		if( billDto.getBillId() <= 0 ) {
			url = "/inventory/sales/pointOfSale/login";
			mv.addObject("message", "Invalid Bill No.");
			mv.setViewName( "redirect:" + url + ".htm" );
			return mv;
		}

		if( billDto.getCustomer() == null ) {
			billDto.setCustomer( new CustomerDto() );
			billDto.getCustomer().setCustomerName( "Guest" );
		}


		mv.addObject( "billDto", billDto );
		url = "/inventory/sales/pointOfSale/addBill";
		mv.setViewName( url );
		return mv;
	}
	
	public void sendSms( BillDto billDto ) {
		String sendToNumber = "";
		CustomerDto customerDto = billDto.getCustomer();
		if( customerDto != null ) {
			List<CustomerDetailDto> details = customerDto.getCustomerDetails();
			if( !CollectionUtils.isEmpty(details) ) {
				sendToNumber = details.get(0).getPhoneNo();
			}
		}
		
		if( !StringUtils.isEmpty(sendToNumber) ) {
			String body = "Dear Customer: \nYour bill amount for bill no. " + billDto.getBillNo() + " is INR " + billDto.getTotal();
			body += ".\n Thank you. Please visit again.";
			SmsService.sendSms(sendToNumber, body);	
		}
		
	}
	
	public BillDto populateBillDtoBeforeUpdate( String input, SkuService skuService, BillService billService ) {
		BillDto billDtoFromJson = gson.fromJson(input, BillDto.class);
		BillDto billDtoFromDB = billService.findById( billDtoFromJson.getBillId() );//41
		UserDto billUser = billDtoFromDB.getUser();

		if( billUser == null || billDtoFromDB.getUser().getUserId() != billDtoFromJson.getUser().getUserId() ) {
			billDtoFromDB.setUser( userService.findUserByUserId( billDtoFromJson.getUser().getUserId() ) );
		}
		
		//Set Customer
		CustomerDto customerDto = billDtoFromDB.getCustomer();
		if( customerDto != null && customerDto.getCustomerId() != billDtoFromJson.getCustomer().getCustomerId() ) {
			billDtoFromDB.setCustomer( customerService.findById( billDtoFromJson.getCustomer().getCustomerId() ) );
		}
		
		billDtoFromDB.setStatus( billDtoFromJson.getStatus() );
		if( billDtoFromJson.getStatus() == ApplicationConstants.STATUS_FINALIZE ) {
			billDtoFromDB.setBillDate( new Date() );
			
		}
		billDtoFromDB.setUpdatedAt( new Date() );
		
		List<BillItemDto> billItems = billDtoFromJson.getBillItems();
		if( !CollectionUtils.isEmpty( billItems ) ) {
			for( BillItemDto billItem : billItems ) {
				SkuDto skuDto = billItem.getSku();
				if( skuDto == null || skuDto.getSkuId() <= 0 ) {
					continue;
				}
				SkuDto skuDtoDB = skuService.findById( skuDto.getSkuId() );
				billItem.setSku( skuDtoDB );
				billItem.setBill( billDtoFromDB );
				billItem.setCreatedAt( billDtoFromDB.getCreatedAt() );
				billItem.setUpdatedAt( billDtoFromDB.getUpdatedAt() );
			}
		}
		
		List<BillAmountDetailDto> billAmountDetails = billDtoFromJson.getBillAmountDetails();
		if( !CollectionUtils.isEmpty( billAmountDetails ) ) {
			for( BillAmountDetailDto billAmountDetail : billAmountDetails ) {
				billAmountDetail.setBill( billDtoFromDB );
				billAmountDetail.setCreatedAt( billDtoFromDB.getCreatedAt() );
				billAmountDetail.setUpdatedAt( billDtoFromDB.getUpdatedAt() );
			}
		}
		
		List<BillPaymentDto> billPaymentDetails = billDtoFromJson.getBillPayments();
		if( !CollectionUtils.isEmpty( billPaymentDetails ) ) {
			for( BillPaymentDto billPayment : billPaymentDetails ) {
				billPayment.setBill( billDtoFromDB );
				billPayment.setCreatedAt( billDtoFromDB.getCreatedAt() );
				billPayment.setUpdatedAt( billDtoFromDB.getUpdatedAt() );
			}
		}
		billDtoFromDB.setBillItems( billItems );
		billDtoFromDB.setBillAmountDetails( billAmountDetails );
		billDtoFromDB.setBillPayments(billPaymentDetails );
		billDtoFromDB.setTotal( billDtoFromJson.getTotal() );
		return billDtoFromDB;
	}
}


