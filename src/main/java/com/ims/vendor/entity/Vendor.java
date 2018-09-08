package com.ims.vendor.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table( name="vendors" )
public class Vendor {

	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY )
	private int id;
	
	@Column( name="vendor_code", unique=true)
    private String vendorCode;
	
	@Column( name="vendor_name", unique=true )
    private String vendorName;
	
	@Column(name="description")
    private String description = "";
	
	@Column( name="gst_no", unique=true )
    private String gstNo;
	
	@Column( name="address" )
    private String address = "";
	
	@Column( name="state" )
    private String state = "";
	
	@Column( name="city" )
    private String city = "";
	
	@Column( name="created_at" )
    private Date createdAt;
	
	@Column( name="updated_at" )
    private Date updatedAt;

	@OneToMany( mappedBy="vendor", cascade = CascadeType.ALL, orphanRemoval=true )
	private List<VendorDetails> vendorDetails;

   public int getId() {
       return this.id;
   }
   
   public void setId(int id) {
       this.id = id;
   }
   public String getVendorCode() {
       return this.vendorCode;
   }
   
   public void setVendorCode(String vendorCode) {
       this.vendorCode = vendorCode;
   }
   public String getVendorName() {
       return this.vendorName;
   }
   
   public void setVendorName(String vendorName) {
       this.vendorName = vendorName;
   }


   public String getDescription() {
       return this.description;
   }
   
   public void setDescription(String description) {
       this.description = description;
   }

   public String getGstNo() {
	return gstNo;
}

public void setGstNo(String gstNo) {
	this.gstNo = gstNo;
}

public String getAddress() {
       return this.address;
   }
   
   public void setAddress(String address) {
       this.address = address;
   }
   public Date getCreatedAt() {
       return this.createdAt;
   }
   
   public void setCreatedAt(Date createdAt) {
       this.createdAt = createdAt;
   }
   public Date getUpdatedAt() {
       return this.updatedAt;
   }
   
   public void setUpdatedAt(Date updatedAt) {
       this.updatedAt = updatedAt;
   }

   public String getDisplayName() {
       return this.getVendorName();
   }


	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the vendorDetails
	 */
	public List<VendorDetails> getVendorDetails() {
		return vendorDetails;
	}

	/**
	 * @param vendorDetails the vendorDetails to set
	 */
	public void setVendorDetails(List<VendorDetails> vendorDetails) {
		this.vendorDetails = vendorDetails;
	}

}
