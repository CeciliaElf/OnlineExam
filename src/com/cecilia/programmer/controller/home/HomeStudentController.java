package com.cecilia.programmer.controller.home;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cecilia.programmer.entity.admin.Student;
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
	@RequestMapping(value = "/examing", method = RequestMethod.GET)
	public ModelAndView examing(ModelAndView model, HttpServletRequest request) {
		Student student =  (Student)request.getSession().getAttribute("student");
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("subjectId", student.getSubjectId());
		queryMap.put("startTime", DateFormatUtil.getDate("yyyy-MM-dd hh:mm:ss", new Date()));
		queryMap.put("endTime", DateFormatUtil.getDate("yyyy-MM-dd hh:mm:ss", new Date()));
		queryMap.put("offset", 0);
		queryMap.put("pagesize", 10); // 拿最新的十条考试
		model.addObject("student", student);
		model.setViewName("/home/user/password");
		return model;
	}
}
