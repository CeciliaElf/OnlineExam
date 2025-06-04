package com.cecilia.programmer.service.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cecilia.programmer.entity.admin.Question;

/**
 * 试题 Service 类
 */
@Service
public interface QuestionService {
	public int add(Question question);
	public int edit(Question question);
	public List<Question> findList(Map<String, Object> queryMap);
	public int delete(Long id);
	public Integer getTotal(Map<String, Object> queryMap);  // 总量
	public Question findByTitle(String title);
	public int getQuestionNumByType(Map<String, Long> queryMap); // 根据试题类型和科目获取试题数量
	public Question findById(Long id);
}
