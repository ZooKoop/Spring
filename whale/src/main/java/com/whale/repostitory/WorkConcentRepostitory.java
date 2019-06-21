package com.whale.repostitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.whale.model.WorkConcent;

public interface WorkConcentRepostitory extends JpaRepository<WorkConcent, String>,JpaSpecificationExecutor<WorkConcent>{
	
}
