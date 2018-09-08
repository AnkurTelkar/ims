package com.ims.util.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.reflect.TypeToken;
import com.ims.util.dto.RightsDto;
import com.ims.util.entity.RightsEntity;
import com.ims.util.repository.RightsRepository;

@Service
public class RightsService {

	Logger logger = LoggerFactory.getLogger( this.getClass().getName() );
	
	@Autowired
	private RightsRepository rightsRepository;
	
	private TypeToken<List<RightsDto>> typeToken = new TypeToken<List<RightsDto>>() {
		private static final long serialVersionUID = 1L;
	};

	public List<RightsDto> getAllRights() {
		List<RightsEntity> rightsEntities = (List<RightsEntity>) rightsRepository.findAll();
		logger.debug("rightsEntities - - - - " + rightsEntities);

		List<RightsDto> rightsDtos = this.entityToDtoList(rightsEntities);

		return rightsDtos;
	}
	
	
	public List<RightsDto> entityToDtoList(List<RightsEntity> rightsEntities) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(rightsEntities, typeToken.getType());
	}


	public RightsEntity dtoToEntity(RightsDto rightsDto) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(rightsDto, RightsEntity.class);
	}
	
	public RightsDto entityToDto(RightsEntity rightsEntity) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(rightsEntity, RightsDto.class);
	}
	
	public RightsDto findRightById(Integer rightId){
		
		RightsEntity entity = rightsRepository.findOne(rightId);
		
		RightsDto rightsDto = this.entityToDto(entity);
		
		return rightsDto;
	}

}
