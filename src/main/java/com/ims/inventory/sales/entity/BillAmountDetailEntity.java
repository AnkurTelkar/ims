package com.ims.inventory.sales.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table( name="inv_bill_amount_details" )
public class BillAmountDetailEntity {

	@Id
	@Column( name="id" )
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column( name="type" )
	private Integer type;
	
	@Column( nullable=false)
	private double amount =  0;
	
	@Column( name="created_at" )
	@Temporal( TemporalType.TIMESTAMP )
	private Date createdAt;

	@Column( name="updated_at" )
	@Temporal( TemporalType.TIMESTAMP )
	private Date updatedAt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name="bill_id")
	private BillEntity bill;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
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

	public BillEntity getBill() {
		return bill;
	}

	public void setBill(BillEntity bill) {
		this.bill = bill;
	}

}
