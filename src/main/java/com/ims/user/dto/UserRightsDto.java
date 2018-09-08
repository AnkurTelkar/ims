package com.ims.user.dto;

import com.ims.util.dto.RightsDto;

public class UserRightsDto {

	private Integer id;
	private UserDto user;
	private RightsDto right;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public RightsDto getRight() {
		return right;
	}

	public void setRight(RightsDto right) {
		this.right = right;
	}

}
