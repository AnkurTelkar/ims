package com.ims.util.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.reflect.TypeToken;
import com.ims.util.dto.MeasuringUnitDto;
import com.ims.util.entity.MeasuringUnitEntity;
import com.ims.util.repository.MeasuringUnitRepository;

@Service
public class MeasuringUnitService {

	@Autowired
	private MeasuringUnitRepository measuringUnitRepository;

	private Logger logger = LoggerFactory.getLogger( MeasuringUnitService.class.getName() ) ;
	private TypeToken<List<MeasuringUnitEntity>> typeToken =  new TypeToken<List<MeasuringUnitEntity>>() {
		private static final long serialVersionUID = 1L;
	};

	public List<MeasuringUnitDto> findAll() {
		List<MeasuringUnitEntity> list = (List<MeasuringUnitEntity> ) measuringUnitRepository.findAll();
		List<MeasuringUnitDto> dtoList  = entityToDtoList(list);
		return dtoList;
	}

	public MeasuringUnitDto findById(int id) {
		MeasuringUnitEntity entity = measuringUnitRepository.findOne(id);
		if( entity == null) {
			return new MeasuringUnitDto();
		}
		return entityToDto( entity );
	}
	
	private List<MeasuringUnitDto> entityToDtoList( List<MeasuringUnitEntity> entityList ) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map( entityList, typeToken.getType() );
	}
	
	
	private MeasuringUnitDto entityToDto( MeasuringUnitEntity entity ) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			return modelMapper.map( entity, MeasuringUnitDto.class );
		} catch( Exception e ) {
			logger.error( e.getMessage(), e );
			throw e;
		}
	}


}