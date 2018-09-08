package com.ims.inventory.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.reflect.TypeToken;
import com.ims.inventory.dto.UnitDto;
import com.ims.inventory.entity.UnitEntity;
import com.ims.inventory.repository.UnitRepository;

@Service
public class UnitService {


	@Autowired
	private UnitRepository unitRepository;

	private Logger logger = LoggerFactory.getLogger( UnitService.class.getName() ) ;
	private TypeToken<List<UnitDto>> typeToken =  new TypeToken<List<UnitDto>>() {
		private static final long serialVersionUID = 1L;
	};

	public List<UnitDto> findAll() {
		List<UnitEntity> unitEntityList = (List<UnitEntity> ) unitRepository.findAll();
		List<UnitDto> unitDtoList  = entityToDtoList(unitEntityList);
		return unitDtoList;
	}

	public UnitDto findById(int id) {
		UnitEntity unitEntity = unitRepository.findOne(id);
		if( unitEntity == null ) {
			return new UnitDto();
		}
		UnitDto dto = entityToDto( unitEntity );
		return dto;
	}
	
	public UnitDto save(UnitDto unitDto) {
		UnitEntity unitEntity = dtoToEntity(unitDto);
		unitEntity = unitRepository.save( unitEntity );
		return entityToDto(unitEntity);
	}
	
	private List<UnitDto> entityToDtoList( List<UnitEntity> unitEntityList ) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map( unitEntityList, typeToken.getType() );
	}
	
	private UnitEntity dtoToEntity( UnitDto unitDto ) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map( unitDto, UnitEntity.class );
	}
	
	private UnitDto entityToDto( UnitEntity unitEntity ) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			return modelMapper.map( unitEntity, UnitDto.class );
		} catch( Exception e ) {
			logger.error( e.getMessage(), e );
			throw e;
		}
	}


}