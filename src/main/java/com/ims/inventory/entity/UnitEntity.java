package com.ims.inventory.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ims.util.entity.MeasuringUnitEntity;

@Entity
@Table(name = "inv_units", catalog = "ims")
public class UnitEntity {
    
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "unit_id")
	private Integer unitId;

	@Column(name = "unit_name", length = 20, unique=true)
    private String unitName;
	
	@Column(name = "amount", precision=10, scale=4)
    private double amount;

	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn( name="measuring_unit_id", referencedColumnName="id")
	private MeasuringUnitEntity measuringUnit;

	@Column(name = "description", length = 80)
	private String description;
	
	@Column(name = "status")
    private int status;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
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

	public MeasuringUnitEntity getMeasuringUnit() {
		return measuringUnit;
	}

	public void setMeasuringUnit(MeasuringUnitEntity measuringUnit) {
		this.measuringUnit = measuringUnit;
	}

}