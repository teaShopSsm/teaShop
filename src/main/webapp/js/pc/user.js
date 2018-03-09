$(document).ready(function() {
    //getCompanys();
    initTable();
    userQuery();
    getCurrentUser();
});
var userColumns =
    [  {
        field : 'userName',
        title : '用户名称',
        align : 'center'
    }, {
        field : 'description',
        title : '描述',
        align : 'center'
    }, {
        field : 'email',
        title : '邮箱',
        align : 'center'
    }, {
        field : 'phone',
        title : '手机',
        align : 'center'
    }, {
        field : 'userId',
        title : '操作',
        align : 'center',
        width : '16%',
        formatter : 'operateFormatter'
    } ];

var userRoleColumns =
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
        formatter : 'operateUserRoleFormatter'
    } ];

function operateFormatter(value, row, index) {
    return [
        '<a class="edit" href="javascript:void(0)" title="编辑" style="color:#f0ad4e" onclick="showUserInfo(' + value + ')">',
        '<i class="glyphicon glyphicon-edit"></i>',
        '</a>'
        + "&nbsp;&nbsp;&nbsp;&nbsp;" +
        '<a class="role" href="javascript:void(0)" title="角色" style="color:#f0ad4e" onclick="showUserRole(' + value + ')">',
        '<i class="glyphicon glyphicon-user"></i>',
        '</a>'
        + "&nbsp;&nbsp;&nbsp;&nbsp;" +
        // '<a class="menu" href="javascript:void(0)" title="菜单" style="color:#f0ad4e" onclick="showUserMenu(' + value + ')">',
        // '<i class="glyphicon glyphicon-book"></i>',
        // '</a>'
        // + "&nbsp;&nbsp;&nbsp;&nbsp;" +
        '<a class="remove" href="javascript:void(0)" title="重置密码" style="color:#f0ad4e" onclick="resetUserPasswrd(' + value + ')">',
        '<i class="glyphicon glyphicon-scissors"></i>',
        '</a>'
        + "&nbsp;&nbsp;&nbsp;&nbsp;"+
        '<a class="remove" href="javascript:void(0)" title="删除" style="color:#f0ad4e" onclick="deleteUser(' + value + ')">',
        '<i class="glyphicon glyphicon-trash"></i>',
        '</a>'
    ].join('');
}

function operateUserRoleFormatter(value, row, index) {
    if(row.flag == '0'){
        return  '<a href="#" id="role_'+row.roleId+'" onclick="bindUserRole(\'' + row.roleId + '\')"><font color="red">绑定</font></a>';
    }else {
        return  '<a href="#" id="role_'+row.roleId+'" onclick="unBindUserRole(\'' + row.roleId + '\')"><font color="black">解绑</font></a>';
    }
}

function operateUserMenuFormatter(value, row, index) {
    if(row.flag == '0'){
        return  '<a href="#" id="page_'+row.pageId +'"  onclick="bindUserMenu(\''+ row.pageId+'\')"><font color="red">绑定</font></a>';
    }else {
        return  '<a href="#" id="page_'+row.pageId+'" onclick="unBindUserMenu(\'' + row.pageId +'\')"><font color="black">解绑</font></a>';
    }
}

function operateUserCompanyFormatter(value,row,index){
    if(row.flag == '0'){
        return '<a href="#" id="com_'+row.companyId +'"  onclick="bindUserCompany(\''+ row.companyId+'\')"><font color="red">绑定</font></a>';
    }else {
        return  '<a href="#" id="com_'+row.companyId+'" onclick="unBindUserCompany(\'' + row.companyId +'\')"><font color="black">解绑</font></a>';
    }
}
function operateUserColdStoreFormatter(value,row,index){
    if(row.flag == '0'){
        return '<a href="#" id="cold_'+value +'"  onclick="bindUserColdStore(\''+ value+'\')"><font color="red">绑定</font></a>';
    }else {
        return  '<a href="#" id="cold_'+value+'" onclick="unBindUserColdStore(\'' + value +'\')"><font color="black">解绑</font></a>';
    }
}


function initTable(){
    $('#userTable').bootstrapTable('destroy');
    $('#userTable').on('page-change.bs.table', function(e, number, size) {
        userQuery();
    });
    // 初始化表格,动态从服务器加载数据
    $("#userTable").bootstrapTable({
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
        columns : userColumns,
        onLoadSuccess : function() {},
        onLoadError : function() {
            errorSwal("加载数据失败");
        }
    });
}

/**
 * 查询用户信息
 */
function userQuery() {
    var userName = $('#userNameQuery').val();
    var userDesc = $('#userDescQuery').val();
    var opts = $("#userTable").bootstrapTable('getOptions');
    var pageNumber= opts.pageNumber;
    var pageSize = opts.pageSize;
    $.ajax({
        url : '../user/blurryUser',
        type:'post',
        data:{
            userName : userName,
            userDesc : userDesc,
            pageNumber:pageNumber,
            pageSize:pageSize
        },
        dataType:'json',
        success:function(data){
            $('#userTable').bootstrapTable('load', data);
        },
        error:function(e){
            errorSwal("加载数据失败");
        }
    });
}


function getCurrentUser(){
    $.ajax( {
        type : 'get',
        url : '../user/getCurrentUser',
        dataType : 'json',
        success : function(data) {
            if(data.flag<0){
                return;
            }else{
                var value =data.data;
                if(value.isSystem==2){
                    $('.companyInsert').hide();
                }else if(value.isSystem==3){
                    $('#userInsert').hide();
                }
            }
        },
        error : function(e) {
            errorSwal('获取角色信息失败!');
        }
    });
}
/**
 * 显示用户信息
 *
 * @param {Object}
 *            userId
 */
function showUserInfo(userId) {
    if (!userId) {
        swal('请选择用户');
        return;
    }
    $.ajax( {
        type : 'get',
        url : '../user/getUserById',
        data : {
            userId : userId
        },
        dataType : 'json',
        success : function(data) {
            var flag = data.flag;
            if (flag < 0) {
                swal(data.message,'warning');
                return;
            }
            $('#userMessage').modal( {
                backdrop : 'static'
            });
            var obj = data.data;
            $('#userId').val(obj.userId);
            $('#userNameUpdate').val(obj.userName);
            $('#userDescUpdate').val(obj.description);
            $('#userEmailUpdate').val(obj.email);
            $('#userPhoneUpdate').val(obj.phone);

        },
        error : function(e) {
            errorSwal('获取角色信息失败!');
        }
    });
}
/**
 * 关闭窗口
 */
function closeUserMessageoModal() {
    $('#userId').val('');
    $('#userNameUpdate').val('');
    $('#userDescUpdate').val('');
    $('#userEmailUpdate').val('');
    $('#userPhoneUpdate').val('');
    $('#userMessage').modal('hide');
}
/**
 * 更新用户信息
 */
function updateUserMessage() {
    var userId = $('#userId').val();
    var userName = $('#userNameUpdate').val();
    if (!userName) {
        errorSwal('请输入用户名');
        return;
    }
    var userPhone = $('#userPhoneUpdate').val();
    if (!checkPhone(userPhone)) {
        errorSwal('请输入正确的电话');
        return;
    }
    var userDesc = $('#userDescUpdate').val();
    var userEmail = $('#userEmailUpdate').val();
    if (!checkMail(userEmail)) {
        errorSwal('请输入正确的邮箱');
        return;
    }
    $.ajax( {
        type : 'post',
        url : '../user/updateUser',
        data : {
            userId : userId,
            userName : userName,
            userDesc : userDesc,
            userEmail : userEmail,
            userPhone : userPhone
        },
        dataType : 'json',
        success : function(data) {
            var flag = data.flag;
            if (flag < 0) {
                swal('警告',data.message,'warning');
                return;
            } else {
                userQuery();
                closeUserMessageoModal();
            }
        },
        error : function() {
            errorSwal('更新失败');
        }
    });
}

/**
 * 编辑 菜单权限
 */
function showUserMenu(userId) {
    if (!userId) {
        warnSwal('请选择用户');
        return;
    }
    $('#userMenuId').val(userId)
    getUserMenus(userId);
    $('#userMenuMessage').modal( {
        backdrop : 'static'
    });
}
/**
 * 获取用户角色
 *
 * @param {Object}
 *            userId
 */
function getUserMenus(userId) {
    $.ajax({
        type : 'post',
        url : '../userPage/getUserMenus',
        data : {
            userId : userId
        },
        dataType : 'json',
        success : function(data) {
            if (data.flag < 0) {
                swal(data.message);
                return;
            } else {
                var value = data.data;
                initUserMenus(value);
            }
        },
        error : function() {
            swal('失败','更新失败','error');
        }
    });
}
function initUserMenus(value){
    $('#userMenuMessage .modal-body div').append('<div id="userMenus"></div>')
    var source = {
        dataType : "json",
        dataFields : [ {
            name:'flag',
            type:'number'
        },{
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
            name : 'desciption',
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
    $("#userMenus").jqxTreeGrid({
        width : '99%',
        source : dataAdapter,
        sortable : true,
        ready : function() {
            $("#userMenus").jqxTreeGrid('expandRow', '0');
        },
        columns : [ {
            text : '名称',
            dataField : 'pageName',
            width : '20%'
        }, {
            text : '路径',
            dataField : 'url',
            width : '25%'
        }, {
            text : '描述',
            dataField : 'desciption',
            width : '20%'
        }, {
            text : '图标',
            dataField : 'icon',
            width : '20%'
        },{
            text:'操作',
            cellsalign : 'left',
            cellsRenderer : operate,
            width : '15%'
        }]
    });

}
function operate(index){
    var row = $("#userMenus").jqxTreeGrid('getRow',index);
    if(row.url){
        if(row.flag == '0'){
            return  '<a href="#" id="page_'+row.pageId +'"  onclick="bindUserMenu(\''+ row.pageId+'\')"><font color="red">绑定</font></a>';
        }else {
            return  '<a href="#" id="page_'+row.pageId+'" onclick="unBindUserMenu(\'' + row.pageId +'\')"><font color="black">解绑</font></a>';
        }
    }else{
        return '';
    }
}
/**
 * 关闭用户菜单对话框
 */
function closeUserMenuMessageModal() {
    $('#userMenuMessage').modal('hide');
    $("#userMenus").jqxTreeGrid('destroy');
}

/**
 * 编辑 菜单权限
 */
function showUserRole(userId) {
    if (!userId) {
        swal('请选择用户!');
    }
    $('#userRoleId').val(userId);
    getUserRoles(userId)
    $('#userRoleMessage').modal( {
        backdrop : 'static'
    });
}
/**
 * 获取用户角色
 *
 * @param {Object}
 *            pageNumber
 * @param {Object}
 *            pageSize
 * @param {Object}
 *            userId
 */
function getUserRoles(userId) {
    $('#userRoleTable').bootstrapTable('destroy');
    // 初始化表格,动态从服务器加载数据
    $("#userRoleTable").bootstrapTable( {
        method : "post", // 使用get请求到服务器获取数据
        contentType : "application/x-www-form-urlencoded",
        url : "../getUserRoles", // 获取数据的Servlet地址
        striped : true, // 表格显示条纹
        pagination : true, // 启动分页
        pageSize : 10, // 每页显示的记录数
        pageNumber : 1, // 当前第几页
        pageList : [ 10, 20,50, 100, 200 ], // 记录数可选列表
        search : false, // 是否启用查询
        showColumns : true, // 显示下拉框勾选要显示的列
        showRefresh : true, // 显示刷新按钮
        sidePagination : "server", // 表示服务端请求
        columns : userRoleColumns,
        // 设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
        // 设置为limit可以获取limit, offset, search, sort, order
        queryParamsType : "undefined",
        queryParams : function queryParams(params) { // 设置查询参数
            var param = {
                userId : userId,
                pageNumber : params.pageNumber,
                pageSize : params.pageSize
            };
            return param;
        },
        onLoadSuccess : function() { // 加载成功时执行
            $('#userRoleMessage').modal( {
                backdrop : 'static'
            });

        },
        onLoadError : function() { // 加载失败时执行
            errorSwal("加载数据失败");
        }
    });
}
/**
 * 关闭用户角色对话框
 */
function closeUserRoleMessageModal() {
    $('#userRoleMessage').modal('hide');
}

/**
 * 给用户绑定菜单
 *
 * @param {Object}
 *            pageId
 */
function bindUserMenu(pageId) {
    var userId = $('#userMenuId').val();
    $.ajax( {
        type : 'post',
        url : '../userPage/addUserPage',
        data : {
            userId : userId,
            pageId : pageId
        },
        dataType : 'json',
        success : function(data) {
            var flag = data.flag;
            if (flag == 0) {
                var row = $("#userMenus").jqxTreeGrid('getRow', pageId);
                row.flag = 1;
                $("#userMenus").jqxTreeGrid('updateRow', pageId, row);
            } else {
                errorSwal(data.message);
            }
        },
        error : function() {
            errorSwal('绑定失败');
        }
    });
}

/**
 * 给用户解绑菜单
 *
 * @param {Object}
 *            pageId
 */
function unBindUserMenu(pageId) {
    var userId = $('#userMenuId').val();
    $.ajax( {
        type : 'post',
        url : '../userPage/deleteUserPage',
        data : {
            userId : userId,
            pageId : pageId
        },
        dataType : 'json',
        success : function(data) {
            var flag = data.flag;
            if (flag == 0) {
                var row = $("#userMenus").jqxTreeGrid('getRow', pageId);
                row.flag = 0;
                $("#userMenus").jqxTreeGrid('updateRow', pageId, row);
            } else {
                errorSwal(data.message);
            }
        },
        error : function() {
            errorSwal('解绑失败');
        }
    });
}

/**
 * 给用户绑定角色
 *
 * @param {Object}
 *            pageId
 */
function bindUserRole(roleId) {
    var userId = $('#userRoleId').val();
    $.ajax( {
        type : 'post',
        url : '../addUserRole',
        data : {
            userId : userId,
            roleId : roleId
        },
        dataType : 'json',
        success : function(data) {
            var flag = data.flag;
            if (flag == 0) {
                $("#role_"+roleId).parent().html('<a href="#" id="role_'+roleId+'" onclick="unBindUserRole(\'' + roleId + '\')"><font color="black">解绑</font></a>');
            } else {
                errorSwal(data.message);
            }
        },
        error : function() {
            errorSwal('绑定失败');
        }
    });
}

/**
 * 给用户解绑角色
 *
 * @param {Object}
 *            roleId
 */
function unBindUserRole(roleId) {
    var userId = $('#userRoleId').val();
    $.ajax( {
        type : 'post',
        url : '../deleteUserRole',
        data : {
            userId : userId,
            roleId : roleId
        },
        dataType : 'json',
        success : function(data) {
            var flag = data.flag;
            if (flag == 0) {
                $("#role_"+roleId).parent().html('<a href="#" id="role_'+roleId+'" onclick="bindUserRole(\'' + roleId + '\')"><font color="red">绑定</font></a>');
            } else {
                errorSwal(data.message);
            }
        },
        error : function() {
            errorSwal('解绑失败');
        }
    });
}

/**
 * 添加用户
 */
var userType = {
    1:'超级管理员',
    2:'商户',
    3:'会员'
};
function userInsert() {
    $('#userMessageInsert').modal( {
        backdrop : 'static'
    });
    var html='';
    for(var i in userType){
        html+='<option value="'+i+'">'+ userType[i]+'</option>'
    }
    $("#userTypeInsert").html(html);
    $("#userTypeInsert").selectpicker('refresh');

}
/**
 * 关闭对话框
 */
function closeUserInsertModal() {
    $('#userMessageInsert').modal('hide');
    $('#userNameInsert').val('');
    $('#jobNoInsert').val('');
    $('#passwordInsert').val('');
    $('#passwdInsert').val('');
    $('#passwordInsert').val('');
    $('#userTypeInsert').selectpicker('val', '-1');
    $('#userEmailInsert').val('');
    $('#userPhoneInsert').val('');
    $('#userDescInsert').val('');
    $('#companyInsert').selectpicker('val', '-1');
    $('#companyInsert').removeAttr('disabled');
}
/**
 * 添加用户
 */
function insertUser() {
    var userName = $('#userNameInsert').val();
    if (!userName) {
        swal('警告','请输入用户名','warning');
        return;
    }
    var password = $('#passwordInsert').val();
    if (!password) {
        swal('警告','请输入密码','warning');
        return;
    }
    password = $.md5(password.trim());
    if(password.lenght>12||password.lenght<6){
        errorSwal('密码介于6到12位');
        return;
    }
    var passwd = $('#passwdInsert').val();
    passwd = $.md5(passwd.trim());
    if (passwd != password) {
        swal('警告','两次密码不一样','warning');
        return;
    }
    var phone = $('#userPhoneInsert').val();
    var email = $('#userEmailInsert').val();
    if(!checkPhone(phone)){
        swal('警告','请输入正确格式的手机号','warning');
        return;
    }
    if(!checkMail(email)){
        swal('警告','请输入正确格式的邮箱','warning');
        return;
    }

    var desc = $('#userDescInsert').val();
    var userType = $("#userTypeInsert").val();
    $.ajax( {
        type : 'post',
        url : '../user/addUser',
        data : {
            userName : userName,
            password : password,
            passwd : passwd,
            email : email,
            phone : phone,
            desc : desc,
            userType : userType
        },
        dataType : 'json',
        success : function(data) {
            var flag = data.flag;
            if (flag < 0) {
                errorSwal(data.message);
                return;
            } else {
                closeUserInsertModal();
                userQuery();
                return;
            }
        },
        error : function() {
            errorSwal('添加用户失败');
        }
    });
}
/*
 * 校验用户手机号码是否唯一
 */
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
/**
 * 重置用户密码
 *
 * @param {Object}
 *            userId
 */
function resetUserPasswrd(userId) {
    if (!userId) {
        swal('警告','请选择用户!','warning');
        return;
    }
    swal( {
        title : "重置密码?",
        text : "你确定要重置这个用户密码吗？",
        type : "warning",
        showCancelButton : true,
        confirmButtonColor : '#DD6B55',
        confirmButtonText : '确定重置!',
        cancelButtonText : "取消!",
        closeOnConfirm : true,
        closeOnCancel : true
    }, function(isConfirm) {
        if (isConfirm) {
            $.ajax( {
                type : 'post',
                url : '../user/resetUserPassword',
                data : {
                    userId : userId
                },
                dataType : 'json',
                success : function(data) {
                    var flag = data.flag;
                    if (flag == 0) {
                        successSwal();
                    } else {
                        swal('错误', data.message, 'error');
                    }
                },
                error : function() {
                    swal('失败', '重置失败', "error");
                }
            });
        } else {
            swal('取消', '密码没有重置', 'error');
        }
    });
}
/**
 * 删除用户
 *
 * @param {Object}
 *            userId
 * @return {TypeName}
 */
function deleteUser(userId) {
    if (!userId) {
        swal('请选择用户!');
        return;
    }
    swal( {
        title : "删除用户?",
        text : "你确定要删除这个用户吗？",
        type : "warning",
        showCancelButton : true,
        confirmButtonColor : '#DD6B55',
        confirmButtonText : '确定删除!',
        cancelButtonText : "取消!",
        closeOnConfirm : true,
        closeOnCancel : true
    }, function(isConfirm) {
        if (isConfirm) {
            $.ajax( {
                type : 'post',
                url : '../user/deleteUser',
                data : {
                    userId : userId
                },
                dataType : 'json',
                success : function(data) {
                    var flag = data.flag;
                    if (flag == 0) {
                        userQuery();
                        swal('删除','删除成功','success');
                    } else {
                        swal('错误', data.message, 'error');
                    }
                },
                error : function() {
                    swal('失败', '删除失败', "error");
                }
            });
        } else {
            swal('取消', '用户没有删除', 'error');
        }
    });
}
/**
 * 用户绑定企业
 */
function showUserCompany(userId) {
    if (!userId) {
        swal('错误','请选择用户','error');
    }
    $('#userCompanyId').val(userId);
    getUserCompany(userId);
    $('#userCompanyMessage').modal({
        backdrop:'static'
    });
}
function getUserCompany(userId){
    $('#userCompanyTable').bootstrapTable('destroy');
    // 初始化表格,动态从服务器加载数据
    $("#userCompanyTable").bootstrapTable( {
        method : "post", // 使用get请求到服务器获取数据
        contentType : "application/x-www-form-urlencoded",
        url : "../userCompany/getUserCompany", // 获取数据的Servlet地址
        striped : true, // 表格显示条纹
        pagination : true, // 启动分页
        pageSize : 10, // 每页显示的记录数
        pageNumber : 1, // 当前第几页
        pageList : [ 10, 20,50, 100, 200 ], // 记录数可选列表
        search : false, // 是否启用查询
        // showColumns : true, // 显示下拉框勾选要显示的列
        // showRefresh : true, // 显示刷新按钮
        sidePagination : "server", // 表示服务端请求
        columns : userCompanyColumns,
        // 设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
        // 设置为limit可以获取limit, offset, search, sort, order
        queryParamsType : "undefined",
        queryParams : function queryParams(params) { // 设置查询参数
            var param = {
                userId : userId,
                pageNumber : params.pageNumber,
                pageSize : params.pageSize
            };
            return param;
        },
        onLoadSuccess : function() { // 加载成功时执行
            $('#userCompanyMessage').modal( {
                backdrop : 'static'
            });
        },
        onLoadError : function() { // 加载失败时执行
            errorSwal("加载数据失败");
        }
    });
}
function bindUserCompany(companyId) {
    var userId = $("#userCompanyId").val();
    $.ajax({
        type:'post',
        data:{
            userId:userId,
            companyId:companyId
        },
        url:'../userCompany/addUserCompany',
        dataType:'json',
        success:function(data) {
            if(data.flag == 1){
                $("#com_"+companyId).parent().html('<a href="#" id="com_'+companyId+'" onclick="unBindUserCompany(\'' + companyId +'\')"><font color="black">解绑</font></a>');
            }else {
                swal('绑定失败',data.message,'error');
            }
        }
    });
}
function unBindUserCompany(companyId){
    var userId = $("#userCompanyId").val();
    $.ajax({
        type:'post',
        data:{
            userId:userId,
            companyId:companyId
        },
        url:'../userCompany/deleteUserCompany',
        dataType:'json',
        success:function(data) {
            if(data.flag == 1){
                $("#com_"+companyId).parent().html('<a href="#" id="com_'+companyId+'" onclick="bindUserCompany(\'' + companyId +'\')"><font color="red">绑定</font></a>');
            }else {
                swal('解绑失败',data.message,'error');
            }
        }
    });
}
function bindUserColdStore(CSID) {
    var userId = $("#userColdStoreId").val();
    $.ajax({
        type:'post',
        data:{
            userId:userId,
            CSID:CSID
        },
        url:'../store/bindUserColdStore',
        dataType:'json',
        success:function(data) {
            if(data.flag == 0){
                $("#cold_"+CSID).parent().html('<a href="#" id="cold_'+CSID+'" onclick="unBindUserColdStore(\'' + CSID +'\')"><font color="black">解绑</font></a>');
            }else {
                swal('绑定失败',data.message,'error');
            }
        }
    });
}
function unBindUserColdStore(CSID){
    var userId = $("#userColdStoreId").val();
    $.ajax({
        type:'post',
        data:{
            userId:userId,
            CSID:CSID
        },
        url:'../store/unBindUserColdStore',
        dataType:'json',
        success:function(data) {
            if(data.flag ==0){
                $("#cold_"+CSID).parent().html('<a href="#" id="cold_'+CSID+'" onclick="bindUserColdStore(\'' + CSID +'\')"><font color="red">绑定</font></a>');
            }else {
                swal('解绑失败',data.message,'error');
            }
        }
    });
}

/**
 * 用户绑定企业
 */
function showUserColdStore(userId) {
    if (!userId) {
        errorSwal('请选择用户');
    }
    $('#userColdStoreId').val(userId);
    getUserColdStore(userId);
    $('#userColdStoreMessage').modal({
        backdrop:'static'
    });
}
function getUserColdStore(userId){
    $('#userColdStoreTable').bootstrapTable('destroy');
    // 初始化表格,动态从服务器加载数据
    $("#userColdStoreTable").bootstrapTable( {
        method : "post", // 使用get请求到服务器获取数据
        contentType : "application/x-www-form-urlencoded",
        url : "../store/getUserColdStore", // 获取数据的Servlet地址
        striped : true, // 表格显示条纹
        pagination : false, // 启动分页
        pageSize : 10, // 每页显示的记录数
        pageNumber : 1, // 当前第几页
        pageList : [ 10, 20,50, 100, 200 ], // 记录数可选列表
        search : false, // 是否启用查询
        // showColumns : true, // 显示下拉框勾选要显示的列
        // showRefresh : true, // 显示刷新按钮
        sidePagination : "server", // 表示服务端请求
        columns : useColdStoreColumns,
        // 设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
        // 设置为limit可以获取limit, offset, search, sort, order
        queryParamsType : "undefined",
        queryParams : function queryParams(params) { // 设置查询参数
            var param = {
                userId : userId
            };
            return param;
        },
        onLoadSuccess : function(data) {
            if(data.flag<0){
                errorSwal(data.message);
            }else{
                $("#userColdStoreTable").bootstrapTable('load',data.data);
            }
        },
        onLoadError : function() { // 加载失败时执行
            errorSwal("加载数据失败");
        }
    });
}



function closeUserColdStoreMessageModal() {
    $("#userColdStoreMessage").modal('hide');
}
/**
 * 获取所属公司
 */
function getCompanys() {
    $.ajax({
        type : "post",
        url : "../company/getOwnCompanyList",
        async : false,
        dataType : 'json',
        success : function(result) {
            var html = "";
            var company = result.data, code;
            for ( var int = 0; int < company.length; int++) {
                html += '<option value="' + company[int].id + '">' + company[int].name + '</option>'
            }
            html = '<option value="-1">请选择</option>'+html;
            $("#companyInsert").html(html);
            $('#companyInsert').selectpicker('refresh');
        }
    })
}