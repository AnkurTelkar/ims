package com.ims.inventory.transaction.dto;


import java.util.Date;
public class TransactionDetailDto {

	private Integer id;
	private int skuId;
	private double quantity;
	private int refType;
	private int refId;
	private Date createdAt;
	private Date updatedAt;
	
	
	
	public TransactionDetailDto(int skuId, double quantity, int refType, int refId, Date createdAt, Date updatedAt) {
		super();
		this.skuId = skuId;
		this.quantity = quantity;
		this.refType = refType;
		this.refId = refId;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getSkuId() {
		return skuId;
	}
	public void setSkuId(int skuId) {
		this.skuId = skuId;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	public int getRefType() {
		return refType;
	}
	public void setRefType(int refType) {
		this.refType = refType;
	}
	public int getRefId() {
		return refId;
	}
	public void setRefId(int refId) {
		this.refId = refId;
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

	

}