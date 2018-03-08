var roleIdQuery = -1;

var roleColumns =
[ {
	field : 'id',
	title : '序号',
	width : '10%',
	align : 'center',
	formatter : function(value, row, index) {
		return index + 1;
	}
}, {
	field : 'roleName',
	title : '角色名称',
	align : 'center'
}, {
	field : 'description',
	title : '描述',
	align : 'center'
}, {
	field : 'roleId',
	title : '操作',
	align : 'center',
	width : '10%',
	formatter : 'operateFormatter'
} ];
var roleMenuColumns =[ {
	field : 'id',
	title : '序号',
	width : '10%',
	align : 'center',
	formatter : function(value, row, index) {
		return index + 1;
	}
}, {
	field : 'pageName',
	title : '菜单名称',
	align : 'center'
}, {
	field : 'pageId',
	title : '菜单ID',
	align : 'center'
}, {
	field : 'description',
	title : '描述',
	align : 'center'
}, 
{
	field : 'url',
	title : '菜单路径',
	align : 'center'
},{
	field : 'pageId',
	title : '操作',
	align : 'center',
	width : '10%',
	formatter : 'operateRoleMenuFormatter'
} ];
	
function operateFormatter(value, row, index) {
	return [
			'<a class="edit" href="javascript:void(0)" title="编辑" style="color:#f0ad4e" onclick="showRoleInfo(' + value + ')">',
			'<i class="glyphicon glyphicon-edit"></i>',
			'</a>'
					+ "&nbsp;&nbsp;&nbsp;&nbsp;"
					+ '<a class="menu" href="javascript:void(0)" title="菜单" style="color:#f0ad4e" onclick="showRoleMenu('+ value + ')">',
			'<i class="glyphicon glyphicon-book"></i>',
			'</a>' 
					+ "&nbsp;&nbsp;&nbsp;&nbsp;"
					+ '<a class="remove" href="javascript:void(0)" title="删除" style="color:#f0ad4e"  onclick="deleteRole(' + value + ')">',
			'<i class="glyphicon glyphicon-trash"></i>', '</a>' ].join('');
}

function operate(value){
	var row = $("#roleMenu").jqxTreeGrid('getRow', value);
	if(!row.url){
		return '';
	}else{
		if(row.flag == 0){
			return '<a href="#" id="pageId_'+value+'"  onclick="bindRoleMenu(\'' + value + '\')"><font color="red">绑定</font></a>';
		}else {
			return '<a href="#" id="pageId_'+value+'"  onclick="unBindRoleMenu(\'' + value + '\')"><font color="black">解绑</font></a>';
		}
	}
}


$(document).ready(function() {
	initTable();
	roleQuery();
});

function initTable(){
	$('#roleTable').bootstrapTable('destroy');
	$('#roleTable').on('page-change.bs.table', function(e, number, size) {
		roleQuery();
	});
	// 初始化表格,动态从服务器加载数据
	$("#roleTable").bootstrapTable({
		 height:getHeight(),
		striped : true, // 表格显示条纹
		pagination : true, // 启动分页
		pageSize : 10, // 每页显示的记录数
		pageNumber : 1, // 当前第几页
		pageList : [ 10, 20, 50, 100, 200 ], // 记录数可选列表
		search : false, // 是否启用查询
		showColumns : true, // 显示下拉框勾选要显示的列
		showRefresh : true, // 显示刷新按钮
		sidePagination : "server", // 表示服务端请求
		columns : roleColumns,
		onLoadSuccess : function(value) {},
		onLoadError : function() {
			errorSwal("加载数据失败");
		}
	});
}

/**
 * 页面信息
 * 
 * @param {Object}
 *            pageNumber
 * @param {Object}
 *            pageSize
 */

function roleQuery() {
	var roleName = $('#roleNameQuery').val();
	var roleDesc = $('#descQuery').val();
	var opts = $("#roleTable").bootstrapTable('getOptions');
	var pageNumber= opts.pageNumber;
	var pageSize = opts.pageSize;
	$.ajax({
		url : '../role/blurryRole',
		type:'post',
		data:{
			roleName : roleName,
			roleDesc : roleDesc,
			pageNumber:pageNumber,
			pageSize:pageSize
		},
		dataType:'json',
		success:function(data){
			$('#roleTable').bootstrapTable('load', data);
		},
		error:function(e){
			errorSwal("加载数据失败");
		}
	});
}

/**
 * 打开插入对话框
 * 
 * @param {Object}
 *            roleId
 */
function insertRoleModel() {
	$('#roleMessageInsert').modal( {
		backdrop : 'static'
	});
}

/**
 * 关闭插入对话框
 * 
 * @param {Object}
 *            roleId
 */
function closeRoleInsertModal(roleId) {
	$('#roleMessageInsert').modal('hide');
}
/**
 * 显示角色信息 对话框
 */
function showRoleInfo(roleId) {
	$.ajax( {
		type : 'get',
		url : '../role/getRoleInfo',
		data : {
			roleId : roleId
		},
		dataType : 'json',
		success : function(data) {
			var flag = data.flag;
			if (flag < 0) {
				toastr.warning(data.message);
				return;
			}
			var obj = data.data;
			$('#roleId').val(obj.roleId);
			$('#roleName').val(obj.roleName);
			$('#roleDesc').val(obj.description);
			$('#roleMessage').modal( {
				backdrop : 'static'
			});
		},
		error : function() {
			toastr.error('获取角色信息失败!');
		}
	});

}
/**
 * 跟新角色信息
 */
function updateRoleMessage() {
	var roleId = $('#roleId').val();
	var roleName = $('#roleName').val();
	var description = $('#roleDesc').val();
	$.ajax( {
		type : 'post',
		url : '../role/updateRoleInfo',
		data : {
			roleId : roleId,
			roleName : roleName,
			roleDesc : description
		},
		dataType : 'json',
		success : function(data) {
			var flag = data.flag;
			if (flag < 0) {
				toastr.warning(data.message);
				return;
			}else{
				roleQuery();
				$('#roleId').val('');
				$('#roleName').val('');
				$('#roleDesc').val('');
				$('#roleMessage').modal('hide');
			}
			roleQuery();
			$('#roleMessage').modal('hide');
		},
		error : function() {
			errorSwal('获取角色信息失败!');
		}
	});
}
/**
 * 隐藏 角色信息对话框
 */
function closeRoleMessageoModal() {
	$('#roleId').val('');
	$('#roleName').val('');
	$('#roleDesc').val('');
	$('#roleMessage').modal('hide');
}

/**
 * 显示角色菜单信息
 * 
 * @param {Object}
 *            roleId
 */
function showRoleMenu(roleId) {
	roleIdQuery = roleId;
	$('#roleMenuId').val(roleId);
	getRoleMenus(roleId);
	$('#roleMenuMessage').modal('show');
}
/**
 * 获取用户的菜单信息
 */
function getRoleMenus(roleId){
	$.ajax({
		type : 'post',
		url : '../role/getRoleMenus',
		data : {
			roleId:roleId
		},
		dataType : 'json',
		success : function(data) {
			initPageInfoTable(data.data);
		},
		error : function(value) {
			errorSwal('获取信息失败');
		}
	});
}
/**
 * 格式化菜单信息
 * 
 * @param value
 * @param row
 * @returns
 */
function initPageInfoTable(value) {
	$('#roleMenuMessage .modal-body div').append('<div id="roleMenu"></div>')
    var source =
    {
        dataType: "json",
        dataFields: [
            { name: 'description', type: 'string' },
            { name: 'flag', type: 'number' },
            { name: 'pageName', type: 'string' },
            { name: 'url', type: 'string' },
            { name: 'pageId', type: 'number' },
            { name: 'prePageId', type: 'number' }
        ],
        hierarchy:
        {
            keyDataField: { name: 'pageId' },
            parentDataField: { name: 'prePageId' }
        },
        id: 'pageId',
        localData: value
    };
    var dataAdapter = new $.jqx.dataAdapter(source);
    $("#roleMenu").jqxTreeGrid(
    {
        width: 800,
        source: dataAdapter,
        ready: function (){
            $("#roleMenu").jqxTreeGrid('expandRow', '0');
        },
        columns : [{
			text : '名称',
			dataField : 'pageName',
			width : '20%'
		}, {
			text : '路径',
			dataField : 'url',
			width : '30%'
		}, {
			text : '描述',
			dataField : 'description',
			width : '30%'
		},{
			text : '操作',
			cellsalign : 'center',
			cellsrenderer : operate,
			width : '20%'
		}]
    });
}
/**
 * 关闭对话框
 */
function closeRoleMenuMessageModal() {
	$('#roleMenuMessage').modal('hide');
	$("#roleMenu").jqxTreeGrid('destroy');
}

/**
 * 添加系统菜单
 */
function insertRole() {
	var roleName = $('#roleNameInsert').val();
	var roleDesc = $('#roleDescInsert').val();
	if (!roleName) {
		errorSwal('请输入角色名称');
		return;
	}
	$.ajax( {
		type : 'post',
		url : '../role/addRole',
		data : {
			roleName : roleName,
			roleDesc : roleDesc
		},
		dataType : 'json',
		success : function(data) {
			var flag = data.flag;
			if (flag < 0) {
				errorSwal(data.message);
			} else {
				$('#roleMessageInsert').modal('hide');
				$('#roleNameInsert').val('');
				$('#roleDescInsert').val('');
				$('#roleMessageInsert').modal('hide');
				roleQuery();
			}
		},
		error : function() {
			errorSwal('添加失败');
		}
	});
}

/**
 * 给角色绑定菜单
 * 
 * @param {Object}
 *            pageId
 */
function bindRoleMenu(pageId) {
	var roleId = $('#roleMenuId').val();
	$.ajax( {
		type : 'post',
		url : '../role/addRolePage',
		data : {
			roleId : roleId,
			pageId : pageId
		},
		dataType : 'json',
		success : function(data) {
			var flag = data.flag;
			if (flag == 0) {
				var row = $("#roleMenu").jqxTreeGrid('getRow', pageId);
                row.flag = 1;
                $("#roleMenu").jqxTreeGrid('updateRow', pageId, row);
			} else {
				errorSwal(data.message);
			}
		},
		error : function() {
			swal('错误', '绑定失败', 'error');
		}
	});
}

/**
 * 给角色解绑菜单
 * 
 * @param {Object}
 *            pageId
 */
function unBindRoleMenu(pageId) {
	var roleId = $('#roleMenuId').val();
	$.ajax( {
		type : 'post',
		url : '../role/deleteRolePage',
		data : {
			roleId : roleId,
			pageId : pageId
		},
		dataType : 'json',
		success : function(data) {
			var flag = data.flag;
			if (flag == 0) {
				var row = $("#roleMenu").jqxTreeGrid('getRow', pageId);
                row.flag = 0;
                $("#roleMenu").jqxTreeGrid('updateRow', pageId, row);
				} else {
					errorSwal(data.message);
			}
		},
		error : function() {
			swal('错误', '绑定失败', 'error');
		}   
	});
}
/**
 * 删除角色
 * 
 * @param {Object}
 *            roleId
 */
function deleteRole(roleId) {
	if (!roleId) {
		errorSwal('请选择角色!');
		return;
	}
	swal( {
		title : "删除角色?",
		text : "你确定要删除这个角色吗？",
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
				url : '../role/deleteRole',
				data : {
					roleId : roleId
				},
				dataType : 'json',
				success : function(data) {
					var flag = data.flag;
					if (flag == 0) {
						roleQuery();
						swal('成功', '删除成功', 'success');
					} else {
						swal('错误', data.message, 'error');
					}
				},
				error : function() {
					swal('失败', '删除失败', "error");
				}
			});
		} else {
			swal('取消', '角色没有删除', 'error');
		}
	});
}
