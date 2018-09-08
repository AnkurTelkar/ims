package com.ims.inventory.dto;

import java.util.Date;

import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

public class CategoryDto {
    
	private Integer categoryId;
	
	@Size( min = 5, max = 30, message = "Please enter code between {min} and {max} characters long" )
	private String categoryCode;
	
	@Size( min = 5, max = 30, message = "Please enter Name between {min} and {max} characters long" )
	private String categoryName;
	
	@Size( max = 255, message = "Limit exceed. {max} characters allowed." )
	private String description;
	
	private int parentCategoryId;
    private int status;
    
    @DateTimeFormat(pattern="MMddyyyy")
	private Date createdAt;
    
    @DateTimeFormat(pattern="MMddyyyy")
	private Date updatedAt;

	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getParentCategoryId() {
		return parentCategoryId;
	}
	public void setParentCategoryId(int parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
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

}