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
	public static Map<String, Object> upload(String workSql_D,MultipartFile sqlFiles) {
		HashMap<String, Object> json = new HashMap<String, Object>();
		File file = new File(workSql_D);//file只用于创建文件，不能用于写入本地文件的路径
		if (!file.exists()) {
			file.mkdirs();
		};
		// 查询文件夹下的所有文件名字
		String[] nameList = new File(file.toString()).list();
		String sqlName ="";
		try {
			sqlName = sqlFiles.getOriginalFilename();
			for (String s : nameList) {
				if (s.equals(sqlName)) {
					json.put("status", false);
					json.put("namesame", sqlName);
					return json;
				}
			}
			//写入本地路径
			sqlFiles.transferTo(new File(workSql_D+ sqlName));
			json.put("status", true);
			json.put("msg", sqlName);
			return json;
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			json.put("status", false);
			json.put("error", sqlName);
			return json;
		}
	}

}
