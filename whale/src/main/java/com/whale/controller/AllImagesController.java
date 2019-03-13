package com.whale.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.whale.model.AllImages;
import com.whale.repostitory.AllImagesRepostitory;
import com.whale.security.model.SecurityUser;
import com.whale.security.repository.SecurityUserRepository;

@Controller
public class AllImagesController {
	@Value("${img.src}")
	private String fileSrc;

	@Autowired
	private SecurityUserRepository securityUserRepository;
	@Autowired
	private AllImagesRepostitory allImagesRepostitory;

	@RequestMapping("/toupload")
	public String toUpLoad() {
		return "upload";
	}

	@PostMapping(value = "/userUpLoad")
	@ResponseBody
	public Map<String, Object> upLoad(@RequestParam("uploadFile")MultipartFile[] uploadFile, Authentication authentication) {
		String username = authentication.getName();
		SecurityUser byuserName = securityUserRepository.findByuserName(username);
		Map<String, Object> json = new HashMap<String, Object>();
		if (null != byuserName) {
			File file = new File(fileSrc + "/all_Img/" + username);
			if (!file.exists()) {
				file.mkdirs();
			}
			try {
				for (int i = 0; i < uploadFile.length; i++) {
				if (!uploadFile[i].isEmpty()) {
					String filename = uploadFile[i].getOriginalFilename();
						AllImages allImages = new AllImages();
						allImages.setImgName(filename);
						allImages.setImgPath(file + "/" + filename);
						allImages.setSecurityUser(byuserName);
						allImagesRepostitory.save(allImages);
						uploadFile[i].transferTo(new File(file + "/" + filename));
				}
			}
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				json.put("status", false);
				//json.put("error", uploadFile[i].getOriginalFilename());
				return json;
			}

			// String filename = uploadFile.getOriginalFilename();
			// try {
			// uploadFile.transferTo(new File(file + "/" + filename));
			// } catch (IllegalStateException | IOException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// json.put("status", false);
			// return json;
			// }
			json.put("status", true);
			return json;
		}
		json.put("status", false);
		return json;
	};
}
