$(document).ready(function() {
    teaOrdersQuery();
});

var teaOrdersColumns =
    [
       /* {
        field : 'id',
        title : '订单编号',
        width : '10%',
        align : 'center',
        /!*formatter : function(value, row, index) {
            return index + 1;
        }*!/
    },*/
        {
        field : 'username',
        title : '会员',
        align : 'center'
    }, {
        field : 'quantity',
        title : '积分',
        align : 'center'
    }, {
        field : 'totalprice',
        title : '订单金额',
        align : 'center'
    }, {
        field : 'telephone',
        title : '联系电话',
        align : 'center'
    },{
        field : 'address',
        title : '联系地址',
        align : 'center'
    },{
        field : 'id',
        title : '操作',
        align : 'center',
        formatter : 'operateFormatter'
    }];
function operateFormatter(value, row, index) {
    return [
        '<a class="edit" href="javascript:void(0)" title="编辑" style="color:#f0ad4e" onclick="updateTeaOrdersInfo(\'' + value + '\')">',
        '<i class="glyphicon glyphicon-edit"></i>',
        '</a>'
    ].join('');
}

function teaOrdersQuery() {
    var username = $("#userNameQuery").val();
    var status = $("#statusQuery").val();
    $('#teaOrdersTable').bootstrapTable('destroy');
    // 初始化表格,动态从服务器加载数据
    $("#teaOrdersTable").bootstrapTable( {
        method : "get", // 使用get请求到服务器获取数据
        contentType : "application/x-www-form-urlencoded",
        url : "../teaOrders/getAll", // 获取数据的Servlet地址
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
        columns : teaOrdersColumns,
        // 设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
        // 设置为limit可以获取limit, offset, search, sort, order
        queryParamsType : "undefined",
        queryParams : function queryParams(params) { // 设置查询参数
            var param = {
                pageNumber : params.pageNumber,
                pageSize : params.pageSize,
                username:username,
                status:status
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

function updateTeaOrders(){
    $('#updateTeaOrders').modal( {
        backdrop : 'static'
    });
}

function closeTeaOrders(){
    $('#updateTeaOrders').modal('hide');
    $("#username").val("");
    $("#code").val("");
    $("#totalprice").val("");
    $("#quantity").val("");
    $("#telephone").val("");
    $("#address").val("");
    $("#status").val("1");
}

function updateTeaOrdersInfo(id){
    $('#updateTeaOrders').modal( {
        backdrop : 'static'
    });
    $.ajax({
        url:'../teaOrders/getOneById',
        data:{'id':id},
        type:'get',
        //dataType:'json',
        success:function(data1){
            var d = (eval("("+data1+")")).data;
            $("#code").val(d.code);
            $("#telephone").val(d.telephone);
            $("#address").val(d.address);
            $("#status").val(d.status);
            $("#username").val(d.username);
            $("#totalprice").val(d.totalprice);
            $("#quantity").val(d.quantity);
            $("#id").val(d.id);
        },
        error:function(data){
            swal('错误', '加载失败！', 'error');
        }
    });
}

function insertTeaOrders(){
    var reg = /^\d+(?:\.\d{1,2})?$/;
    if($("#totalprice").val() != '') {
        if (!reg.test($("#totalprice").val())) {
            errorSwal("请输入整数或者两位小数的订单金额！");
            return;
        }
    }else{
        errorSwal("请输入订单金额！");
        return;
    }

    if($("#username").val() == ''){
        errorSwal("请输入会员名！");
        return;
    }else{
        var b = true;
        $.ajax({
            url:'../teaOrders/isHave',
            async:false,
            data:{'username':$("#username").val()},
            type:'get',
            success:function (data) {
                if(data != 0){
                    b = false;
                }else{
                    b = true;
                }
            }
        });
        if(!b){
            errorSwal("该会员不存在");
            return;
        }
    }

    if($("#quantity").val() == ''){
        errorSwal("请输入积分数！");
        return;
    }else{
        if($("#totalprice").val() == ''){
            errorSwal("请输入订单金额！");
            return;
        }else{
            if((($("#quantity").val())/($("#totalprice").val())) == 10){
                errorSwal("请核对订单金额和订单积分！");
                return;
            }
        }
    }

    $.ajax( {
        type : 'post',
        url : '../teaOrders/editOrders',
        data : $('#addForm').serialize(),
        dataType : 'json',
        success : function(data) {
            if(data.flag==0){
                closeTeaOrders();
                swal('正确', '编辑成功', 'success');
                teaOrdersQuery();
            }else{
                swal('错误', '编辑失败！', 'error');
            }

        },
        error : function() {
            swal('错误', '编辑失败！', 'error');
        }
    });


}