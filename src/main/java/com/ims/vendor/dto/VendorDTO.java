package com.ims.vendor.dto;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.AutoPopulatingList;

public class VendorDTO {

	private int id;
    
    @Size( min = 5, max = 30, message = "Please enter Vendor # between {min} and {max} characters long" )
    private String vendorCode;
    
    @Size( min = 5, max = 30, message = "Please enter Vendor Name between {min} and {max} characters long" )
    private String vendorName;
    
    @Size( max = 255, message = "Limit exceed. {max} characters allowed." )
    private String description;
    
    @Size( max = 15, message = "Limit exceed. GSTIN no. length should not exceed {max}" )
    private String gstNo;
    
    @Size( max = 255, message = "Please enter address less than {max} characters long" )
    private String address;
    
    @Size( max = 20, message = "Please enter state less than {max} characters long" )
    private String state;
    
    @Size( max = 20, message = "Please enter city less than {max} characters long" )
    private String city;

    @DateTimeFormat(pattern="MMddyyyy")
    private Date createdAt;
    
    @DateTimeFormat(pattern="MMddyyyy")
    private Date updatedAt;

    @Valid
    private List<VendorDetailsDTO> vendorDetails = new AutoPopulatingList<VendorDetailsDTO>( VendorDetailsDTO.class );

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
       return this.getVendorCode() + " - " + this.getVendorName();
   }

	/**
	 * @return the gstNo
	 */
	public String getGstNo() {
		return gstNo;
	}

	/**
	 * @param gstNo the gstNo to set
	 */
	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
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
	public List<VendorDetailsDTO> getVendorDetails() {
		return vendorDetails;
	}

	/**
	 * @param vendorDetails the vendorDetails to set
	 */
	public void setVendorDetails(List<VendorDetailsDTO> vendorDetails) {
		this.vendorDetails = vendorDetails;
	}



}
