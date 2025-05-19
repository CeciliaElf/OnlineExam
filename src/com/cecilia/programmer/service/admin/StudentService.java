package com.cecilia.programmer.service.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cecilia.programmer.entity.admin.Student;

/**
 * 考生 Service 类
 */
@Service
public interface StudentService {
	public int add(Student student);
	public int edit(Student student);
	public List<Student> findList(Map<String, Object> queryMap);
	public int delete(Long id);
	public Integer getTotal(Map<String, Object> queryMap);  // 总量
	public Student findByName(String name); // 防止有相同账号, 按照登录名查询
}
