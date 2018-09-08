package com.ims.user.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.ims.util.entity.RightsEntity;

@Entity
@Table(name = "user_rights")
public class UserRightsEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserEntity user;

	@OneToOne
	@JoinColumn(name = "right_id", referencedColumnName = "id")
	private RightsEntity right;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public RightsEntity getRight() {
		return right;
	}

	public void setRight(RightsEntity right) {
		this.right = right;
	}

}
