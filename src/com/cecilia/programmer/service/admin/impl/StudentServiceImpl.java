package com.cecilia.programmer.service.admin.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cecilia.programmer.dao.admin.StudentDao;
import com.cecilia.programmer.entity.admin.Student;
import com.cecilia.programmer.service.admin.StudentService;

/**
 * @author cecilia
 * 学科专业实现类
 */
@Service
public class StudentServiceImpl implements StudentService {
	@Autowired
	private StudentDao studentDao;

	@Override
	public int add(Student student) {
		// TODO Auto-generated method stub
		return studentDao.add(student);
	}

	@Override
	public int edit(Student student) {
		// TODO Auto-generated method stub
		return studentDao.edit(student);
	}

	@Override
	public List<Student> findList(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return studentDao.findList(queryMap);
	}

	@Override
	public int delete(Long id) {
		// TODO Auto-generated method stub
		return studentDao.delete(id);
	}

	@Override
	public Integer getTotal(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return studentDao.getTotal(queryMap);
	}

	@Override
	public Student findByName(String name) {
		// TODO Auto-generated method stub
		return studentDao.findByName(name);
	}
	
}
