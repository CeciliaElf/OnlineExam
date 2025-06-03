package com.cecilia.programmer.service.admin.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cecilia.programmer.dao.admin.ExamPaperDao;
import com.cecilia.programmer.entity.admin.ExamPaper;
import com.cecilia.programmer.service.admin.ExamPaperService;

/**
 * @author cecilia
 * 试卷实现类
 */
@Service
public class ExamPaperServiceImpl implements ExamPaperService {
	@Autowired
	private ExamPaperDao examPaperDao;
	
	@Override
	public int delete(Long id) {
		// TODO Auto-generated method stub
		return examPaperDao.delete(id);
	}

	@Override
	public Integer getTotal(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return examPaperDao.getTotal(queryMap);
	}

	@Override
	public int add(ExamPaper examPaper) {
		// TODO Auto-generated method stub
		return examPaperDao.add(examPaper);
	}

	@Override
	public int edit(ExamPaper examPaper) {
		// TODO Auto-generated method stub
		return examPaperDao.edit(examPaper);
	}

	@Override
	public List<ExamPaper> findList(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return examPaperDao.findList(queryMap);
	}

	@Override
	public List<ExamPaper> findHistory(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return examPaperDao.findHistory(queryMap);
	}

	@Override
	public Integer getHistoryTotal(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return examPaperDao.getHistoryTotal(queryMap);
	}

	@Override
	public ExamPaper find(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return examPaperDao.find(queryMap);
	}

	@Override
	public int submitPaper(ExamPaper examPaper) {
		// TODO Auto-generated method stub
		return examPaperDao.submitPaper(examPaper);
	}
}
