$(document).ready(function() {
    teaGoodsQuery();
});

var teaGoodsColumns =
    [ {
        field : 'id',
        title : '商品编号',
        width : '10%',
        align : 'center',
        /*formatter : function(value, row, index) {
            return index + 1;
        }*/
    }, {
        field : 'goodsname',
        title : '商品名称',
        align : 'center'
    }, {
        field : 'price',
        title : '商品价格',
        align : 'center'
    }, {
        field : 'brief',
        title : '商品简介',
        align : 'center'
    },{
        field : 'id',
        title : '操作',
        align : 'center',
        formatter : 'operateFormatter'
    }];
function operateFormatter(value, row, index) {
    return [
        '<a class="edit" href="javascript:void(0)" title="编辑" style="color:#f0ad4e" onclick="updateTeaGoodsInfo(\'' + value + '\')">',
        '<i class="glyphicon glyphicon-edit"></i>',
        '</a>'
        + "&nbsp;&nbsp;&nbsp;&nbsp;"
        + '<a class="remove" href="javascript:void(0)" title="删除" onclick="deleteTeaGoods(' + value + ')">',
        '<i class="glyphicon glyphicon-remove"></i>', '</a>' ].join('');
}

function teaGoodsQuery() {
    var goodsName = $("#goodsNameQuery").val();
    var goodsDesc = $("#goodsDescQuery").val();
    $('#teaGoodsTable').bootstrapTable('destroy');
    // 初始化表格,动态从服务器加载数据
    $("#teaGoodsTable").bootstrapTable( {
        method : "get", // 使用get请求到服务器获取数据
        contentType : "application/x-www-form-urlencoded",
        url : "../teaGoods/getAll", // 获取数据的Servlet地址
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
                goodsName:goodsName,
                goodsDesc:goodsDesc
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

function addTeaGoods(){
    $("#point-title").html("添加商品");
    $("#pic").fileinput({
        language : 'zh',
        uploadUrl : "../teaGoods/upload",
        autoReplace : true,
        showPreview : false,//是否显示预览
        showRemove : true, //显示移除按钮
        maxFileCount : 1,
        allowedFileExtensions : [ "jpg", "png", "gif" ,"jpeg"],
        browseClass : "btn btn-primary", //按钮样式
        previewFileIcon : "<i class='glyphicon glyphicon-king'></i>",
        // msgFilesTooMany: "选择上传的文件数量({1}) 超过允许的最大数值{1}！",
    }).on("fileuploaded", function(e, data) {
        var res = data.response;
        $("#picpath").val(res.data);
    });
    $('#addTeaGoods').modal( {
        backdrop : 'static'
    });
}

function closeTeaGoods(){
    $('#addTeaGoods').modal('hide');
    $("#goodsname").val("");
    $("#brief").val("");
    $("#picpath").val("");
    $("#price").val("");
    $("#id").val("");
    $("#picture").attr("src","");
    $("#pic").fileinput('clear');
}

function updateTeaGoodsInfo(id){
    $('#addTeaGoods').modal( {
        backdrop : 'static'
    });
    $("#point-title").html("编辑商品");
    $.ajax({
        url:'../teaGoods/getOneById',
        data:{'id':id},
        type:'get',
        //dataType:'json',
        success:function(data1){
            var d = (eval("("+data1+")")).data;
            $("#id").val(d.id);
            $("#goodsname").val(d.goodsname);
            $("#brief").val(d.brief);
            $("#picpath").val(d.picpath);
            $("#price").val(d.price);
            $("#picture").attr("src",d.picpath);
        },
        error:function(data){
            swal('错误', '加载失败！', 'error');
        }
    });
    $("#pic").fileinput({
        language : 'zh',
        uploadUrl : "../teaGoods/upload",
        autoReplace : true,
        showPreview : false,//是否显示预览
        showRemove : true, //显示移除按钮
        maxFileCount : 1,
        allowedFileExtensions : [ "jpg", "png", "gif" ,"jpeg"],
        browseClass : "btn btn-primary", //按钮样式
        previewFileIcon : "<i class='glyphicon glyphicon-king'></i>",
        // msgFilesTooMany: "选择上传的文件数量({1}) 超过允许的最大数值{1}！",
    }).on("fileuploaded", function(e, data) {
        var res = data.response;
        $("#picpath").val(res.data);
        $("#picture").attr("src","");
    });

    //$("#renterId").attr("disabled","true");
}

function insertTeaGoods(){
    var reg = /^\d+(?:\.\d{1,2})?$/;
    if($("#price").val() != '') {
        if (!reg.test($("#price").val())) {
            errorSwal("请输入整数或者两位小数！");
            return;
        }
    }else{
        errorSwal("请输入商品价格！");
        return;
    }

    if($("#goodsname").val() == ''){
        errorSwal("请输入商品名称！");
        return;
    }

    if($("#point-title").html() == "添加商品"){
        $.ajax( {
            type : 'post',
            url : '../teaGoods/addTeaGoods',
            data : $('#addForm').serialize(),
            dataType : 'json',
            success : function(data) {
                if(data.flag==0){
                    closeTeaGoods();
                    swal('正确', '添加成功', 'success');
                    teaGoodsQuery();
                }else{
                    swal('错误', '添加失败！', 'error');
                }

            },
            error : function() {
                swal('错误', '添加失败！', 'error');
            }
        });
    }else if($("#point-title").html() == "编辑商品"){
        $.ajax( {
            type : 'post',
            url : '../teaGoods/editGoods',
            data : $('#addForm').serialize(),
            dataType : 'json',
            success : function(data) {
                if(data.flag==0){
                    closeTeaGoods();
                    swal('正确', '编辑成功', 'success');
                    teaGoodsQuery();
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

function deleteTeaGoods(id){
    swal( {
        title : "删除商品?",
        text : "你确定要删除这个商品吗？",
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
                url: '../teaGoods/deleteTeaGoods',
                data:{"id":id},
                dataType : 'json',
                success:function(value){
                    if(value.flag==0){
                        teaGoodsQuery();
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
            swal('取消', '商品没有删除', 'error');
        }
    });
}