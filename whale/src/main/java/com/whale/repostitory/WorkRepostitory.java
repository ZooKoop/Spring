package com.whale.repostitory;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.whale.model.Work;

public interface WorkRepostitory extends JpaRepository<Work, String>,JpaSpecificationExecutor<Work>{
	@Query(value = "select * from Work a,SECURITY_USER b where a.security_user_id = b.id and b.USER_NAME = :userName",nativeQuery = true)
	List<Work> quAll(@Param("userName")String userName);
	
	Work findByticketNumber(String number);
	
}
