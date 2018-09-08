package com.ims.inventory.purchasing.dto;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.ims.util.utility.Utility;
import com.ims.vendor.dto.VendorDTO;


public class ReceivingDto {

	private int receivingId;
	private String receivingNo;
	private String invoiceNo = "";
	private VendorDTO vendor;
	private String description;
	private int status;
	private double total;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date invoiceDate;
	private Date createdAt;
	private Date updatedAt;
	private List<ReceivingItemDto> receivingItems;
	private List<ReceivingAmountDetailDto> recivingAmountDetails;

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


	public VendorDTO getVendor() {
		return vendor;
	}

	public void setVendor(VendorDTO vendor) {
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

	public List<ReceivingItemDto> getReceivingItems() {
		return receivingItems;
	}

	public void setReceivingItems(List<ReceivingItemDto> receivingItems) {
		this.receivingItems = receivingItems;
	}

	public List<ReceivingAmountDetailDto> getRecivingAmountDetails() {
		return recivingAmountDetails;
	}

	public void setRecivingAmountDetails(List<ReceivingAmountDetailDto> recivingAmountDetails) {
		this.recivingAmountDetails = recivingAmountDetails;
	}
	
	public String getStatusStr() {
		Utility utility = new Utility();
		return utility.getStatusStr( this.status );
	}
}
