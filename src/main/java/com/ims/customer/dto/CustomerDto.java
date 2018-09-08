package com.ims.customer.dto;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

public class CustomerDto {

	private int customerId;
	
	@Size( min = 5, max = 30, message = "Please enter Customer # between {min} and {max} characters long" )
    private String customerCode;
	
	@Size( max = 30, message = "Please enter Customer Name between {min} and {max} characters long" )
    private String customerName;
	
	@Size( max = 15, message = "Limit exceed. GSTIN no. length should not exceed {max}" )
    private String gstNo;
	
	@Size( max = 255, message = "Limit exceed. {max} characters allowed." )
    private String description;
	
	 @Size( max = 255, message = "Please enter address less than {max} characters long" )
    private String address;
	 
	 @Size( max = 20, message = "Please enter state less than {max} characters long" )
    private String state;
	 
	 @Size( max = 20, message = "Please enter city less than {max} characters long" )
    private String city;
	 
	 @DateTimeFormat(pattern="MMddyyyy")
    private Date createdAt;
	 
	 @DateTimeFormat(pattern="MMddyyyy")
    private Date updatedAt;
	 
	 @Valid
	private List<CustomerDetailDto> customerDetails;

	

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<CustomerDetailDto> getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(List<CustomerDetailDto> customerDetails) {
		this.customerDetails = customerDetails;
	}
	
	public String getDisplayName() {
		String displayName = "";
		if( !StringUtils.isEmpty( this.customerName )  ) {
			displayName = this.customerName;
		}
		
		if( !StringUtils.isEmpty( this.address )  ) {
			displayName += ", " + this.address;
		}
		
		if( !CollectionUtils.isEmpty( this.customerDetails ) && this.customerDetails.size() > 0 )	{
			displayName += " - " + this.customerDetails.get(0).getPhoneNo();
		}
		return displayName;
	}
   
	public String getChitDisplayName() {
		String displayName = "";
		if( !StringUtils.isEmpty( this.customerName )  ) {
			displayName = "<b>"+ this.customerName + "</b>";
		}
		if( !StringUtils.isEmpty( getAddress() )  ) {
			displayName += "\n<br>" + getAddress();
		}
		if( !StringUtils.isEmpty( getCity() )  ) {
			displayName += "\n<br>" + getCity() + ", " + getState();
		}
		if( !CollectionUtils.isEmpty( this.customerDetails ) && this.customerDetails.size() > 0 )	{
			displayName += "\n<br><b>Contact:</b>" + this.customerDetails.get(0).getPhoneNo();
		}
		if( !StringUtils.isEmpty(getGstNo()) ) {
			displayName += "\n<br><b>GSTIN:</b> " + getGstNo();	
		}
		return displayName;
	}
	
	public String getPhoneNo() {
		String phoneNo = "";
		if( !CollectionUtils.isEmpty( customerDetails ) ) {
			phoneNo = this.customerDetails.get(0).getPhoneNo();
		}
		return phoneNo;
	}
	
	public String getEmail() {
		String phoneNo = "";
		if( !CollectionUtils.isEmpty( customerDetails ) ) {
			phoneNo = this.customerDetails.get(0).getEmail();
		}
		return phoneNo;
	}
}
