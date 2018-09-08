package com.ims.inventory.sales.dto;

import java.util.Date;

public class BillPaymentDto {

	private Integer id;
	private Integer type;
	private double amount = 0;
	private Date createdAt;
	private Date updatedAt;
	private BillDto bill;

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
	public BillDto getBill() {
		return bill;
	}
	public void setBill(BillDto bill) {
		this.bill = bill;
	}

}
