package com.cecilia.programmer.service.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cecilia.programmer.entity.admin.ExamPaperAnswer;

/**
 * 试卷答案 Service 类
 */
@Service
public interface ExamPaperAnswerService {
	public int add(ExamPaperAnswer examPaperAnswer);
	public int edit(ExamPaperAnswer examPaperAnswer);
	public List<ExamPaperAnswer> findList(Map<String, Object> queryMap);
	public int delete(Long id);
	public Integer getTotal(Map<String, Object> queryMap);  // 总量
	public List<ExamPaperAnswer> findListByUser(Map<String, Object> queryMap);
	public int submitAnswer(ExamPaperAnswer examPaperAnswer);
}
