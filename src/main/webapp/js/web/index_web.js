$(document).ready(function() {
    GoodsQuery();


});

//查询所有商品
function GoodsQuery(){
    $.ajax({
        url:'/TeaShop/web/getGoodsByAddtime',
        type:'get',
        //dataType:'json',
        success:function(data){
            var html = '<ul>';
            for (var i=0;i<data.length;i++){
                html += "<li>"+
                "<img src='../../upload/"+data[i].picpath+"'>"+
                    "<p class='goods_name'>"+data[i].goodsname+"</p>"+
                "<p class='goods_desc'>"+data[i].brief+"</p>"+
                "<p class='goods_price pull-left'>¥"+data[i].price+"</p>"+
                "</li>"
            }
            html += "</ul>"
            $("#goodsList").html(html)

        },
        error:function(data){
            swal('错误', '加载失败！', 'error');
        }
    });
}