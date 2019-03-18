package com.whale.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
	public Map<String, Object> upLoad(MultipartFile uploadFile, Authentication authentication) {
		String username = authentication.getName();
		SecurityUser byuserName = securityUserRepository.findByuserName(username);
		Map<String, Object> json = new HashMap<String, Object>();
		String filename = uploadFile.getOriginalFilename();
		if (null != byuserName) {
			File file = new File(fileSrc + "/all_Img/" + username);
			if (!file.exists()) {
				file.mkdirs();
			}
			if (!uploadFile.isEmpty()) {
				try {
					AllImages allImages = new AllImages();
					allImages.setImgName(filename);
					allImages.setImgPath("http://localhost:8080/images/all_Img/"+username + "/" + filename);
					allImages.setSecurityUser(byuserName);
					allImagesRepostitory.save(allImages);
					uploadFile.transferTo(new File(file + "/" + filename));
					json.put("status", true);
					json.put("msg", filename);
					return json;
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
					json.put("status", false);
					json.put("error", filename);
					return json;
				}
			}
		}
		json.put("status", false);
		return json;
	};
	@GetMapping("/queryAll")
	@ResponseBody
	public Page<AllImages> queryAll(
			@RequestParam(value = "page") Integer page,
			@RequestParam(value = "size", defaultValue = "5") Integer size) {
		PageRequest pageable = PageRequest.of(page, size);
		Page<AllImages> findAll = allImagesRepostitory.findAll(pageable);
		return findAll;
	}
//	@GetMapping("/queryAll")
//	public String queryAll(Model model) {
//		List<AllImages> findAll = allImagesRepostitory.findAll();
//		model.addAttribute("list",findAll);
//		return "upload";
//	}
}
