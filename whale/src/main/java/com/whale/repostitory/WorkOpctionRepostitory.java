package com.whale.repostitory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.whale.model.WorkOpction;

public interface WorkOpctionRepostitory extends JpaRepository<WorkOpction, String> {
	@Query("select a from WorkOpction a")
	Page<WorkOpction> findList(Pageable pageable);
}
