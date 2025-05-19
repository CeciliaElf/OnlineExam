package com.cecilia.programmer.dao.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cecilia.programmer.entity.admin.ExamPaperAnswer;

/**
 * 试卷 Dao 层
 * @author cecilia
 */
@Repository
public interface ExamPaperAnswerDao {
	public int add(ExamPaperAnswer examPaperAnswer);
	public int edit(ExamPaperAnswer examPaperAnswer);
	public List<ExamPaperAnswer> findList(Map<String, Object> queryMap);
	public int delete(Long id);
	public Integer getTotal(Map<String, Object> queryMap);  // 总量
}
