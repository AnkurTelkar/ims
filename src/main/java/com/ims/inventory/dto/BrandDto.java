package com.ims.inventory.dto;

import java.util.Date;

import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;


public class BrandDto {
	private Integer brandId;
	
	@Size( min = 5, max = 30, message = "Please enter code between {min} and {max} characters long" )
	private String brandCode;
	
	@Size( min = 2, max = 30, message = "Please enter Name between {min} and {max} characters long" )
	private String brandName;
	
	@Size( max = 255, message = "Limit exceed. {max} characters allowed." )
	private String description;
	
	@DateTimeFormat(pattern="MMddyyyy")
	private Date createdAt;
	
	@DateTimeFormat(pattern="MMddyyyy")
	private Date updatedAt;

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public void setUpdatedAt(Date updateAt) {
		this.updatedAt = updateAt;
	}


}