$(document).ready(function() {
	tenantQuery();
	
	
});

var tenantColumns =
	[ {
		field : 'renterId',
		title : '商户/租户编号',
		width : '20%',
		align : 'center',
		/*formatter : function(value, row, index) {
			return index + 1;
		}*/
	}, {
		field : 'renterName',
		title : '商户/租户名称或姓名',
		align : 'center'
	}, {
		field : 'businessRange',
		title : '经营种类',
		align : 'center'
	},{
		field : 'renterId',
		title : '操作',
		align : 'center',
		formatter : 'operateFormatter'
	}];
function operateFormatter(value, row, index) {
	return [
			'<a class="edit" href="javascript:void(0)" title="编辑" style="color:#f0ad4e" onclick="updateTenantInfo(\'' + value + '\')">',
			'<i class="glyphicon glyphicon-edit"></i>',
			'</a>' 
					+ "&nbsp;&nbsp;&nbsp;&nbsp;"
					+ '<a class="remove" href="javascript:void(0)" title="删除" onclick="deleteTenant(' + value + ')">',
			'<i class="glyphicon glyphicon-remove"></i>', '</a>' ].join('');
}

function tenantQuery() {
	$('#tenantTable').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tenantTable").bootstrapTable( {
		method : "get", // 使用get请求到服务器获取数据
		contentType : "application/x-www-form-urlencoded",
		url : "../tenant/getAll", // 获取数据的Servlet地址
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
		columns : tenantColumns,
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
		responseHandler: function(data) {
            return {
                "total": data.total,//总页数
                "rows": data.list   //数据
             };
        },
		onLoadSuccess : function() { // 加载成功时执行
			//successSwal("加载成功");
			
		},
		onLoadError : function() { // 加载失败时执行
			//errorSwal("加载数据失败");
		}
	});
}

function addTenant(){
	$("#point-title").html("添加租户");
	$('#addTenant').modal( {
		backdrop : 'static'
	});
}

function closeTenant(){
	$("#renterId").val("");
	$("#renterName").val("");
	$("#idPhoto").val("");
	$("#name").val("");
	$("#phone").val("");
	$("#email").val("");
	$("#businessRange").val("");
	$("#id1").val("");
	$("#id1Pic").val("");
	$("#id2").val("");
	$("#id2Pic").val("");
	$("#id3").val("");
	$("#id3Pic").val("");
	$("#renterId").attr("disabled","false");
	$('#addTenant').modal('hide');
}

function updateTenantInfo(renterId){
	$.ajax({
		url:'../tenant/getOneByRenterId',
		data:{'renterId':renterId},
		type:'get',
		//dataType:'json',
		success:function(data1){
			var data = eval("("+data1+")");
			$("#renterId").val(data.renterId);
			$("#renterName").val(data.renterName);
			$("#idPhoto").val(data.idPhoto);
			$("#name").val(data.name);
			$("#phone").val(data.phone);
			$("#email").val(data.email);
			$("#businessRange").val(data.businessRange);
			$("#id1").val(data.id1);
			$("#id1Pic").val(data.id1Pic);
			$("#id2").val(data.id2);
			$("#id2Pic").val(data.id2Pic);
			$("#id3").val(data.id3);
			$("#id3Pic").val(data.id3Pic);
		},
		error:function(data){
			swal('错误', '加载失败！', 'error');
		}
	});
	
	
	$('#addTenant').modal( {
		backdrop : 'static'
	});
	$("#point-title").html("编辑租户");
	$("#renterId").attr("disabled","true");
}

function isRepeat(){
	var id = 0;
	$.ajax({
		url:'../tenant/isRepeat',
		data:{'rentId':$("#renterId").val()},
		type:'get',
		success:function(data){
			//id = data;
			if(data >= 1){
				swal('错误', '此租户已存在！', 'error');
			}
		}
	});
	return id;
}

function insertTenant(){
	if(!($("#renterId").val())){
		swal('错误', '租户编号不得为空！', 'error');
		return;
	}
	if($("#point-title").html() == "添加租户"){
		if(isRepeat() >= 1){
			return;
		}
		
		$.ajax( {
			type : 'post',
			url : '../tenant/addTenant',
			data : $('#addForm').serialize(),
			dataType : 'json',
			success : function(data) {
				if(data==1){
					closeTenant();
					swal('正确', '添加成功', 'success');
					tenantQuery();
				}else{
					swal('错误', '添加失败！', 'error');
				}
				
			},
			error : function() {
				swal('错误', '添加失败！', 'error');
			}
		});
	}else if($("#point-title").html() == "编辑租户"){
		alert("1212dewr");
		$.ajax( {
			type : 'post',
			url : '../tenant/editTenant',
			data : $('#addForm').serialize(),
			dataType : 'json',
			success : function(data) {
				if(data==1){
					closeTenant();
					swal('正确', '编辑成功', 'success');
					tenantQuery();
				}else{
					swal('错误', '编辑失败！', 'error');
				}
				
			},
			error : function() {
				swal('错误', '编辑失败！', 'error');
			}
		});
	}
	
	
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

function deleteUser(rentId){
	swal( {
		title : "删除租户?",
		text : "你确定要删除这个租户吗？",
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
				url: '../tenant/deleteTenant',
				data:{"rentId":rentId},
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