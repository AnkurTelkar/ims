package com.ims.inventory.sales.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ims.customer.entity.CustomerEntity;
import com.ims.user.entity.UserEntity;

@Entity
@Cacheable
@Table( name="inv_bills", catalog="ims" )
public class BillEntity {

	@Id
	@Column( name="bill_id" )
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int billId;
	
	@Column(nullable=false, unique=true, name="bill_no")
	private String billNo;
	
	@ManyToOne
	@JoinColumn( name="customer_id" )
	private CustomerEntity customer;
	
	@ManyToOne
	@JoinColumn( name="user_id" )
	private UserEntity user;
	
	@Column( nullable=false, length=100 )
	private String description;
	
	@Column( nullable=false )
	private int status;
	
	@Column( nullable=false)
	private double total;

	@Column( name="bill_date" )
	@Temporal( TemporalType.TIMESTAMP )
	private Date billDate;
	
	@Column( name="created_at" )
	@Temporal( TemporalType.TIMESTAMP )
	private Date createdAt;
	
	@Column( name="updated_at" )
	@Temporal( TemporalType.TIMESTAMP )
	private Date updatedAt;

	@OneToMany( mappedBy = "bill", cascade=CascadeType.ALL, orphanRemoval=true )
	private List<BillItemEntity> billItems;
	
	@OneToMany( mappedBy = "bill", cascade=CascadeType.ALL, orphanRemoval=true )
	private List<BillAmountDetailEntity> billAmountDetails;
	
	@OneToMany( mappedBy = "bill", cascade=CascadeType.ALL, orphanRemoval=true )
	private List<BillPaymentEntity> billPayments;
	public int getBillId() {
		return billId;
	}
	public void setBillId(int billId) {
		this.billId = billId;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	
	public CustomerEntity getCustomer() {
		return customer;
	}
	public void setCustomer(CustomerEntity customer) {
		this.customer = customer;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public Date getBillDate() {
		return billDate;
	}
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
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
	public List<BillItemEntity> getBillItems() {
		return billItems;
	}
	public void setBillItems(List<BillItemEntity> billItems) {
		this.billItems = billItems;
	}
	public List<BillAmountDetailEntity> getBillAmountDetails() {
		return billAmountDetails;
	}
	public void setBillAmountDetails(List<BillAmountDetailEntity> billAmountDetails) {
		this.billAmountDetails = billAmountDetails;
	}
	public UserEntity getUser() {
		return user;
	}
	public void setUser(UserEntity user) {
		this.user = user;
	}
	public List<BillPaymentEntity> getBillPayments() {
		return billPayments;
	}
	public void setBillPayments(List<BillPaymentEntity> billPayments) {
		this.billPayments = billPayments;
	}
}
