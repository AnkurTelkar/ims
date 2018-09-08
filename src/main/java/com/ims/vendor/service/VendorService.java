package com.ims.vendor.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.common.reflect.TypeToken;
import com.ims.vendor.dto.VendorDTO;
import com.ims.vendor.dto.VendorDetailsDTO;
import com.ims.vendor.entity.Vendor;
import com.ims.vendor.repository.VendorRepository;



@Service
public class VendorService {
	
	@Autowired
	private VendorRepository vendorDao;
	private Logger logger = LoggerFactory.getLogger( VendorService.class.getName() ) ;
	private TypeToken<List<VendorDTO>> typeToken =  new TypeToken<List<VendorDTO>>() {
		private static final long serialVersionUID = 1L;
	};
	
	@Transactional
	public String getVendorCode() {
		String vendorCode = vendorDao.getVendorCode();
		return vendorCode;
	}
	
	@Transactional( rollbackFor=Exception.class )
	public VendorDTO saveOrUpdate( VendorDTO vendorDto ) throws Exception {
		try {
			if( vendorDto.getId() <= 0 ) {
				vendorDto.setCreatedAt( new Date() );
			} else {
				Vendor vendorEntityDB = vendorDao.getVendor( vendorDto.getId() );
				vendorDto.setCreatedAt( vendorEntityDB.getCreatedAt() );
			}
			if( vendorDto.getGstNo() == "" ) {
				vendorDto.setGstNo( null );
			}
			
			vendorDto.setUpdatedAt( new Date() );
			
			Iterator<VendorDetailsDTO> iterator = vendorDto.getVendorDetails().iterator();
			while( iterator.hasNext() ) {
				VendorDetailsDTO vendorDetailsDTO = iterator.next();
				if( vendorDetailsDTO == null || 
						( StringUtils.isEmpty( vendorDetailsDTO.getContactPerson() ) 
								&& StringUtils.isEmpty( vendorDetailsDTO.getEmail() )
								&& StringUtils.isEmpty( vendorDetailsDTO.getPhoneNo() ) ) ) {
					iterator.remove();
				} else {
					vendorDetailsDTO.setVendorDto( vendorDto );
				}
			}
			
			Vendor vendorEntity = dtoToEntity( vendorDto );
	        vendorDao.saveOrUpdate( vendorEntity );
	        return entityToDto( vendorEntity );
		} catch( Exception e ) {
			logger.error( e.getMessage(), e );
			throw e;
		}
	}
	
	@Transactional(readOnly=true)
	public VendorDTO getVendor( int id ) {
		Vendor vendorEntity = vendorDao.getVendor( id );
		VendorDTO vendorDto = entityToDto( vendorEntity );
		return vendorDto;
	}
	
	public List<VendorDTO> getAllVendors() {
		List<Vendor> vendorEntityList = vendorDao.getAllVendors();
		List<VendorDTO> vendorDtoList = entityToDtoList( vendorEntityList );
		return vendorDtoList;
	}
	
	private VendorDTO entityToDto( Vendor vendorEntity ) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			return modelMapper.map( vendorEntity, VendorDTO.class );
		} catch( Exception e ) {
			logger.error( e.getMessage(), e );
			throw e;
		}
	}
	
	private Vendor dtoToEntity( VendorDTO vendorDto ) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map( vendorDto, Vendor.class );
	}
	
	private List<VendorDTO> entityToDtoList( List<Vendor> vendorEntityList ) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map( vendorEntityList, typeToken.getType() );
	}
	
}
