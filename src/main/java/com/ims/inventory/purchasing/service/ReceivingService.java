package com.ims.inventory.purchasing.service;

import java.util.List;


import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.reflect.TypeToken;
import com.ims.inventory.InventoryConstants;
import com.ims.inventory.purchasing.dto.ReceivingDto;
import com.ims.inventory.purchasing.entity.ReceivingEntity;
import com.ims.inventory.purchasing.repository.ReceivingRepository;
import com.ims.inventory.transaction.utility.TransactionUtility;

@Service
public class ReceivingService {


	@Autowired
	private ReceivingRepository receivingRepository;
	
	@Autowired
	private TransactionUtility transactionUtility;
	
	private Logger logger = LoggerFactory.getLogger( ReceivingService.class.getName() ) ;
	private TypeToken<List<ReceivingDto>> typeToken =  new TypeToken<List<ReceivingDto>>() {
		private static final long serialVersionUID = 1L;
	};

	public List<ReceivingDto> findAll() {
		List<ReceivingEntity> receivingEntityList = (List<ReceivingEntity> ) receivingRepository.findAll();
		List<ReceivingDto> receivingDtoList  = entityToDtoList(receivingEntityList);
		return receivingDtoList;
	}

	public ReceivingDto findById(int id) {
		ReceivingEntity receivingEntity = receivingRepository.findOne(id);
		if( receivingEntity == null ) {
			return new ReceivingDto();
		}
		ReceivingDto dto = entityToDto( receivingEntity );
		return dto;
	}
	
	public String getNextCode() {
		return String.format("%05d", receivingRepository.getNextCode() );
		
	}
	
	@Transactional
	public ReceivingDto save(ReceivingDto receivingDto, int refType) {
		ReceivingEntity receivingEntity = dtoToEntity(receivingDto);
		ReceivingDto receivingDtoBeforeUpdate = findById( receivingDto.getReceivingId() );
		receivingEntity = receivingRepository.save( receivingEntity );
		ReceivingDto updatedReceivingDto = entityToDto(receivingEntity);
		
		if( refType == InventoryConstants.INV_TRANSACTION_RECEIVE_SKU ) {
			transactionUtility.recordReceivingTrancaction(receivingDtoBeforeUpdate, updatedReceivingDto, refType);
		}
		
		return updatedReceivingDto;
	}
	
	private List<ReceivingDto> entityToDtoList( List<ReceivingEntity> receivingEntityList ) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map( receivingEntityList, typeToken.getType() );
	}
	
	private ReceivingEntity dtoToEntity( ReceivingDto receivingDto ) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map( receivingDto, ReceivingEntity.class );
	}
	
	private ReceivingDto entityToDto( ReceivingEntity receivingEntity ) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			return modelMapper.map( receivingEntity, ReceivingDto.class );
		} catch( Exception e ) {
			logger.error( e.getMessage(), e );
			throw e;
		}
	}


}