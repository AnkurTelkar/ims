package com.ims.inventory.dto;

import java.util.Date;

import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.ims.util.dto.MeasuringUnitDto;

public class UnitDto {
    
	private Integer unitId;
	
	@Size( min = 3, max = 30, message = "Please enter Name between {min} and {max} characters long" )
    private String unitName;
	
    private double amount;
	private MeasuringUnitDto measuringUnit;
	
	@Size( max = 255, message = "Limit exceed. {max} characters allowed." )
	private String description;
    private int status;
    
    @DateTimeFormat(pattern="MMddyyyy")
	private Date createdAt;
    
    @DateTimeFormat(pattern="MMddyyyy")
	private Date updatedAt;
	public Integer getUnitId() {
		return unitId;
	}
	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
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
	public MeasuringUnitDto getMeasuringUnit() {
		return measuringUnit;
	}
	public void setMeasuringUnit(MeasuringUnitDto measuringUnit) {
		this.measuringUnit = measuringUnit;
	}
	
}