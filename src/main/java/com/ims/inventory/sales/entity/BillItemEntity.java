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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ims.inventory.entity.SkuEntity;
import com.ims.user.entity.UserEntity;

@Entity
@Table( name="inv_bill_items" )
public class BillItemEntity {

	@Id
	@Column( name="bill_item_id" )
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer billItemId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn( name = "sku_id" )
	private SkuEntity sku;

	@Column( nullable=false)
	private double quantity;

	@Column( nullable=false)
	private double discount;

	@Column( nullable=false)
	private double price;

	@Column( name="created_at" )
	@Temporal( TemporalType.TIMESTAMP )
	private Date createdAt;
	
	@Column( name="updated_at" )
	@Temporal( TemporalType.TIMESTAMP )
	private Date updatedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name="bill_id")
	private BillEntity bill;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn( name = "sold_by" )
	private UserEntity soldBy;

	public Integer getBillItemId() {
		return billItemId;
	}

	public void setBillItemId(Integer billItemId) {
		this.billItemId = billItemId;
	}

	public SkuEntity getSku() {
		return sku;
	}

	public void setSku(SkuEntity sku) {
		this.sku = sku;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
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

	public UserEntity getSoldBy() {
		return soldBy;
	}

	public void setSoldBy(UserEntity soldBy) {
		this.soldBy = soldBy;
	}

	

}
