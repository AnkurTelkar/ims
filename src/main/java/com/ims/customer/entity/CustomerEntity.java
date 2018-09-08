package com.ims.customer.entity;

import static javax.persistence.GenerationType.IDENTITY;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table( name="customers", catalog = "ims")
public class CustomerEntity {

	@Id
	@GeneratedValue( strategy=IDENTITY )
	private int customerId;
	
	@Column( name="customer_code", unique=true)
    private String customerCode;
	
	@Column( name="customer_name", unique=true )
    private String customerName;
	
	@Column(name="description")
    private String description = "";

	@Column( name="gst_no", unique=true )
    private String gstNo;
	
	@Column( name="address" )
    private String address = "";
	
	@Column( name="state" )
    private String state = "";
	
	@Column( name="city" )
    private String city = "";
	
	@Column( name="created_at" )
    private Date createdAt;
	
	@Column( name="updated_at" )
    private Date updatedAt;

	@OneToMany( mappedBy="customer", cascade = CascadeType.ALL, orphanRemoval=true )
	private List<CustomerDetailEntity> customerDetails;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
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

	public List<CustomerDetailEntity> getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(List<CustomerDetailEntity> customerDetails) {
		this.customerDetails = customerDetails;
	}

   
}
