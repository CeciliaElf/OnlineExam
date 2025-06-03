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
import com.cecilia.programmer.service.admin.QuestionService;

/**
 * 前台用户考试控制器
 */
@RequestMapping("/home/exam")
@Controller
public class HomeExamController {
	@Autowired
	private ExamService examService;
	@Autowired
	private ExamPaperService examPaperService;
	@Autowired
	private ExamPaperAnswerService examPaperAnswerService;
	@Autowired
	private QuestionService questionService;
	private List<ExamPaper> list;
	/**
	 * 开始考试前检查合法性，随机生成试题
	 * @param examId
	 * @return
	 */
	@RequestMapping(value = "/start_exam", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> startExam(Long examId, HttpServletRequest request) {
		Map<String, String> ret = new HashMap<String, String>();
		Exam exam = examService.findById(examId);
		if (exam == null) {
			ret.put("type", "error");
			ret.put("message", "考试信息不存在");
			return ret;
		}
		if (exam.getEndTime().getTime() < new Date().getTime()) {
			ret.put("type", "error");
			ret.put("message", "该考试已结束");
			return ret;
		}
		Student student = (Student)request.getSession().getAttribute("student");
		if (exam.getSubjectId().longValue() != student.getSubjectId().longValue()) {
			ret.put("type", "error");
			ret.put("message", "学科不同，无权进行考试");
			return ret;
		}
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("examId", exam.getId());
		queryMap.put("studentId", student.getId());
		ExamPaper find = examPaperService.find(queryMap);
		if (find != null) {
			if (find.getStatus() == 1) {
				// 表示已经考过
				ret.put("type", "error");
				ret.put("message", "您已经考过这门试了，不能再考了");
			}
			// 到这里表示试卷已经生成，但是没有提交考试，可以开始重新考试
			// 记录默认开始考试
			ret.put("type", "success");
			ret.put("message", "可以开始考试");
			return ret;
		}
		// 此时说明符合考试条件，随机生成试卷试题
		// 做判断，看是否满足生成试卷的条件
		// 获取单选题
		int singleQuestionTotalNum = questionService.getQuestionNumByType(Question.QUESTION_TYPE_SINGLE);
		if (exam.getSingleQuestionNum() > singleQuestionTotalNum) {
			ret.put("type", "error");
			ret.put("message", "单选题数量超过题库总数，无法生成试卷");
			return ret;
		}
		// 获取多选题
		int mutiQuestionTotalNum = questionService.getQuestionNumByType(Question.QUESTION_TYPE_MUTI);
		if (exam.getMutiQuestionNum() > mutiQuestionTotalNum) {
			ret.put("type", "error");
			ret.put("message", "多选题数量超过题库总数，无法生成试卷");
			return ret;
		}
		// 获取判断题
		int chargeQuestionTotalNum = questionService.getQuestionNumByType(Question.QUESTION_TYPE_CHARGE);
		if (exam.getChargeQuestionNum() > chargeQuestionTotalNum) {
			ret.put("type", "error");
			ret.put("message", "判断题数量超过题库总数，无法生成试卷");
			return ret;
		}
		// 所有条件都满足，开始创建试卷，随机生成试题
		ExamPaper examPaper = new ExamPaper();
		examPaper.setCreateTime(new Date());
		examPaper.setExamId(examId);
		examPaper.setStatus(0);
		examPaper.setStudentId(student.getId());
		examPaper.setTotalScore(exam.getTotalScore());
		examPaper.setUseTime(0);
		if (examPaperService.add(examPaper) <= 0) {
			ret.put("type", "error");
			ret.put("message", "试卷生成失败，请联系管理员");
			return ret;
		}
		// 试卷已经正式生成，现在开始随机生成试题
		Map<String, Object> queryQuestionMap = new HashMap<String, Object>();
		queryQuestionMap.put("questionType", Question.QUESTION_TYPE_SINGLE);
		queryQuestionMap.put("offset", 0);
		queryQuestionMap.put("pageSize", 99999);
		if (exam.getSingleQuestionNum() > 0) {
			// 考试规定，单选题数量大于0
			// 获取所有单选题
			List<Question> singleQuestionList = questionService.findList(queryQuestionMap);
			// 随机选取考试规定数量的单选题，插入到数据库中
			List<Question> selectedSingleQuestionList = getRandomList(singleQuestionList, exam.getSingleQuestionNum());
			insertQuestionAnswer(selectedSingleQuestionList, examId, examPaper.getId(), student.getId());
		}
		if (exam.getMutiQuestionNum() > 0) {
			queryQuestionMap.put("questionType", Question.QUESTION_TYPE_MUTI);
			// 获取所有多选题
			List<Question> muiltQuestionList = questionService.findList(queryQuestionMap);
			// 随机选取考试规定数量的多选题，插入到数据库中
			List<Question> selectedMuiltQuestionList = getRandomList(muiltQuestionList, exam.getMutiQuestionNum());
			insertQuestionAnswer(selectedMuiltQuestionList, examId, examPaper.getId(), student.getId());
		}
		if (exam.getChargeQuestionNum() > 0) {
			queryQuestionMap.put("questionType", Question.QUESTION_TYPE_CHARGE);
			// 获取所有判断题
			List<Question> chargeQuestionList = questionService.findList(queryQuestionMap);
			// 随机选取考试规定数量的判断题，插入到数据库中
			List<Question> selectedChargeQuestionList = getRandomList(chargeQuestionList, exam.getChargeQuestionNum());
			insertQuestionAnswer(selectedChargeQuestionList, examId, examPaper.getId(), student.getId());
		}
		exam.setPaperNum(exam.getPaperNum() + 1);
		examService.updateExam(exam);
		ret.put("type", "success");
		ret.put("message", "试卷生成成功");
		return ret;
	}
	
	/**
	 * 开始进行考试
	 * @param model
	 * @param examId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "examing", method = RequestMethod.GET)
	public ModelAndView index(ModelAndView model, Long examId, HttpServletRequest request) {
		Student student = (Student)request.getSession().getAttribute("student");
		Exam exam = examService.findById(examId);
		Map<String, Object> queryMap = new HashMap<String, Object>();
		if (exam == null) {
			model.setViewName("/home/exam/error");
			model.addObject("message", "当前考试不存在");
			return model;
		}
		if (exam.getEndTime().getTime() < new Date().getTime()) {
			model.setViewName("/home/exam/error");
			model.addObject("message", "当前考试已经过期");
			return model;
		}
		if (exam.getSubjectId().longValue() != student.getSubjectId().longValue()) {
			model.setViewName("/home/exam/error");
			model.addObject("message", "您所属科目与考试科目不符合，不能进行考试");
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
		if (examPaper.getStatus() == 1) {
			model.setViewName("/home/exam/error");
			model.addObject("message", "你已经考过改门考试了");
			return model;
		}
		Date now = new Date();
		Object attributeStartTime = request.getSession().getAttribute("startExamTime");
		if (attributeStartTime == null) {
			request.getSession().setAttribute("startExamTime", now);
		}
		Date startExamTime = (Date) request.getSession().getAttribute("startExamTime");
		int passedTime =  (int) (now.getTime() - startExamTime.getTime()) / 1000 / 60;
		if (passedTime >= exam.getAvailableTime()) {
			// 表示时间耗尽，不允许答题，按0分处理
			examPaper.setScore(0);
			examPaper.setStartExamTime(startExamTime);
			examPaper.setStatus(1);
			examPaper.setUseTime(passedTime);
			examPaperService.submitPaper(examPaper);
			model.setViewName("/home/exam/error");
			model.addObject("message", "当前考试时间耗尽，考生还未提交试卷，按照0分处理");
			return model;
		}
		Integer hour = (exam.getAvailableTime() - passedTime) / 60;
		Integer minitute = (exam.getAvailableTime() - passedTime) % 60;
		Integer second = (exam.getAvailableTime() * 60 - (int) (now.getTime() - startExamTime.getTime()) / 1000) % 60;
		queryMap.put("examPaperId", examPaper.getId());
		List<ExamPaperAnswer> findListByUser = examPaperAnswerService.findListByUser(queryMap);
		model.addObject("title", exam.getName() + "开始考试");
		model.addObject("singleQuestionList", getExamPaperAnswerList(findListByUser, Question.QUESTION_TYPE_SINGLE));
		model.addObject("mutiQuestionList", getExamPaperAnswerList(findListByUser, Question.QUESTION_TYPE_MUTI));
		model.addObject("chargeQuestionList", getExamPaperAnswerList(findListByUser, Question.QUESTION_TYPE_CHARGE));
		model.addObject("hour", hour);
		model.addObject("minitute", minitute);
		model.addObject("second", second);
		model.addObject("exam", exam);
		model.addObject("examPaper", examPaper);
		model.addObject("singleScore", Question.QUESTION_TYPE_SINGLE_SCORE);
		model.addObject("mutiScore", Question.QUESTION_TYPE_MUTI_SCORE);
		model.addObject("chargeScore", Question.QUESTION_TYPE_CHARGE_SCORE);
		model.addObject("singleQuestion", Question.QUESTION_TYPE_SINGLE);
		model.addObject("mutiQuestion", Question.QUESTION_TYPE_MUTI);
		model.addObject("chargeQuestion", Question.QUESTION_TYPE_CHARGE);
		model.setViewName("home/exam/examing");
		return model;
	}
	
	/**
	 * 用户选择答题提交单个答案
	 * @param examPaperAnswer
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/submit_answer", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> submitAnswer(ExamPaperAnswer examPaperAnswer, HttpServletRequest request) {
		Map<String, String> ret = new HashMap<String, String>();
		if (examPaperAnswer == null) {
			ret.put("type", "error");
			ret.put("message", "请正确操作");
			return ret;
		}
		Exam exam = examService.findById(examPaperAnswer.getExamId());
		if (exam == null) {
			ret.put("type", "error");
			ret.put("message", "考试信息不存在");
			return ret;
		}
		if (exam.getEndTime().getTime() < new Date().getTime()) {
			ret.put("type", "error");
			ret.put("message", "该考试已结束");
			return ret;
		}
		Student student = (Student)request.getSession().getAttribute("student");
		if (exam.getSubjectId().longValue() != student.getSubjectId().longValue()) {
			ret.put("type", "error");
			ret.put("message", "学科不同，无权进行考试");
			return ret;
		}
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("examId", exam.getId());
		queryMap.put("studentId", student.getId());
		ExamPaper findExamPaper = examPaperService.find(queryMap);
		if (findExamPaper == null) {
			ret.put("type", "error");
			ret.put("message", "不存在试卷");
			return ret;
		}
		if (findExamPaper.getId().longValue() != examPaperAnswer.getExamPaperId().longValue()) {
			ret.put("type", "error");
			ret.put("message", "考试试卷不正确，请规范操作");
			return ret;
		}
		Question question = questionService.findById(examPaperAnswer.getQuestionId());
		if (question == null) {
			ret.put("type", "error");
			ret.put("message", "该试题不存在，请规范操作");
			return ret;
		}
		// 此时可以将答案插入到数据库中
		examPaperAnswer.setStudentId(student.getId());
		if (question.getAnswer().equals(examPaperAnswer.getAnswer())) {
			examPaperAnswer.setIsCorrect(1);
		}
		if (examPaperAnswerService.submitAnswer(examPaperAnswer) <= 0) {
			ret.put("type", "error");
			ret.put("message", "答题出错，请联系管理员");
			return ret;
		}
		ret.put("type", "success");
		ret.put("message", "答题成功");
		return ret;
	}
	
	/**
	 * 用户选择答题提交单个答案
	 * @param examPaperAnswer
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/submit_exam", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> submitExam(Long examId, Long examPaperId, HttpServletRequest request) {
		Map<String, String> ret = new HashMap<String, String>();
		Exam exam = examService.findById(examId);
		if (exam == null) {
			ret.put("type", "error");
			ret.put("message", "考试不存在，请正确操作");
			return ret;
		}
		if (exam.getEndTime().getTime() < new Date().getTime()) {
			ret.put("type", "error");
			ret.put("message", "该考试已结束");
			return ret;
		}
		Student student = (Student)request.getSession().getAttribute("student");
		if (exam.getSubjectId().longValue() != student.getSubjectId().longValue()) {
			ret.put("type", "error");
			ret.put("message", "学科不同，无权进行操作");
			return ret;
		}
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("examId", exam.getId());
		queryMap.put("studentId", student.getId());
		ExamPaper findExamPaper = examPaperService.find(queryMap);
		if (findExamPaper == null) {
			ret.put("type", "error");
			ret.put("message", "不存在试卷");
			return ret;
		}
		if (findExamPaper.getId().longValue() != examPaperId.longValue()) {
			ret.put("type", "error");
			ret.put("message", "考试试卷不正确，请规范操作");
			return ret;
		}
		if (findExamPaper.getStatus() == 1) {
			ret.put("type", "error");
			ret.put("message", "请勿重复提交");
			return ret;
		}
		// 此时计算考试得分
		queryMap.put("examPaperId", examPaperId);
		// 这门考试的所有的答题信息
		List<ExamPaperAnswer> examPaperAnswerList = examPaperAnswerService.findListByUser(queryMap);
		int score = 0;
		for (ExamPaperAnswer examPaperAnswer: examPaperAnswerList) {
			if (examPaperAnswer.getIsCorrect() == 1) {
				score += examPaperAnswer.getQuestion().getScore();
			}
		}
		findExamPaper.setEndExamTime(new Date());
		findExamPaper.setScore(score);
		findExamPaper.setStartExamTime((Date)request.getSession().getAttribute("startExamTime"));
		findExamPaper.setStatus(1);
		findExamPaper.setUseTime((int)(findExamPaper.getEndExamTime().getTime() - findExamPaper.getStartExamTime().getTime()) / 1000 / 60);
		examPaperService.submitPaper(findExamPaper);
		// 计算考试统计结果，更新考试的已考人数，及格人数
		exam.setExamedNum(exam.getExamedNum() + 1);
		if (findExamPaper.getScore() >= exam.getPassScore()) {
			// 说明及格了
			exam.setPassNum(exam.getPassNum() + 1);
		}
		request.setAttribute("startExamTime", null);
		// 更新统计结果
		examService.updateExam(exam);
		ret.put("type", "success");
		ret.put("message", "提交成功");
		return ret;
	}
	
	/**
	 * 随机从给定的 List 中选取给定数量（n）的元素
	 * @param questionList
	 * @param n
	 * @return
	 */
	private List<Question> getRandomList(List<Question> questionList, int n) {
		// 如果小于要选取的数量，直接返回 true
		if (questionList.size() <= n) {
			return questionList;
		}
		// 追踪已经被选取的索引，避免重复
		Map<Integer, String> selectedMap = new HashMap<Integer, String>();
		// 用于存放最终的 Question 对象
		List<Question> selectedList = new ArrayList<Question>();
		while (selectedList.size() < n) {
			for (Question question: questionList) {
				int index = (int)(Math.random() * questionList.size());
				if (!selectedMap.containsKey(index)) {
					selectedMap.put(index, "");
					selectedList.add(questionList.get(index));
				}
			}
		}
		return selectedList;
	}
	
	/**
	 * 插入指定数量的试题到答题详情表
	 * @param questionList
	 * @param examId
	 * @param examPaperId
	 * @param studentId
	 */
	private void insertQuestionAnswer(List<Question> questionList, Long examId, Long examPaperId, Long studentId) {
		for (Question question: questionList) {
			ExamPaperAnswer examPaperAnswer = new ExamPaperAnswer();
			examPaperAnswer.setExamId(examId);
			examPaperAnswer.setExamPaperId(examPaperId);
			examPaperAnswer.setIsCorrect(0);
			examPaperAnswer.setQuestionId(question.getId());
			examPaperAnswer.setStudentId(studentId);
			examPaperAnswerService.add(examPaperAnswer);
		}
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
}
