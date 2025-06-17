<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="../common/user_header.jsp" %>  
<body>

	<div class="tm_main">
    	
        
        <div class="tm_container">
        	<div class="tm_navtitle">
				<h1>历史考试</h1>
                <span>历史考试，请选择以下列表中我参加过的考试进行详情查看。</span>
            </div>
        </div>
        
        <div class="tm_container">
			<form action="history_list" method="get">
			<div class="tm_searchbox">
				试卷名称 :
				<input type="text" name="name" class="tm_txts" size="10" maxlength="20" value="${ name }" /> &nbsp;

				<button class="tm_btns" type="submit">搜索</button>
			</div>

        	<table width="100%" cellpadding="10" border="0" class="tm_table_list">
            	<thead>
                	<tr>
                        <th>考试名称</th>
						<th>考试状态</th>
                        <th>考试时长</th>
                    	<th>考试耗时</th>
                    	<th>考试时间</th>
                        <th>考试科目</th>
                        <th>试卷得分</th>
                        <th>及格分数/卷面总分</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
					<c:if test="${empty historyList}">
						<tr>
							<td colspan="9">你还没有考过试</td>
						</tr>
					</c:if>
					<c:forEach items="${ historyList }" var="history">
						<tr>
							<td>${ history.exam.name }</td>
							<td>已批阅</td>
							<td><span class="tm_label">${ history.exam.availableTime }</span> 分钟</td>
							<td><span class="tm_label">${ history.useTime }</span> 分钟</td>
							<td>
								<fmt:formatDate value="${ history.startExamTime }" pattern="yyyy-MM-dd HH:mm:ss" />
								<br/><fmt:formatDate value="${ history.endExamTime }" pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td>${ subject.name }</td>
							<td>
								<span class="tm_label">${ history.score }</span>
								<c:if test="${ history.exam.passScore >= history.score }">
									<font color="red"><b>不及格</b></font>
								</c:if>	
							</td>
							<td>${ history.exam.passScore }/${ history.exam.totalScore }</td>
							<td>
								<a class="tm_btn tm_btn_primary" style="text-decoration: none; color: white;" target="_blank" href="review_exam?examId=${ history.exam.id }&examPaperId=${ histroy.id }">回顾试卷</a>
							</td>
						</tr>
					</c:forEach>
                </tbody>
            </table>
			</form>
			<table width="100%" cellpadding="10" border="0" class="tm_table_list">
				<tfoot>
					<tr>
						<td>
							<div class="tm_pager_foot">
								<a href="" class="tm_btns" style="color:white;text-decoration:none;">上一页</a>&nbsp; <a href="" class="tm_btns" style="color:white;text-decoration:none;">下一页</a>&nbsp; 
							</div>
						</td>
					</tr>
				</tfoot>
			</table>
        </div>
        
        
    </div>
	<script type="text/javascript">
		window.onload = function(){
			$(".tm_table_list tbody tr").mouseover(function(){
				$(this).attr("style","background:#f5f5f5");
			});
			$(".tm_table_list tbody tr").mouseout(function(){
				$(this).attr("style","background:#ffffff");
			});
		}
	</script>
</body>
</html>