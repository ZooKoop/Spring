package com.whale.repostitory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.whale.model.AllImages;

public interface AllImagesRepostitory extends JpaRepository<AllImages, String> {
	@Query("select a from AllImages a")
	Page<AllImages> findList(Pageable pageable);
}
