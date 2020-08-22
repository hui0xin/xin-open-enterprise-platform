package com.xin.commons.multi.jdbctemplate.service.impl;

import java.util.List;
import java.util.Map;
import com.xin.commons.multi.jdbctemplate.dao.MysqlStudentDao1;
import com.xin.commons.multi.jdbctemplate.dao.MysqlStudentDao2;
import com.xin.commons.multi.jdbctemplate.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("studentService")
public class StudentServiceImp implements StudentService {

	@Autowired
	private MysqlStudentDao2 mysqlStudentDao2;
	@Autowired
	private MysqlStudentDao1 mysqlStudentDao1;
	
	@Override
	public List<Map<String, Object>> getAllStudentsFrom1() {

		return this.mysqlStudentDao1.getAllStudents();
	}

	@Override
	public List<Map<String, Object>> getAllStudentsFrom2() {

		return this.mysqlStudentDao2.getAllStudents();
	}

}
