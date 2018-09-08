package com.ims.inventory.dto;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

public class ItemDto {

	private Integer itemId;
	
	@Size( min = 5, max = 30, message = "Please enter code between {min} and {max} characters long" )
	private String itemCode;
	
	@Size( min = 5, max = 30, message = "Please enter Name between {min} and {max} characters long" )
	private String itemName;
	
	@Size( max = 255, message = "Limit exceed. {max} characters allowed." )
	private String description;
	
	@Valid
	private CategoryDto category;
	
	/*private Integer typeId;*/
	@Valid
	private BrandDto brand;
	
	@Valid
	private UnitDto packUnit;
	
	@Valid
	private UnitDto trackUnit;
	private Integer status;
	
	@DateTimeFormat(pattern="MMddyyyy")
	private Date createdAt;
	
	@DateTimeFormat(pattern="MMddyyyy")
	private Date updatedAt;
	
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public CategoryDto getCategory() {
		return category;
	}
	public void setCategory(CategoryDto category) {
		this.category = category;
	}
	public BrandDto getBrand() {
		return brand;
	}
	public void setBrand(BrandDto brand) {
		this.brand = brand;
	}
	public UnitDto getPackUnit() {
		return packUnit;
	}
	public void setPackUnit(UnitDto packUnit) {
		this.packUnit = packUnit;
	}
	public UnitDto getTrackUnit() {
		return trackUnit;
	}
	public void setTrackUnit(UnitDto trackUnit) {
		this.trackUnit = trackUnit;
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

}
