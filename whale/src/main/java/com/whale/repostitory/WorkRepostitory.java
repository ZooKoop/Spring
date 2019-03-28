package com.whale.repostitory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.whale.model.Work;

public interface WorkRepostitory extends JpaRepository<Work, String> {
	@Query("select a from Work a")
	Page<Work> findList(Pageable pageable);

	Work findByticketNumber(Integer number);
}
