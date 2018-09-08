package com.ims.util.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ims.inventory.entity.SkuEntity;
import com.ims.inventory.purchasing.repository.ReceivingRepository;
import com.ims.inventory.repository.SkuRepository;
import com.ims.inventory.sales.repository.BillRepository;
import com.ims.inventory.transaction.repository.TransactionRepository;
import com.ims.util.dto.DashboardDto;

@Service
public class DashboardService {
	
	@Autowired
	private BillRepository billRepository;
	@Autowired
	private ReceivingRepository receivingRepository;
	@Autowired
	private SkuRepository skuRepository;
	@Autowired
	private TransactionRepository transactionRepository;
	
	public DashboardDto getDashboardData() {
		DashboardDto dashboardDto = new DashboardDto();
		Date start = new Date();
		Date end = new Date();
		int billCountToday = getBillCount(start, end);
		int outOfStock = getSkuCountOutOfStock();
		int receivingCountToday = getReceivingCount(start, end);
		
		dashboardDto.setSalesToday( billCountToday );
		dashboardDto.setPurchasesToday( receivingCountToday );
		dashboardDto.setOutOfStock( outOfStock );
		
		List<Object[]> topSkusList = transactionRepository.getTopSkus( new PageRequest(0, 5, Direction.DESC, "counts") );
		if( !CollectionUtils.isEmpty(topSkusList) ) {
			for( Object[] topSku : topSkusList ) {
				int skuId = Integer.parseInt( String.valueOf( topSku[0] ) );
				topSku[0] = skuRepository.findOne( skuId ).getDescription();
			}
		}
		dashboardDto.setTopSkus(topSkusList);
		
		return dashboardDto;
	}

	private int getSkuCountOutOfStock() {
		int outOfStock = 0;
		List<SkuEntity> skuList = skuRepository.findAllQuantityLessThanThreshold();
		if( !CollectionUtils.isEmpty(skuList) ) {
			outOfStock = skuList.size();
		}
		return outOfStock;
	}

	private int getReceivingCount(Date start, Date end) {
		List<Object[]> receivings = receivingRepository.findAllCreatedOrModified(start, end);
		int receivingCountToday = 0;
		if( !CollectionUtils.isEmpty(receivings) ) {
			receivingCountToday = receivings.size();
		}
		return receivingCountToday;
	}

	private int getBillCount(Date start, Date end) {
		List<Object[]> bills = billRepository.findAllCreatedOrModified(start, end);
		int billCountToday = 0;
		if( !CollectionUtils.isEmpty(bills) ) {
			billCountToday = bills.size();
		}
		return billCountToday;
	}
	
}