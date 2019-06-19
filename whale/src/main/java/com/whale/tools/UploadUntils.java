package com.whale.tools;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

public class UploadUntils {
	/**
	 * 
	 * @param workSql_D 
	 * @param workSql_D_TEM 
	 * @param workSql_L 
	 * @param fileMultipartFiles
	 * @return
	 */
	/**
	 * 
	 * @param workSql_D 本地路径只用于创建文件夹
	 * @param workSql_D_TEM 写入数据库的本地路径
	 * @param workSql_L 本地路径只用于创建浏览器开头路径
	 * @param workSql_L_TEM 写入数据库的浏览器路径
	 * @param fileMultipartFiles
	 * @return
	 */
	public static Map<String, Object> upload(String workSql_D, String workSql_D_TEM, String workSql_L,String workSql_L_TEM, MultipartFile[] fileMultipartFiles) {
		HashMap<String, Object> json = new HashMap<String, Object>();
		File file = new File(workSql_D);//file只用于创建文件，不能用于写入本地文件的路径
		if (!file.exists()) {
			file.mkdirs();
		}
		;
		// 查询文件夹下的所有文件名字
		String[] nameList = new File(file.toString()).list();
		String sqlName = "";//查询相同名字返回前端
		workSql_D_TEM = "";//初始化一下，传过来是null
		workSql_L_TEM = "";//初始化一下，传过来是null
		try {
			for (MultipartFile m : fileMultipartFiles) {
				sqlName = m.getOriginalFilename();
				for (String s : nameList) {
					if (s.equals(sqlName)) {
						json.put("status", false);
						json.put("namesame", sqlName);
						return json;
					}
				}
				// 拼出work sqlurl字段值存数据库不能用本地路径
				workSql_D_TEM += workSql_D + sqlName + ",";
				workSql_L_TEM += workSql_L + sqlName + ",";
				m.transferTo(new File(workSql_D + sqlName));
			}
			json.put("status", true);
			json.put("msg", sqlName);
			json.put("workSql_D_TEM", workSql_D_TEM);
			json.put("workSql_L_TEM", workSql_L_TEM);
			workSql_D_TEM = "";//恢复空，传过来是null
			workSql_L_TEM = "";//恢复空，传过来是null
			return json;
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			json.put("status", false);
			json.put("error", sqlName);
			return json;
		}
	}

}
