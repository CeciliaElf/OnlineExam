package com.cecilia.programmer.controller.home;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
}
