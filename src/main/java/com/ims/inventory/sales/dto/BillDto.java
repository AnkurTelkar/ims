package com.ims.inventory.sales.dto;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.CollectionUtils;

import com.ims.ApplicationConstants;
import com.ims.customer.dto.CustomerDto;
import com.ims.user.dto.UserDto;
import com.ims.util.utility.Utility;


public class BillDto {

	private int billId;
	private String billNo;
	private CustomerDto customer;
	private String description;
	private int status;
	private double total;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date billDate;
	private UserDto user;
	private Date createdAt;
	
	private Date updatedAt;
	private List<BillItemDto> billItems;
	private List<BillAmountDetailDto> billAmountDetails;
	private List<BillPaymentDto> billPayments;
	public int getBillId() {
		return billId;
	}
	public void setBillId(int billId) {
		this.billId = billId;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public CustomerDto getCustomer() {
		return customer;
	}
	public void setCustomer(CustomerDto customer) {
		this.customer = customer;
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
	public Date getBillDate() {
		return billDate;
	}
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
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
	public List<BillItemDto> getBillItems() {
		return billItems;
	}
	public void setBillItems(List<BillItemDto> billItems) {
		this.billItems = billItems;
	}
	public List<BillAmountDetailDto> getBillAmountDetails() {
		return billAmountDetails;
	}
	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}
	public void setBillAmountDetails(List<BillAmountDetailDto> billAmountDetails) {
		this.billAmountDetails = billAmountDetails;
	}

	public String getStatusStr() {
		Utility utility = new Utility();
		return utility.getStatusStr( this.status );
	}
	public List<BillPaymentDto> getBillPayments() {
		return billPayments;
	}
	public void setBillPayments(List<BillPaymentDto> billPayments) {
		this.billPayments = billPayments;
	}
	
	public double getPaymentByTypeId( int typeId ) {
		double toReturn = 0.00;
		if( CollectionUtils.isEmpty( billPayments ) ) {
			return toReturn;
		}
		for( BillPaymentDto billPayment : billPayments ) {
			if( billPayment.getType() == typeId ) {
				toReturn = billPayment.getAmount();
				break;
			}
		}
		return toReturn;
	}
	
	public BillAmountDetailDto getAmountDetailByTypeId( int typeId ) {
		BillAmountDetailDto toReturn = new BillAmountDetailDto();
		if( CollectionUtils.isEmpty( billAmountDetails ) ) {
			return toReturn;
		}
		for( BillAmountDetailDto billAmount : billAmountDetails ) {
			if( billAmount.getType() == typeId ) {
				toReturn = billAmount;
				break;
			}
		}
		return toReturn;
	}
	
	public double getPaidAmount() {
		double paidAmount = 0;
		if( CollectionUtils.isEmpty( billAmountDetails ) ) {
			return paidAmount;
		}
		
		for( BillPaymentDto billPayment : billPayments ) {
			if( billPayment.getType() != ApplicationConstants.PAYMENT_TYPE_ACCOUNT ) {
				paidAmount += billPayment.getAmount();	
			}
		}
		return paidAmount;
	}
}
