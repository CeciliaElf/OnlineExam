package com.cecilia.programmer.dao.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cecilia.programmer.entity.admin.Question;

/**
 * 试题 Dao 层
 * @author cecilia
 */
@Repository
public interface QuestionDao {
	public int add(Question question);
	public int edit(Question question);
	public List<Question> findList(Map<String, Object> queryMap);
	public int delete(Long id);
	public Integer getTotal(Map<String, Object> queryMap);  // 总量
	public Question findByTitle(String title);
	public int getQuestionNumByType(int questionType);
	public Question findById(Long id);
}
