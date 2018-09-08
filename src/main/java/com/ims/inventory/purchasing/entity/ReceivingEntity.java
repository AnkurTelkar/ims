package com.ims.inventory.purchasing.entity;

import java.util.Date;
import java.util.List;

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

import org.springframework.format.annotation.DateTimeFormat;

import com.ims.vendor.entity.Vendor;

@Entity 
@Table( name="inv_receivings", catalog="ims" )
public class ReceivingEntity {


	@Id
	@Column( name="receiving_id" )
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int receivingId;
	
	@Column(nullable=false, unique=true, name="receiving_no")
	private String receivingNo;
	
	@Column(nullable=false,  name="invoice_no")
	private String invoiceNo = "";
	
	@ManyToOne
	@JoinColumn( name="vendor_id", referencedColumnName="id" )
	private Vendor vendor;
	
	@Column( nullable=false, length=100 )
	private String description;

	@Column( nullable=false )
	private int status;

	@Column( nullable=false)
	private double total;

	
	@Column( name="invoice_date" )
	@Temporal( TemporalType.TIMESTAMP )
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date invoiceDate;
	
	@Column( name="created_at" )
	@Temporal( TemporalType.TIMESTAMP )
	private Date createdAt;

	@Column( name="updated_at" )
	@Temporal( TemporalType.TIMESTAMP )
	private Date updatedAt;

	@OneToMany( mappedBy = "receiving", cascade=CascadeType.ALL, orphanRemoval=true )
	private List<ReceivingItemEntity> receivingItems;
	
	@OneToMany( mappedBy = "receiving", cascade=CascadeType.ALL, orphanRemoval=true )
	private List<ReceivingAmountDetailEntity> recivingAmountDetails;

	public int getReceivingId() {
		return receivingId;
	}

	public void setReceivingId(int receivingId) {
		this.receivingId = receivingId;
	}


	public String getReceivingNo() {
		return receivingNo;
	}

	public void setReceivingNo(String receivingNo) {
		this.receivingNo = receivingNo;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
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

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
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

	public List<ReceivingItemEntity> getReceivingItems() {
		return receivingItems;
	}

	public void setReceivingItems(List<ReceivingItemEntity> receivingItems) {
		this.receivingItems = receivingItems;
	}

	public List<ReceivingAmountDetailEntity> getRecivingAmountDetails() {
		return recivingAmountDetails;
	}

	public void setRecivingAmountDetails(List<ReceivingAmountDetailEntity> recivingAmountDetails) {
		this.recivingAmountDetails = recivingAmountDetails;
	}
	
}
