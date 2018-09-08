package com.ims.inventory.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.reflect.TypeToken;
import com.ims.inventory.dto.CategoryDto;
import com.ims.inventory.entity.CategoryEntity;
import com.ims.inventory.repository.CategoryRepository;

@Service
public class CategoryService {


	@Autowired
	private CategoryRepository categoryRepository;

	private Logger logger = LoggerFactory.getLogger( CategoryService.class.getName() ) ;
	private TypeToken<List<CategoryDto>> typeToken =  new TypeToken<List<CategoryDto>>() {
		private static final long serialVersionUID = 1L;
	};

	public List<CategoryDto> findAll() {
		List<CategoryEntity> categoryEntityList = (List<CategoryEntity> ) categoryRepository.findAll();
		List<CategoryDto> categoryDtoList  = entityToDtoList(categoryEntityList);
		return categoryDtoList;
	}

	public CategoryDto findById(int id) {
		CategoryEntity categoryEntity = categoryRepository.findOne(id);
		if( categoryEntity == null ) {
			return new CategoryDto();
		}
		return entityToDto( categoryEntity );
	}
	
	public String getNextCode() {
		return String.format("%05d", categoryRepository.getNextCode() );
		
	}

	public CategoryDto save(CategoryDto categoryDto) {
		CategoryEntity categoryEntity = dtoToEntity(categoryDto);
		categoryEntity = categoryRepository.save( categoryEntity );
		return entityToDto(categoryEntity);
	}
	
	private List<CategoryDto> entityToDtoList( List<CategoryEntity> categoryEntityList ) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map( categoryEntityList, typeToken.getType() );
	}
	
	private CategoryEntity dtoToEntity( CategoryDto categoryDto ) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map( categoryDto, CategoryEntity.class );
	}
	
	private CategoryDto entityToDto( CategoryEntity categoryEntity ) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			return modelMapper.map( categoryEntity, CategoryDto.class );
		} catch( Exception e ) {
			logger.error( e.getMessage(), e );
			throw e;
		}
	}


}