package com.whale.controller;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.whale.model.WorkConcent;
import com.whale.security.model.SecurityUser;
import com.whale.security.repository.SecurityUserRepository;
import com.whale.services.WorkConcentServices;
import com.whale.services.WorkServices;
import com.whale.tools.UploadUntils;

@Controller
@RequestMapping("/back/workConcent")
public class WorkConcentController {
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
//
//	@Autowired
//	private WorkServices workServices;
	@Autowired
	private WorkConcentServices workConcentServices;

};
