package com.cecilia.programmer.service.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cecilia.programmer.entity.admin.Subject;

/**
 * 学科专业 Service 类
 */
@Service
public interface SubjectService {
	public int add(Subject subject);
	public int edit(Subject subject);
	public List<Subject> findList(Map<String, Object> queryMap); // 根据传入的查询条件，从数据库中查询（检索）符合条件的学科专业（Subject）列表，并返回这些 Subject 对象组成的列表。
	public int delete(Long id);
	public Integer getTotal(Map<String, Object> queryMap);  // 总量
	public Subject findById(Long id);
}
