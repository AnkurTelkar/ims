package com.ims.customer.service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.reflect.TypeToken;
import com.ims.customer.dto.CustomerDto;
import com.ims.customer.entity.CustomerEntity;
import com.ims.customer.repository.CustomerRepository;
import com.ims.inventory.sales.dto.BillDto;
import com.ims.inventory.sales.repository.BillRepository;
import com.ims.inventory.sales.service.BillService;

@Service
public class CustomerService {


	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	BillRepository billRepository;

	private Logger logger = LoggerFactory.getLogger( CustomerService.class.getName() ) ;
	private TypeToken<List<CustomerDto>> typeToken =  new TypeToken<List<CustomerDto>>() {
		private static final long serialVersionUID = 1L;
	};

	public List<CustomerDto> findAll() {
		List<CustomerEntity> customerEntityList = (List<CustomerEntity> ) customerRepository.findAll();
		List<CustomerDto> customerDtoList  = entityToDtoList(customerEntityList);
		return customerDtoList;
	}
	
	public JSONArray findCustomerInfo( String input ) {
		JSONArray jsonArray = new JSONArray();
		List<CustomerEntity> customerEntityList  = customerRepository.findByCustomerNameContainingOrAddressContainingOrCustomerDetailsPhoneNoContaining(input, input, input);
		if( CollectionUtils.isEmpty(customerEntityList) ) {
			return jsonArray;
		}
		List<CustomerDto> customerDtoList = entityToDtoList(customerEntityList);
		for( CustomerDto dto : customerDtoList ) {
			input = input.toLowerCase();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", dto.getCustomerId());
			jsonObject.put("customerName", dto.getCustomerName());
			jsonObject.put("phoneNo", dto.getPhoneNo());
			jsonObject.put("email", dto.getEmail());
			jsonObject.put("displayName", dto.getDisplayName());
			jsonArray.put(jsonObject);
		
		}
		return jsonArray;
	}

	public CustomerDto findById(int id) {
		CustomerEntity customerEntity = customerRepository.findOne(id);
		if( customerEntity == null ) {
			return new CustomerDto();
		} 
		return entityToDto( customerEntity );
	}
	
	public CustomerDto findByCustomerName(String customerName) {
		CustomerEntity customerEntity = customerRepository.findByCustomerName( customerName );
		if( customerEntity == null ) {
			return null;
		} 
		return entityToDto( customerEntity );
	}
	
	public String getNextCode() {
		return String.format("%05d", customerRepository.getNextCode() );
		
	}

	public CustomerDto save(CustomerDto customerDto) {
		CustomerEntity customerEntity = dtoToEntity(customerDto);
		customerEntity = customerRepository.save( customerEntity );
		return entityToDto(customerEntity);
	}
	
	public String delete( int customerId ) {
		CustomerEntity customerEntity = customerRepository.findOne(customerId);
		int billCount = billRepository.countByCustomerId(customerId);
		if( billCount > 0 ) {
			return "Cannot Delete " + customerEntity.getCustomerName() + ". This Customer have dependent Bills";
		}
		
		customerRepository.delete( customerEntity );
		return customerEntity.getCustomerName() + " deleted Successfully!";
	}
	
	public CustomerDto findByPhoneNo( String phoneNo ) {
		CustomerEntity customerEntity = customerRepository.findByCustomerDetailsPhoneNo(phoneNo);
		CustomerDto customerDto = null;
		
		if( customerEntity != null ) {
			customerDto = entityToDto( customerEntity );
		}
		
		/*if( customerDto == null ) {
			customerDto = findByCustomerNameOrCreateNew(phoneNo);
		}*/
		return customerDto;
	}
	
	public CustomerDto findByEmail( String email ) {
		CustomerEntity customerEntity = customerRepository.findByCustomerDetailsEmail(email);
		CustomerDto customerDto = null;
		if( customerEntity != null ) {
			customerDto = entityToDto( customerEntity );
		}
		/*if( customerDto == null ) {
			customerDto = findByCustomerNameOrCreateNew(email);
		}*/
		return customerDto;
	}
	
	public CustomerDto findByCustomerNameOrCreateNew( String customerInfo ) {
		CustomerDto customerDto = findByCustomerName(customerInfo);
		if( customerDto == null ) {
			customerDto = new CustomerDto();
			customerDto.setCustomerName( customerInfo );
			customerDto = save(customerDto);
		}
		return customerDto;
	}
	
	private CustomerEntity dtoToEntity( CustomerDto customerDto ) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map( customerDto, CustomerEntity.class );
	}
	
	private List<CustomerDto> entityToDtoList( List<CustomerEntity> customerEntityList ) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map( customerEntityList, typeToken.getType() );
	}
	
	public CustomerDto entityToDto( CustomerEntity customerEntity ) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			return modelMapper.map( customerEntity, CustomerDto.class );
		} catch( Exception e ) {
			logger.error( e.getMessage(), e );
			throw e;
		}
	}
	
}
