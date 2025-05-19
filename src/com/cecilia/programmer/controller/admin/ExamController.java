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

import com.cecilia.programmer.entity.admin.Exam;
import com.cecilia.programmer.entity.admin.Question;
import com.cecilia.programmer.page.admin.Page;
import com.cecilia.programmer.service.admin.ExamService;
import com.cecilia.programmer.service.admin.QuestionService;
import com.cecilia.programmer.service.admin.SubjectService;

/**
 * 考试管理后台控制器
 * @author cecilia
 */
@RequestMapping("/admin/exam")
@Controller
public class ExamController {
	@Autowired
	private ExamService examService;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private SubjectService subjectService;
	
	/**
	 * 考试列表页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView List(ModelAndView model) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("offset", 0);
		queryMap.put("page", 99999);
		model.addObject("subjectList", subjectService.findList(queryMap));
		model.addObject("singleScore", Question.QUESTION_TYPE_SINGLE_SCORE);
		model.addObject("mutiScore", Question.QUESTION_TYPE_MUTI_SCORE);
		model.addObject("chargeScore", Question.QUESTION_TYPE_CHARGE_SCORE);
		model.setViewName("exam/list");
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
			@RequestParam(name = "name", defaultValue = "") String name, 
			@RequestParam(name = "subjectId", required = false) Long subjectId,
			@RequestParam(name = "startTime", required = false) String startTime,
			@RequestParam(name = "endTime", required = false) String endTime,
			Page page) {
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("name", name);
		if (subjectId != null) {
			queryMap.put("subjectId", subjectId);
		}
		if (!StringUtils.isEmpty(startTime)) {
			queryMap.put("startTime", startTime);
		}
		if (!StringUtils.isEmpty(endTime)) {
			queryMap.put("endTime", endTime);
		}
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		ret.put("rows", examService.findList(queryMap));
		ret.put("total", examService.getTotal(queryMap));
		return ret;
	}
	
	/**
	 * 添加考试
	 * @param exam
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(Exam exam) {
		Map<String, String> ret = new HashMap<String, String>();
		int questionNum = exam.getSingleQuestionNum() + exam.getMutiQuestionNum() + exam.getChargeQuestionNum();
		int totalScore = exam.getSingleQuestionNum() * Question.QUESTION_TYPE_SINGLE_SCORE +
					     exam.getMutiQuestionNum() * Question.QUESTION_TYPE_MUTI_SCORE +
					     exam.getChargeQuestionNum() * Question.QUESTION_TYPE_CHARGE_SCORE;
		if (exam == null) {
			ret.put("type", "error");
			ret.put("msg", "请填写正确的考试讯息");
			return ret;
		}
		if (StringUtils.isEmpty(exam.getName())) {
			ret.put("type", "error");
			ret.put("msg", "请填写考试名称");
			return ret;
		}
		if (exam.getSubjectId() == null) {
			ret.put("type", "error");
			ret.put("msg", "请选择所属科目");
			return ret;
		}
		if (exam.getStartTime() == null) {
			ret.put("type", "error");
			ret.put("msg", "请填写考试开始时间");
			return ret;
		}
		if (exam.getEndTime() == null) {
			ret.put("type", "error");
			ret.put("msg", "请填写考试结束时间");
			return ret;
		}
		if (exam.getPassScore() == 0) {
			ret.put("type", "error");
			ret.put("msg", "请填写考试及格分数");
			return ret;
		}
		if (exam.getSingleQuestionNum() == 0 && exam.getMutiQuestionNum() == 0 && exam.getChargeQuestionNum() == 0) {
			ret.put("type", "error");
			ret.put("msg", "单选题，多选题，判断题至少有一种题目");
			return ret;
		}
		exam.setCreateTime(new Date());
		// 算试题总数和总分，此时去查询所填写的题型数量是够满足
		// 获取单选题
		int singleQuestionTotalNum = questionService.getQuestionNumByType(Question.QUESTION_TYPE_SINGLE);
		if (exam.getSingleQuestionNum() > singleQuestionTotalNum) {
			ret.put("type", "error");
			ret.put("msg", "单选题数量超过题库总数，请修改");
			return ret;
		}
		// 获取多选题
		int mutiQuestionTotalNum = questionService.getQuestionNumByType(Question.QUESTION_TYPE_MUTI);
		if (exam.getMutiQuestionNum() > mutiQuestionTotalNum) {
			ret.put("type", "error");
			ret.put("msg", "多选题数量超过题库总数，请修改");
			return ret;
		}
		// 获取判断题
		int chargeQuestionTotalNum = questionService.getQuestionNumByType(Question.QUESTION_TYPE_CHARGE);
		if (exam.getChargeQuestionNum() > chargeQuestionTotalNum) {
			ret.put("type", "error");
			ret.put("msg", "判断题数量超过题库总数，请修改");
			return ret;
		}
		exam.setQuestionNum(questionNum);
		exam.setTotalScore(totalScore);
		if (examService.add(exam) <= 0) {
			ret.put("type", "error");
			ret.put("msg", "添加失败，请联系管理员");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "添加成功");
		return ret;
	}
	
	/**
	 * 编辑考试
	 * @param exam
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(Exam exam) {
		Map<String, String> ret = new HashMap<String, String>();
		int questionNum = exam.getSingleQuestionNum() + exam.getMutiQuestionNum() + exam.getChargeQuestionNum();
		int totalScore = exam.getSingleQuestionNum() * Question.QUESTION_TYPE_SINGLE_SCORE +
					     exam.getMutiQuestionNum() * Question.QUESTION_TYPE_MUTI_SCORE +
					     exam.getChargeQuestionNum() * Question.QUESTION_TYPE_CHARGE_SCORE;
		if (exam == null) {
			ret.put("type", "error");
			ret.put("msg", "请选择正确的考试讯息进行编辑");
			return ret;
		}
		if (StringUtils.isEmpty(exam.getName())) {
			ret.put("type", "error");
			ret.put("msg", "请填写考试名称");
			return ret;
		}
		if (exam.getSubjectId() == null) {
			ret.put("type", "error");
			ret.put("msg", "请选择所属科目");
			return ret;
		}
		if (exam.getStartTime() == null) {
			ret.put("type", "error");
			ret.put("msg", "请填写考试开始时间");
			return ret;
		}
		if (exam.getEndTime() == null) {
			ret.put("type", "error");
			ret.put("msg", "请填写考试结束时间");
			return ret;
		}
		if (exam.getPassScore() == 0) {
			ret.put("type", "error");
			ret.put("msg", "请填写考试及格分数");
			return ret;
		}
		if (exam.getSingleQuestionNum() == 0 && exam.getMutiQuestionNum() == 0 && exam.getChargeQuestionNum() == 0) {
			ret.put("type", "error");
			ret.put("msg", "单选题，多选题，判断题至少有一种题目");
			return ret;
		}
		// 算试题总数和总分，此时去查询所填写的题型数量是够满足
		// 获取单选题
		int singleQuestionTotalNum = questionService.getQuestionNumByType(Question.QUESTION_TYPE_SINGLE);
		if (exam.getSingleQuestionNum() > singleQuestionTotalNum) {
			ret.put("type", "error");
			ret.put("msg", "单选题数量超过题库总数，请修改");
			return ret;
		}
		// 获取多选题
		int mutiQuestionTotalNum = questionService.getQuestionNumByType(Question.QUESTION_TYPE_MUTI);
		if (exam.getMutiQuestionNum() > mutiQuestionTotalNum) {
			ret.put("type", "error");
			ret.put("msg", "多选题数量超过题库总数，请修改");
			return ret;
		}
		// 获取判断题
		int chargeQuestionTotalNum = questionService.getQuestionNumByType(Question.QUESTION_TYPE_CHARGE);
		if (exam.getChargeQuestionNum() > chargeQuestionTotalNum) {
			ret.put("type", "error");
			ret.put("msg", "判断题数量超过题库总数，请修改");
			return ret;
		}
		exam.setQuestionNum(questionNum);
		exam.setTotalScore(totalScore);
		if (examService.edit(exam) <= 0) {
			ret.put("type", "error");
			ret.put("msg", "编辑失败，请联系管理员");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "编辑成功");
		return ret;
	}
	
	/**
	 * 删除考试
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
		 * 在删除的时候，用户隶属于某一个考试，当某个考试下由用户的时候，删除学科会报错（外键约束）
		 * 数据库有数据一致性，当发现有外键约束的表里有数据的时候，会提示不能删除这个数据（有关联的数据），所以要加上一个异常判断
		 */
		try {
			if (examService.delete(id) <= 0) {
				ret.put("type", "error");
				ret.put("msg", "删除失败，请联系管理员");
				return ret;
			}
		} catch (Exception e) {
			// TODO: handle exception
			ret.put("type", "error");
			ret.put("msg", "该考试下存在试卷或考试记录讯息，不能删除");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "删除成功");
		return ret;
	}
}
