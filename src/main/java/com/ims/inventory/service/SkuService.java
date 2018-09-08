package com.ims.inventory.service;

import java.util.Date;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.ims.inventory.InventoryConstants;
import com.ims.inventory.dto.SkuDto;
import com.ims.inventory.entity.SkuEntity;
import com.ims.inventory.repository.SkuRepository;
import com.ims.inventory.transaction.utility.TransactionUtility;
import com.ims.util.utility.Utility;

@Service
public class SkuService {

	@Autowired
	private Gson gson;
	
	@Autowired
	private SkuRepository skuRepository;
	
	@Autowired
	private TransactionUtility transactionUtility;

	private Logger logger = LoggerFactory.getLogger( this.getClass().getName() ) ;
	private TypeToken<List<SkuDto>> typeToken =  new TypeToken<List<SkuDto>>() {
		private static final long serialVersionUID = 1L;
	};
	
	private TypeToken<List<SkuEntity>> typeTokenEntity =  new TypeToken<List<SkuEntity>>() {
		private static final long serialVersionUID = 1L;
	};

	public List<SkuDto> findAll() {
		List<SkuEntity> skuEntityList = (List<SkuEntity> ) skuRepository.findAll();
		List<SkuDto> skuDtoList  = entityToDtoList(skuEntityList);
		return skuDtoList;
	}

	public SkuDto findById(int id) {
		SkuEntity skuEntity = skuRepository.findOne(id);
		if( skuEntity == null ) {
			return new SkuDto();
		}
		return entityToDto( skuEntity );
	}
	
	public String getNextCode( int addedRecords ) {
		int nextCode = skuRepository.getNextCode() + addedRecords;
		return String.format("%05d", nextCode );
		
	}
	
	public List<SkuDto> findByItemId( int itemId ) {
		List<SkuEntity> skuEntityList = skuRepository.findByItemItemId( itemId );
		return entityToDtoList( skuEntityList );
	}

	public boolean save( List<SkuDto> skuDtoList ) {
		try {
			if( CollectionUtils.isEmpty( skuDtoList ) ) {
				return false;
			}
			List<SkuEntity> skuEntityList = dtoToEntityList(skuDtoList);
			skuRepository.save( skuEntityList );
			return true;
		} catch( Exception e ) {
			logger.error( e.getMessage(), e );
			return false;
		}
	}
	
	@Transactional
	public SkuDto save(SkuDto skuDto, int refType) {
		SkuEntity skuEntity = dtoToEntity(skuDto);
		SkuDto skuDtoBeforeUpdate = findById( Utility.getValidInt( skuDto.getSkuId() ) );
		skuEntity = skuRepository.save( skuEntity );
		SkuDto updatedSkuDto = entityToDto(skuEntity);
		
		if( refType == InventoryConstants.INV_TRANSACTION_CREATE_UPDATE_SKU ) {
			transactionUtility.recordSkuTransaction(skuDtoBeforeUpdate, updatedSkuDto, refType);
		}
		
		return updatedSkuDto;
	}
	
	
	public JSONArray findSkuDetails( String input ) {
		List<Object[]> list = skuRepository.findSkuDetails( "%" + input + "%" );
		JSONArray jsonArray = new JSONArray();
		if ( !CollectionUtils.isEmpty(list) ) {
			for (int index = 0; index < list.size(); index++) {
				Object[] record = ( Object[] ) list.get(index);
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("barcode", record[0] );
				jsonObject.put("skuId", record[1] );
				jsonObject.put("cost", record[2] );
				jsonObject.put("gst", record[3] );
				jsonObject.put("retailPrice", record[4] );
				jsonObject.put("sellingPrice", record[5] );
				jsonObject.put("skuDetails", record[6] );
				jsonObject.put("skuShortDetails", record[7] );
				jsonObject.put("priceWithoutGst", record[8] );
				jsonArray.put( jsonObject );
			}
		}
		logger.info( "\nSKU search Results:" + jsonArray );
		return jsonArray;
	}
	
	public JSONObject findSkuDetailsByBarcode( String barcode ) {
		SkuEntity skuEntity = skuRepository.findByBarcode( barcode );
		JSONObject jsonObject = new JSONObject();
		if( skuEntity == null ) {
			return jsonObject;
		}
		jsonObject.put("barcode", skuEntity.getBarcode() );
		jsonObject.put("skuId", skuEntity.getSkuId() );
		jsonObject.put("cost", skuEntity.getCost() );
		jsonObject.put("gst", skuEntity.getGst() );
		jsonObject.put("priceWithoutGst", skuEntity.getSellingPrice() / ( 1 + (skuEntity.getGst()/100) ) );
		jsonObject.put("retailPrice", skuEntity.getRetailPrice() );
		jsonObject.put("sellingPrice", skuEntity.getSellingPrice() );
		String skuDetails = skuEntity.getItem().getItemName() + " - " + skuEntity.getSkuCode() + " - " + skuEntity.getDescription();
		jsonObject.put("skuDetails", skuDetails );
		String skuShortDetails = skuEntity.getSkuCode() + " - " + skuEntity.getDescription();
		jsonObject.put("skuShortDetails", skuShortDetails );
		logger.info( "\nSKU search by barcode Results:" + jsonObject );
		return jsonObject;
	}
	
	private List<SkuDto> entityToDtoList( List<SkuEntity> skuEntityList ) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map( skuEntityList, typeToken.getType() );
	}
	
	private List<SkuEntity> dtoToEntityList( List<SkuDto> skuDtoList ) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map( skuDtoList, typeTokenEntity.getType() );
	}
	
	private SkuEntity dtoToEntity( SkuDto skuDto ) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map( skuDto, SkuEntity.class );
	}
	
	private SkuDto entityToDto( SkuEntity skuEntity ) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			return modelMapper.map( skuEntity, SkuDto.class );
		} catch( Exception e ) {
			logger.error( e.getMessage(), e );
			throw e;
		}
	}
	@Transactional
	public boolean updateSkus( String input, int itemId ) throws Exception {
		boolean toReturn = false;
		try {
			List<SkuDto> skuDtoList = gson.fromJson(input, typeToken.getType() );
			for( SkuDto skuDto : skuDtoList ) {
				SkuDto skuDtoDB = findById( skuDto.getSkuId() );
				if( skuDto.getSkuId() > 0 ) {
					skuDto.setCreatedAt( skuDtoDB.getCreatedAt() );
				} else {
					skuDto.setCreatedAt( new Date() );
				}
				
				skuDto.setUpdatedAt( new Date() );
				skuDto.setItem( skuDtoDB.getItem() );
				save(skuDto, InventoryConstants.INV_TRANSACTION_CREATE_UPDATE_SKU);
			}
			toReturn = true;
		} catch( Exception e ) {
			logger.error( e.getMessage(), e );
			toReturn = false;
			throw e;
		} 
		return toReturn;
	}

}