$(function(){
		$("#username").blur(function(){
			var username = $("#username").val();
			if(username != null && username.length > 0 ){
				// $.ajax({
				// 	url:'/TeaShop/User/isHave',
				// 	data:{'username':username},
				// 	type:'get',
				// 	dataType:'json',
				// 	success:function(data){
				// 		if(data < 1){
				// 			swal('错误', '此用户不存在！', 'error');
				// 		}
				// 	},
				// 	error:function(data){
				// 		console.log(data);
				// 	}
				// });
				
			}else{
				swal('错误', '请输入账号！', 'error');
			}
		});
		
		
	});

function login(){
	var username = $("#username").val();
	var password = $("#password").val();
	if(username != null && username.length > 0){
		 if(password != null && password > 0){
			 // $.ajax({
				// 	url:'/TeaShop/User/isHave',
				// 	data:{'username':username},
				// 	type:'get',
				// 	success:function(data){
				// 		console.log(data);
				// 		if(data < 1){
				// 			swal('错误', '此用户不存在！', 'error');
				// 		}else{
				//
				// 		}
				// 	}
				// });
             $.ajax({
                 url:'/TeaShop/login1',
                 data:{'username':username,"password":password},
                 type:'post',
                 dataType:'json',
                 success:function(data){
                     if(data.flag == 0){
                         //swal('成功', '登陆成功！！', 'success');
                         window.location.href="starter.html";
                     }else if(data.flag == 1){//顾客登陆
                         window.location.href="pc/web/index_web.html";
					 }else{
                         swal('错误', '账号或密码错误！', 'error');
                     }
                 }
             });
		 }else{
			 swal('错误', '请输入密码！', 'error');
		 }
		
	}else{
		swal('错误', '请输入账号！', 'error');
	}
	

}
