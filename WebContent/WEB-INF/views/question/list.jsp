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
            <label>试题题目：</label><input class="wu-text easyui-textbox" id="search-title" style="width:150px">
            <label>试题类型：</label>
            <select class="wu-text easyui-combobox" id="search-question-type" panelHeight="auto" style="width:150px">
            	<option value = "-1">全部</option>
            	<option value = "0">单选</option>
            	<option value = "1">多选</option>
            	<option value = "2">判断</option>
            </select>
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
                <td align="right">试题题目:</td>
                <td><input name="title" id="add-title" rows="6" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写试题题目'" /></td>
            </tr>
            <tr>
                <td align="right">所属类型:</td>
                <td>
                	<select name="questionType" class="easyui-combobox easyui-validatebox" panelHeight="auto" style="width:268px" data-options="required:true, missingMessage:'请选择试题类型'">
		            	<option value = "0">单选</option>
		            	<option value = "1">多选</option>
		            	<option value = "2">判断</option>
		            </select>
                </td>
            </tr>
            <tr>
                <td align="right">试题选项A:</td>
                <td><input name="attrA" id="add-attrA" rows="6" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写选项A'"/></td>
            </tr>
            <tr>
                <td align="right">试题选项B:</td>
                <td><input name="attrB" id="add-attrB" rows="6" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写选项B'"/></td>
            </tr>
            <tr>
                <td align="right">试题选项C:</td>
                <td><input name="attrC" id="add-attrC" rows="6" class="wu-text" /></td>
            </tr>
            <tr>
                <td align="right">试题选项D:</td>
                <td><input name="attrD" id="add-attrD" rows="6" class="wu-text" /></td>
            </tr>
            <tr>
                <td align="right">正确答案:</td>
                <td><input name="answer" id="add-answer" rows="6" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写正确答案'"/></td>
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
                <td align="right">试题题目:</td>
                <td><input name="title" id="edit-title" rows="6" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写试题题目'" /></td>
            </tr>
            <tr>
                <td align="right">所属类型:</td>
                <td>
                	<select name="questionType" id="edit-questionType" class="easyui-combobox easyui-validatebox" panelHeight="auto" style="width:268px" data-options="required:true, missingMessage:'请选择试题类型'">
		            	<option value = "0">单选</option>
		            	<option value = "1">多选</option>
		            	<option value = "2">判断</option>
		            </select>
                </td>
            </tr>
            <tr>
                <td align="right">试题选项A:</td>
                <td><input name="attrA" id="edit-attrA" rows="6" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写选项A'"/></td>
            </tr>
            <tr>
                <td align="right">试题选项B:</td>
                <td><input name="attrB" id="edit-attrB" rows="6" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写选项B'"/></td>
            </tr>
            <tr>
                <td align="right">试题选项C:</td>
                <td><input name="attrC" id="edit-attrC" rows="6" class="wu-text" /></td>
            </tr>
            <tr>
                <td align="right">试题选项D:</td>
                <td><input name="attrD" id="edit-attrD" rows="6" class="wu-text" /></td>
            </tr>
            <tr>
                <td align="right">正确答案:</td>
                <td><input name="answer" id="edit-answer" rows="6" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写正确答案'"/></td>
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
					$("#add-password").val("");
					$("#add-trueName").val("");
					$("#add-tel").val("");
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
            title: "编辑试题信息",
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
				$("#edit-title").val(item.title);
				$("#edit-attrA").val(item.attrA);
				$("#edit-attrB").val(item.attrB);
				$("#edit-attrC").val(item.attrC);
				$("#edit-attrD").val(item.attrD);
				$("#edit-answer").val(item.answer);
				$("#edit-questionType").combobox('setValue' ,item.questionType);
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
            title: "添加试题信息",
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
				$("#add-form input").val("");
			}
        });
	}
	
	/**
	* 搜索按钮监听
	*/
	$("#search-btn").click(function() {
		var option = {title: $("#search-title").val()};
		var questionType = $("#search-question-type").combobox('getValue');
		if (questionType != -1) {
			option.questionType = questionType
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
			{ field:'title',title:'试题题目',width:300,sortable:true},
			{ field:'score',title:'试题分值',width:50,sortable:true},
			{ field:'questionType',title:'试题类型',width:100,sortable:true, formatter: function(value, row, index) {
					switch (value) {
						case 0: return "单选";
						case 1: return "多选";
						case 2: return "判断";
						default: return value;
					}
			}},
			{ field:'attrA',title:'选项A',width:150},
			{ field:'attrB',title:'选项B',width:150},
			{ field:'attrC',title:'选项C',width:150},
			{ field:'attrD',title:'选项D',width:150},
			{ field:'answer',title:'正确答案',width:80},
			{ field:'createTime',title:'添加时间',width:200, formatter: function(value, index, row) {
				return format(value);
			}},
		]],
	});
</script>