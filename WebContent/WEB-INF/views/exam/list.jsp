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
            <label>考试名称：</label><input class="wu-text easyui-textbox" id="search-name" style="width:150px">
            <label>所属科目：</label>
            <select class="wu-text easyui-combobox" id="search-subject" panelHeight="auto" style="width:150px">
            	<!-- 从后台遍历 -->
            	<option value="-1">全部</option>
            	<c:forEach items="${ subjectList }" var="subject">
            		<option value="${ subject.id }">${ subject.name }</option>
            	</c:forEach>
            </select>
            <label>考试开始时间：</label><input class="wu-text easyui-datetimebox" id="search-startTime" style="width:150px">
            <label>考试结束时间：</label><input class="wu-text easyui-datetimebox" id="search-endTime" style="width:150px">
            <a href="#" id="search-btn" class="easyui-linkbutton" iconCls="icon-search">搜寻</a>
        </div>
    </div>
    <!-- 工具栏结束 -->
    <table id="data-datagrid" class="easyui-datagrid" toolbar="#wu-toolbar"></table>
</div>
<!-- 添加窗口 -->
<div id="add-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:420px; padding:10px;">
	<form id="add-form" method="post">
        <table>
        	<tr>
                <td align="right">考试名称:</td>
                <td><input type="text" name="name" id="add-name" rows="6" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写考试名称'" /></td>
            </tr>
            <tr>
                <td align="right">所属科目:</td>
                <td>
                	<select name="subjectId" class="easyui-combobox easyui-validatebox" panelHeight="auto" style="width:268px" data-options="required:true, missingMessage:'请选择考试科目'">
		            	<c:forEach items="${ subjectList }" var="subject">
		            		<option value="${ subject.id }">${ subject.name }</option>
		            	</c:forEach>
		            </select>
                </td>
            </tr>
            <tr>
                <td align="right">考试开始时间:</td>
                <td><input type="text" name="startTime" id="add-startTime" rows="6" class="wu-text easyui-validatebox easyui-datetimebox" editable="false" /></td>
            </tr>
            <tr>
                <td align="right">考试结束时间:</td>
                <td><input type="text" name="endTime" id="add-endTime" rows="6" class="wu-text easyui-validatebox easyui-datetimebox" editable="false" /></td>
            </tr>
            <tr>
                <td align="right">考试限制时间:</td>
                <td><input type="text" name="availableTime" id="add-availableTime" rows="6" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写考试限制时间'" /></td>
            </tr>
            <tr>
                <td align="right">及格分数:</td>
                <td><input type="text" name="passScore" id="add-passScore" rows="6" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写考试及格分数'" /></td>
            </tr>
            <tr>
                <td align="right">单选题数量:</td>
                <td><input type="text" placeholder="每个单选题${ singleScore }分" name="singleQuestionNum" id="add-singleQuestionNum" rows="6" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写考试单选题数量'" /></td>
            </tr>
            <tr>
                <td align="right">多选题数量:</td>
                <td><input type="text" placeholder="每个多选题${ mutiScore }分" name="mutiQuestionNum" id="add-mutiQuestionNum" rows="6" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写考试多选题数量'" /></td>
            </tr>
            <tr>
                <td align="right">判断题数量:</td>
                <td><input type="text" placeholder="每个判断题${ chargeScore }分" name="chargeQuestionNum" id="add-chargeQuestionNum" rows="6" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写考试判断题数量'" /></td>
            </tr>
        </table>
    </form>
</div>
<!--修改弹窗-->
<div id="edit-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:450px; padding:10px;">
	<form id="edit-form" method="post">
		<input type="hidden" name="id" id="edit-id">
        <table>
        	<tr>
                <td align="right">考试名称:</td>
                <td><input type="text" name="name" id="edit-name" rows="6" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写考试名称'" /></td>
            </tr>
            <tr>
                <td align="right">所属科目:</td>
                <td>
                	<select id="edit-subjectId" name="subjectId" class="easyui-combobox easyui-validatebox" panelHeight="auto" style="width:268px" data-options="required:true, missingMessage:'请选择考试科目'">
		            	<c:forEach items="${ subjectList }" var="subject">
		            		<option value="${ subject.id }">${ subject.name }</option>
		            	</c:forEach>
		            </select>
                </td>
            </tr>
            <tr>
                <td align="right">考试开始时间:</td>
                <td><input type="text" name="startTime" id="edit-startTime" rows="6" class="wu-text easyui-validatebox easyui-datetimebox" editable="false" /></td>
            </tr>
            <tr>
                <td align="right">考试结束时间:</td>
                <td><input type="text" name="endTime" id="edit-endTime" rows="6" class="wu-text easyui-validatebox easyui-datetimebox" editable="false" /></td>
            </tr>
            <tr>
                <td align="right">考试限制时间:</td>
                <td><input type="text" name="availableTime" id="edit-availableTime" rows="6" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写考试限制时间'" /></td>
            </tr>
            <tr>
                <td align="right">及格分数:</td>
                <td><input type="text" name="passScore" id="edit-passScore" rows="6" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写考试及格分数'" /></td>
            </tr>
            <tr>
                <td align="right">单选题数量:</td>
                <td><input type="text" placeholder="每个单选题${ singleScore }分" name="singleQuestionNum" id="edit-singleQuestionNum" rows="6" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写考试单选题数量'" /></td>
            </tr>
            <tr>
                <td align="right">多选题数量:</td>
                <td><input type="text" placeholder="每个多选题${ mutiScore }分" name="mutiQuestionNum" id="edit-mutiQuestionNum" rows="6" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写考试多选题数量'" /></td>
            </tr>
            <tr>
                <td align="right">判断题数量:</td>
                <td><input type="text" placeholder="每个判断题${ chargeScore }分" name="chargeQuestionNum" id="edit-chargeQuestionNum" rows="6" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写考试判断题数量'" /></td>
            </tr>
        </table>
    </form>
</div>
<%@include file="../common/footer.jsp" %>
<script type="text/javascript">
	/**
	* 添加记录
	*/
	function add(){
		var validate = $("#add-form").form("validate");
		if(!validate) {
			$.messager.alert("消息提醒", "请检查你输入的数据", "warning");
		}
		var data = $("#add-form").serialize()
		$.ajax({
			url:'add',
			dataType:'json',
			type:'post',
			data:data,
			success:function(data){
				if(data.type == 'success'){
					$.messager.alert('信息提示', '添加成功！' ,'info');
					$("#add-name").val("");
					$('#add-dialog').dialog('close');
					$('#data-datagrid').datagrid('reload')
				} else {
					$.messager.alert('信息提示', data.msg, 'warning');
				}
			},
		});
	}

	/**
	* 编辑记录
	*/
	function edit(){
		var validate = $("#edit-form").form("validate");
		if(!validate) {
			$.messager.alert("消息提醒", "请检查你输入的数据", "warning");
		}
		var data = $("#edit-form").serialize()
		$.ajax({
			url:'edit',
			dataType:'json',
			type:'post',
			data:data,
			success:function(data){
				if(data.type == 'success'){
					$.messager.alert('信息提示', '修改成功！' ,'info');
	                $('#edit-dialog').dialog('close');
	                $('#data-datagrid').datagrid('reload')
				} else {
					$.messager.alert('信息提示', data.msg, 'warning');
				}
			},
		});
	}
	
	/**
	* 删除记录
	*/
	function remove(){
		$.messager.confirm('信息提示','确定要删除该记录？', function(result){
			if(result){
				var item = $('#data-datagrid').datagrid('getSelected');  // 获取到选中的那一行
				if (item == null || item.length == 0) {
					$.messager.alert('讯息提示', '请选择要删除的数据', 'info');
					return;
				}
				$.ajax({
					url:'delete',
					dataType:'json',
					type:'post',
					data:{id: item.id},
					success:function(data){
						if(data.type == 'success'){
							$.messager.alert('信息提示', '删除成功！' ,'info');
							$('#data-datagrid').datagrid('reload')
						} else {
							$.messager.alert('信息提示', data.msg, 'warning');
						}
					}
				});
			}	
		});
	}
	
	/**
	* 打开编辑窗口
	*/
	function openEdit(){
		// $('#add-form').form('clear');
		var item = $('#data-datagrid').datagrid('getSelected');  // 获取到选中的那一行
		if (item == null || item.length == 0) {
			$.messager.alert('讯息提示', '请选择要编辑的数据', 'info');
			return;
		}
		$('#edit-dialog').dialog({
			closed: false,
			modal:true,
            title: "编辑考试信息",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: edit,
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#edit-dialog').dialog('close');                    
                }
            }],
			onBeforeOpen: function() {
				$("#edit-id").val(item.id);
				$("#edit-name").val(item.name);
				$("#edit-startTime").datetimebox('setValue', format(item.startTime));
				$("#edit-endTime").datetimebox('setValue', format(item.endTime));
				$("#edit-passScore").val(item.passScore);
				$("#edit-singleQuestionNum").val(item.singleQuestionNum);
				$("#edit-mutiQuestionNum").val(item.mutiQuestionNum);
				$("#edit-chargeQuestionNum").val(item.chargeQuestionNum);
				$("#edit-availableTime").val(item.availableTime);
				$("#edit-subjectId").combobox('setValue' ,item.subjectId);
			}
        });
	}
	
	/**
	* 打开添加窗口
	*/
	function openAdd(){
		// $('#add-form').form('clear');
		$('#add-dialog').dialog({
			closed: false,
			modal:true,
            title: "添加考试信息",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: add         // 点击确定后，去调用 add 方法
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#add-dialog').dialog('close');                    
                }
            }],
			onBeforeOpen: function() {
				// $("#add-form input").val("");
			}
        });
	}
	
	/**
	* 搜索按钮监听
	*/
	$("#search-btn").click(function() {
		var option = {name: $("#search-name").val()};
		var subjectId = $("#search-subject").combobox('getValue');
		if (subjectId != -1) {
			option.subjectId = subjectId;
		}
		var startTime = $("#search-startTime").datetimebox('getValue');
		var endTime = $("#search-endTime").datetimebox('getValue');
		if (startTime != null && startTime != '') {
			option.startTime = startTime;
		}
		if (endTime != null && endTime != '') {
			option.endTime = endTime;
		}
		$("#data-datagrid").datagrid("reload", option)
	});
	
	function add0(m){return m<10?'0'+m:m }
	function format(shijianchuo){
	//shijianchuo是整数，否则要parseInt转换
		var time = new Date(shijianchuo);
		var y = time.getFullYear();
		var m = time.getMonth()+1;
		var d = time.getDate();
		var h = time.getHours();
		var mm = time.getMinutes();
		var s = time.getSeconds();
		return y+'-'+add0(m)+'-'+add0(d)+' '+add0(h)+':'+add0(mm)+':'+add0(s);
	}
	
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
			{ field:'name',title:'考试名称',width:300,sortable:true},
			{ field:'subjectId',title:'所属学科',width:100,sortable:true, formatter: function(value, row, index) {
				var subjectList = $("#search-subject").combobox("getData");
				for (var i = 0; i < subjectList.length; i++) {
					if (subjectList[i].value == value) return subjectList[i].text;
				}
				return value;
			}},
			{ field:'startTime',title:'考试开始日期',width:200, formatter: function(value, index, row) {
				return format(value);
			}},
			{ field:'endTime',title:'考试结束日期',width:200, formatter: function(value, index, row) {
				return format(value);
			}},
			{ field:'availableTime',title:'限制时间',width:100, formatter: function(value, index, row) {
				return value + '分钟';
			}},
			{ field:'questionNum',title:'试题总数',width:100},
			{ field:'totalScore',title:'总分',width:60},
			{ field:'passScore',title:'及格分数',width:80},
			{ field:'singleQuestionNum',title:'单选题数量',width:100},
			{ field:'mutiQuestionNum',title:'多选题数量',width:100},
			{ field:'chargeQuestionNum',title:'判断题数量',width:100},
			{ field:'paperNum',title:'生成试卷数量',width:150},
			{ field:'examedNum',title:'已考人数',width:100},
			{ field:'passNum',title:'及格人数',width:100},
			{ field:'createTime',title:'添加时间',width:200, formatter: function(value, index, row) {
				return format(value);
			}},
		]],
	});
</script>