package com.whale.repostitory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.whale.model.UserInfor;

public interface UserInforRepostitory extends JpaRepository<UserInfor, String> {
	@Query("select u from UserInfor u")
	Page<UserInfor> findList(Pageable pageable);

	UserInfor findByUserName(String userName);
	
}
