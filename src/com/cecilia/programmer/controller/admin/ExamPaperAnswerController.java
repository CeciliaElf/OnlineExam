package com.cecilia.programmer.controller.admin;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cecilia.programmer.page.admin.Page;
import com.cecilia.programmer.service.admin.ExamPaperAnswerService;
import com.cecilia.programmer.service.admin.ExamService;
import com.cecilia.programmer.service.admin.QuestionService;
import com.cecilia.programmer.service.admin.StudentService;

/**
 * 试卷答题管理后台控制器
 * @author cecilia
 */
@RequestMapping("/admin/examPaperAnswer")
@Controller
public class ExamPaperAnswerController {
	@Autowired
	private ExamPaperAnswerService examPaperAnswerService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private ExamService examService;
	@Autowired
	private QuestionService questionService;
	
	/**
	 * 试卷答题列表页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView List(ModelAndView model) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("offset", 0);
		queryMap.put("page", 99999);
		model.addObject("examList", examService.findList(queryMap));
		model.addObject("studentList", studentService.findList(queryMap));
		model.addObject("questionList", questionService.findList(queryMap));
		model.setViewName("examPaperAnswer/list");
		return model;
	}
	
	/**
	 * 模糊搜索分页显示列表查询
	 * @param name
	 * @param subjectId
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody // 返回 Json 字符串
	public Map<String, Object> List(
			@RequestParam(name = "examId", required = false) Long examId,
			@RequestParam(name = "studentId", required = false) Long studentId,
			@RequestParam(name = "questionId", required = false) Integer questionId,
			Page page) {
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		if (examId != null) {
			queryMap.put("examId", examId);
		}
		if (studentId != null) {
			queryMap.put("studentId", studentId);
		}
		if (questionId != null) {
			queryMap.put("questionId", questionId);
		}
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		ret.put("rows", examPaperAnswerService.findList(queryMap));
		ret.put("total", examPaperAnswerService.getTotal(queryMap));
		return ret;
	}
	
}
