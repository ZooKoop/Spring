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
	/**
	 * 本地字段,只判断是否创建文件夹不写入库
	 */
	@Value("${whale.workSql_D}")
	private String workSql_D;
	/**
	 * 浏览器字段不写入库
	 */
	@Value("${whale.workSql_L}")
	private String workSql_L;
	/**
	 * 临时本地字段写入库
	 */
	private String workSql_D_TEM;
	/**
	 * 临时浏览器字段写入库
	 */
	private String workSql_L_TEM;

	@Autowired
	private WorkServices workServices;
	@Autowired
	private SecurityUserRepository securityUserRepository;

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

	/**
	 * 模态框跳转用
	 * 
	 * @return
	 */
	@RequestMapping("/toAdd")
	public String toAdd() {
		return "/back/work/work_add";
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
				work.setSqlUrls(workSql_L_TEM);
				work.setSqlUrlsLocal(workSql_D_TEM);
				if (workServices.add(work)) {
					hashMap.put("success", "200");
				}
			}
		} else {
			hashMap.put("fail", "400");
		}
		return hashMap;
	}

	@GetMapping("/delete")
	@ResponseBody
	public String del(String id) {
		try {
			Work w = workServices.findById(id);
			if (!StringUtils.isBlank(w.getSqlUrlsLocal())) {
				String[] split = w.getSqlUrlsLocal().split(",");
				List<String> asList = Arrays.asList(split);
				for (String s : asList) {
					boolean delete = new File(s).delete();
					System.out.println("删除结果：" + delete);
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

	@PostMapping("/delete_All")
	@ResponseBody
	public String delete_All(String ids) {
		try {
			List<String> idList = Arrays.asList(ids.split(","));
			for (String id : idList) {// 放置报空指针异常
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
		Work work_last = workServices.findById(work.getId());
		work.setSecurityUser(securityUserRepository.findByuserName(authentication.getName()));
		if (!StringUtils.isBlank(workSql_L_TEM) && StringUtils.isBlank(work_last.getSqlUrls())
				&& !StringUtils.isBlank(workSql_D_TEM) && StringUtils.isBlank(work_last.getSqlUrlsLocal())) {// 如果前不空，后空
			work.setSqlUrls(workSql_L_TEM);
			work.setSqlUrlsLocal(workSql_D_TEM);
		}
		if (StringUtils.isBlank(workSql_L_TEM) && !StringUtils.isBlank(work_last.getSqlUrls())
				&& StringUtils.isBlank(workSql_D_TEM) && !StringUtils.isBlank(work_last.getSqlUrlsLocal())) {// 前空，后不空
			work.setSqlUrls(work_last.getSqlUrls());
			work.setSqlUrlsLocal(work_last.getSqlUrlsLocal());
		}
		if (!StringUtils.isBlank(workSql_L_TEM) && !StringUtils.isBlank(work_last.getSqlUrls())
				&& !StringUtils.isBlank(workSql_D_TEM) && !StringUtils.isBlank(work_last.getSqlUrlsLocal())) {// 前不空，后不空
			work.setSqlUrls(work_last.getSqlUrls() + workSql_L_TEM);
			work.setSqlUrlsLocal(work_last.getSqlUrlsLocal() + workSql_D_TEM);
		}
//		if (work_last.getSqlUrls().equals(workSql_L_TEM) && work_last.getSqlUrlsLocal().equals(workSql_D_TEM)) {// 前=后
//			work.setSqlUrls(work_last.getSqlUrls());
//			work.setSqlUrlsLocal(work_last.getSqlUrlsLocal());
//		}
		if (workServices.update(work)) {
			return "200";
		}
		return "400";
	}

	@PostMapping("/sqlUpload")
	@ResponseBody
	public Map<String, Object> sqlUpload(MultipartFile[] sqlurl_font) {
		Map<String, Object> upload = UploadUntils.upload(workSql_D, workSql_D_TEM,workSql_L, workSql_L_TEM, sqlurl_font);
		System.out.println(upload.get("namesame") + "查找文件夹下相同的名字的文件");
		try {
			if (null != upload.get("workSql_D_TEM") && null != upload.get("workSql_L_TEM")) {// 判断的时候不能toString，在get的时候就已经报空指针了
				workSql_D_TEM = upload.get("workSql_D_TEM").toString();
				workSql_L_TEM = upload.get("workSql_L_TEM").toString();
			}
		} catch (Exception e) {
		}
		
		return upload;
	}

	public String getWorkSql_D_TEM() {
		return workSql_D_TEM;
	}

	public void setWorkSql_D_TEM(String workSql_D_TEM) {
		this.workSql_D_TEM = workSql_D_TEM;
	}

	public String getWorkSql_L_TEM() {
		return workSql_L_TEM;
	}

	public void setWorkSql_L_TEM(String workSql_L_TEM) {
		this.workSql_L_TEM = workSql_L_TEM;
	}
	
	
};
