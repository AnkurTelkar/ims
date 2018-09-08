package com.ims.inventory.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "inv_items", catalog = "ims")
public class ItemEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "item_id")
	private Integer itemId;
	
	@Column(name = "item_code", length = 20, unique=true)
	private String itemCode;
	
	@Column(name = "item_name", length = 20, unique=true)
	private String itemName;

	@Column(name = "description", length = 80)
	private String description;

	@ManyToOne( fetch=FetchType.LAZY )
	@JoinColumn( name="category_id" )
	private CategoryEntity category;
	
	/*@Column(name = "type_id")
	private Integer typeId;*/
	
	@ManyToOne()
	@JoinColumn( name="brand_id" )
	private BrandEntity brand;
	
	@ManyToOne( fetch=FetchType.LAZY )
	@JoinColumn( name="pack_unit_id" )
	private UnitEntity packUnit;
	
	@ManyToOne( fetch=FetchType.LAZY )
	@JoinColumn( name="track_unit_id")
	private UnitEntity trackUnit;
	
	@Column(name = "status")
	private Integer status;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
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

	public CategoryEntity getCategory() {
		return category;
	}

	public void setCategory(CategoryEntity category) {
		this.category = category;
	}

	public BrandEntity getBrand() {
		return brand;
	}

	public void setBrand(BrandEntity brand) {
		this.brand = brand;
	}

	public UnitEntity getPackUnit() {
		return packUnit;
	}

	public void setPackUnit(UnitEntity packUnit) {
		this.packUnit = packUnit;
	}

	public UnitEntity getTrackUnit() {
		return trackUnit;
	}

	public void setTrackUnit(UnitEntity trackUnit) {
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
