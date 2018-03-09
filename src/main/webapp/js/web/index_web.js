$(document).ready(function() {
    GoodsQuery();


});

//查询所有商品
function GoodsQuery(){
    $.ajax({
        url:'../goods/getGoodsByAddtime',
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
}