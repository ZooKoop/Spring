package com.whale.repostitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whale.model.ImgType;

public interface ImgTypeRepostitory extends JpaRepository<ImgType, String> {
}
