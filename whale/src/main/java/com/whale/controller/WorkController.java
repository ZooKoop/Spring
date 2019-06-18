package com.whale.controller;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.whale.model.Work;
import com.whale.security.model.SecurityUser;
import com.whale.security.repository.SecurityUserRepository;
import com.whale.services.WorkServices;
import com.whale.tools.UploadUntils;

@Controller
@RequestMapping("/back/work")
public class WorkController {
	@Value("${whale.src}")
	private String fileSrc;
	@Autowired
	private WorkServices workServices;
	@Autowired
	private SecurityUserRepository securityUserRepository;

	private String sqlUrl_tem;

	@GetMapping("/toWork")
	public String toWork(HttpServletRequest httpServletRequest, Model model) {
		String queryUrl = httpServletRequest.getRequestURI();
		model.addAttribute("queryUrl", queryUrl);
		return "back/work/work";
	}

	@GetMapping("/queryAll")
	@ResponseBody
	// public Page<Work> queryAll(@RequestParam(value = "page") Integer page,
	// @RequestParam(value = "size", defaultValue = "5") Integer size,
	// Authentication authentication,
	// Work work
	// ) {
	public Map<String, Object> queryAll(Authentication authentication, Work work) {
		String userName = authentication.getName();
		SecurityUser byuserName = securityUserRepository.findByuserName(userName);
		work.setSecurityUser(byuserName);
		if (null != byuserName) {
			// Page<Work> queryAll = workServices.queryAll(page,size,work);
			List<Work> queryAll = workServices.quAll(work);
			HashMap<String, Object> m = new HashMap<>();
			m.put("data", queryAll);
			return m;
		}
		return null;
	}

	@PostMapping("/sqlUpload")
	@ResponseBody
	public Map<String, Object> sqlUpload(MultipartFile[] sqlurl_font) {
		Map<String, Object> upload = UploadUntils.upload(fileSrc + "/sql", sqlurl_font);
		System.out.println(upload.get("namesame"));
		if(null != upload.get("sqlUrl_tem")) {//判断的时候不能toString，在get的时候就已经报空指针了
			sqlUrl_tem = upload.get("sqlUrl_tem").toString();
		}
		return upload;
	}

	@PostMapping("/add")
	@ResponseBody
	public Map<String, Object> add(Work work, Authentication authentication) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		if (work.getTicketNumber() != null) {
			if (workServices.findByTicketNumber(work.getTicketNumber())) {
				hashMap.put("repeat", "222");
			} else {
				work.setSecurityUser(securityUserRepository.findByuserName(authentication.getName()));
				work.setSqlUrls(sqlUrl_tem);
				boolean add = workServices.add(work);
				if (add) {
					hashMap.put("success", "200");
				}
			}
		} else {
			hashMap.put("fail", "400");
		}
		return hashMap;
	}

	@PostMapping("/delete_All")
	@ResponseBody
	public String delete_All(String ids) {
		try {
			List<String> idList = Arrays.asList(ids.split(","));
			for (String id : idList) {//放置报空指针异常
				Work findById = workServices.findById(id);
				if (!StringUtils.isBlank(findById.getSqlUrls())) {
					String[] sqlUrlLit = findById.getSqlUrls().split(",");
					for (String f : sqlUrlLit) {
						File file = new File(f);
						if (file.exists() && file.isFile()) {
							File absoluteFile = file.getAbsoluteFile();
							absoluteFile.delete();
						}
					}
				}
				workServices.delete_All(idList);
			}
			return "200";
		} catch (Exception e) {
			e.printStackTrace();
			return "400";
		}

	}

	@GetMapping("/delete")
	@ResponseBody
	public String del(String id) {
		try {
			Work w = workServices.findById(id);
			if (!StringUtils.isBlank(w.getSqlUrls())) {
				String[] split = w.getSqlUrls().split(",");
				List<String> asList = Arrays.asList(split);
				for (String s : asList) {
					File file = new File(s);
					if (file.exists() && file.isFile()) {
						File absoluteFile = file.getAbsoluteFile();
						boolean delete = absoluteFile.delete();
						 System.out.println(delete);
					}
				}
			}
			workServices.del(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "400";
		}
		return "200";
	}

	/**
	 * 模态框跳转用
	 * 
	 * @return
	 */
	@RequestMapping("/toAdd")
	public String toAdd() {
		return "/back/work/work_add";
	}

	@RequestMapping("/toUpdate")
	public String toUpdate() {
		return "/back/work/work_edit";
	}

	/**
	 * 获取要编辑的信息回显用
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/toUpdateInfo")
	@ResponseBody
	public Work toUpdateInfo(String id) {
		Work work = workServices.findById(id);
		// System.out.println(work);
		return work;
	}

	@RequestMapping("/Update")
	@ResponseBody
	public String update(Work work, Authentication authentication) {
		work.setSecurityUser(securityUserRepository.findByuserName(authentication.getName()));
		if (workServices.update(work)) {
			return "200";
		}
		return "400";
	}

	public String getSqlUrl_tem() {
		return sqlUrl_tem;
	}

	public void setSqlUrl_tem(String sqlUrl_tem) {
		this.sqlUrl_tem = sqlUrl_tem;
	}

};
