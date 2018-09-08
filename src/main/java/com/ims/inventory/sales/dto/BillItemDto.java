package com.ims.inventory.sales.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.ims.inventory.dto.SkuDto;
import com.ims.user.dto.UserDto;

public class BillItemDto {


	private Integer billItemId;
	private SkuDto sku;
	private double quantity;
	private double discount;
	private double price;
	private Date createdAt;
	private Date updatedAt;
	private BillDto bill;
	private UserDto soldBy;

	public Integer getBillItemId() {
		return billItemId;
	}
	public void setBillItemId(Integer billItemId) {
		this.billItemId = billItemId;
	}
	public SkuDto getSku() {
		return sku;
	}
	public void setSku(SkuDto sku) {
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
	public BillDto getBill() {
		return bill;
	}
	public void setBill(BillDto bill) {
		this.bill = bill;
	}
	public UserDto getSoldBy() {
		return soldBy;
	}
	public void setSoldBy(UserDto soldBy) {
		this.soldBy = soldBy;
	}
	public String getSkuDisplayName() {
		return this.getSku().getDescription();
	}
	
	public double getPriceWithGst() {
		double priceWithGst = getPrice() * this.getSku().getGst();
		BigDecimal bd = new BigDecimal(priceWithGst);
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		return bd.doubleValue();
	}
	
}
