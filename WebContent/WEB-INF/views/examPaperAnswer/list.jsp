<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../common/header.jsp" %>
<div class="easyui-layout" data-options="fit:true">
	<!-- 工具栏 -->
	<div id="wu-toolbar">
        <div class="wu-toolbar-button">
			<%@include file="../common/menus.jsp" %>	
        </div>
        <div class="wu-toolbar-search">
            <label>所属考试：</label>
            <select class="wu-text easyui-combobox" id="search-exam" panelHeight="200px" style="width:150px">
            	<!-- 从后台遍历 -->
            	<option value="-1">全部</option>
            	<c:forEach items="${ examList }" var="exam">
            		<option value="${ exam.id }">${ exam.name }</option>
            	</c:forEach>
            </select>
            <label>所属考生：</label>
            <select class="wu-text easyui-combobox" id="search-student" panelHeight="200px" style="width:150px">
            	<!-- 从后台遍历 -->
            	<option value="-1">全部</option>
            	<c:forEach items="${ studentList }" var="student">
            		<option value="${ student.id }">${ student.name }</option>
            	</c:forEach>
            </select>
            <label>所属试题：</label>
            <select class="wu-text easyui-combobox" id="search-question" panelHeight="200px" style="width:150px">
            	<c:forEach items="${ questionList }" var="question">
            		<option value="${ question.id }">${ question.title }</option>
            	</c:forEach>
            </select>
            <a href="#" id="search-btn" class="easyui-linkbutton" iconCls="icon-search">搜寻</a>
        </div>
    </div>
    <!-- 工具栏结束 -->
    <table id="data-datagrid" class="easyui-datagrid" toolbar="#wu-toolbar"></table>
</div>
<%@include file="../common/footer.jsp" %>
<script type="text/javascript">
	/**
	* 搜索按钮监听
	*/
	$("#search-btn").click(function() {
		var option = {};
		var examId = $("#search-exam").combobox('getValue');
		if (examId != -1) {
			option.examId = examId;
		}
		var studentId = $("#search-student").combobox('getValue');
		if (studentId != -1) {
			option.studentId = studentId;
		}
		var questionId = $("#search-question").combobox('getValue');
		if (questionId != -1) {
			option.questionId = questionId;
		}
		$("#data-datagrid").datagrid("reload", option)
	});

	/**
	* 载入数据
	*/
	$('#data-datagrid').datagrid({
		url:'list',
		rownumbers:true,         // 显示行号
		singleSelect:true,      // 单选还是多选（true 单选， false 多选）
		pageSize:20,             // 每页条数         
		pagination:true,         // 分页
		multiSort:true,
		fitColumns:true,
		idField: 'id',
	    treeField: 'name',
	    nowrap: false,
		fit:true,
		columns:[[
			{ field:'chk',checkbox: true},
			{ field:'examId',title:'所属考试',width:150,sortable:true, formatter: function(value, row, index) {
				var examList = $("#search-exam").combobox("getData");
				for (var i = 0; i < examList.length; i++) {
					if (examList[i].value == value) return examList[i].text;
				}
				return value;
			}},
			{ field:'examPaperId',title:'试卷ID',width:80},
			{ field:'questionId',title:'所属试题',width:200, formatter: function(value, index, row) {
				var questionList = $("#search-question").combobox("getData");
				for (var i = 0; i < questionList.length; i++) {
					if (questionList[i].value == value) return questionList[i].text;
				}
				return value;
			}},
			{ field:'studentId',title:'所属学生',width:100,sortable:true, formatter: function(value, row, index) {
				var studentList = $("#search-student").combobox("getData");
				for (var i = 0; i < studentList.length; i++) {
					if (studentList[i].value == value) return studentList[i].text;
				}
				return value;
			}},
			{ field:'answer',title:'提交答案',width:100},
			{ field:'isCorrect',title:'是否正确',width:100, formatter: function(value, index, row) {
				if (value == 0) {
					return '错误';
				}
				return '正确';
			}},
		]],
	});
</script>