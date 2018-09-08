package com.ims.inventory.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.reflect.TypeToken;
import com.ims.inventory.dto.BrandDto;
import com.ims.inventory.entity.BrandEntity;
import com.ims.inventory.repository.BrandRepository;

@Service
public class BrandService {


	@Autowired
	private BrandRepository brandRepository;

	private Logger logger = LoggerFactory.getLogger( BrandService.class.getName() ) ;
	private TypeToken<List<BrandDto>> typeToken =  new TypeToken<List<BrandDto>>() {
		private static final long serialVersionUID = 1L;
	};

	public List<BrandDto> findAll() {
		List<BrandEntity> brandEntityList = (List<BrandEntity> ) brandRepository.findAll();
		List<BrandDto> brandDtoList  = entityToDtoList(brandEntityList);
		return brandDtoList;
	}

	public BrandDto findById(int id) {
		BrandEntity brandEntity = brandRepository.findOne(id);
		if( brandEntity == null ) {
			return new BrandDto();
		} 
		return entityToDto( brandEntity );
	}
	
	public String getNextCode() {
		return String.format("%05d", brandRepository.getNextCode() );
		
	}

	public BrandDto save(BrandDto brandDto) {
		BrandEntity brandEntity = dtoToEntity(brandDto);
		brandEntity = brandRepository.save( brandEntity );
		return entityToDto(brandEntity);
	}
	
	private List<BrandDto> entityToDtoList( List<BrandEntity> brandEntityList ) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map( brandEntityList, typeToken.getType() );
	}
	
	private BrandEntity dtoToEntity( BrandDto brandDto ) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map( brandDto, BrandEntity.class );
	}
	
	private BrandDto entityToDto( BrandEntity brandEntity ) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			return modelMapper.map( brandEntity, BrandDto.class );
		} catch( Exception e ) {
			logger.error( e.getMessage(), e );
			throw e;
		}
	}


}