package com.whale.tools;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public class UploadUntils {
	/**
	 * 
	 * @param fileSrc 本地路径
	 * @param fileMultipartFiles 多上传
	 * @param sqlUrl_tem 临时变量，赋值给其他类
	 * @return
	 */
	public static Map<String, Object> upload(String fileSrc,MultipartFile[] fileMultipartFiles) {
		HashMap<String, Object> json = new HashMap<String, Object>();
		File file = new File(fileSrc);
		if (!file.exists()) {
			file.mkdirs();
		};
		// 查询文件夹下的所有文件名字
		String[] nameList = new File(file.toString()).list();
		String sqlName ="";
		String sqlUrl_tem ="";
		try {
			for (MultipartFile m : fileMultipartFiles) {
				sqlName = m.getOriginalFilename();
				// 拼出work sqlurl字段值
				sqlUrl_tem += file+ "/" + sqlName + ",";
				for (String s : nameList) {
					if (s.equals(sqlName)) {
						json.put("status", false);
						json.put("namesame", sqlName);
						return json;
					}
				}
				m.transferTo(new File(file + "/" + sqlName));
			}
			json.put("status", true);
			json.put("msg", sqlName);
			json.put("sqlUrl_tem", sqlUrl_tem);
			return json;
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			json.put("status", false);
			json.put("error", sqlName);
			return json;
		}
	}
	
}
