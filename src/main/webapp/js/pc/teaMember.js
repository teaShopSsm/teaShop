$(document).ready(function() {
    teaMemberQuery();
});

var teaGoodsColumns =
    [ {
        field : 'userId',
        title : '会员编号',
        width : '10%',
        align : 'center',
        /*formatter : function(value, row, index) {
            return index + 1;
        }*/
    }, {
        field : 'userName',
        title : '会员昵称',
        align : 'center'
    }, {
        field : 'email',
        title : '邮箱',
        align : 'center'
    }, {
        field : 'phone',
        title : '手机',
        align : 'center'
    },{
        field : 'integral',
        title : '积分',
        align : 'center'
    },{
        field : 'idDeleted',
        title : '状态',
        align : 'center'
    },{
        field : 'userId',
        title : '操作',
        align : 'center',
        formatter : 'operateFormatter'
    }];
function operateFormatter(value, row, index) {
    return [
       "&nbsp;&nbsp;&nbsp;&nbsp;"
        + '<a class="remove" href="javascript:void(0)" title="删除" onclick="deleteTeaMember(' + value + ')">',
        '<i class="glyphicon glyphicon-remove"></i>', '</a>' ].join('');
}

function teaMemberQuery() {
    var memberName = $("#userNameQuery").val();

    $('#teaMemberTable').bootstrapTable('destroy');
    // 初始化表格,动态从服务器加载数据
    $("#teaMemberTable").bootstrapTable( {
        method : "get", // 使用get请求到服务器获取数据
        contentType : "application/x-www-form-urlencoded",
        url : "../user/getAllMember", // 获取数据的Servlet地址
        toolbar: '#toolbar',
        striped : true, // 表格显示条纹
        pagination : true, // 启动分页
        pageSize : 10, // 每页显示的记录数
        pageNumber : 1, // 当前第几页
        pageList : [ 10, 20,50 ], // 记录数可选列表
        search : false, // 是否启用查询
        showColumns : true, // 显示下拉框勾选要显示的列
        showRefresh : true, // 显示刷新按钮
        sidePagination : "server", // 表示服务端请求
        columns : teaGoodsColumns,
        // 设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
        // 设置为limit可以获取limit, offset, search, sort, order
        queryParamsType : "undefined",
        queryParams : function queryParams(params) { // 设置查询参数
            var param = {
                pageNumber : params.pageNumber,
                pageSize : params.pageSize,
                userName:memberName,
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


function deleteTeaMember(id){
    swal( {
        title : "禁用该会员吗?",
        text : "你确定要禁用该会员吗？",
        type : "warning",
        showCancelButton : true,
        confirmButtonColor : '#DD6B55',
        confirmButtonText : '确定禁用!',
        cancelButtonText : "取消!",
        closeOnConfirm : false,
        closeOnCancel : true
    }, function(isConfirm) {
        if (isConfirm) {
            $.ajax( {
                type : 'post',
                url: '../user/forbidMember',
                data:{"id":id},
                dataType : 'json',
                success:function(value){
                    if(value.flag==0){
                        teaMemberQuery();
                        swal({
                            title : '成功',
                            text : '禁用成功',
                            type : 'success',
                            timer : 800,
                            closeOnConfirm : false
                        });
                    }else{
                        swal({
                            title : '错误',
                            text : '禁用失败,请重试',
                            type : 'error',
                            timer : 2000,
                            closeOnConfirm : false
                        });
                    }

                },
                error:function(){
                    swal({
                        title : '错误',
                        text : '禁用失败,请重试',
                        type : 'error',
                        timer : 2000,
                        closeOnConfirm : false
                    });
                }
            });
        } else {
            swal('取消', '会员没有禁用', 'error');
        }
    });
}