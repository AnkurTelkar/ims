package com.ims.inventory.transaction.utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ims.ApplicationConstants;
import com.ims.inventory.dto.SkuDto;
import com.ims.inventory.purchasing.dto.ReceivingDto;
import com.ims.inventory.purchasing.dto.ReceivingItemDto;
import com.ims.inventory.sales.dto.BillDto;
import com.ims.inventory.sales.dto.BillItemDto;
import com.ims.inventory.service.SkuService;
import com.ims.inventory.transaction.dto.TransactionDetailDto;
import com.ims.inventory.transaction.service.TransactionService;
import com.ims.util.utility.Utility;

@Component("transactionUtility")
public class TransactionUtility {
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private SkuService skuService;
	
	public void recordSkuTransaction( SkuDto skuDto, SkuDto updatedSkuDto, int refType ) {
		List<TransactionDetailDto> transactionDetailList = new ArrayList<>();
		Date currentDate = new Date();
		if( Utility.getValidInt( skuDto.getSkuId() ) > 0 ) {
			TransactionDetailDto transactionBeforeSave = new TransactionDetailDto(skuDto.getSkuId(), skuDto.getQuantity() * -1, refType, skuDto.getSkuId(), currentDate, currentDate);
			transactionDetailList.add(transactionBeforeSave);
		}
		
		if( Utility.getValidInt( updatedSkuDto.getSkuId()  ) > 0 ) {
			TransactionDetailDto transactionAfterSave = new TransactionDetailDto(updatedSkuDto.getSkuId(), updatedSkuDto.getQuantity(), refType, updatedSkuDto.getSkuId(), currentDate, currentDate);
			transactionDetailList.add(transactionAfterSave);
		}
		
		transactionService.saveTransactions(transactionDetailList);
	}
	
	public void recordReceivingTrancaction( ReceivingDto receivingDto, ReceivingDto updatedReceivingDto, int refType ) {
		List<TransactionDetailDto> transactionDetailList = new ArrayList<>();
		Map<Integer, SkuDto> skuDtoMap = new HashMap<>();
		//Before Update process. Deduct previous sku qty 
		int beforeUpdateFactor = receivingDto.getStatus() == ApplicationConstants.STATUS_RETURN ? 1 : -1;
		processReceivingTransaction(receivingDto, refType, transactionDetailList, skuDtoMap, beforeUpdateFactor);
		//After Update process. Add new sku qty
		int afterUpdateFactor = updatedReceivingDto.getStatus() == ApplicationConstants.STATUS_RETURN ? -1 : 1;
		processReceivingTransaction(updatedReceivingDto, refType, transactionDetailList, skuDtoMap, afterUpdateFactor);
		
		transactionService.saveTransactions(transactionDetailList);
		skuService.save( new ArrayList<>(skuDtoMap.values()) );
	}

	private void processReceivingTransaction(ReceivingDto receivingDto, int refType, List<TransactionDetailDto> transactionDetailList, Map<Integer, SkuDto> skuDtoMap, int factor) {
		if( receivingDto.getReceivingId() > 0 ) {
			Date currentDate = new Date();
			List<ReceivingItemDto> receivingItemList = receivingDto.getReceivingItems();
			if( !CollectionUtils.isEmpty(receivingItemList) ) {
				for( ReceivingItemDto itemDto : receivingItemList ) {
					int skuId = itemDto.getSku().getSkuId();
					SkuDto skuDto = skuDtoMap.get( skuId );
					if( skuDto == null ) {
						skuDtoMap.put( skuId, itemDto.getSku() );
						skuDto = skuDtoMap.get( skuId );
					}
					double actualQty = itemDto.getQuantity() * factor;
					TransactionDetailDto transactionBeforeSave = new TransactionDetailDto(skuDto.getSkuId(), actualQty, refType, receivingDto.getReceivingId(), currentDate, currentDate);
					skuDto.setQuantity( skuDto.getQuantity() + ( actualQty ) );
					transactionDetailList.add(transactionBeforeSave);
					
				}
			}
		}
	}
	
	public void recordSalesTrancaction( BillDto billDto, BillDto updatedBillDto, int refType ) {
		List<TransactionDetailDto> transactionDetailList = new ArrayList<TransactionDetailDto>();
		Map<Integer, SkuDto> skuDtoMap = new HashMap<>();
		//Before Update process. Add previous sku qty
		int beforeUpdateFactor = updatedBillDto.getStatus() == ApplicationConstants.STATUS_REFUND ? -1 : 1;
		processBillTransaction(billDto, refType, transactionDetailList, skuDtoMap, beforeUpdateFactor);
		//After Update process. Deduct new sku qty
		if( updatedBillDto.getStatus() != ApplicationConstants.STATUS_VOID ) {
			int afterUpdateFactor = updatedBillDto.getStatus() == ApplicationConstants.STATUS_REFUND ? 1 : -1;
			processBillTransaction(updatedBillDto, refType, transactionDetailList, skuDtoMap, afterUpdateFactor);	
		}
		transactionService.saveTransactions(transactionDetailList);
		skuService.save( new ArrayList<>(skuDtoMap.values()) );
	}
	
	private void processBillTransaction(BillDto billDto, int refType, List<TransactionDetailDto> transactionDetailList, Map<Integer, SkuDto> skuDtoMap, int factor) {
		Date currentDate = new Date();
		if( billDto.getBillId() > 0 ) {
			List<BillItemDto> billItemList = billDto.getBillItems();
			if( !CollectionUtils.isEmpty(billItemList) ) {
				for( BillItemDto itemDto : billItemList ) {
					int skuId = itemDto.getSku().getSkuId();
					SkuDto skuDto = skuDtoMap.get( skuId );
					if( skuDto == null ) {
						skuDtoMap.put( skuId, itemDto.getSku() );
						skuDto = skuDtoMap.get( skuId );
					}
					double actualQty = itemDto.getQuantity() * factor;
					TransactionDetailDto transactionBeforeSave = new TransactionDetailDto(skuDto.getSkuId(), actualQty, refType, billDto.getBillId(), currentDate, currentDate);
					skuDto.setQuantity( skuDto.getQuantity() + ( actualQty ) );
					transactionDetailList.add(transactionBeforeSave);
				}
			}
		}
	}
}
