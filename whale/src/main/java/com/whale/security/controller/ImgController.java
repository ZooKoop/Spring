package com.whale.security.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ImgController {
	@Value("${img.src}")
	private String fileSrc;

	@RequestMapping(value = "/userUpLoad", method = RequestMethod.POST)
	@ResponseBody
	public String upLoad(MultipartFile uploadFile) {
		String filename = uploadFile.getOriginalFilename();
		File file = new File(fileSrc+"/user_head_Img");
		if (!file.exists()) {
			file.mkdirs();
		}
		try {
			uploadFile.transferTo(new File(file + "/" + filename));
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "图片上传失败！";
		}
		return "图片上传成功！";
	};
}
