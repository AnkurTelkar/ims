package com.ims.reports.dto;

import com.ims.customer.dto.CustomerDto;
import com.ims.util.utility.Utility;

public class SaleSummaryDto {

	private CustomerDto customer;
	private double totalBills;
	private double totalPurchaseAmount;
	private double paidViaCheck;
	private double paidViaCash;
	private double onAccount;
	private double paidOther;

	public CustomerDto getCustomer() {
		return customer;
	}
	public void setCustomer(CustomerDto customer) {
		this.customer = customer;
	}
	public double getTotalBills() {
		return totalBills;
	}
	public void setTotalBills(double totalBills) {
		this.totalBills = totalBills;
	}
	public double getTotalPurchaseAmount() {
		return totalPurchaseAmount;
	}
	public void setTotalPurchaseAmount(double totalPurchaseAmount) {
		this.totalPurchaseAmount = totalPurchaseAmount;
	}
	public double getPaidViaCheck() {
		return paidViaCheck;
	}
	public void setPaidViaCheck(double paidViaCheck) {
		this.paidViaCheck = paidViaCheck;
	}
	public double getPaidViaCash() {
		return paidViaCash;
	}
	public void setPaidViaCash(double paidViaCash) {
		this.paidViaCash = paidViaCash;
	}
	public double getOnAccount() {
		return onAccount;
	}
	public void setOnAccount(double onAccount) {
		this.onAccount = onAccount;
	}
	public double getPaidOther() {
		return paidOther;
	}
	public void setPaidOther(double paidOther) {
		this.paidOther = paidOther;
	}
	
	public double getRemainingDues() {
		double remainingDues = this.totalPurchaseAmount - ( this.paidViaCash + this.paidViaCheck + this.paidOther );
		return Utility.roundUp( remainingDues, 2 );
	}
}
