package com.whale.security.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whale.model.UserInfor;
import com.whale.security.model.SecurityUser;

public interface SecurityUserRepository extends JpaRepository<SecurityUser, String> {
	@Query("select u from UserInfor u")
	Page<UserInfor> findList(Pageable pageable);

	SecurityUser findByuserName(String username);

}
