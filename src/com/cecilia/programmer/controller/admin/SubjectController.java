package com.cecilia.programmer.controller.admin;

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

import com.cecilia.programmer.entity.admin.Subject;
import com.cecilia.programmer.page.admin.Page;
import com.cecilia.programmer.service.admin.SubjectService;

/**
 * 学科专业管理后台控制器
 * @author cecilia
 */
@RequestMapping("/admin/subject")
@Controller
public class SubjectController {
	@Autowired
	private SubjectService subjectService;
	/**
	 * 学科专业列表页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView List(ModelAndView model) {
		model.setViewName("subject/list");
		return model;
	}
	
	/**
	 * 模糊搜索分页显示列表查询
	 * @param name
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody // 返回 Json 字符串
	public Map<String, Object> List(
			@RequestParam(name = "name", defaultValue = "") String name, Page page) {
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("name", name);
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		ret.put("rows", subjectService.findList(queryMap));
		ret.put("total", subjectService.getTotal(queryMap));
		return ret;
	}
	
	/**
	 * 添加学科专业
	 * @param subject
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(Subject subject) {
		Map<String, String> ret = new HashMap<String, String>();
		if (subject == null) {
			ret.put("type", "error");
			ret.put("msg", "请填写正确的学科讯息");
			return ret;
		}
		if (StringUtils.isEmpty(subject.getName())) {
			ret.put("type", "error");
			ret.put("msg", "请填写学科名称");
			return ret;
		}
		if (subjectService.add(subject) <= 0) {
			ret.put("type", "error");
			ret.put("msg", "添加失败，请联系管理员");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "添加成功");
		return ret;
	}
	
	/**
	 * 编辑学科专业
	 * @param subject
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(Subject subject) {
		Map<String, String> ret = new HashMap<String, String>();
		if (subject == null) {
			ret.put("type", "error");
			ret.put("msg", "请填写正确的学科讯息");
			return ret;
		}
		if (StringUtils.isEmpty(subject.getName())) {
			ret.put("type", "error");
			ret.put("msg", "请填写学科名称");
			return ret;
		}
		if (subjectService.edit(subject) <= 0) {
			ret.put("type", "error");
			ret.put("msg", "编辑失败，请联系管理员");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "编辑成功");
		return ret;
	}
	
	/**
	 * 删除学科专业
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
		 * 在删除的时候，用户隶属于某一个学科专业，当某个学科专业下由用户的时候，删除学科会报错（外键约束）
		 * 数据库有数据一致性，当发现有外键约束的表里有数据的时候，会提示不能删除这个数据（有关联的数据），所以要加上一个异常判断
		 */
		try {
			if (subjectService.delete(id) <= 0) {
				ret.put("type", "error");
				ret.put("msg", "删除失败，请联系管理员");
				return ret;
			}
		} catch (Exception e) {
			// TODO: handle exception
			ret.put("type", "error");
			ret.put("msg", "该学科下存在考生讯息，不能删除");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "删除成功");
		return ret;
	}
}
