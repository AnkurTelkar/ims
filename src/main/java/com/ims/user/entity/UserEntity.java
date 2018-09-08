package com.ims.user.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "users", catalog = "ims")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "user_id")
	private Integer userId;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserRightsEntity> userRights;

	@Column(name = "first_name", length = 20, nullable = false)
	private String firstName;

	@Column(name = "last_name", length = 20)
	private String lastName;

	@Column(name = "login_id", length = 20, unique = true, nullable = false)
	private String loginId;

	@Column(name = "date_of_birth")
	@Temporal( TemporalType.TIMESTAMP )
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dateOfBirth;

	@Column(name = "address", length = 80)
	private String address;

	@Column(name = "user_code", length = 15, unique = true, nullable = false)
	private String userCode;

	@Column(name = "phone_number", length = 12)
	private String phoneNumber;

	@Column(name = "email", length = 30, unique = true)
	private String email;

	@Column(name = "user_role", nullable = false)
	private Integer userRole;

	@Column
	private String password;

	@Column
	private Integer status;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "creation_time")
	private Date creationTime;
	
	
	public UserEntity(){
		
	}
	

	public UserEntity(UserEntity entity) {
		super();
		this.userId = entity.userId;
		this.userRights = entity.userRights;
		this.firstName = entity.firstName;
		this.lastName = entity.lastName;
		this.loginId = entity.loginId;
		this.dateOfBirth = entity.dateOfBirth;
		this.address = entity.address;
		this.userCode = entity.userCode;
		this.phoneNumber = entity.phoneNumber;
		this.email = entity.email;
		this.userRole = entity.userRole;
		this.password = entity.password;
		this.status = entity.status;
		this.creationTime = entity.creationTime;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public List<UserRightsEntity> getUserRights() {
		return userRights;
	}

	public void setUserRights(List<UserRightsEntity> userRights) {
		this.userRights = userRights;
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
	
	public String getUserName() {
		return loginId;
	}

}