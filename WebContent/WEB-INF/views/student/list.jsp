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
            <label>考生名称：</label><input class="wu-text easyui-textbox" id="search-name" style="width:150px">
            <label>所属学科：</label>
            <select class="wu-text easyui-combobox" id="search-subject" panelHeight="auto" style="width:150px">
            	<option value = "-1">全部</option>
            	<c:forEach items="${subjectList}" var="subject">
            		<option value="${subject.id}">${subject.name}</option>
            	</c:forEach>
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
                <td align="right">考生账号:</td>
                <td><input name="name" id="add-name" rows="6" class="wu-textarea" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写考生账号'" /></td>
            </tr>
            <tr>
                <td align="right">所属学科专业:</td>
                <td>
                	<select name="subjectId" class="easyui-combobox" panelHeight="auto" style="width:268px" data-options="required:true, missingMessage:'请选择学科'">
		                <c:forEach items="${subjectList}" var="subject">
		                <option value="${subject.id}">${subject.name}</option>
		                </c:forEach>
		            </select>
                </td>
            </tr>
            <tr>
                <td align="right">考生密码:</td>
                <td><input name="password" id="add-password" rows="6" class="wu-textarea" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写考生密码'" /></td>
            </tr>
            <tr>
                <td align="right">姓名:</td>
                <td><input name="trueName" id="add-truename" rows="6" class="wu-textarea" class="wu-text" /></td>
            </tr>
            <tr>
                <td align="right">考生手机:</td>
                <td><input name="tel" id="add-tel" rows="6" class="wu-textarea" class="wu-text" /></td>
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
                <td align="right">考生账号:</td>
                <td><input name="name" id="edit-name" rows="6" class="wu-textarea" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写考生账号'" /></td>
            </tr>
            <tr>
                <td align="right">所属学科专业:</td>
                <td>
                	<select id = "edit-subjectId" name="subjectId" class="easyui-combobox" panelHeight="auto" style="width:268px" data-options="required:true, missingMessage:'请选择学科'">
		                <c:forEach items="${subjectList}" var="subject">
		                <option value="${subject.id}">${subject.name}</option>
		                </c:forEach>
		            </select>
                </td>
            </tr>
            <tr>
                <td align="right">考生密码:</td>
                <td><input name="password" id="edit-password" rows="6" class="wu-textarea" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写考生密码'" /></td>
            </tr>
            <tr>
                <td align="right">姓名:</td>
                <td><input name="trueName" id="edit-truename" rows="6" class="wu-textarea" class="wu-text" /></td>
            </tr>
            <tr>
                <td align="right">考生手机:</td>
                <td><input name="tel" id="edit-tel" rows="6" class="wu-textarea" class="wu-text" /></td>
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
	                $("#edit-name").val("");
	                $("#edit-password").val("");
	                $("#edit-truename").val("");
	                $("#edit-tel").val("");
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
            title: "编辑考生信息",
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
				$("#edit-password").val(item.password);
				$("#edit-truename").val(item.trueName);
				$("#edit-tel").val(item.tel);
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
            title: "添加考生信息",
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
			//onBeforeOpen: function() {
			//	$("#content-text").val("");
			//}
        });
	}
	
	/**
	* 搜索按钮监听
	*/
	$("#search-btn").click(function() {
		var option = {name: $("#search-name").val()};
		var subjectId = $("#search-subject").combobox('getValue');
		if (subjectId != -1) {
			option.subjectId = subjectId
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
		fit:true,
		columns:[[
			{ field:'chk',checkbox: true},
			{ field:'name',title:'考生登录名',width:100,sortable:true},
			{ field:'subjectId',title:'所属学科',width:100,sortable:true, formatter: function(value, row, index) {
					// 获取 id
					var subjectList = $("#search-subject").combobox('getData');
					for (var i = 0; i < subjectList.length; i++) {
						if (subjectList[i].value == value) {
							return subjectList[i].text;
						}
					}
					return value;
			}},
			{ field:'password',title:'登录密码',width:200},
			{ field:'trueName',title:'姓名',width:100},
			{ field:'tel',title:'考生手机号码',width:200},
			{ field:'createTime',title:'注册时间',width:200, formatter: function(value, index, row) {
				return format(value);
			}},
		]],
	});
</script>