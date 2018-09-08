package com.ims.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ims.customer.entity.CustomerEntity;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer>  {
	
	@Query("SELECT coalesce(MAX(c.customerId) + 1, 1) FROM CustomerEntity c")
	int getNextCode();
	
	CustomerEntity findByCustomerDetailsPhoneNo( String phoneNo );
	
	CustomerEntity findByCustomerDetailsEmail( String email );
	
	CustomerEntity findByCustomerName( String customerName );
	
	List<CustomerEntity> findByCustomerNameContainingOrAddressContainingOrCustomerDetailsPhoneNoContaining( String input, String input2, String input3 );
	
}
