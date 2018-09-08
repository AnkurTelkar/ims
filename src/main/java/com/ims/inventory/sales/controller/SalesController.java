package com.ims.inventory.sales.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.ims.ApplicationConstants;
import com.ims.customer.dto.CustomerDto;
import com.ims.customer.service.CustomerService;
import com.ims.inventory.InventoryConstants;
import com.ims.inventory.sales.dto.BillDto;
import com.ims.inventory.sales.dto.BillPaymentDto;
import com.ims.inventory.sales.service.BillService;
import com.ims.inventory.sales.utility.BillUtility;
import com.ims.reports.SaleSummaryService;
import com.ims.reports.dto.SaleSummaryDto;
import com.ims.user.dto.UserDto;
import com.ims.user.service.UserService;
import com.ims.util.utility.Utility;

@Controller
@SessionAttributes("posUserDto")
@RequestMapping(value = "/inventory/sales/pointOfSale")
public class SalesController {


	Logger logger = LoggerFactory.getLogger("Point Of sale login");

	@Autowired
	private BillService billService;

	@Autowired
	private UserService userService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private BillUtility billUtility;

	@Autowired
	private SaleSummaryService saleSummaryService;


	@RequestMapping(value = "/login.htm", method = RequestMethod.GET)
	public ModelAndView loginPointOfSale( @RequestParam  Map<String, String> requestMap, HttpSession session ) {
		ModelAndView mv = new ModelAndView();
		String message = "";
		UserDto posUserDto = null;
		Date loggedInTime = null;
		String url = "/inventory/sales/pointOfSale/login";
		try {
			String posUserCode = requestMap.get( "field0" );
			posUserDto = ( UserDto ) session.getAttribute( "posUserDto" );
			loggedInTime = ( Date ) session.getAttribute( "loggedInTime" );
			message = Utility.getValidString( requestMap.get( "message" ) );
			if( posUserDto != null ) {
				url = "/inventory/sales/pointOfSale/postLogin";
			} else if( posUserCode  != null ) {
				posUserDto = userService.findByUserCode( posUserCode );
				loggedInTime = new Date();
				if( posUserDto == null ) {
					message = "Invalid Login Id: " + posUserCode;
				} else {
					session.setAttribute( "posUserDto" , posUserDto);
					session.setAttribute( "loggedInTime", loggedInTime);
					url = "/inventory/sales/pointOfSale/postLogin";
				}
			}
		} catch( Exception e ) {
			logger.error( e.getMessage(), e );
			message = "Invalide login id. Please provide valid number. Login failed due to: " + e.getMessage();
		}

		if( posUserDto != null ) {
			mv.addObject( "posUserDto" , posUserDto );
			mv.addObject( "loggedInTime" , loggedInTime );
			/*JSONArray billList = billService.findAllCreatedOrModified( new Date(), new Date() );
			mv.addObject( "billList", billList );*/
		}

		mv.addObject( "message" , message );
		mv.setViewName( url );
		return mv;
	}

	@RequestMapping(value = "/addBill.htm", method = {RequestMethod.GET, RequestMethod.POST})
	public String createBill( @RequestParam  Map<String, String> requestMap, HttpSession session) {
		CustomerDto customerDto = billUtility.getOrSaveCustomer( requestMap, customerService );
		UserDto posUserDto = ( UserDto ) session.getAttribute( "posUserDto" );
		BillDto billDto = new BillDto();
		Date date = new Date();
		billDto.setBillNo( billService.getNextCode() );
		billDto.setStatus( ApplicationConstants.STATUS_DRAFT );
		billDto.setCreatedAt( date );
		billDto.setUpdatedAt( date );
		if( customerDto.getCustomerId() > 0 ) {
			billDto.setCustomer( customerDto );	
		}
		billDto.setUser( posUserDto );
		billDto.setDescription( "" );
		billDto = billService.save( billDto, InventoryConstants.INV_TRANSACTION_SALE_SKU );
		session.setAttribute( "billDto", billDto );
		return "redirect:/inventory/sales/pointOfSale/" + billDto.getBillId() +"/openBill.htm";
	}

	@RequestMapping(value = "/openBill.htm", method = {RequestMethod.GET, RequestMethod.POST})
	public String openBill( @RequestParam  Map<String, String> requestMap, HttpSession session) {
		String billNo = requestMap.get( "billNo" );
		BillDto billDto = billService.findByBillNo(billNo);
		session.setAttribute( "billDto", billDto );
		return "redirect:/inventory/sales/pointOfSale/" + billDto.getBillId() +"/openBill.htm";
	}

	@RequestMapping(value = "/{id}/openBill.htm", method = RequestMethod.GET)
	public ModelAndView openBill( @PathVariable(value="id") int id, HttpSession session ) {
		BillDto billDto = ( BillDto ) session.getAttribute( "billDto" );
		session.removeAttribute( "billDto" );
		if( billDto == null ) {
			billDto = billService.findById( id );
		}
		return billUtility.openBill(billDto, session);
	}
	
	@RequestMapping(value = "/{id}/printBill.htm", method = RequestMethod.GET)
	public ModelAndView printBill( @PathVariable(value="id") int id, HttpSession session ) {
		BillDto billDto = ( BillDto ) session.getAttribute( "billDto" );
		session.removeAttribute( "billDto" );
		if( billDto == null ) {
			billDto = billService.findById( id );
		}
		ModelAndView mv = billUtility.openBill(billDto, session);
		mv.setViewName( "/inventory/sales/pointOfSale/printBill" );
		return mv;
	}

	@RequestMapping(value = "/logout.htm", method = RequestMethod.GET)
	public ModelAndView logoutPointOfSale(SessionStatus status ) {
		ModelAndView mv = new ModelAndView();
		String url = "/inventory/sales/pointOfSale/login";
		status.setComplete();
		String message = "Staff Member Logged out successfully!";
		mv.addObject( "message" , message );
		mv.setViewName( url );
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/findBills.htm", method = RequestMethod.GET)
	public String findBills(@RequestParam("start") Date start, @RequestParam("end") Date end, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.setAttribute( "findBillsStartDate", start );
		session.setAttribute( "findBillsEndDate", end ); 
		
		JSONArray billList = billService.findAllCreatedOrModified( start, end );
		return billList.toString();
	}
	
	@ResponseBody
	@RequestMapping(value = "/findCustomerInfo.htm", method = RequestMethod.GET)
	public String findCustomerInfo(@RequestParam("input") String input, HttpServletRequest request, HttpServletResponse response) {
		JSONArray customerDetails = customerService.findCustomerInfo(input);
		return customerDetails.toString();
	}

	@RequestMapping(value = "/saleSummary.htm", method = RequestMethod.GET)
	public ModelAndView saleSummary() {
		ModelAndView mv = new ModelAndView();
		String url = "/inventory/sales/saleSummary";
		Map<Integer, SaleSummaryDto> saleSummaryMap = saleSummaryService.getSalesSummary();
		mv.addObject( "saleSummaryDtoList", saleSummaryMap.values() );
		mv.setViewName( url );
		return mv;

	}
	
	@RequestMapping(value = "/itemSaleDetails.htm", method = {RequestMethod.GET})
	public ModelAndView itemSaleDetails( @RequestParam("start") Date start, @RequestParam("end") Date end, HttpServletRequest request ) {
		ModelAndView mv = new ModelAndView();
		String url = "/inventory/sales/itemSaleDetails";
		List<Object[]> list = saleSummaryService.getItemSaleDetails( start, end );
		mv.addObject( "itemSaleDetails", list );
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		mv.addObject("dateCriteria", dateFormat.format(start) + " - " + dateFormat.format(end) );
		mv.setViewName( url );
		return mv;

	}
	
	@RequestMapping(value = "/taxReport.htm", method = {RequestMethod.GET})
	public ModelAndView taxReport( @RequestParam("start") Date start, @RequestParam("end") Date end, HttpServletRequest request ) {
		String url = "/inventory/sales/taxReport";
		ModelAndView mv = taxReportMV(start, end, url);
		return mv;
	}

	@RequestMapping(value = "/printTaxReport.htm", method = RequestMethod.GET)
	public ModelAndView printTaxReport( @RequestParam("start") Date start, @RequestParam("end") Date end, HttpServletRequest request ) {
		String url = "/inventory/sales/printTaxReport";
		ModelAndView mv = taxReportMV(start, end, url);
		return mv;
	}

	@RequestMapping(value = "/{customerId}/listInvoices.htm", method = RequestMethod.GET)
	public ModelAndView listInvoices( @PathVariable(value="customerId") int customerId ) {
		ModelAndView mv = new ModelAndView();
		mv.addObject( "invoiceList", billService.findByCustomerId(customerId) );
		mv.addObject( "customerDisplayName", customerService.findById(customerId).getDisplayName() );
		String url = "/inventory/sales/listInvoices";
		mv.setViewName( url );
		return mv;
	}
	
	private ModelAndView taxReportMV(Date start, Date end, String url) {
		ModelAndView mv = new ModelAndView();
		List<Object[]> list = saleSummaryService.getTaxReport( start, end );
		List<Object> summary = saleSummaryService.getTaxReportSummary(start, end);
		mv.addObject( "taxReportDetails", list );
		mv.addObject( "taxReportSummary", summary );
		mv.addObject("startDate", start);
		mv.addObject("endDate", end);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		mv.addObject("dateCriteria", dateFormat.format(start) + " - " + dateFormat.format(end) );
		mv.setViewName( url );
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/finalizeBill.htm", method = RequestMethod.GET)
	public String updateBill(@RequestParam("paymentType") int paymentType, @RequestParam("billId") int billId, HttpServletRequest request, HttpServletResponse response) {
		String message = "Bill Finalized Successfully";
		try {
			BillPaymentDto finalizePaymentDto = null;
			BillDto billDto = billService.findById( billId );
			List<BillPaymentDto> billPayments = billDto.getBillPayments();
			if( billDto != null && !CollectionUtils.isEmpty(billPayments) ) {
				for( BillPaymentDto paymentDto : billPayments ) {
					if( paymentDto.getType() == paymentType ) {
						finalizePaymentDto = paymentDto;
					}
					if( paymentDto.getType() == ApplicationConstants.PAYMENT_TYPE_ACCOUNT ) {
						paymentDto.setAmount( 0 );
					}
				}
			}
			
			double balance = billDto.getTotal() - billDto.getPaidAmount();
			if( finalizePaymentDto == null ) {
				finalizePaymentDto = new BillPaymentDto();
				finalizePaymentDto.setBill(billDto);
				finalizePaymentDto.setCreatedAt( new Date() );
				finalizePaymentDto.setUpdatedAt( new Date() );
				finalizePaymentDto.setType(paymentType);
				billPayments.add(finalizePaymentDto);
			}
			finalizePaymentDto.setAmount( finalizePaymentDto.getAmount() + balance );
			billDto.setStatus( ApplicationConstants.STATUS_FINALIZE );
			billDto.setUpdatedAt( new Date() );
			billDto = billService.save(billDto, 0);
		} catch( Exception e ) {
			logger.error( e.getMessage(), e );
			message = e.getMessage();
		}
		JSONObject json = new JSONObject();
		json.put("message", message);
		return json.toString();
	}
}
