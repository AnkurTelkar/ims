package com.ims.util.dto;

import java.util.List;

public class DashboardDto {
	
	private int salesToday;
	private int purchasesToday;
	private int outOfStock;
	private List<Object[]> topSkus;

	public int getSalesToday() {
		return salesToday;
	}
	public void setSalesToday(int salesToday) {
		this.salesToday = salesToday;
	}
	public int getPurchasesToday() {
		return purchasesToday;
	}
	public void setPurchasesToday(int purchasesToday) {
		this.purchasesToday = purchasesToday;
	}
	public int getOutOfStock() {
		return outOfStock;
	}
	public void setOutOfStock(int outOfStock) {
		this.outOfStock = outOfStock;
	}
	public List<Object[]> getTopSkus() {
		return topSkus;
	}
	public void setTopSkus(List<Object[]> topSkus) {
		this.topSkus = topSkus;
	}

}
