package com.ims.inventory.dto;

import java.util.Date;

import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

public class SkuDto {

	private Integer skuId;
	
	@Size(min = 4, max = 13, message = "Please enter barcode between {min} and {max} characters long.")
	private String barcode;
	
	@Size( min = 5, max = 30, message = "Please enter Name between {min} and {max} characters long" )
	private String skuCode;
	
	@Size( max = 255, message = "Limit exceed. {max} characters allowed." )
	private String description;
	
	private double quantity;
	private double threshold;
	private double cost;
	private double gst;
	private String hsn;
	private double retailPrice;
	private double discount;
	private double sellingPrice;
	private Integer status;
	private Date createdAt;
	private Date updatedAt;
	@DateTimeFormat(pattern="MMddyyyy")
	private int createdBy;
	@DateTimeFormat(pattern="MMddyyyy")
	private int updatedBy;
	private ItemDto item;

	public Integer getSkuId() {
		return skuId;
	}

	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
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

	public double getThreshold() {
		return threshold;
	}

	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getGst() {
		return gst;
	}

	public void setGst(double gst) {
		this.gst = gst;
	}

	public double getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(double retailPrice) {
		this.retailPrice = retailPrice;
	}
	
	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public int getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}

	
	
	public ItemDto getItem() {
		return item;
	}

	public void setItem(ItemDto item) {
		this.item = item;
	}

	public double extendedCost() {
    	double extendedCost = this.cost;
    	if( gst > 0 ) {
    		extendedCost += this.cost * gst /100;
    	}
    	return extendedCost;
    }
	
	public double sellingPrice() {
		if( discount > 0  ) {
			sellingPrice = (double) Math.round( ( retailPrice / discount ) * 100 ) /100;
		}
		return sellingPrice;
    }

	public String getHsn() {
		return hsn;
	}

	public void setHsn(String hsn) {
		this.hsn = hsn;
	}
}
