package com.ims.inventory.purchasing.dto;

import java.util.Date;

import com.ims.inventory.dto.SkuDto;

public class ReceivingItemDto {


	private Integer receivingItemId;
	private SkuDto sku;
	private String description;
	private double quantity;
	private double discount;
	private double gst;
	private double cost;
	private Date createdAt;
	private Date updatedAt;
	private ReceivingDto receiving;

	public Integer getReceivingItemId() {
		return receivingItemId;
	}

	public void setReceivingItemId(Integer receivingItemId) {
		this.receivingItemId = receivingItemId;
	}

	public SkuDto getSku() {
		return sku;
	}

	public void setSku(SkuDto sku) {
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

	public ReceivingDto getReceiving() {
		return receiving;
	}

	public void setReceiving(ReceivingDto receiving) {
		this.receiving = receiving;
	}


	public String getSkuDisplayName() {
		return this.getSku().getItem().getItemName() + " - " + this.getSku().getSkuCode() + " - " + this.getSku().getDescription();
	}
	
	public double getTotalCost() {
		return this.getCost() * this.getQuantity();
	}
	
	public double getTotalGst() {
		return this.getTotalCost() * this.getGst() / 100;
	}
	public double getTotalDiscount() {
		return this.getTotalCost() * this.getDiscount() / 100;
	}
	
	public double getTotalCgst() {
		return getTotalGst() / 2;
	}
	
	public double getTotalSgst() {
		return getTotalGst() / 2;
	}

	public double getCgst() {
		return gst/2;
	}
	public double getSgst() {
		return gst/2;
	}
}
