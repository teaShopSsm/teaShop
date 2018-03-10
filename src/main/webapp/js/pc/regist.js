function regist() {
    var userName = $('#username').val();
    if (!userName) {
        swal('警告','请输入用户名','warning');
        return;
    }
    var password = $('#password').val();
    if (!password) {
        swal('警告','请输入密码','warning');
        return;
    }
    password = $.md5(password.trim());
    if(password.lenght>12||password.lenght<6){
        errorSwal('密码介于6到12位');
        return;
    }
    var passwd = $('#passwd').val();
    passwd = $.md5(passwd.trim());
    if (passwd != password) {
        swal('警告','两次密码不一样','warning');
        return;
    }
    var phone = $('#phone').val();
    var email = $('#email').val();
    if(!checkPhone(phone)){
        swal('警告','请输入正确格式的手机号','warning');
        return;
    }
    if(!checkMail(email)){
        swal('警告','请输入正确格式的邮箱','warning');
        return;
    }

    $.ajax( {
        type : 'post',
        url : '/TeaShop/register',
        data : {
            userName : userName,
            password : password,
            passwd : passwd,
            email : email,
            phone : phone,
            userType : 3
        },
        dataType : 'json',
        success : function(data) {
            var flag = data.flag;
            if (flag < 0) {
                errorSwal(data.message);
                return;
            } else {
                successSwal("恭喜你注册成功，两秒后跳转。。。。");
                setTimeout(function(){//两秒后跳转
                    location.href = "login.html";//PC网页式跳转
                },2000);

            }
        },
        error : function() {
            errorSwal('添加用户失败');
        }
    });
}
function checkPhone(phone) {
    var phoneReg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1}))+\d{8})$/;
    if (!phone || !phoneReg.test(phone)) {
        return false;
    }else {
        return true;
    }
}
/*
 * 对用户邮箱进行校验
 */
function checkMail(email) {
    var emailReg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
    if (!email || !emailReg.test(email)) {
        return false;
    }else {
        return true;
    }
}