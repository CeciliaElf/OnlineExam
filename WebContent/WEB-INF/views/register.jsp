<script type="text/javascript">
$(document).ready(function(){ 
    tm_bindPasswordLevelChecker("u_userpass");
}); 

var tm = {
    doGoBack : function(){
        window.location.href = 'login';
    },

    doRegister : function(){
        var formcheck = $("#form_register").validationEngine('validate', {showOneMessage: true});

        if(!formcheck){
            return false;
        }

        var u_username = $("input[name='name']").val();
        var u_userpass = $("input[name='password']").val();
        var u_realname = $("input[name='truename']").val();
        var u_subjectId = $("select[name='subjectId']").val();
        var u_tel = $("input[name='tel']").val();

        if(baseutil.isEmpty(u_username)){
            alert('没有填写用户名');
            return;
        }
        if(baseutil.isEmpty(u_userpass)){
            alert('没有填写登录密码');
            return;
        }

        if(baseutil.containsSpecialWord(u_username)){
            alert("用户名中不能包含特殊字符");
            return;
        }

        $(".tm_btn_primary").text('提交...');
        var tmdata = {
            "name": u_username, 
            "password": tm.doencrypt(u_userpass), 
            "subjectId": u_subjectId, 
            "u_tel": u_tel, 
            "truename": u_realname, 
            "t": Math.random()
        };
        
        $.ajax({
            type: "POST",
            url: "register",
            dataType: "json",
            data: tmdata,
            success: function(data){
                if(data.type == 'success'){
                    window.location = "login.html";
                } else {
                    alert(data.msg);
                    window.location.reload();
                }
            },
            error: function(){
                alert('系统忙，请稍后再试');
                window.location.reload();
            }
        }); 
    }
};
</script> 