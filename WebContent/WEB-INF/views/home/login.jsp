<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="common/header.jsp" %>
	<style>
		.tm_login_body{
			background:url('../resources/home/images/001.jpg');
			background-size:cover;
			-moz-background-size:cover;
			background-repeat:no-repeat;
		}
		.tm_login_container{ width:500px; margin:200px auto; clear:both}
		.tm_login_title{
			height:80px;
			margin:10px 0 15px 0;
			background:#fff;
			text-align:center;
			border-bottom:solid 1px #eee;
		}
		.tm_login_title img{
			height:50px;
		}
		.tm_login_title span{
			font-size:22px; 
			line-height:80px;
			font-family:'Microsoft Yahei',Tahoma, Geneva, 'Simsun';
		}
		.tm_login_form{ 
			width:100%; 
			height:320px;
			clear:both; 
			-moz-border-radius:8px;
			-webkit-border-radius:8px;
			border-radius:8px;
			padding:1px;
			background:#fff;
		}
		.tm_login_table{ width:400px; margin:20px auto;}
		.tm_login_table tr th{ width:100px;}
		.tm_login_table tr td{ width:300px; text-align:left}

		.tm_login_title_table{ width:400px; margin:0px auto;}
		.tm_login_title_table tr th{ width:100px;}
		.tm_login_title_table tr td{ width:300px; text-align:left}
		
		.tm_login_foot{ width:100%; line-height:20px; text-align:center; clear:both; margin:20px 0}
		
		
		html { overflow: hidden; } 
		body { overflow: hidden; } 

		.layui-layer-btn{text-align:center !important;}
	</style>
  
<body class="tm_login_body">

	<div class="tm_login_container">
    	<div class="tm_login_form">
			<div class="tm_login_title">
				<table border="0" cellpadding="0" cellspacing="0" class="tm_login_title_table">
					<tbody>
					<tr>
						<th><img src="../resources/home/images/logo_min.png" align="absmiddle"></th>
						<td><span>OnlineExam考试系统</span></td>
					</tr>
				</tbody></table>
			</div>
            <table border="0" cellpadding="5" cellspacing="0" class="tm_login_table">
                <tbody><tr>
                    <th>用户名</th>
                    <td><input type="text" class="tm_txt" name="name" maxlength="20" value="" style="width:255px"></td>
                </tr>
                <tr>
                    <th>密 &nbsp; 码</th>
                    <td><input type="password" class="tm_txt" name="password" maxlength="20" value="" style="width:255px"></td>
                </tr>
				
                <tr>
                    <th></th>
                    <td>
						<div style="margin-top:10px">
							<button type="button" class="tm_btn tm_btn_primary" style="width:50%" onclick="tm.doLogin();">登录</button>
							<button type="button" class="tm_btn" onclick="tm.goRegister();" style="width:40%">注册</button>
						</div>
                    </td>
                </tr>
                <tr>
                    <th></th>
                    <td></td>
                </tr>
            </tbody></table>
        </div>
        <%@include file="common/footer.jsp" %>
	</div>

	<script type="text/javascript">
		$(document).ready(function(){ 
			checkBrowser();
		}); 

		function getBrowserInfo(){
			try{
				var Sys = {};
				var ua = navigator.userAgent.toLowerCase();
				console.log(ua);
				var re =/(msie|trident|firefox|chrome|opera|version).*?([\d.]+)/;
				var m = ua.match(re);
				Sys.browser = m[1].replace(/version/, "'safari");
				Sys.ver = m[2];
				return Sys;
			}catch(e){}
		}

		function checkBrowser(){
			var tmBrowser = getBrowserInfo();
			var isSupportedBrowser = false;
			if(tmBrowser){
				if(tmBrowser.browser == "firefox" || tmBrowser.browser == "chrome"){
					isSupportedBrowser = true;
				}
			}
			if(!isSupportedBrowser){
				layer.open({
					title:"浏览器提示", 
					content:"为达到最佳使用效果，请使用Chrome、FireFox访问系统。",
					btnAlign: "c"
				});
			}
		}


		var tm = {
			doReset : function(){
				$("input[name='name']").val('');
				$("input[name='password']").val('');
			},
			goRegister : function(){
				window.location="register";
			},
			doLogin : function(){
				var username = $("input[name='name']").val();
				var userpass = $("input[name='password']").val();
				if(baseutil.isEmpty(username)){
					alert('没有填写用户名');
					return;
				}
				if(baseutil.isEmpty(userpass)){
					alert('没有填写登录密码');
					return;
				}
				$(".tm_btn_primary").text('登录...');
				
				$.ajax({
					type: "POST",
					url: "login",
					dataType: "json",
					data: {"name":username, "password":userpass},
					success: function(data){
						if(data.type == 'success'){
							window.location="user/index";
						}else{
							alert(data.message);
							//$(".tm_btn_primary").text('提交');
							//return;
							window.location.reload();
						}
					},
					error: function(){
						//$(".tm_btn_primary").text('登录');
						alert('系统忙，请稍后再试');
						window.location.reload();
					}
				}); 

			}
		};
	</script>



</body></html>