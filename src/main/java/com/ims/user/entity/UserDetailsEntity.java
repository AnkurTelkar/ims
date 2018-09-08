package com.ims.user.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.util.StringUtils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsEntity extends UserEntity implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserDetailsEntity(UserEntity userEntity) {
		super(userEntity);

	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		List<String> userRoles = new ArrayList<>();
		userRoles.add(""+super.getUserRole());
		//userRoles.add("2");

		String roles = StringUtils.collectionToCommaDelimitedString(userRoles);
		return AuthorityUtils.commaSeparatedStringToAuthorityList(roles);

		// return AuthorityUtils.commaSeparatedStringToAuthorityList("1,2");
	}

	@Override
	public String getUsername() {
		return super.getUserName();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
