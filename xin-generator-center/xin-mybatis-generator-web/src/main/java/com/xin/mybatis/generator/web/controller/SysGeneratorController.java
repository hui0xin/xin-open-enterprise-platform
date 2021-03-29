package com.xin.mybatis.generator.web.controller;

import com.xin.commons.support.response.ResponseResult;
import com.xin.mybatis.generator.web.service.SysGeneratorService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成
 */
@Controller
@RequestMapping("/generator")
public class SysGeneratorController {

	@Autowired
	private SysGeneratorService sysGeneratorService;
	
	/**
	 * 获取table列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public ResponseResult list(@RequestParam Map<String, Object> params){
		//查询列表数据
		List<Map<String, Object>> list = sysGeneratorService.queryList(params);
		return ResponseResult.success(list);
	}
	
	/**
	 * 生成代码
	 */
	@RequestMapping("/code")
	public void code(HttpServletResponse response) throws IOException{
		Map<String, Object> params = new HashMap<>();
		List<Map<String, Object>> list = sysGeneratorService.queryList(params);
		List tableNames = new ArrayList() ;
		for(Map<String, Object> map:list){
			tableNames.add(map.get("tableName"));
		}
		byte[] data = sysGeneratorService.generatorCode(tableNames);
		response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"code.zip\"");
        response.addHeader("Content-Length", "" + data.length);  
        response.setContentType("application/octet-stream; charset=UTF-8");  
        IOUtils.write(data, response.getOutputStream());
	}
}
