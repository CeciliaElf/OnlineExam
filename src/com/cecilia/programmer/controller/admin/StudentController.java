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

import com.cecilia.programmer.entity.admin.Student;
import com.cecilia.programmer.page.admin.Page;
import com.cecilia.programmer.service.admin.StudentService;
import com.cecilia.programmer.service.admin.SubjectService;

/**
 * 考生管理后台控制器
 * @author cecilia
 */
@RequestMapping("/admin/student")
@Controller
public class StudentController {
	@Autowired
	private StudentService studentService;
	@Autowired
	private SubjectService subjectService;
	/**
	 * 考生列表页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView List(ModelAndView model) {
		Map<String, Object> queryMap = new HashMap<String, Object>(); 
		queryMap.put("offset", 0);
		queryMap.put("pageSize", 99999);
		model.addObject("subjectList", subjectService.findList(queryMap)); // 将所有的学科讯息拿到展示到了页面里，在前端演示
		model.setViewName("student/list");
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
			@RequestParam(name = "name", defaultValue = "") String name, 
			@RequestParam(name = "subjectId", required = false) String subjectId,
			Page page) {
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("name", name);
		if (subjectId != null) {
			queryMap.put("subjectId", subjectId);
		}
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		ret.put("rows", studentService.findList(queryMap));
		ret.put("total", studentService.getTotal(queryMap));
		return ret;
	}
	
	/**
	 * 添加考生
	 * @param student
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(Student student) {
		Map<String, String> ret = new HashMap<String, String>();
		if (student == null) {
			ret.put("type", "error");
			ret.put("msg", "请填写正确的考生讯息");
			return ret;
		}
		if (StringUtils.isEmpty(student.getName())) {
			ret.put("type", "error");
			ret.put("msg", "请填写考生用户名");
			return ret;
		}
		if (StringUtils.isEmpty(student.getPassword())) {
			ret.put("type", "error");
			ret.put("msg", "请填写考生密码");
			return ret;
		}
		if (student.getSubjectId() == null) {
			ret.put("type", "error");
			ret.put("msg", "请填写考生所属专业");
			return ret;
		}
		student.setCreateTime(new Date());
		// 添加之前判断登录名是否存在
		if (isExistName(student.getName(), -1l)) {
			ret.put("type", "error");
			ret.put("msg", "该登录账号已经存在");
			return ret;
		}
		if (studentService.add(student) <= 0) {
			ret.put("type", "error");
			ret.put("msg", "添加失败，请联系管理员");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "添加成功");
		return ret;
	}
	
	/**
	 * 编辑考生
	 * @param student
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(Student student) {
		Map<String, String> ret = new HashMap<String, String>();
		if (student == null) {
			ret.put("type", "error");
			ret.put("msg", "请填写正确的考生讯息");
			return ret;
		}
		if (StringUtils.isEmpty(student.getName())) {
			ret.put("type", "error");
			ret.put("msg", "请填写考生用户名");
			return ret;
		}
		if (StringUtils.isEmpty(student.getPassword())) {
			ret.put("type", "error");
			ret.put("msg", "请填写考生密码");
			return ret;
		}
		if (student.getSubjectId() == null) {
			ret.put("type", "error");
			ret.put("msg", "请填写考生所属专业");
			return ret;
		}
		if (isExistName(student.getName(), student.getId())) {
			ret.put("type", "error");
			ret.put("msg", "该登录账号已经存在");
			return ret;
		}
		if (studentService.edit(student) <= 0) {
			ret.put("type", "error");
			ret.put("msg", "编辑失败，请联系管理员");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "编辑成功");
		return ret;
	}
	
	/**
	 * 删除考生
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
		 * 在删除的时候，用户隶属于某一个考生，当某个考生下由用户的时候，删除学科会报错（外键约束）
		 * 数据库有数据一致性，当发现有外键约束的表里有数据的时候，会提示不能删除这个数据（有关联的数据），所以要加上一个异常判断
		 */
		try {
			if (studentService.delete(id) <= 0) {
				ret.put("type", "error");
				ret.put("msg", "删除失败，请联系管理员");
				return ret;
			}
		} catch (Exception e) {
			// TODO: handle exception
			ret.put("type", "error");
			ret.put("msg", "该考生下存在考试讯息，不能删除");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "删除成功");
		return ret;
	}
	
	/**
	 * 判断用户名是否存在
	 * @param name
	 * @param id
	 * @return
	 */
	private Boolean isExistName(String name, Long id) {
		Student student = studentService.findByName(name);
		// 登录名为空
		if (student == null) {
			return false;
		}
		// 找到相同 id
		if (student.getId().longValue() == id.longValue()) {
			return false;
		}
		return true;
	}
}
