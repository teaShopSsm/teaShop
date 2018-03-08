$(document).ready(function() {
	// getPrePage();
	pageQuery();
	$("#pageIconInsert").blur(function() {
		$("#showIconInsert").html('<i class="' + $("#pageIconInsert").val() + '"></i>');
	});
	$("#pageIconUpdate").blur(function() {
		$("#showIconUpdate").html('<i class="' + $("#pageIconUpdate").val() + '"></i>');
	});
});

/**
 * 获取上级菜单
 * 
 * @param {Object}
 *            key
 */
function getPrePage() {
	$.ajax({
		type : 'get',
		url : '../page/getAllPrePage',
		dataType : 'json',
		async : false,
		success : function(value) {
			var flag = value.flag;
			if (flag < 0) {
				errorSwal(value.message);
				return;
			} else {
				var data = value.data;
				// 创建下拉按钮
				$('#dropDownButton, #dropDownButton2').jqxDropDownButton({
					width : '100%'
				});
				// 初始提示为：请选择
				var dropDownContent = '<div style="position: relative; margin-left: 3px; margin-top: 5px;">请选择</div>';
				$('#dropDownButton, #dropDownButton2').jqxDropDownButton('setContent', dropDownContent);
				// 准备下拉列表数据
				initAddPageTable(data);
			}
		},
		error : function() {
			errorSwal('获取信息失败');
		}
	});
}

/**
 * 格式化显示上级菜单
 * 
 * @param value
 */
function initAddPageTable(value) {
	var source = {
		dataType : "json",
		dataFields : [ {
			name : 'pageId',
			type : 'number'
		}, {
			name : 'pageName',
			type : 'string'
		}, {
			name : 'prePageId',
			type : 'number'
		} ],
		id : 'pageId',
		localData : value
	};
	// 创建data配适器
	var dataAdapter = new $.jqx.dataAdapter(source);
	// perform Data Binding
	dataAdapter.dataBind();
	// 对应记录
	var records = dataAdapter.getRecordsHierarchy('pageId', 'prePageId', 'items', [ {
		name : 'pageName',
		map : 'label'
	}, {
		name : 'pageId',
		map : 'id'
	} ]);
	$('#pagePreInsert, #pagePreUpdate').jqxTree({
		source : records,
		width : '100%',
		height : '200px'
	});
	// #pagePreInsert添加根目录，在最前面添加，并添加选中事件
	var treeItems = $("#pagePreInsert").jqxTree('getItems');
	var firstItem = treeItems[0];
	var firstItemElement = firstItem ? firstItem.element : null;
	$('#pagePreInsert').jqxTree('addBefore', {
		label : '---请选择---',
		id : 'chooseRoot_Save'
	}, firstItemElement);
	$('#chooseRoot_Save').click(function() {
		$('#prePageTableId').val(0); // 请选择，那么prePageId为0
		$("#dropDownButton").jqxDropDownButton('close'); // 选中后关闭该下拉框
	});
	// #pagePreInsert下拉菜单选中事件
	$('#pagePreInsert').on(
			'select',
			function(event) {
				var args = event.args;
				var item = $('#pagePreInsert').jqxTree('getItem', args.element);
				// 选择上级菜单后，返回上级菜单的id: item.id 和 name: item.label
				$('#prePageTableId').val(item.id); // 利用隐藏域属性保存id

				var dropDownContent = '<div style="position: relative; margin-left: 3px; margin-top: 5px;">'
						+ item.label + '</div>';
				$("#dropDownButton").jqxDropDownButton('setContent', dropDownContent); // 设置下拉框的值
				$("#dropDownButton").jqxDropDownButton('close'); // 选中后关闭该下拉框
			});
	// 同样#pagePreUpadte需要添加根目录和下拉菜单
	treeItems = $("#pagePreUpdate").jqxTree('getItems');
	firstItem = treeItems[0];
	firstItemElement = firstItem ? firstItem.element : null;
	$('#pagePreUpdate').jqxTree('addBefore', {
		label : '---请选择---',
		id : 'chooseRoot_Update'
	}, firstItemElement);
	$('#chooseRoot_Update').click(function() {
		$('#prePageTableId').val(0); // 请选择，那么prePageId为0
		$("#dropDownButton2").jqxDropDownButton('close'); // 选中后关闭该下拉框
	});

	$('#pagePreUpdate').on(
			'select',
			function(event) {
				var args = event.args;
				var item = $('#pagePreUpdate').jqxTree('getItem', args.element);
				// 选择上级菜单后，返回上级菜单的id: item.id 和 name: item.label
				$('#prePageTableId').val(item.id); // 利用隐藏域属性保存id

				var dropDownContent = '<div style="position: relative; margin-left: 3px; margin-top: 5px;">'
						+ item.label + '</div>';
				$("#dropDownButton2").jqxDropDownButton('setContent', dropDownContent); // 设置下拉框的值
				$("#dropDownButton2").jqxDropDownButton('close'); // 选中后关闭该下拉框
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
function pageQuery() {
	var pageName = $('#pageNameQuery').val();
	var pageDesc = $('#pageDescQuery').val();
	$.ajax({
		type : 'get',
		url : '../page/blurryPage',
		data : {
			pageName : pageName,
			pageDesc : pageDesc
		},
		dataType : 'json',
		success : function(data) {
			var flag = data.flag;
			if (flag < 0) {
				errorSwal(data.message);
				return;
			} else {
				initPageInfoTable(data.data);
			}
		},
		error : function(value) {
			errorSwal('获取信息失败');
		}
	});
}

/**
 * 格式化显示 页面信息
 * 
 * @param {Object}
 *            data
 */
function initPageInfoTable(value) {
	var source = {
		dataType : "json",
		dataFields : [ {
			name : 'pageId',
			type : 'number'
		}, {
			name : 'pageName',
			type : 'string'
		}, {
			name : 'prePageId',
			type : 'number'
		}, {
			name : 'url',
			type : 'string'
		}, {
			name : 'description',
			type : 'string'
		}, {
			name : 'icon',
			type : 'string'
		} ],
		hierarchy : {
			keyDataField : {
				name : 'pageId'
			},
			parentDataField : {
				name : 'prePageId'
			}
		},
		id : 'pageId',
		localData : value
	};
	var dataAdapter = new $.jqx.dataAdapter(source);
	$("#pageInfoTable").jqxTreeGrid({
		width : '99%',
		source : dataAdapter,
		sortable : true,
		ready : function() {
			$("#pageInfoTable").jqxTreeGrid('expandRow', '0');
		},
		columns : [ {
			text : '名称',
			dataField : 'pageName',
			width : '20%'
		}, {
			text : '路径',
			dataField : 'url',
			width : '20%'
		}, {
			text : '描述',
			dataField : 'description',
			width : '20%'
		}, {
			text : '图标',
			dataField : 'icon',
			width : '25%'
		}, {
			text : '操作',
			cellsalign : 'left',
			cellsrenderer : operate,
			events : 'operateEvents',
			width : '15%'
		} ]
	});

	$('#pageInfoTable').on('rowSelect', function(event) {
		var args = event.args;
		var row = args.row;
		var id = row.pageId;
		$('#pageTableId').val(id);
		if (row.level !== 0) {
		}
	});

}

/**
 * 操作：显示信息、显示删除
 * 
 * @param {Object}
 *            value
 * @param {Object}
 *            row
 * @param {Object}
 *            index
 * @return {TypeName}
 */
function operate(value, row, index) {
	return [ '<span class="glyphicon glyphicon-edit" onclick="showPageInfo()" style="color:#f0ad4e; cursor:pointer"></span>'
			+ '&nbsp;&nbsp;&nbsp;'
			+ '<span class="glyphicon glyphicon-trash" onclick="deletePage(this)" style="color:#f0ad4e; cursor:pointer"></span>' ]
			.join('');
}
/**
 * 删除页面信息
 * 
 * @param {Object}
 *            data
 */
function deletePage(obj) {

	pageId = $('#pageTableId').val();
	if (!pageId) {
		errorSwal('请选择菜单!');
		return;
	}
	swal({
		title : "删除菜单?",
		text : "你确定要删除这个菜单吗？",
		type : "warning",
		showCancelButton : true,
		confirmButtonColor : '#DD6B55',
		confirmButtonText : '确定删除!',
		cancelButtonText : "取消!",
		closeOnConfirm : false,
		closeOnCancel : true
	}, function(isConfirm) {
		if (isConfirm) {
			$
					.ajax({
						type : 'post',
						url : '../userPage/deletePage',
						data : {
							pageId : pageId
						},
						dataType : 'json',
						success : function(data) {
							var flag = data.flag;
							if (flag == 0) {
								// pageQuery();
								$("#pageInfoTable").jqxTreeGrid('deleteRow',
										$(obj).parent("td").parent("tr").attr("data-key"));
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
			swal('取消', '页面没有删除', 'error');
		}
	});
}

/**
 * 打开插入对话框
 * 
 * @param {Object}
 *            roleId
 */
function pageInsert() {

	$('#prePageId').val(0);// 新增时候，将上级ID置为0
	getPrePage();
	var dropDownContent = '<div style="position: relative; margin-left: 3px; margin-top: 3px;">' + '请选择' + '</div>';
	$('#dropDownButton2').jqxDropDownButton('setContent', dropDownContent);
	$('#pageMessageInsert').modal({
		backdrop : 'static'
	});
}

/**
 * 插入 菜单
 */
function insertPage() {
	var prePageId = $('#prePageTableId').val();
	if (prePageId == '-1') {
		errorSwal('请选择上级菜单');
		return;
	}
	var pageName = $('#pageNameInsert').val();
	if (!pageName) {
		errorSwal('请输入菜单名称');
		return;
	}
	var pageUrl = $('#pageURLInsert').val();
	var flag = 0;
	$.ajax({
		type : 'post',
		url : '../page/getPrePageIdById',
		data : {
			'pageId' : prePageId
		},
		dataType : 'json',
		async : false,
		success : function(data) {
			if (data.data > 0) {
				flag = 0;
			} else {
				flag = 1;
			}
		}
	});
	if (!flag) {
		if (!pageUrl) {
			swal('错误', '请输入菜单路径', 'error');
			return;
		}
	}

	var pageIcon = $('#pageIconInsert').val();
	var pageOrder = $('#pageOrderCodeInsert').val();
	var re = /^[0-9]*[1-9][0-9]*$/;
	if (pageOrder.length > 0 && !re.test(pageOrder)) {
		errorSwal('菜单序号请输入整数');
		return;
	}

	var pageDesc = $('#pageDescInsert').val();
	// var pageTypeId = $('#pageTypeInsert').selectpicker('val');
	$.ajax({
		url : '../page/addPage',
		type : 'post',
		data : {
			prePageId : prePageId,
			pageName : pageName,
			url : pageUrl,
			// pageTypeId : pageTypeId,
			pageIcon : pageIcon,
			pageOrder : pageOrder,
			pageDesc : pageDesc
		},
		dataType : 'json',
		success : function(data) {
			var flag = data.flag;
			if (flag < 0) {
				errorSwal(data.message);
				return;
			} else {
				closePageInsertModal();
				pageQuery();
				successSwal('添加菜单成功');
			}
		},
		error : function() {
			errorSwal('添加失败');
		}
	});
}
/**
 * 关闭插入对话框
 * 
 * @param {Object}
 *            roleId
 */
function closePageInsertModal() {
	$('#pageMessageInsert').modal('hide');
	$('#pagePerInsert').selectpicker('val', '-1');
	$('#pageNameInsert').val('');
	$('#pageURLInsert').val('');
	$('#pageIconInsert').val('');
	$('#pageOrderInsert').val('');
	$('#pageDescInsert').val('');
}
/**
 * 显示菜单信息 对话框
 */
function showPageInfo(pageId) {
	getPrePage();
	pageId = $('#pageTableId').val();
	if (!pageId) {
		toastr.warning('请选择菜单');
		return;
	}
	$.ajax({
		type : 'get',
		url : '../page/getPageById',
		data : {
			pageId : pageId
		},
		dataType : 'json',
		success : function(data) {
			var flag = data.flag;
			if (flag < 0) {
				toastr.warning(data.message);
				return;
			}
			var obj = data.data;
			$('#pageId').val(obj.pageId);
			// 表单属性赋值，需要根据上级菜单的id，查询上级菜单的名称
			// 若上级菜单prePageId为0，或者为空，则表明没有上级菜单
			var prePageId = obj.prePageId;
			if (prePageId != 0 && prePageId != null) {
				$.ajax({
					type : 'get',
					url : '../page/getPageById',
					data : {
						pageId : obj.prePageId
					},
					dataType : 'json',
					success : function(data) {
						var flag = data.flag;
						if (flag < 0) {
							toastr.warning(data.message);
							return;
						}
						var dropDownContent = '<div style="position: relative; margin-left: 3px; margin-top: 5px;">'
								+ data.data.pageName + '</div>';
						$('#dropDownButton2').jqxDropDownButton('setContent', dropDownContent);
						$('#prePageTableId').val(data.data.pageId); // 让该隐藏域记录上级菜单id
					},
					error : function() {
					}
				});
			} else {
				// 若本身没有上级菜单，则变为“请选择”
				var dropDownContent = '<div style="position: relative; margin-left: 3px; margin-top: 5px;">请选择</div>';
				$('#dropDownButton2').jqxDropDownButton('setContent', dropDownContent);
				$('#prePageTableId').val(0); // 让该隐藏域记录上级菜单id
			}
			// 表单属性赋值
			$('#pageNameUpdate').val(obj.pageName);
			$('#pageURLUpdate').val(obj.url);
			$('#pageIconUpdate').val(obj.icon);
			$('#showIconUpdate').html('<i class="' + obj.icon + '"></i>');
			$('#pageOrderCodeUpdate').val(obj.orderCode);
			$('#pageDescUpdate').val(obj.description);
			$('#pageMessage').modal({
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
function updatePageMessage() {
	var pageId = $('#pageId').val();
	if (!pageId) {
		errorSwal('请选择菜单');
		return;
	}
	var prePageId = $('#prePageTableId').val();
	if (prePageId == '-1') {
		errorSwal('请选择上级菜单');
		return;
	}
	var pageName = $('#pageNameUpdate').val();
	if (!pageName) {
		errorSwal('请输入菜单名称');
		return;
	}
	var pageUrl = $('#pageURLUpdate').val();
	var flag = 0;
	$.ajax({
		type : 'post',
		url : '../page/getPrePageIdById',
		data : {
			'pageId' : prePageId
		},
		dataType : 'json',
		async : false,
		success : function(data) {
			if (data.data > 0) {
				flag = 0;
			} else {
				flag = 1;
			}
		}
	});
	if (!flag) {
		if (!pageUrl) {
			swal('错误', '请输入菜单路径', 'error');
			return;
		}
	}

	var pageIcon = $('#pageIconUpdate').val();
	var pageOrder = $('#pageOrderCodeUpdate').val();
	var re = /^[0-9]*[1-9][0-9]*$/;
	if (pageOrder.length > 0 && !re.test(pageOrder)) {
		errorSwal('菜单序号请输入整数');
		return;
	}
	var pageDesc = $('#pageDescUpdate').val();
	$.ajax({
		type : 'post',
		url : '../page/editPage',
		data : {
			pageId : pageId,
			prePageId : prePageId,
			pageName : pageName,
			url : pageUrl,
			pageIcon : pageIcon,
			pageOrder : pageOrder,
			pageDesc : pageDesc
		},
		dataType : 'json',
		success : function(data) {
			var flag = data.flag;
			if (flag < 0) {
				errorSwal(data.message);
				return;
			} else {
				pageQuery();
				closePageMessageoModal();
				successSwal('更新菜单信息成功');
			}
		},
		error : function() {
			errorSwal('更新失败');
		}
	});
}
/**
 * 隐藏 角色信息对话框
 */
function closePageMessageoModal() {
	$('#pageMessage').modal('hide');
	$('#pagePerUpdate').selectpicker('val', '-1');
	$('#pageNameUpdate').val('');
	$('#pageURLUpdate').val('');
	$('#pageIconUpdate').val('');
	$('#pageOrderUpdate').val('');
	$('#pageDescUpdate').val('');
	$('#pageTypeUpdate').selectpicker('val', '-1');
}
