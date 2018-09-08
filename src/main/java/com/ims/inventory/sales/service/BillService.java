package com.ims.inventory.sales.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.reflect.TypeToken;
import com.ims.ApplicationConstants;
import com.ims.customer.dto.CustomerDto;
import com.ims.customer.service.CustomerService;
import com.ims.inventory.InventoryConstants;
import com.ims.inventory.dto.SkuDto;
import com.ims.inventory.sales.dto.BillDto;
import com.ims.inventory.sales.dto.BillItemDto;
import com.ims.inventory.sales.entity.BillEntity;
import com.ims.inventory.sales.repository.BillRepository;
import com.ims.inventory.transaction.utility.TransactionUtility;
import com.ims.printing.service.PrintService;
import com.ims.util.utility.Utility;

@Service
public class BillService {


	@Autowired
	private BillRepository billRepository;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private TransactionUtility transactionUtility;
	
	@Autowired
	private PrintService printService;
	

	private Logger logger = LoggerFactory.getLogger( BillService.class.getName() ) ;
	private TypeToken<List<BillDto>> typeToken =  new TypeToken<List<BillDto>>() {
		private static final long serialVersionUID = 1L;
	};

	public List<BillEntity> findAll() {
		List<BillEntity> billEntityList = (List<BillEntity> ) billRepository.findAll();
		//List<BillDto> billDtoList  = entityToDtoList(billEntityList);
		return billEntityList;
	}

	public BillDto findById(int id) {
		BillEntity billEntity = billRepository.findOne(id);
		if( billEntity == null ) {
			return new BillDto();
		}
		BillDto dto = entityToDto( billEntity );
		return dto;
	}
	
	public BillDto findByBillNo( String billNo ) {
		BillEntity billEntity = billRepository.findByBillNo(billNo);
		if( billEntity == null ) {
			return new BillDto();
		}
		BillDto dto = entityToDto( billEntity );
		return dto;
	}
	
	public String getNextCode() {
		return String.format("%05d", billRepository.getNextCode() );
		
	}
	
	@Transactional
	public BillDto save(BillDto billDto, int refType) {
		BillEntity billEntity = dtoToEntity(billDto);
		BillDto billDtoBeforeUpdate = findById( billDto.getBillId() );
		billEntity = billRepository.save( billEntity );
		//BillDto updatedBillDto = entityToDto(billEntity);
		billDto.setBillId( billEntity.getBillId() );
		if( refType == InventoryConstants.INV_TRANSACTION_SALE_SKU ) {
			transactionUtility.recordSalesTrancaction(billDtoBeforeUpdate, billDto, refType);
		}
		
		return billDto;
	}
	
	public JSONArray findAllCreatedOrModified( Date start, Date end ) {
		List<Object[]> list = billRepository.findAllCreatedOrModified( start, end );
		JSONArray jsonArray = new JSONArray();
		if ( !CollectionUtils.isEmpty(list) ) {
			for (int index = 0; index < list.size(); index++) {
				Object[] record = ( Object[] ) list.get(index);
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("billId", record[0] );
				jsonObject.put("billNo", record[1] );
				int customerId = 0;
				if( record[2] != null ) {
					customerId = ( Integer ) record[2];					
				}
				CustomerDto customerDto = customerService.findById( customerId );
				jsonObject.put("customerDisplayName", customerDto.getDisplayName() );
				int status = 0;
				if( record[3] != null ) {
					status = ( Integer ) record[3];					
				}
				jsonObject.put("total", record[4] );
				jsonObject.put("statusStr", new Utility().getStatusStr(status) );
				jsonObject.put("status", status );
				jsonArray.put( jsonObject );
			}
		}
		logger.info( "\nBill search Results:" + jsonArray );
		return jsonArray;
	}
	
	public List<BillDto> findByCustomerId( int customerId ) {
		List<BillEntity> billEntityList = billRepository.findByCustomerCustomerId(customerId);
		if( CollectionUtils.isEmpty(billEntityList) ) {
			return new ArrayList<BillDto>();
		}
		return entityToDtoList(billEntityList);
	}
	
	private List<BillDto> entityToDtoList( List<BillEntity> billEntityList ) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map( billEntityList, typeToken.getType() );
	}
	
	private BillEntity dtoToEntity( BillDto billDto ) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map( billDto, BillEntity.class );
	}
	
	private BillDto entityToDto( BillEntity billEntity ) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			return modelMapper.map( billEntity, BillDto.class );
		} catch( Exception e ) {
			logger.error( e.getMessage(), e );
			throw e;
		}
	}

	public JSONObject printBill( int billId ) {
		return printBill( findById( billId ) );
		 
	}

	public JSONObject printBill(BillDto billDto) {
		String receiptText = printService.getReceipt();
		String items = "";
		receiptText = receiptText.replace( "{{billNo}}", billDto.getBillNo() + "" );
		String customerName = "Guest";
		CustomerDto customerDto = billDto.getCustomer();
		if( customerDto != null ) {
			customerName = customerDto.getChitDisplayName();
		}
		receiptText = receiptText.replace( "{{customerInfo}}", customerName );
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		receiptText = receiptText.replace( "{{dateTime}}", dateFormat.format( new Date() ) );
		double totalGST = 0;
		double totalWithoutGST = 0;
		List<BillItemDto> billItemsList = billDto.getBillItems();
		if( !CollectionUtils.isEmpty(billItemsList) ) {
			int index = 1;
			for( BillItemDto dto : billItemsList ) {
				String displayName = dto.getSkuDisplayName();
				if( displayName.length() > 24 ) {
					displayName = displayName.substring(0, 24);
				}
				SkuDto skuDto = dto.getSku();
				double price = dto.getPrice();//this price is without gst
				double itemTotalWithouGst = dto.getQuantity() * price;
				totalWithoutGST += itemTotalWithouGst;
				totalGST += itemTotalWithouGst * skuDto.getGst() / 100;
				items += String.format( "%-3d|%-24s|%-8s|%4.1f|%4.0f|%7.2f|%8.2f", index++, displayName, skuDto.getHsn(), skuDto.getGst(), dto.getQuantity(), price, dto.getQuantity() * price ) + "\n";
			}
		}
		
		double discount = billDto.getAmountDetailByTypeId(ApplicationConstants.CASH_DISCOUNT).getAmount();
		double discountP = Math.round( Math.abs( discount * 100.0 / totalWithoutGST ) );
		receiptText = receiptText.replace( "{{items}}", items );
		receiptText = receiptText.replace( "{{subTotal}}", String.format( "%58.2f", totalWithoutGST ) );
		receiptText = receiptText.replace( "{{discountP}}", String.format("%-14s", "(" + discountP + "%):" ) );
		receiptText = receiptText.replace( "{{discount}}", String.format( "%41.2f", discount ) );
		receiptText = receiptText.replace( "{{cgst}}", String.format( "%59.2f", totalGST/2 ) );
		receiptText = receiptText.replace( "{{sgst}}", String.format( "%59.2f", totalGST/2 ) );
		receiptText = receiptText.replace( "{{grandTotal}}", String.format( "%52.2f", billDto.getTotal() ) );
		receiptText = receiptText.replace("\n", System.getProperty("line.separator"));
		boolean success = printService.printReceipt( receiptText );
		JSONObject jsonObject = new JSONObject();
		if(success) {
			jsonObject.put( "message", "Bill Printed Successfully");
		} else {
			jsonObject.put( "message", "Error: Bill Print Failed!");	
		}
		return jsonObject;
	}
	
	public List<Object[]> getItemSaleDetails( Date start, Date end) {
		List<Object[]> list = billRepository.findBillItemSaleDetails(start, end);
		return list;
	}
	public List<Object[]> getTaxReport( Date start, Date end) {
		java.sql.Date startSql = new java.sql.Date(start.getTime());
		java.sql.Date endSql = new java.sql.Date(end.getTime());
		List<Object[]> list = billRepository.findTaxReportData(startSql.toString(), endSql.toString());
		return list;
	}
	
	public List<Object> getTaxReportSummary( Date start, Date end) {
		java.sql.Date startSql = new java.sql.Date(start.getTime());
		java.sql.Date endSql = new java.sql.Date(end.getTime());
		List<Object> list = billRepository.findTaxReportSummaryData(startSql.toString(), endSql.toString());
		return list;
	}
	
	
}