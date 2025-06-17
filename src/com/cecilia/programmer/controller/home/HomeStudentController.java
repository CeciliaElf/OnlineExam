package com.cecilia.programmer.controller.home;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cecilia.programmer.entity.admin.Exam;
import com.cecilia.programmer.entity.admin.ExamPaper;
import com.cecilia.programmer.entity.admin.ExamPaperAnswer;
import com.cecilia.programmer.entity.admin.Question;
import com.cecilia.programmer.entity.admin.Student;
import com.cecilia.programmer.service.admin.ExamPaperAnswerService;
import com.cecilia.programmer.service.admin.ExamPaperService;
import com.cecilia.programmer.service.admin.ExamService;
import com.cecilia.programmer.service.admin.StudentService;
import com.cecilia.programmer.service.admin.SubjectService;
import com.cecilia.programmer.util.DateFormatUtil;

/**
 * 前端考生中心控制器
 * @author cecilia
 */
@RequestMapping("/home/user")
@Controller
public class HomeStudentController {
	@Autowired
	private StudentService studentService;
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private ExamService examService;
	@Autowired
	private ExamPaperService examPaperService;
	@Autowired
	private ExamPaperAnswerService examPaperAnswerService;
	private int pageSize = 10;
	
	/**
	 * 考生中心首页
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(ModelAndView model) {
		model.addObject("title", "考生中心");
		model.setViewName("/home/user/index");
		return model;
	}
	

	 /**
	  * 考生中心欢迎界面
	  * @param model
	  * @param request
	  * @return
	  */
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public ModelAndView welcome(ModelAndView model, HttpServletRequest request) {
		model.addObject("title", "考生中心");
		Student student =  (Student)request.getSession().getAttribute("student");
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("subjectId", student.getSubjectId());
		queryMap.put("startTime", DateFormatUtil.getDate("yyyy-MM-dd hh:mm:ss", new Date()));
		queryMap.put("endTime", DateFormatUtil.getDate("yyyy-MM-dd hh:mm:ss", new Date()));
		queryMap.put("offset", 0);
		queryMap.put("pagesize", 10); // 拿最新的十条考试
		model.addObject("examList", examService.findListByUser(queryMap));
		queryMap.remove("subjectId");
		queryMap.put("studentId", student.getId());
		model.addObject("historyList", examPaperService.findHistory(queryMap));
		model.addObject("subject", subjectService.findById(student.getSubjectId()));
		model.setViewName("/home/user/welcome");
		return model;
	}
	
	/**
	 * 获取当前登录用户的用户名和真实姓名
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/get_current", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> get_current(HttpServletRequest request) {
		Map<String, String> ret = new HashMap<String, String>();
		Object attribute =  request.getSession().getAttribute("student");
		if (attribute == null) {
			ret.put("type", "error");
			ret.put("message", "登录信息失效");
			return ret;
		}
		ret.put("type", "success");
		ret.put("message", "获取成功");
		Student student = (Student)attribute;
		ret.put("username", student.getName());
		ret.put("trueName", student.getTrueName());
		return ret;
	}
	
	/**
	 * 用户基本信息界面
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public ModelAndView profile(ModelAndView model, HttpServletRequest request) {
		Student student =  (Student)request.getSession().getAttribute("student");
		model.addObject("title", "考生信息");
		model.addObject("student", student);
		model.addObject("subject", subjectService.findById(student.getSubjectId()));
		model.setViewName("/home/user/profile");
		return model;
	}
	
	/**
	 * 修改用户信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/update_info", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> update_info(Student student ,HttpServletRequest request) {
		Map<String, String> ret = new HashMap<String, String>();
		Student onlineStudent =  (Student)request.getSession().getAttribute("student");
		onlineStudent.setTel(student.getTel());
		onlineStudent.setTrueName(student.getTrueName());
		if (studentService.edit(onlineStudent) <= 0) {
			ret.put("type", "error");
			ret.put("message", "修改失败，请联系管理员");
			return ret;
		}
		// 修改成功 重置 session 中的用户信息
		request.getSession().setAttribute("student", onlineStudent);
		ret.put("type", "success");
		ret.put("message", "获取成功");
		return ret;
	}
	
	/**
	 * 用户退出登录
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		request.getSession().setAttribute("student", null);
		return "redirect:login";
	}
	
	/**
	 * 用户修改密码
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/password", method = RequestMethod.GET)
	public ModelAndView password(ModelAndView model, HttpServletRequest request) {
		Student student =  (Student)request.getSession().getAttribute("student");
		model.addObject("student", student);
		model.setViewName("/home/user/password");
		return model;
	}
	
	/**
	 * 修改密码提交
	 * @param student
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/update_password", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> update_password(Student student , String oldPassword, HttpServletRequest request) {
		Map<String, String> ret = new HashMap<String, String>();
		Student onlineStudent =  (Student)request.getSession().getAttribute("student");
		if (!onlineStudent.getPassword().equals(oldPassword)) {
			ret.put("type", "error");
			ret.put("message", "旧密码错误");
			return ret;
		}
		onlineStudent.setPassword(student.getPassword());
		if (studentService.edit(onlineStudent) <= 0) {
			ret.put("type", "error");
			ret.put("message", "修改失败，请联系管理员");
			return ret;
		}
		// 修改成功 重置 session 中的用户信息
		request.getSession().setAttribute("student", onlineStudent);
		ret.put("type", "success");
		ret.put("message", "修改成功");
		return ret;
	}
	
	/**
	 * 获取当前学生正在进行的考试信息
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/exam_list", method = RequestMethod.GET)
	public ModelAndView examList(ModelAndView model,
			@RequestParam(name = "name",defaultValue = "") String name,
			@RequestParam(name = "page",defaultValue = "1") Integer page,
			HttpServletRequest request) {
		Student student =  (Student)request.getSession().getAttribute("student");
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("subjectId", student.getSubjectId());
		queryMap.put("name", name);
		queryMap.put("offset", getOffset(page, pageSize));
		queryMap.put("pageSize", pageSize); // 拿最新的十条考试
		model.addObject("examList", examService.findListByUser(queryMap));
		model.addObject("name", name);
		model.addObject("subject", subjectService.findById(student.getSubjectId()));
		model.addObject("nowTime", System.currentTimeMillis());
		model.setViewName("/home/user/exam_list");
		if (page < 1) {
			page = 1;
		}
		model.addObject("page", page);
		return model;
	}
	
	/**
	 * 当前学生考过的考试列表
	 * @param model
	 * @param name
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/history_list", method = RequestMethod.GET)
	public ModelAndView historyList(ModelAndView model,
			@RequestParam(name = "name",defaultValue = "") String name,
			@RequestParam(name = "page",defaultValue = "1") Integer page,
			HttpServletRequest request) {
		Student student =  (Student)request.getSession().getAttribute("student");
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("name", name);
		queryMap.put("studentId", student.getId());
		queryMap.put("offset", getOffset(page, pageSize));
		queryMap.put("pageSize", pageSize); // 拿最新的十条考试
		model.addObject("historyList", examPaperService.findHistory(queryMap));
		model.addObject("name", name);
		model.addObject("subject", subjectService.findById(student.getSubjectId()));
		model.setViewName("/home/user/history_list");
		if (page < 1) {
			page = 1;
		}
		model.addObject("page", page);
		return model;
	}
	
	/**
	 * 
	 * @param model
	 * @param examId
	 * @param examPaperId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/review_exam", method = RequestMethod.GET)
	public ModelAndView index(ModelAndView model, Long examId, Long examPaperId, HttpServletRequest request) {
		Student student = (Student)request.getSession().getAttribute("student");
		Exam exam = examService.findById(examId);
		Map<String, Object> queryMap = new HashMap<String, Object>();
		if (exam == null) {
			model.setViewName("/home/exam/error");
			model.addObject("message", "当前考试不存在");
			return model;
		}
		queryMap.put("examId", examId);
		queryMap.put("studentId", student.getId());
		// 根据考试信息和学生信息获取试卷
		ExamPaper examPaper = examPaperService.find(queryMap);
		if (examPaper == null) {
			model.setViewName("/home/exam/error");
			model.addObject("message", "当前考试不存在试卷");
			return model;
		}
		if (examPaper.getStatus() == 0) {
			model.setViewName("/home/exam/error");
			model.addObject("message", "你还没有考过这门考试");
			return model;
		}
		queryMap.put("examPaperId", examPaper.getId());
		List<ExamPaperAnswer> findListByUser = examPaperAnswerService.findListByUser(queryMap);
		model.addObject("title", exam.getName() + "回顾试卷");
		model.addObject("singleQuestionList", getExamPaperAnswerList(findListByUser, Question.QUESTION_TYPE_SINGLE));
		model.addObject("mutiQuestionList", getExamPaperAnswerList(findListByUser, Question.QUESTION_TYPE_MUTI));
		model.addObject("chargeQuestionList", getExamPaperAnswerList(findListByUser, Question.QUESTION_TYPE_CHARGE));
		model.addObject("exam", exam);
		model.addObject("examPaper", examPaper);
		model.addObject("singleScore", Question.QUESTION_TYPE_SINGLE_SCORE);
		model.addObject("mutiScore", Question.QUESTION_TYPE_MUTI_SCORE);
		model.addObject("chargeScore", Question.QUESTION_TYPE_CHARGE_SCORE);
		model.addObject("singleQuestion", Question.QUESTION_TYPE_SINGLE);
		model.addObject("mutiQuestion", Question.QUESTION_TYPE_MUTI);
		model.addObject("chargeQuestion", Question.QUESTION_TYPE_CHARGE);
		model.setViewName("home/user/review_exam");
		return model;
	}
	
	/**
	 * 返回指定类型的试题
	 * @param examPaperAnswers
	 * @param questionType
	 * @return
	 */
	private List<ExamPaperAnswer> getExamPaperAnswerList(List<ExamPaperAnswer> examPaperAnswers, int questionType) {
		List<ExamPaperAnswer> newExamAnswers = new ArrayList<ExamPaperAnswer>();
		 for (ExamPaperAnswer examPaperAnswer: examPaperAnswers) {
			 if (examPaperAnswer.getQuestion().getQuestionType() == questionType) {
				 newExamAnswers.add(examPaperAnswer);
			 }
		 }
		 return newExamAnswers;
	}
	
	/**
	 * 计算数据库查询数据游标
	 * @param page
	 * @param pageSize
	 * @return
	 */
	private int getOffset(int page, int pageSize) {
		if (page < 1) {
			page = 1;
		}
		return (page - 1) * pageSize;
	}
}
