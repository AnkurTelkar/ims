package com.ims.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ims.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	
	UserEntity findByUserCode(String userCode);

	UserEntity findByLoginId(String loginId);

	UserEntity findByEmail(String email);	

	@Query("SELECT MAX (u.userId) FROM UserEntity u")
	Integer findByUserIdMax();

	@Query("select u FROM UserEntity u WHERE u.loginId = :loginId AND u.password= :password and u.status=1")
	UserEntity findByLoginIdAndPasswordAndStatus(@Param("loginId") String loginId, @Param("password") String password);

}