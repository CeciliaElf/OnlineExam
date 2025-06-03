<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../common/header.jsp" %>
<div class="easyui-layout" data-options="fit:true">
	<!-- Begin of toolbar -->
	<div id="wu-toolbar">
        <div class="wu-toolbar-button">
			<%@include file="../common/menus.jsp" %>	
        </div>
        <div class="wu-toolbar-search">
            <label>用户名称：</label><input class="wu-text easyui-textbox" id="search-name" style="width:150px">
            <label>所属角色：</label>
            <select class="wu-text easyui-combobox" id="search-role" panelHeight="auto" style="width:150px">
            	<option value = "-1">全部</option>
            	<c:forEach items="${roleList}" var="role">
            		<option value="${role.id}">${role.name}</option>
            	</c:forEach>
            </select>
            <label>性别：</label>
            <select class="wu-text easyui-combobox" id="search-sex" panelHeight="auto" style="width:150px">
            	<option value = "-1">全部</option>
            	<option value = "0">未知</option>
            	<option value = "1" >男</option>
            	<option value = "2">女</option>
            </select>
            <a href="#" id="search-btn" class="easyui-linkbutton" iconCls="icon-search">搜寻</a>
        </div>
    </div>
    <!-- End of toolbar -->
    <table id="data-datagrid" class="easyui-datagrid" toolbar="#wu-toolbar"></table>
</div>
<!-- 添加弹窗 -->
<div id="add-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:420px; padding:10px;">
	<form id="add-form" method="post">
        <table>
            <tr>
                <td width="60" align="right">头像预览:</td>
                <td valign="middle">
                	<img id="preview-photo" style="float:left;" src="/OnlineExam/resources/admin/easyui/images/user_avatar.jpg" width="100px">
                	<a style="float:left; margin-top: 40px" href="javascript:void(0)" class = "easyui-linkbutton" iconCls="icon-folder" onclick="uploadPhoto()" plain="true">上传头像</a>
                </td>
            </tr>
            <tr>
                <td width="60" align="right">头像:</td>
                <td><input type="text" id = "add-photo" name="photo" value="/OnlineExam/resources/admin/easyui/images/user_avatar.jpg" readonly="readonly" class="wu-text" /></td>
            </tr>
            <tr>
                <td width="60" align="right">用户名:</td>
                <td><input type="text" name="username" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写用户名称'" /></td>
            </tr>
            
            <tr>
                <td width="60" align="right">密码:</td>
                <td><input type="password" name="password" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写密码'" /></td>
            </tr>
            
            <tr>
                <td width="60" align="right">所属角色:</td>
                <td>
                	<select name="roleId" class="easyui-combobox" panelHeight="auto" style="width:268px" data-options="required:true, missingMessage:'请选择角色'">
		                <c:forEach items="${roleList}" var="role">
		                <option value="${role.id}">${role.name}</option>
		                </c:forEach>
		            </select>
                </td>
            </tr>
            
             <tr>
                <td width="60" align="right">性别:</td>
                <td>
                	<select name="sex" class="easyui-combobox" panelHeight="auto" style="width:268px">
		            	<option value = "0">未知</option>
		            	<option value = "1" >男</option>
		            	<option value = "2">女</option>
		            </select>
                </td>
            </tr>
            
            <tr>
                <td width="60" align="right">年龄:</td>
                <td><input type="text" name="age" class="wu-text easyui-numberbox easyui-validatebox" data-options="required:true, min:1, precision:0, missingMessage:'请填写年龄'" /></td>
            </tr>
            
            <tr>
                <td width="60" align="right">地址:</td>
                <td><input type="text" name="address" class="wu-text easyui-validatebox"/></td>
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
                <td width="60" align="right">头像预览:</td>
                <td valign="middle">
                	<img id="edit-preview-photo" style="float:left;" src="/BaseProjectSSM/resources/admin/easyui/images/user_avatar.jpg" width="100px">
                	<a style="float:left; margin-top: 40px" href="javascript:void(0)" class = "easyui-linkbutton" iconCls="icon-folder" onclick="uploadPhoto()" plain="true">上传头像</a>
                </td>
            </tr>
            <tr>
                <td width="60" align="right">头像:</td>
                <td><input type="text" id = "edit-photo" name="photo" value="/BaseProjectSSM/resources/admin/easyui/images/user_avatar.jpg" readonly="readonly" class="wu-text" /></td>
            </tr>
            <tr>
            <tr>
                <td width="60" align="right">用户名:</td>
                <td><input type="text" id="edit-username" name="username" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写用户名称'" /></td>
            </tr>
            
            <tr>
                <td width="60" align="right">所属角色:</td>
                <td>
                	<select name="roleId" id="edit-roleId" class="easyui-combobox" panelHeight="auto" style="width:268px" data-options="required:true, missingMessage:'请选择角色'">
		                <c:forEach items="${roleList}" var="role">
		                <option value="${role.id}">${role.name}</option>
		                </c:forEach>
		            </select>
                </td>
            </tr>
            
             <tr>
                <td width="60" align="right">性别:</td>
                <td>
                	<select name="sex" id="edit-sex" class="easyui-combobox" panelHeight="auto" style="width:268px">
		            	<option value = "0">未知</option>
		            	<option value = "1" >男</option>
		            	<option value = "2">女</option>
		            </select>
                </td>
            </tr>
            
            <tr>
                <td width="60" align="right">年龄:</td>
                <td><input type="text" id="edit-age" name="age" class="wu-text easyui-numberbox easyui-validatebox" data-options="required:true, min:1, precision:0, missingMessage:'请填写年龄'" /></td>
            </tr>
            
            <tr>
                <td width="60" align="right">地址:</td>
                <td><input type="text" id="edit-address" name="address" class="wu-text easyui-validatebox"/></td>
            </tr>
        </table>
    </form>
</div>
<div id="process-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-upload', title:'正在上传头像'" style="width:450px; padding:10px;">
	<div id="p" class="easyui-progressbar" style="width: 400px;" data-options="text:'头像上传中......'"></div>
</div>
<input type="file" id = "photo-file" style="display:none;" onchange="upload()">
<%@include file="../common/footer.jsp" %>
<!-- End of easyui-dialog -->
<script type="text/javascript">
	/**
	* 上传头像
	*/
    function start(){
        var value = $('#p').progressbar('getValue');
        if (value < 100){
            value += Math.floor(Math.random() * 10);
            $('#p').progressbar('setValue', value);
        } else {
        	$('#p').progressbar('setValue', 0)
		}
    };
    
	function upload() {
		// 使用 Ajax 方式上传头像
		if ($("#photo-file").val() == "") return;
		var formData = new FormData(); // 主要用于异步上传文件或提交表单数据
		formData.append("photo", document.getElementById('photo-file').files[0]);
		$("#process-dialog").dialog("open") // .dialog() 方法就是用来将一个 HTML 元素转换成一个可交互的对话框（弹窗）
		var interval = setInterval(start, 200);
		$.ajax({
			url: "upload_photo", // 请求的 URL 地址
			type: "post",
			data: formData, // 发送到服务器的数据
			contentType: false, // 设置请求头的 Content-Type. 设置为 false 时，jQuery 不会设置 Content-Type 请求头。对于 FormData 对象，浏览器会自动设置正确的 Content-Type (通常是 multipart/form-data)，并包含正确的边界字符串。因此，在使用 FormData 时，必须将 contentType 设置为 false，否则会导致上传失败
			processData: false, // 是否对数据进行序列化处理. 设置为 false 时，jQuery 不会对 data 选项中提供的数据进行序列化处理。对于 FormData 对象，也必须设置为 false，因为 FormData 对象已经包含了正确的格式，不需要 jQuery 再进行处理。如果设置为 true，jQuery 会尝试将 FormData 对象转换为字符串，导致上传失败。
			success: function(data) {
				clearInterval(interval);
				$("#process-dialog").dialog("close")  // 关闭进度条
				if (data.type == 'success') {
					$("#preview-photo").attr("src", data.filePath) // 后台的 filePath
					$("#add-photo").val(data.filePath);
					$("#edit-preview-photo").attr("src", data.filePath) // 后台的 filePath
					$("#edit-photo").val(data.filePath);
				} else {
					$.messager.alert("消息提醒", data.msg, "warning");
				}
			},
			error: function() {
				clearInterval(interval);
				$("#process-dialog").dialog("close")  // 关闭进度条
				$.messager.alert("消息提醒", "上传失败", "warning");
			}
		})
	}

	function uploadPhoto() {
		// 打开文件选择窗口
		$("#photo-file").click();
	}

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
					$('#add-dialog').dialog('close');
					$('#data-datagrid').datagrid('reload')
				} else {
					$.messager.alert('信息提示', data.msg, 'warning');
				}
			}
		});
	}
	
	/**
	* 修改记录
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
			}
		});
	}
	
	/**
	* 删除记录
	*/
	function remove(){
		$.messager.confirm('信息提示','确定要删除该记录？', function(result){
			if(result){
				var item = $('#data-datagrid').datagrid('getSelections');  // 获取到选中的那一行
				if (item == null || item.length == 0) {
					$.messager.alert('讯息提示', '请选择要删除的数据', 'info');
					return;
				}
				var ids = "";
				for (var i = 0; i < item.length; i++) {
					ids += item[i].id + ",";
				}
				$.ajax({
					url:'delete',
					dataType:'json',
					type:'post',
					data:{ids: ids},
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
	* 打开添加窗口
	*/
	function openAdd(){
		// $('#add-form').form('clear');
		$('#add-dialog').dialog({
			closed: false,
			modal:true,
            title: "添加用户信息",
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
		        // 重置头像默认值
	            var defaultAvatar = "/BaseProjectSSM/resources/admin/easyui/images/user_avatar.jpg";
	            $("#preview-photo").attr("src", defaultAvatar); // attr 设置元素的属性
	            $("input[name='photo']").val(defaultAvatar);
			}
        });
	}
	
	/**
	* 打开修改窗口
	*/
	function openEdit(){
		// $('#edit-form').form('clear');
		var item = $('#data-datagrid').datagrid('getSelections');  // 获取到选中的那一行
		if (item == null || item.length == 0) {
			$.messager.alert('讯息提示', '请选择要修改的数据', 'info');
			return;
		}
		if (item.length > 1) {
			$.messager.alert('讯息提示', '请选择一条数据进行修改', 'info');
			return;
		}
		item = item[0];
		$('#edit-dialog').dialog({
			closed: false,
			modal:true,
            title: "修改用户信息",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: edit
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#edit-dialog').dialog('close');                    
                }
            }],
            onBeforeOpen: function() {
				$("#edit-id").val(item.id);
				$("#edit-preview-photo").attr("src", item.photo)
				$("#edit-photo").val(item.photo);
				$("#edit-username").val(item.username);
				$("#edit-roleId").combobox("setValue", item.roleId);
				$("#edit-sex").combobox("setValue", item.sex);
				 // $("#edit-age").val(item.age); 错误写法  EasyUI 的 numberbox 組件需要特殊方式初始化，而不能直接使用 val() 方法。
				$("#edit-age").numberbox("setValue", item.age);
				$("#edit-address").val(item.address);
			},
        });
	}
	
	/**
	* 搜索按钮监听
	*/
	$("#search-btn").click(function() {
		var roleId = $("#search-role").combobox("getValue");
		var sex = $("#search-sex").combobox("getValue");
		var option = {username: $("#search-name").val()};
		if (roleId != -1) {
			option.roleId = roleId;
		}
		// json 可以这样直接添加字段
		if (sex != -1) {
			option.sex = sex;
		}
		$("#data-datagrid").datagrid("reload", option)
	});
	
	/**
	* 载入数据
	*/
	$('#data-datagrid').datagrid({
		url:'list',
		rownumbers:true,         // 显示行号
		singleSelect:false,      // 单选还是多选（true 单选， false 多选）
		pageSize:20,             // 每页条数         
		pagination:true,         // 分页
		multiSort:true,
		fitColumns:true,
		idField: 'id',
	    treeField: 'name',
		fit:true,
		columns:[[
			{ field:'chk',checkbox: true},
			{ field:'photo',title:'用户头像',width:100, align:'center', formatter:function(value, row, index) {
				var img = "<img src='" + value + "' width='50px' />";
				return img;
			}},
			{ field:'username',title:'用户名',width:100,sortable:true},
			{ field:'password',title:'密码',width:180},
			{ field:'roleId',title:'所属角色',width:100, formatter:function(value, row, index) {
				var roleList = $("#search-role").combobox("getData");
				for (var i = 0; i < roleList.length; i++) {
					if (value == roleList[i].value)
						return roleList[i].text;
				}
				return value;
			}},
			{ field:'sex',title:'性别',width:100, formatter:function(value, row, index) {
				switch(value) {
					case "0": {
						return "未知";
					}
					case "1": {
						return "男";
					}
					case "2": {
						return "女";
					}
				}
				return value;
			}},
			{ field:'age',title:'年龄',width:100},
			{ field:'address',title:'地址',width:200},
		]],
		onLoadSuccess: function(data) {
			$('.authority-edit').linkbutton({text:'编辑权限', plain:true, iconCls:'icon-edit'})
		}
	});
</script>