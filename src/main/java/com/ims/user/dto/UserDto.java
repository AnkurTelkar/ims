package com.ims.user.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.DateTimeFormat;

public class UserDto {

	private Integer userId;

	@Size(min = 1, max = 30, message = "First name required (must contain 1 to 30 letters)")
	private String firstName;

	@Size(min = 1, max = 30, message = "Last name required (must contain 1 to 30 letters)")
	private String lastName;

	@Size(min = 5, max = 8, message = "Login ID required (must contain 5 to 8 letters)")
	private String loginId;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dateOfBirth;

	@Size(max = 255, message = "Please enter address less than {max} characters long")
	private String address;

	@Size(max = 10, message = "Please enter userCode less than {max} characters long")
	private String userCode;

	@Size(min = 2, max = 11, message = "Phone Number required (must contain 10 to 11 letters)")
	private String phoneNumber;

	@Email
	private String email;

	// @Size(min = 5, max = 30, message="Please select user role")
	private Integer userRole;

	@Size(min = 4, max = 6, message = "Password required (must contain 4 to 6 letters)")
	private String password;

	private Integer status;

	private Date creationTime;

	List<UserRightsDto> userRights;

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public Integer getUserRole() {
		return userRole;
	}

	public void setUserRole(Integer userRole) {
		this.userRole = userRole;
	}

	public String getFullName() {
		return this.firstName + " " + this.lastName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public List<UserRightsDto> getUserRights() {
		return userRights;
	}

	public void setUserRights(List<UserRightsDto> userRights) {
		this.userRights = userRights;
	}

}