package com.cecilia.programmer.controller.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cecilia.programmer.entity.admin.Question;
import com.cecilia.programmer.page.admin.Page;
import com.cecilia.programmer.service.admin.QuestionService;

/**
 * 试题管理后台控制器
 * @author cecilia
 */
@RequestMapping("/admin/question")
@Controller
public class QuestionController {
	@Autowired
	private QuestionService questionService;
	/**
	 * 试题列表页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView List(ModelAndView model) {
		model.setViewName("question/list");
		return model;
	}
	
	/**
	 * 模糊搜索分页显示列表查询
	 * @param title
	 * @param questionType
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody // 返回 Json 字符串
	public Map<String, Object> List(
			@RequestParam(name = "title", defaultValue = "") String title, 
			@RequestParam(name = "questionType", required = false) Integer questionType,
			Page page) {
		Map<String, Object> ret = new HashMap<String, Object>();
		// HashMap (ret) 来存放最终要返回给前端的数据。
		// 创建了一个 HashMap (queryMap) 来存放传递给服务层（questionService）的查询条件。
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("title", title);
		if (questionType != null) {
			queryMap.put("questionType", questionType);
		}
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		ret.put("rows", questionService.findList(queryMap));
		ret.put("total", questionService.getTotal(queryMap));
		return ret;
	}
	
	/**
	 * 添加试题
	 * @param question
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(Question question) {
		Map<String, String> ret = new HashMap<String, String>();
		if (question == null) {
			ret.put("type", "error");
			ret.put("msg", "请填写正确的试题讯息");
			return ret;
		}
		if (StringUtils.isEmpty(question.getTitle())) {
			ret.put("type", "error");
			ret.put("msg", "请填写试题题目");
			return ret;
		}
		if (StringUtils.isEmpty(question.getAnswer())) {
			ret.put("type", "error");
			ret.put("msg", "请填写试题正确答案");
			return ret;
		}
		if (StringUtils.isEmpty(question.getAttrA())) {
			ret.put("type", "error");
			ret.put("msg", "请填写试题选项A");
			return ret;
		}
		if (StringUtils.isEmpty(question.getAttrB())) {
			ret.put("type", "error");
			ret.put("msg", "请填写试题选项B");
			return ret;
		}
		question.setScoreByType();
		question.setCreateTime(new Date());
		if (questionService.add(question) <= 0) {
			ret.put("type", "error");
			ret.put("msg", "添加失败，请联系管理员");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "添加成功");
		return ret;
	}
	
	/**
	 * 编辑试题
	 * @param question
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(Question question) {
		Map<String, String> ret = new HashMap<String, String>();
		if (question == null) {
			ret.put("type", "error");
			ret.put("msg", "请填写正确的试题讯息");
			return ret;
		}
		if (StringUtils.isEmpty(question.getTitle())) {
			ret.put("type", "error");
			ret.put("msg", "请填写试题题目");
			return ret;
		}
		if (StringUtils.isEmpty(question.getAnswer())) {
			ret.put("type", "error");
			ret.put("msg", "请填写试题正确答案");
			return ret;
		}
		if (StringUtils.isEmpty(question.getAttrA())) {
			ret.put("type", "error");
			ret.put("msg", "请填写试题选项A");
			return ret;
		}
		if (StringUtils.isEmpty(question.getAttrB())) {
			ret.put("type", "error");
			ret.put("msg", "请填写试题选项B");
			return ret;
		}
		question.setScoreByType();
		if (questionService.edit(question) <= 0) {
			ret.put("type", "error");
			ret.put("msg", "编辑失败，请联系管理员");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "编辑成功");
		return ret;
	}
	
	/**
	 * 删除试题
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> delete(Long id) {
		Map<String, String> ret = new HashMap<String, String>();
		if (id == null) {
			ret.put("type", "error");
			ret.put("msg", "请选择要删除的数据");
			return ret;
		}
		/**
		 * 特殊注意点
		 * 在删除的时候，用户隶属于某一个试题，当某个试题下由用户的时候，删除学科会报错（外键约束）
		 * 数据库有数据一致性，当发现有外键约束的表里有数据的时候，会提示不能删除这个数据（有关联的数据），所以要加上一个异常判断
		 */
		try {
			if (questionService.delete(id) <= 0) {
				ret.put("type", "error");
				ret.put("msg", "删除失败，请联系管理员");
				return ret;
			}
		} catch (Exception e) {
			// TODO: handle exception
			ret.put("type", "error");
			ret.put("msg", "该试题下存在考试讯息，不能删除");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "删除成功");
		return ret;
	}
}
