package com.ims.inventory.purchasing.entity;

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

@Entity
@Table( name="inv_receiving_items" )
public class ReceivingItemEntity {

	@Id
	@Column( name="receiving_item_id" )
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer receivingItemId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn( name = "sku_id" )
	private SkuEntity sku;
	@Column( nullable=false, length=255 )
	private String description;

	@Column( nullable=false)
	private double quantity;

	@Column( nullable=false)
	private double discount;

	@Column( nullable=false)
	private double gst;

	@Column( nullable=false)
	private double cost;

	@Column( name="created_at" )
	@Temporal( TemporalType.TIMESTAMP )
	private Date createdAt;
	
	@Column( name="updated_at" )
	@Temporal( TemporalType.TIMESTAMP )
	private Date updatedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name="receiving_id")
	private ReceivingEntity receiving;

	public Integer getReceivingItemId() {
		return receivingItemId;
	}

	public void setReceivingItemId(Integer receivingItemId) {
		this.receivingItemId = receivingItemId;
	}

	public SkuEntity getSku() {
		return sku;
	}

	public void setSku(SkuEntity sku) {
		this.sku = sku;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public double getGst() {
		return gst;
	}

	public void setGst(double gst) {
		this.gst = gst;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
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

	public ReceivingEntity getReceiving() {
		return receiving;
	}

	public void setReceiving(ReceivingEntity receiving) {
		this.receiving = receiving;
	}


}
