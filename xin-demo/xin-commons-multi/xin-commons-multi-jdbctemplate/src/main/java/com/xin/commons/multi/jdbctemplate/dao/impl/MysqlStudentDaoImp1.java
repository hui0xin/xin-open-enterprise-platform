package com.xin.commons.multi.jdbctemplate.dao.impl;

import com.xin.commons.multi.jdbctemplate.dao.MysqlStudentDao1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class MysqlStudentDaoImp1 implements MysqlStudentDao1 {
	
	@Autowired
	@Qualifier("mysqlJdbcTemplate1")
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Map<String, Object>> getAllStudents() {

		return this.jdbcTemplate.queryForList("select * from student");
	}

}
