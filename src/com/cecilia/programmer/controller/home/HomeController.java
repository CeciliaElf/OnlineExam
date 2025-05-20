package com.cecilia.programmer.controller.home;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cecilia.programmer.entity.admin.Student;
import com.cecilia.programmer.service.admin.StudentService;
import com.cecilia.programmer.service.admin.SubjectService;

/**
 * 前端主页控制器
 * @author cecilia
 */
@RequestMapping("/home")
@Controller
public class HomeController {
	@Autowired
	private SubjectService subjectService;
	
	@Autowired
	private StudentService studentService;
	/**
	 * 前端首页
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(ModelAndView model) {
		return model;
	}
	
	/**
	 * 前端用户登录
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView model) {
		model.addObject("title", "用户登录");
		model.setViewName("/home/login");
		return model;
	}
	
	/**
	 * 前端用户注册
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register(ModelAndView model) {
		model.addObject("title", "用户注册");
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("offset", 0);
		queryMap.put("page", 99999);
		model.addObject("subjectList", subjectService.findList(queryMap));
		model.setViewName("/home/register");
		return model;
	}
	
	/**
	 * 用户注册提交
	 * @param student
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> register(Student student) {
		Map<String, String> ret = new HashMap<String, String>();
		if (student == null) {
			ret.put("type", "error");
			ret.put("message", "请填写正确的考生讯息");
			return ret;
		}
		if (StringUtils.isEmpty(student.getName())) {
			ret.put("type", "error");
			ret.put("message", "请填写考生登录名");
			return ret;
		}
		if (StringUtils.isEmpty(student.getPassword())) {
			ret.put("type", "error");
			ret.put("message", "请填写考生密码");
			return ret;
		}
		if (student.getSubjectId() == null) {
			ret.put("type", "error");
			ret.put("message", "请选择所属学科专业");
			return ret;
		}
		// 查找用户名是否可用
		Student existStudent = studentService.findByName(student.getName());
		if (existStudent != null) {
			ret.put("type", "error");
			ret.put("message", "该用户名已存在");
			return ret;
		}
		student.setCreateTime(new Date());
		try {
			if (studentService.add(student) <= 0) {
				ret.put("type", "error");
				ret.put("message", "注册失败，请联系管理员");
				return ret;
			}
		} catch (Exception e) {
			// TODO: handle exception
			ret.put("type", "error");
			ret.put("message", "数据库连接异常");
			return ret;
		}
		ret.put("type", "success");
		ret.put("message", "注册成功");
		return ret;
	}
	
	/**
	 * 用户登录提交
	 * @param student
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	// 把登录信息保存到 session 中
	public Map<String, String> login(Student student, HttpServletRequest request) {
		Map<String, String> ret = new HashMap<String, String>();
		if (student == null) {
			ret.put("type", "error");
			ret.put("message", "请填写正确的考生讯息");
			return ret;
		}
		if (StringUtils.isEmpty(student.getName())) {
			ret.put("type", "error");
			ret.put("message", "请填写考生登录名");
			return ret;
		}
		if (StringUtils.isEmpty(student.getPassword())) {
			ret.put("type", "error");
			ret.put("message", "请填写考生密码");
			return ret;
		}
		// 查找用户名是否可用
		Student existStudent = studentService.findByName(student.getName());
		if (existStudent == null) {
			ret.put("type", "error");
			ret.put("message", "该用户名不存在");
			return ret;
		}
		if (!student.getPassword().equals(existStudent.getPassword())) {
			ret.put("type", "error");
			ret.put("message", "密码错误");
			return ret;
		}
		request.getSession().setAttribute("student", existStudent);
		ret.put("type", "success");
		ret.put("message", "登录成功");
		return ret;
	}
}
