package com.ims.reports;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ims.ApplicationConstants;
import com.ims.customer.entity.CustomerEntity;
import com.ims.customer.service.CustomerService;
import com.ims.inventory.sales.entity.BillEntity;
import com.ims.inventory.sales.entity.BillPaymentEntity;
import com.ims.inventory.sales.service.BillService;
import com.ims.reports.dto.SaleSummaryDto;
import com.ims.util.utility.Utility;

@Service
public class SaleSummaryService {

	@Autowired
	BillService billService;
	
	@Autowired
	CustomerService customerService;
	
	public Map<Integer, SaleSummaryDto> getSalesSummary() {
		List<BillEntity> billEntities = billService.findAll();
		Map<Integer, SaleSummaryDto> salesSummaryMap = new TreeMap<Integer, SaleSummaryDto>();
		if ( !CollectionUtils.isEmpty( billEntities ) ) {
			for( BillEntity billEntity : billEntities ) {
				if( billEntity.getStatus() == ApplicationConstants.STATUS_VOID ) {
					continue;
				}
				CustomerEntity customerEntity = billEntity.getCustomer();
				if( customerEntity == null ) {
					customerEntity = new CustomerEntity();
				}
				SaleSummaryDto saleSummaryDto = salesSummaryMap.get( customerEntity.getCustomerId() );
				if( saleSummaryDto == null ) {
					saleSummaryDto = new SaleSummaryDto();
					salesSummaryMap.put(customerEntity.getCustomerId(), saleSummaryDto);
				}
				List<BillPaymentEntity> billPayments = billEntity.getBillPayments();
				if ( !CollectionUtils.isEmpty( billPayments ) ) {
					for( BillPaymentEntity billPaymentEntity : billPayments ) {
						double amount =billEntity.getStatus() == ApplicationConstants.STATUS_REFUND ? -billPaymentEntity.getAmount() : billPaymentEntity.getAmount();
						if( billPaymentEntity.getType() == ApplicationConstants.PAYMENT_TYPE_CASH ) {
							saleSummaryDto.setPaidViaCash( Utility.roundUp( saleSummaryDto.getPaidViaCash() + amount , 2) );
						} else if( billPaymentEntity.getType() == ApplicationConstants.PAYMENT_TYPE_CHECK ) {
							saleSummaryDto.setPaidViaCheck( Utility.roundUp( saleSummaryDto.getPaidViaCheck() + amount, 2 ) );
						} else if( billPaymentEntity.getType() == ApplicationConstants.PAYMENT_TYPE_ACCOUNT ) {
							saleSummaryDto.setOnAccount( Utility.roundUp( saleSummaryDto.getOnAccount() + amount, 2 ) );
						} else {
							saleSummaryDto.setPaidOther( Utility.roundUp( saleSummaryDto.getPaidOther() + amount, 2 ) );
						}
					}
				}
				saleSummaryDto.setCustomer( customerService.entityToDto(customerEntity) );
				saleSummaryDto.setTotalBills( saleSummaryDto.getTotalBills() + 1 );
				double total = billEntity.getStatus() == ApplicationConstants.STATUS_REFUND ? -billEntity.getTotal() : billEntity.getTotal();
				saleSummaryDto.setTotalPurchaseAmount( Utility.roundUp( saleSummaryDto.getTotalPurchaseAmount() + total, 2 ) );
			}
		}
		return salesSummaryMap;
	}
	
	public List<Object[]> getItemSaleDetails( Date start, Date end ) {
		return billService.getItemSaleDetails( start, end );
	}
	
	public List<Object[]> getTaxReport( Date start, Date end ) {
		return billService.getTaxReport( start, end );
	}
	
	public List<Object> getTaxReportSummary( Date start, Date end ) {
		return billService.getTaxReportSummary( start, end );
	}
	
}
