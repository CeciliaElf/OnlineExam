package com.cecilia.programmer.entity.admin;

import org.springframework.stereotype.Component;

/**
 * 试卷答题讯息实体
 * @author cecilia
 */
@Component
public class ExamPaperAnswer {
	private Long id;   // id
	private Long studentId; // 所属学生 id
	private Long examId; // 所属考试 id
	private Long examPaperId; // 所属试卷 id
	private Long questionId; // 所属试题 id
	private String answer; // 提交答案
	private int isCorrect; // 是否正确
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public Long getExamId() {
		return examId;
	}
	public void setExamId(Long examId) {
		this.examId = examId;
	}
	public Long getExamPaperId() {
		return examPaperId;
	}
	public void setExamPaperId(Long examPaperId) {
		this.examPaperId = examPaperId;
	}
	public Long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public int getIsCorrect() {
		return isCorrect;
	}
	public void setIsCorrect(int isCorrect) {
		this.isCorrect = isCorrect;
	}
}
