package com.ims.vendor.dto;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

public class VendorDetailsDTO {

	private int id;
	
	@Email
	private String email;

	@Size( max = 11, message = "Invalid phone no. Length must not exceed {max}" )
	private String phoneNo;
	
	@Size( max = 30, message = "Contact person length must not exceed {max}" )
	private String contactPerson;
	
	private VendorDTO vendorDto;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	/**
	 * @return the vendorDto
	 */
	public VendorDTO getVendorDto() {
		return vendorDto;
	}

	/**
	 * @param vendorDto the vendorDto to set
	 */
	public void setVendorDto(VendorDTO vendorDto) {
		this.vendorDto = vendorDto;
	}

}
