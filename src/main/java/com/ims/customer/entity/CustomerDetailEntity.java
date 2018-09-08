package com.ims.customer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table( name="customer_details", catalog = "ims" )
public class CustomerDetailEntity {
	
	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY )
	private int id;
	
	@Column( name="email", unique=true)
	private String email;
	
	@Column( name="phone_no", unique=true)
	private String phoneNo = "";
	
	@Column( name="contact_person")
	private String contactPerson;

	@ManyToOne( fetch=FetchType.LAZY )
	@JoinColumn( name="customer_id", referencedColumnName="customerId" )
	private CustomerEntity customer;

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

	public CustomerEntity getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerEntity customer) {
		this.customer = customer;
	}
	
}
