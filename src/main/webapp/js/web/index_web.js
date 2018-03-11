$(document).ready(function () {
    GoodsQuery();


});

//查询所有商品
function GoodsQuery() {
    $.ajax({
        url: '/TeaShop/web/getGoodsByAddtime',
        type: 'get',
        //dataType:'json',
        success: function (data) {
            var html = '<ul>';
            for (var i = 0; i < data.length; i++) {
                var goods = data[i];
                html += "<li>" +
                    "<img src='../../upload/" + goods.picpath + "'>" +
                    "<p class='goods_name'>" + goods.goodsname + "</p>" +
                    "<p class='goods_desc'>" + goods.brief + "</p>" +
                    "<p class='goods_price pull-left'>¥" + goods.price + "</p>" +
                    "<p class='goods_add'>" +
                    "<button class='btn btn-success btn-sm pull-right' onclick=\"location.href='/TeaShop/pc/order_confirm.html?goodsId=" + goods.id + "'\">立即购买</button>" +
                    "</p>" +
                    "</li>";
            }
            html += "</ul>"
            $("#goodsList").html(html)

        },
        error: function (data) {
            swal('错误', '加载失败！', 'error');
        }
    });
}