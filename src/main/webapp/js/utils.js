/**
 * Created by se7en on 2017/11/10.
 */

var host = window.location.hostname + ':8080';
var realtimeSocket = host + '/realtime', alarmSocket = host + '/alarm', socket;
function successSwal(message, timer) {
    swal({
        title : "提示",
        text : message ? message : "操作成功！",
        // type: "info",
        type : "success",
        timer : timer ? timer : 1500,
        closeOnConfirm : false
    });
}

function errorSwal(message, timer) {
    swal({
        title : "错误",
        text : message ? message : "操作失败！",
        type : "error",
        timer : timer ? timer : 1500,
        closeOnConfirm : false
    });
}
function warnSwal(message, timer) {
    swal({
        title : "警告",
        text : message ? message : "操作失败！",
        type : "warning",
        timer : timer ? timer : 1500,
        closeOnConfirm : false
    });
}
function loadSwal(title) {
    swal({
        showCancelButton : false,
        showConfirmButton : false,
        title : title || "加载中",
        imageUrl : ".。。/images/file-loader.gif"
    });
}
var numberFormat = {
    numberDecimal : 2
};
/**
 * 根据获取到的资源权限信息，格式化资源树
 *
 * @param selectedResources
 */
function formatResourcesTree(selectedResources) {
    var length = selectedResources.length;
    var oAuth = '';
    for ( var i = 0; i < length; i++) {
        var o = selectedResources[i];
        if (o.auth == 0) {
            var ooAuth = o.id;
            for ( var j = 0; j < length; j++) {
                var oo = selectedResources[j];
                if (oo.auth == 1 && oo.pId == o.id) {
                    ooAuth += ('|' + oo.id)
                }
            }
            oAuth += (ooAuth + '$');
        }
    }
    return oAuth;
}

var utils = {
    formatString : 'yyyy-MM-dd HH:mm:ss',
    /**
     * 格式化时间
     *
     * @param value
     * @returns {string}
     */
    formatTime : function(value) {
        var t = new Date(value), tf = function(i) {
            return (i < 10 ? '0' : '') + i;
        }
        return this.formatString.replace(/yyyy|yy|MM|dd|HH|mm|ss/g, function(a) {
            switch (a) {
                case 'yy':
                    return tf((t.getFullYear() + "").substr(2, 3));
                    break;
                case 'yyyy':
                    return tf(t.getFullYear());
                    break;
                case 'MM':
                    return tf(t.getMonth() + 1);
                    break;
                case 'mm':
                    return tf(t.getMinutes());
                    break;
                case 'dd':
                    return tf(t.getDate());
                    break;
                case 'HH':
                    return tf(t.getHours());
                    break;
                case 'ss':
                    return tf(t.getSeconds());
                    break;
            }
        });
    },
    /**
     * 日期格式化
     *
     * @param {}
     *            time
     * @param {}
     *            format
     * @return {}
     */
    dateFormat : function(time, format) {
        var t = new Date(time), tf = function(i) {
            return (i < 10 ? '0' : '') + i;
        }
        return format.replace(/yyyy|yy|MM|dd|HH|mm|ss/g, function(a) {
            switch (a) {
                case 'yy':
                    return tf((t.getFullYear() + "").substr(2, 3));
                    break;
                case 'yyyy':
                    return tf(t.getFullYear());
                    break;
                case 'MM':
                    return tf(t.getMonth() + 1);
                    break;
                case 'mm':
                    return tf(t.getMinutes());
                    break;
                case 'dd':
                    return tf(t.getDate());
                    break;
                case 'HH':
                    return tf(t.getHours());
                    break;
                case 'ss':
                    return tf(t.getSeconds());
                    break;
            }
        });
    },
    dateFormatter : function(value) {
        return new Date(value * 1000).Format("yyyy-MM-dd hh:mm:ss");
    },
    numberFormatter : function(value, rt, numberDecimal) {
        if (rt == 1) {
            return parseInt(value);
        }
        if (typeof (value) == 'number') {
            if (numberDecimal && numberDecimal > 0 && numberDecimal < 5) {
                return value.toFixed(numberDecimal);
            } else {
                return value.toFixed(numberFormat.numberDecimal);
            }
        } else {
            return 'N/A';
        }
    },
    alarmFormater : function getAlarmDesc(RT, DS) {
        var UNKNOWN = "恢复报警";
        var CONFIRM = "报警确认";
        var MASK_L1 = 1 << 1;
        var MASK_H1 = 1 << 2;
        var MASK_L2 = (1 << 3) | (1 << 1);
        var MASK_H2 = (1 << 3) | (1 << 2);
        var MASK_L3 = (1 << 1) | 1;
        var MASK_H3 = (1 << 2) | 1;
        var MASK_L4 = (1 << 3) | (1 << 1) | 1;
        var MASK_H4 = (1 << 3) | (1 << 2) | 1;
        var MASK_C = (1 << 3) | (1 << 2) | (1 << 1) | 1;
        var MASK_AL = (DS >> 7 & 1) << 1 | (DS & 1);
        /**
         * 如果是 DX点
         */
        if (((DS & 128) >> 7) == 1) {
            if (RT == 1) {
                // DX报警
                switch (parseInt(MASK_AL)) {
                    case 0:
                    case 1:
                        return UNKNOWN;
                    case 2:
                        return '<span style="red">变0报警</span>';
                    case 3:
                        return '<span style="red">变1报警</span>';
                    default:
                        return UNKNOWN;
                }
            } else {
                // AX或其它报警
                var intAS = DS >> 1;
                intAS = intAS & 15;
                switch (intAS) {
                    case MASK_L1:
                        return '<span style="color:red;">报警低限</span>';
                    case MASK_L2:
                        return '<span style="color:red;">报警低2限</span>';
                    case MASK_L3:
                        return '<span style="color:red;">报警低3限</span>';
                    case MASK_L4:
                        return '<span style="color:red;">报警低4限</span>';
                    case MASK_H1:
                        return '<span style="color:red;">报警高限</span>';
                    case MASK_H2:
                        return '<span style="color:red;">报警高2限</span>';
                    case MASK_H3:
                        return '<span style="color:red;">报警高3限</span>';
                    case MASK_H4:
                        return '<span style="color:red;">报警高4限</span>';
                    case MASK_C:
                        return '<div style="color:red">变化报警</div>';
                    default:
                        return UNKNOWN;
                }
            }
        } else {
            if (((DS & 32) >> 5) == 1) {
                return UNKNOWN;
            } else {
                return CONFIRM;
            }
        }
    },
    statusFormatter : function(RT, DS) {
        if (((DS & 0x8000) == 0x8000) || DS < 0) { // 第15位为1或者第14位为1，表示超时。
            return "<span style='color: #FFFF00;'>Timeout</span>";
        }
        var UNKNOWN = "<span>Good</span>";
        var MASK_L1 = 1 << 1;
        var MASK_H1 = 1 << 2;
        var MASK_L2 = (1 << 3) | (1 << 1);
        var MASK_H2 = (1 << 3) | (1 << 2);
        var MASK_L3 = (1 << 1) | 1;
        var MASK_H3 = (1 << 2) | 1;
        var MASK_L4 = (1 << 3) | (1 << 1) | 1;
        var MASK_H4 = (1 << 3) | (1 << 2) | 1;
        var MASK_AL = (DS >> 7 & 1) << 1 | (DS & 1);
        if (RT.toString() == '1') {
            // DX报警
            switch (parseInt(MASK_AL)) {
                case 0:
                case 1:
                    return UNKNOWN;
                    break;
                case 2:
                    return "<span style='color:#FF0000;'>变0报警</span>";
                    break;
                case 3:
                    return "<span style='color:#FF0000;'>变1报警</span>";
                    break;
                default:
                    return UNKNOWN;
            }
        } else {
            if (((DS & 128) >> 7) == 1) {
                var intAS = DS >> 1;
                // 8限报警
                intAS = intAS & 15;
                switch (intAS) {
                    case MASK_L1:
                        return "<span style='color:#0000FF;'>报警低限</span>";
                    case MASK_L2:
                        return "<span style='color:#0000FF;'>报警低2限</span>";
                    case MASK_L3:
                        return "<span style='color:#0000FF;'>报警低3限</span>";
                    case MASK_L4:
                        return "<span style='color:#0000FF;'>报警低4限</span>";
                    case MASK_H1:
                        return "<span style='color:#FF0000;'>报警高限</span>";
                    case MASK_H2:
                        return "<span style='color:#FF0000;'>报警高2限</span>";
                    case MASK_H3:
                        return "<span style='color:#FF0000;'>报警高3限</span>";
                    case MASK_H4:
                        return "<span style='color:#FF0000;'>报警高4限</span>";
                    default:
                        return UNKNOWN;
                }
            } else {
                return UNKNOWN;
            }
        }
    },
    formatHM : function(value) {
        return ((parseInt(value / 60) > 0) ? (parseInt(value / 60) + '时') : '') + parseInt(value % 60) + '分';
    }
}

var RegExpUtils = {
    comCode :  /^[A-Z0-9_]*$/,
    check : function(value, RegExp) {
        if (RegExp && value) {
            return RegExp.test(value);
        } else {
            return false;
        }
    }
}
/**
 * 格式化年份处理
 */
function formatYear(beginYear) {
    if (!beginYear) {
        beginYear = 2011;
    }
    var year = new Date().getFullYear();
    var yearArray = new Array();
    var yearObject = null;
    for ( var i = 2011; i <= year; i++) {
        yearObject = new Object();
        yearObject.text = i;
        yearObject.value = i;
        yearArray.push(yearObject);
    }
    return yearArray;
}

Date.prototype.Format = function(fmt) { // author: meizz
    var o = {
        "M+" : this.getMonth() + 1, // 月份
        "d+" : this.getDate(), // 日
        "h+" : this.getHours(), // 小时
        "m+" : this.getMinutes(), // 分
        "s+" : this.getSeconds(), // 秒
        "q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
        "S" : this.getMilliseconds()
        // 毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for ( var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

function ajaxFail(e) {
    if (e.status = 200) {
        window.location.href = "../user/mobileLoginOut";
    } else {
        $.alert('请求出错');
    }
}

function ajaxFailPC(e) {
    if (e.status == 200) {
        window.location.href = "../user/loginOut";
    } else {
        errorSwal('请求出错');
    }
}

function initStyle() {
    $.ajax({
        type : 'get',
        url : '../style/getMobileImage',
        dataType : 'json',
        success : function(data) {
            if (data.flag < 0) {
                $.alert(data.message);
            } else {
                initImage(data.data);
            }
        },
        error : function(e) {
            ajaxFail(e);
        }
    });
}
function initImage(data) {
    var html = "";
    for ( var i = 0; i < data.length; i++) {
        var image = data[i];
        html += '<div class="swiper-slide">' + '<img src="' + image.imageUrl + '" style="width: 100%;height: 5rem">'
            + '</div>';
    }
    $("#topicImage").html(html);
    $(".swiper-container").swiper({
        speed : 800,
        autoplay : 10000,
        spaceBetween : 10,
        pagination : '.swiper-pagination'
    });
}
function getHeight() {
    var height = $(window).height() - $(".navbar navbar-default navbar-fixed-top").height() - $("clearfix").height();
    return height;
}

function getInnerHeight() {
    var height = $(window).height() - $(".navbar navbar-default navbar-fixed-top").height() - $("clearfix").height()
        - 500;
    return height;
}
var isMobile = {
    Android : function() {
        return navigator.userAgent.match(/Android/i) ? true : false
    },
    BlackBerry : function() {
        return navigator.userAgent.match(/BlackBerry/i) ? true : false
    },
    iOS : function() {
        return navigator.userAgent.match(/iPhone|iPad|iPod/i) ? true : false
    },
    Windows : function() {
        return navigator.userAgent.match(/IEMobile/i) ? true : false
    },
    any : function() {
        return (isMobile.Android() || isMobile.BlackBerry() || isMobile.iOS() || isMobile.Windows())
    }
}
