package com.ims.inventory.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.reflect.TypeToken;
import com.ims.inventory.dto.ItemDto;
import com.ims.inventory.entity.ItemEntity;
import com.ims.inventory.repository.ItemRepository;

@Service
public class ItemService {


	@Autowired
	private ItemRepository itemRepository;
	
	private Logger logger = LoggerFactory.getLogger( ItemService.class.getName() ) ;
	private TypeToken<List<ItemDto>> typeToken =  new TypeToken<List<ItemDto>>() {
		private static final long serialVersionUID = 1L;
	};

	public List<ItemDto> findAll() {
		List<ItemEntity> itemEntityList = (List<ItemEntity> ) itemRepository.findAll();
		List<ItemDto> itemDtoList  = entityToDtoList(itemEntityList);
		return itemDtoList;
	}

	public ItemDto findById(int id) {
		ItemEntity itemEntity = itemRepository.findOne(id);
		return entityToDto( itemEntity );
	}
	
	public String getNextCode() {
		return String.format("%05d", itemRepository.getNextCode() );
		
	}

	public ItemDto save(ItemDto itemDto) {
		ItemEntity itemEntity = dtoToEntity(itemDto);
		itemEntity = itemRepository.save( itemEntity );
		return entityToDto(itemEntity);
	}
	
	private List<ItemDto> entityToDtoList( List<ItemEntity> itemEntityList ) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map( itemEntityList, typeToken.getType() );
	}
	
	private ItemEntity dtoToEntity( ItemDto itemDto ) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map( itemDto, ItemEntity.class );
	}
	
	private ItemDto entityToDto( ItemEntity itemEntity ) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			return modelMapper.map( itemEntity, ItemDto.class );
		} catch( Exception e ) {
			logger.error( e.getMessage(), e );
			throw e;
		}
	}


}