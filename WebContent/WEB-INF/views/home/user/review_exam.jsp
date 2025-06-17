<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>${ title }</title>

<link href="../../resources/home/exam/css/main.css" rel="stylesheet" type="text/css" />
<link href="../../resources/home/exam/css/iconfont.css" rel="stylesheet" type="text/css" />
<link href="../../resources/home/exam/css/test.css" rel="stylesheet" type="text/css" />
<style>
.hasBeenAnswer {
	background: #5d9cec;
	color:#fff;
}
</style>

</head>
<body>
<div class="main">
	<!--nr start-->
	<div class="test_main">
		<div class="nr_left">
			<div class="test">
				<form action="" method="post">
					<div class="test_title">
						<p class="test_time">
							<img style="float: left; margin-top: 15px; margin-left: 15px;" src="../../resources/home/exam/images/time.png" width="20px"/><b class="alt-1">${ exam.availableTime }</b>
						</p>
						<font><input type="button" disabled="disabled" style="background-color: gray;" name="test_jiaojuan" value="交卷"></font>
					</div>
					<c:if test="${ exam.singleQuestionNum != 0 }">
						<div class="test_content">
							<div class="test_content_title">
								<h2>单选题</h2>
								<p>
									<span>共</span><i class="content_lit">${ exam.singleQuestionNum }</i><span>题，</span><span>合计</span><i class="content_fs">${ exam.singleQuestionNum * singleScore }</i><span>分</span>
								</p>
							</div>
						</div>
						<div class="test_content_nr">
							<ul>
								<c:forEach items="${ singleQuestionList }" var="sq" varStatus="sqids">				
										<li id="qu_${ singleQuestion }_${ sq.question.id }" data-key="${ sq.id }">
											<div class="test_content_nr_tt">
												<i>${ sqids.index + 1 }</i><span>(${ sq.question.score }分)</span><font>${ sq.question.title }</font>
											</div>
	
											<div class="test_content_nr_main">
												<ul>
													
														<li class="option" data-type="single" data-value="A">
															
																<input type="radio" class="radioOrCheck" name="answer${ sq.id }"
																	id="${ singleQuestion }_answer_${ sq.id }_option_1"
																	<c:if test="${ sq.answer == 'A' }">
																		checked
																	</c:if>
																/>
															
															
															<label for="${ singleQuestion }_answer_${ sq.id }_option_1">
																A.
																<p class="ue" style="display: inline;">${ sq.question.attrA }</p>
															</label>
														</li>
													
														<li class="option" data-type="single" data-value="B">
															
																<input type="radio" class="radioOrCheck" name="answer${ sq.id }"
																	id="${ singleQuestion }_answer_${ sq.id }_option_2"
																	<c:if test="${ sq.answer == 'B' }">
																		checked
																	</c:if>
																/>
															
															
															<label for="${ singleQuestion }_answer_${ sq.id }_option_2">
																B.
																<p class="ue" style="display: inline;">${ sq.question.attrB }</p>
															</label>
														</li>
													
														<li class="option" data-type="single" data-value="C">
															
																<input type="radio" class="radioOrCheck" name="answer${ sq.id }"
																	id="${ singleQuestion }_answer_${ sq.id }_option_3"
																	<c:if test="${ sq.answer == 'C' }">
																		checked
																	</c:if>
																/>
															
															
															<label for="${ singleQuestion }_answer_${ sq.id }_option_3">
																C.
																<p class="ue" style="display: inline;">${ sq.question.attrC }</p>
															</label>
														</li>
													
														<li class="option" data-type="single" data-value="D">
															
																<input type="radio" class="radioOrCheck" name="answer${ sq.id }"
																	id="${ singleQuestion }_answer_${ sq.id }_option_4"
																	<c:if test="${ sq.answer == 'D' }">
																		checked
																	</c:if>
																/>
															
															
															<label for="${ singleQuestion }_answer_${ sq.id }_option_4">
																D.
																<p class="ue" style="display: inline;">${ sq.question.attrD }</p>
															</label>
														</li>
														<li class="option" data-type="single" data-value="D">
															<label style="color: red;">正确答案：${ sq.question.answer }</label>
														</li>
												</ul>
											</div>
										</li>
								</c:forEach>
							</ul>
						</div>
					</c:if>
					<c:if test="${ exam.mutiQuestionNum != 0 }">
						<div class="test_content">
							<div class="test_content_title">
								<h2>多选题</h2>
								<p>
									<span>共</span><i class="content_lit">${ exam.mutiQuestionNum }</i><span>题，</span><span>合计</span><i class="content_fs">${ exam.mutiQuestionNum * mutiScore }</i><span>分</span>
								</p>
							</div>
						</div>
						<div class="test_content_nr">
							<ul>
								<c:forEach items="${ mutiQuestionList }" var="mq" varStatus="mqids">
									<li id="qu_${ mutiQuestion }_${ mq.question.id }" data-key="${ mq.id }">
										<div class="test_content_nr_tt">
											<i>${ mqids.index + exam.singleQuestionNum + 1 }</i><span>(${ mq.question.score }分)</span><font>${ mq.question.title }</font>
										</div>

										<div class="test_content_nr_main">
											<ul>
												
													<li class="option" data-type="muilt" data-value="A">
														
														
															<input type="checkbox" class="radioOrCheck" name="answer1"
																id="${ muiltQuestion }_answer_${ mq.id }_option_1" value="A"
																<c:if test="${ fn:contains(mq.answer, 'A') }">
																	checked
																</c:if>
															/>
														
														<label for="${ muiltQuestion }_answer_${ mq.id }_option_1">
															A.
															<p class="ue" style="display: inline;">${ mq.question.attrA }</p>
														</label>
													</li>
												
													<li class="option" data-type="muilt" data-value="B">
														
														
															<input type="checkbox" class="radioOrCheck" name="answer1"
																id="${ muiltQuestion }_answer_${ mq.id }_option_2" value="B"
																<c:if test="${ fn:contains(mq.answer, 'B') }">
																	checked
																</c:if>
															/>
														
														<label for="${ muiltQuestion }_answer_${ mq.id }_option_2">
															B.
															<p class="ue" style="display: inline;">${ mq.question.attrB }</p>
														</label>
													</li>
												
													<li class="option" data-type="muilt" data-value="C">
														
														
															<input type="checkbox" class="radioOrCheck" name="answer1"
																id="${ muiltQuestion }_answer_${ mq.id }_option_3" value="C"
																<c:if test="${ fn:contains(mq.answer, 'C') }">
																	checked
																</c:if>
															/>
														
														<label for="${ muiltQuestion }_answer_${ mq.id }_option_3">
															C.
															<p class="ue" style="display: inline;">${ mq.question.attrC }</p>
														</label>
													</li>
												
													<li class="option" data-type="muilt" data-value="D">
														
														
															<input type="checkbox" class="radioOrCheck" name="answer1"
																id="${ muiltQuestion }_answer_${ mq.id }_option_4" value="D"
																<c:if test="${ fn:contains(mq.answer, 'D') }">
																	checked
																</c:if>
															/>
														
														<label for="${ muiltQuestion }_answer_${ mq.id }_option_4">
															D.
															<p class="ue" style="display: inline;">${ mq.question.attrD }</p>
														</label>
													</li>
													<li class="option" data-type="single" data-value="D">
															<label style="color: red;">正确答案：${ mq.question.answer }</label>
													</li>
											</ul>
										</div>
									</li>
								</c:forEach>
							</ul>
						</div>
					</c:if>
					<c:if test="${ exam.chargeQuestionNum != 0 }">
						<div class="test_content">
							<div class="test_content_title">
								<h2>判断题</h2>
								<p>
									<span>共</span><i class="content_lit">${ exam.chargeQuestionNum }</i><span>题，</span><span>合计</span><i class="content_fs">${ exam.chargeQuestionNum * chargeScore }</i><span>分</span>
								</p>
							</div>
						</div>
						<div class="test_content_nr">
							<ul>
								<c:forEach items="${ chargeQuestionList }" var="cq" varStatus="cqids">				
										<li id="qu_${ chargeQuestion }_${ cq.question.id }" data-key="${ cq.id }">
											<div class="test_content_nr_tt">
												<i>${ cqids.index + exam.singleQuestionNum + exam.mutiQuestionNum + 1 }</i><span>(${ cq.question.score }分)</span><font>${ cq.question.title }</font>
											</div>
	
											<div class="test_content_nr_main">
												<ul>
													
														<li class="option" data-type="charge" data-value="A">
															
																<input type="radio" class="radioOrCheck" name="answer${ cq.id }"
																	id="${ chargeQuestion }_answer_${ cq.id }_option_1"
																	<c:if test="${ cq.answer == 'A' }">
																		checked
																	</c:if>
																/>
															
															
															<label for="${ chargeQuestion }_answer_${ cq.id }_option_1">
																A.
																<p class="ue" style="display: inline;">${ cq.question.attrA }</p>
															</label>
														</li>
													
														<li class="option" data-type="charge" data-value="B">
															
																<input type="radio" class="radioOrCheck" name="answer${ cq.id }"
																	id="${ chargeQuestion }_answer_${ cq.id }_option_2"
																	<c:if test="${ cq.answer == 'B' }">
																		checked
																	</c:if>
																/>
															
															
															<label for="${ chargeQuestion }_answer_${ cq.id }_option_2">
																B.
																<p class="ue" style="display: inline;">${ cq.question.attrB }</p>
															</label>
														</li>
														<li class="option" data-type="single" data-value="D">
															<label style="color: red;">正确答案：${ cq.question.answer }</label>
														</li>
												</ul>
											</div>
										</li>
								</c:forEach>
							</ul>
						</div>
					</c:if>
				</form>
			</div>

		</div>
		<div class="nr_right">
			<div class="nr_rt_main">
				<div class="rt_nr1">
					<div class="rt_nr1_title">
						<h1>
							<span style="font-size: 18px;">答题卡</span>
						</h1>
						<p class="test_time">
							<img style="float: left; margin-top: 15px; margin-left: 15px;" src="../../resources/home/exam/images/time.png" width="20px"/><b class="alt-1">${ exam.availableTime }</b>
						</p>
					</div>
					<c:if test="${ exam.singleQuestionNum != 0 }">
						<div class="rt_content">
							<div class="rt_content_tt">
								<h2>单选题</h2>
								<p>
									<span>共</span><i class="content_lit">${ exam.singleQuestionNum }</i><span>题</span>
								</p>
							</div>
							<div class="rt_content_nr answerSheet">
								<ul>
									<c:forEach items="${ singleQuestionList }" var="ssq" varStatus="ssqids">	
										<li><a href="#qu_${ singleQuestion }_${ ssq.question.id }">${ ssqids.index + 1 }</a></li>
									</c:forEach>
								</ul>
							</div>
						</div>
					</c:if>
					<c:if test="${ exam.mutiQuestionNum != 0 }">
						<div class="rt_content">
							<div class="rt_content_tt">
								<h2>多选题</h2>
								<p>
									<span>共</span><i class="content_lit">${ exam.mutiQuestionNum }</i><span>题</span>
								</p>
							</div>
							<div class="rt_content_nr answerSheet">
								<ul>
									<c:forEach items="${ mutiQuestionList }" var="mmq" varStatus="mmqids">
										<li><a href="#qu_${ mutiQuestion }_${ mmq.question.id }">${ mqids.index + exam.singleQuestionNum + 1 }</a></li>
									</c:forEach>
								</ul>
							</div>
						</div>
					</c:if>
					<c:if test="${ exam.chargeQuestionNum != 0 }">
						<div class="rt_content">
							<div class="rt_content_tt">
								<h2>判断题</h2>
								<p>
									<span>共</span><i class="content_lit">${ exam.chargeQuestionNum }</i><span>题</span>
								</p>
							</div>
							<div class="rt_content_nr answerSheet">
								<ul>
									<c:forEach items="${ chargeQuestionList }" var="ccq" varStatus="ccqids">	
										<li><a href="#qu_${ chargeQuestion }_${ ccq.question.id }">${ ccqids.index + exam.singleQuestionNum + exam.mutiQuestionNum + 1 }</a></li>
									</c:forEach>
								</ul>
							</div>
						</div>
					</c:if>
				</div>

			</div>
		</div>
	</div>
	<!--nr end-->
	<div class="foot"></div>
</div>

<script src="../../resources/home/exam/js/jquery-1.11.3.min.js"></script>
<script src="../../resources/home/exam/js/jquery.easy-pie-chart.js"></script>
<!--时间js-->
<script src="../../resources/home/exam/js/jquery.countdown.js"></script>
<script>
	window.jQuery(function($) {
		"use strict";
		
		$('time').countDown({
			with_separators : false,
			
		});
		$('.alt-1').countDown({
			css_class : 'countdown-alt-1'
		});
		$('.alt-2').countDown({
			css_class : 'countdown-alt-2'
		});
		
	});
	
	$(function() {
		$('li.option input').click(function() {
			var examId = $(this).closest('.test_content_nr_main').closest('li').attr('id'); // 得到题目ID
			var cardLi = $('a[href=#' + examId + ']'); // 根据题目ID找到对应答题卡
			// 设置已答题
			if(!cardLi.hasClass('hasBeenAnswer')){
				cardLi.addClass('hasBeenAnswer');
			}
		})
	})
	function autoSubmitExam(){}
</script>

<div style="text-align:center;margin:50px 0; font:normal 14px/24px 'MicroSoft YaHei';">
<p>[OnlineExam] 在线考试系统</p>
<p><a href="https://github.com/CeciliaElf/OnlineExam" target="_blank">GitHub</a></p>
</div>
</body>
</html>