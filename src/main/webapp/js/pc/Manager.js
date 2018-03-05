$(document).ready(function() {
	managerQuery();
});

var managerColumns =
	[ {
		field : 'id',
		title : '序号',
		width : '10%',
		align : 'center',
		formatter : function(value, row, index) {
			return index + 1;
		}
	}, {
		field : 'name',
		title : '姓名',
		align : 'center'
	}, {
		field : 'userName',
		title : '账号',
		align : 'center'
	},{
		field : 'createDate',
		title : '创建时间',
		align : 'center'
	},{
		field : 'email',
		title : '邮箱',
		align : 'center'
	},{
		field : 'phone',
		title : '电话',
		align : 'center'
	},{
		field : 'userId',
		title : '操作',
		align : 'center',
		formatter : 'operateFormatter'
	}];
function operateFormatter(value, row, index) {
	return [
			'<a class="edit" href="javascript:void(0)" title="编辑" style="color:#f0ad4e" onclick="modifyUserInfo(' + value + ')">',
			'<i class="glyphicon glyphicon-edit"></i>',
			'</a>' 
					+ "&nbsp;&nbsp;&nbsp;&nbsp;"
					+ '<a class="remove" href="javascript:void(0)" title="删除" onclick="deleteUser(' + value + ')">',
			'<i class="glyphicon glyphicon-remove"></i>', '</a>' ].join('');
}

function managerQuery() {
	$('#managerTable').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#managerTable").bootstrapTable( {
		method : "get", // 使用get请求到服务器获取数据
		contentType : "application/x-www-form-urlencoded",
		url : "../User/queryManager", // 获取数据的Servlet地址
		toolbar: '#toolbar',
		striped : true, // 表格显示条纹
		pagination : true, // 启动分页
		pageSize : 10, // 每页显示的记录数
		pageNumber : 1, // 当前第几页
		pageList : [ 10, 20,50, 100, 200 ], // 记录数可选列表
		search : false, // 是否启用查询
		showColumns : true, // 显示下拉框勾选要显示的列
		showRefresh : true, // 显示刷新按钮
		sidePagination : "server", // 表示服务端请求
		columns : managerColumns,
		// 设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
		// 设置为limit可以获取limit, offset, search, sort, order
		queryParamsType : "undefined",
		queryParams : function queryParams(params) { // 设置查询参数
			var param = {
				pageNumber : params.pageNumber,
				pageSize : params.pageSize
			};
			return param;
		},
		onLoadSuccess : function() { // 加载成功时执行
			//successSwal("加载成功");
		},
		onLoadError : function() { // 加载失败时执行
			//errorSwal("加载数据失败");
		}
	});
}

function AddUser(){
	$('#AddUser').modal( {
		backdrop : 'static'
	});
}

function closeNewUser(){
	$("#UserNameInsert").val("");
	$("#passwordInsert").val("");
	$("#nameInsert").val("");
	$("#EmailInsert").val("");
	$("#PhoneInsert").val("");
	$('#AddUser').modal('hide');
}

function confirmNewUser(){
	var UserNameInsert = $("#UserNameInsert").val();
	var passwordInsert=$("#passwordInsert").val();
	var nameInsert=$("#nameInsert").val();
	var EmailInsert=$("#EmailInsert").val();
	var PhoneInsert=$("#PhoneInsert").val();
	
	if(!UserNameInsert){
		alert("账号不能为空");
		return;
	}
	if(!passwordInsert){
		alert("密码不能为空");
		return;
	}
	if(!nameInsert){
		alert("姓名不能为空");
		return;
	}
	if(!EmailInsert){
		alert("邮箱不能为空");
		return;
	}
	if(!PhoneInsert){
		alert("手机不能为空");
		return;
	}
	
	$.ajax( {
		type : 'get',
		url : '../User/confirmNewUser',
		data : {
			UserName : UserNameInsert,
			password:passwordInsert,
			name:nameInsert,
			Email:EmailInsert,
			Phone:PhoneInsert
		},
		dataType : 'json',
		success : function(data) {
			if(data==1){
				closeNewUser();
				managerQuery();
			}else{
				alert("添加失败");
			}
			
		},
		error : function() {
			alert("添加失败");
		}
	});
}

function modifyUserInfo(user_id){
	 $.ajax({
			url: '../User/SelectUserInfo',
			data:{"user_id":user_id},
			type: 'GET',
			dataType : 'json',
			success: function(data){
				$("#UserNamemodify").val(data.userName);  
				$("#namemodify").val(data.name);  
				$("#Emailmodify").val(data.email);  
				$("#Phonemodify").val(data.phone);  
				$("#user_id").val(user_id);
				$('#modifyUser').modal( {
					backdrop : 'static'
				});
			},error:function(){
				swal({
					title : '错误',
					text : '请重试！',
					type : 'error',
					timer : 2000,
					closeOnConfirm : false
				});
			}
		});//ajax 
}

function closemodifyUser(){
	$("#UserNamemodify").val("");  
	$("#namemodify").val("");  
	$("#Emailmodify").val("");  
	$("#Phonemodify").val("");  
	$('#modifyUser').modal('hide');
}

function confirmmodifyUser(){
	var UserNamemodify=  $("#UserNamemodify").val();  
	var namemodify =$("#namemodify").val();  
	var Emailmodify= $("#Emailmodify").val();  
	var Phonemodify=$("#Phonemodify").val();  
	var userId= $("#user_id").val();
	if(!UserNamemodify){
		alert("账号不能为空"); 
		return; 
	}
	if(!namemodify){
		alert("姓名不能为空"); 
		return; 
	}
	if(!Emailmodify){
		alert("邮箱不能为空"); 
		return; 
	}
	if(!Phonemodify){
		alert("电话不能为空"); 
		return; 
	}
	$.ajax( {
		type : 'get',
		url : '../User/confirmmodifyUser',
		data : {
			UserName : UserNamemodify,
			name:namemodify,
			Email:Emailmodify,
			Phone:Phonemodify,
			userId:userId
		},
		dataType : 'json',
		success : function(data) {
			if(data==1){
				closemodifyUser();
				managerQuery();
			}else{
				alert("更新失败");
			}
			
		},
		error : function() {
			alert("更新失败");
		}
	});
}

function deleteUser(user_id){
	swal( {
		title : "删除用户?",
		text : "你确定要删除这个用户吗？",
		type : "warning",
		showCancelButton : true,
		confirmButtonColor : '#DD6B55',
		confirmButtonText : '确定删除!',
		cancelButtonText : "取消!",
		closeOnConfirm : false,
		closeOnCancel : true
	}, function(isConfirm) {
		if (isConfirm) {
			$.ajax( {
				type : 'post',
				url: '../User/deleteUser',
				data:{"userId":user_id},
				dataType : 'json',
				success:function(value){
					if(value==1){
						managerQuery();		
						swal({
							title : '成功',
							text : '删除成功',
							type : 'success',
							timer : 800,
							closeOnConfirm : false
						});
					}else{
						swal({
							title : '错误',
							text : '删除失败,请重试',
							type : 'error',
							timer : 2000,
							closeOnConfirm : false
						});
					}
					
				},
				error:function(){
					swal({
						title : '错误',
						text : '删除失败,请重试',
						type : 'error',
						timer : 2000,
						closeOnConfirm : false
					});
				}
			});
		} else {
			swal('取消', '角色没有删除', 'error');
		}
	});
}