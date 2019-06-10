package com.whale.repostitory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.whale.model.Work;

public interface WorkRepostitory extends JpaRepository<Work, String>,JpaSpecificationExecutor<Work>{
//	@Query(value = "select * from Work a,SECURITY_USER b where a.security_user_id = b.id and b.USER_NAME = :userName",nativeQuery = true)
//	Page<Work> findAll(Pageable pageable,@Param("userName")String userName);

	Work findByticketNumber(String number);
}
