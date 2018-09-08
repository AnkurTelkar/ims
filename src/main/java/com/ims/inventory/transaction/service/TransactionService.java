package com.ims.inventory.transaction.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.collections.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.reflect.TypeToken;
import com.ims.inventory.transaction.dto.TransactionDetailDto;
import com.ims.inventory.transaction.entity.TransactionDetailEntity;
import com.ims.inventory.transaction.repository.TransactionRepository;

@Service
public class TransactionService {
	/*private TypeToken<List<TransactionDetailDto>> typeToken =  new TypeToken<List<TransactionDetailDto>>() {
		private static final long serialVersionUID = 1L;
	};
*/
	private TypeToken<List<TransactionDetailEntity>> typeTokenEntity =  new TypeToken<List<TransactionDetailEntity>>() {
		private static final long serialVersionUID = 1L;
	};

	Logger logger = LoggerFactory.getLogger( this.getClass().getName() );
	@Autowired
	private TransactionRepository transactionRepository;

	/*private TypeToken<List<TransactionDetailEntity>> typeToken = new TypeToken<List<TransactionDetailEntity>>() {
		private static final long serialVersionUID = 1L;
	};*/

	public boolean saveTransaction( TransactionDetailDto transactionDetailDto ) {
		try {
			TransactionDetailEntity transactionDetailEntity = dtoToEntity(transactionDetailDto);
			transactionRepository.save( transactionDetailEntity );
			return true;
		} catch( Exception e ) {
			logger.error( e.getMessage() , e);
			return false;
		}

	}

	public boolean saveTransactions( List<TransactionDetailDto> transactionDetailList ) {
		try {
			if( CollectionUtils.isEmpty( transactionDetailList ) ) {
				return false;
			}
			List<TransactionDetailEntity> transactionDetailEntities = dtoToEntityList(transactionDetailList);
			transactionRepository.save( transactionDetailEntities );
			return true;
		} catch( Exception e ) {
			logger.error( e.getMessage() , e);
			return false;
		}
	}

	public TransactionDetailEntity dtoToEntity(TransactionDetailDto transactionDetailDto) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(transactionDetailDto, TransactionDetailEntity.class);
	}

	public TransactionDetailDto entityToDto(TransactionDetailEntity transactionDetailEntity) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(transactionDetailEntity, TransactionDetailDto.class);
	}

	private List<TransactionDetailEntity> dtoToEntityList( List<TransactionDetailDto> transactionDetailDtos ) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map( transactionDetailDtos, typeTokenEntity.getType() );
	}

}