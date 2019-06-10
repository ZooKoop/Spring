package com.whale.repostitory;

import org.springframework.data.jpa.repository.JpaRepository;
import com.whale.model.WorkOpction;

public interface WorkOpctionRepostitory extends JpaRepository<WorkOpction, String> {
	WorkOpction findByOpctionCode(String opctionCode);
}
