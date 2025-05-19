package com.cecilia.programmer.service.admin.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cecilia.programmer.dao.admin.ExamPaperAnswerDao;
import com.cecilia.programmer.entity.admin.ExamPaperAnswer;
import com.cecilia.programmer.service.admin.ExamPaperAnswerService;

/**
 * @author cecilia
 * 试卷答题实现类
 */
@Service
public class ExamPaperAnswerServiceImpl implements ExamPaperAnswerService {
	@Autowired
	private ExamPaperAnswerDao examPaperAnswerDao;

	@Override
	public int add(ExamPaperAnswer examPaperAnswer) {
		// TODO Auto-generated method stub
		return examPaperAnswerDao.add(examPaperAnswer);
	}

	@Override
	public int edit(ExamPaperAnswer examPaperAnswer) {
		// TODO Auto-generated method stub
		return examPaperAnswerDao.edit(examPaperAnswer);
	}

	@Override
	public List<ExamPaperAnswer> findList(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return examPaperAnswerDao.findList(queryMap);
	}

	@Override
	public int delete(Long id) {
		// TODO Auto-generated method stub
		return examPaperAnswerDao.delete(id);
	}

	@Override
	public Integer getTotal(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return examPaperAnswerDao.getTotal(queryMap);
	}
}
