package com.whale.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ImgController {
	@Value("${img.src}")
	private String fileSrc;

	@RequestMapping("/toupload")
	public String toUpLoad() {
		return "upload";
	}

	@PostMapping(value = "/userUpLoad")
	@ResponseBody
	public Map<String, Object> upLoad(MultipartFile uploadFile) {
		
//		for (int i = 0; i < file.length; i++) {
//            if (!file[i].isEmpty()) {
//
//                //上传文件，随机名称，";"分号隔开
//                fileName.add(FileUtil.uploadImage(request, "/upload/"+TimeUtils.formateString(new Date(), "yyyy-MM-dd")+"/", file[i], true));
//            }
//        }
		
		Map<String, Object> json = new HashMap<String, Object>();
		String filename = uploadFile.getOriginalFilename();
		File file = new File(fileSrc + "/all_Img");
		if (!file.exists()) {
			file.mkdirs();
		}
		try {
			uploadFile.transferTo(new File(file + "/" + filename));
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json.put("status", false);
			return json;
		}

		json.put("status", true);
		return json;
	};
}
