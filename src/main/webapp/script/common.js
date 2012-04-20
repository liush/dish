/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-4-3
 * Time: 上午11:16
 * To change this template use File | Settings | File Templates.
 */
(function ($) {
    var DishConst = {};
    DishConst.RESULT = "result";
    DishConst.SUCCESS = "success";
    DishConst.FAIL = "fail";
    var UserController = {};
    UserController.USER_EXISTS = "0";
    UserController.TABLE_EXISTS = "0";
    var ConsumeController = {};
    ConsumeController.TABLE_EXISTS_CONSUME = "0";
    ConsumeController.MONEY_ERROR = "9";
    ConsumeController.MONEY_SMALL = "90";
    ConsumeController.CREATE = 1;
    ConsumeController.UPDATE = 2;
    ConsumeController.DELETE = 3;
    var Consume = {};
    Consume.NO_PAY = "0";
    Consume.PAY = "1";
    Consume.REVERSE_PAY = "2";
    var Information = {};
    Information.DISH = 1;
    Information.WATER = 2;
    Information.OTHER = 3;
    var User = {};
    User.CASH = 1;
    User.MANAGER = 2;
    User.SHOPKEEPER = 3;
    User.ADMIN = 9;
    window.User = User;
    window.DishConst = DishConst;
    window.UserController = UserController;
    window.ConsumeController = ConsumeController;
    window.Information = Information;
    window.Consume = Consume;
    var features = "location=0,menubar=0" +
        ",resizable=0,scrollbars=0" +
        ",status=0,toolbar=0,directories=0";
    window.features = features;
    (function () {
        $.myAjax = function (o) {
            return $.ajax(o);
        }
        $.fn.serializeJson = function () {
            var array = $(this).serializeArray();
            var data = {};
            $.map(array, function (item) {
                data[item.name] = item.value;
            });
            return data;
        }
    })();


})(jQuery);

/**
 * 格式化日期信息
 * @param time long型毫秒级
 */
function formatTime(time) {
    var date = new Date();
    date.setTime(time);
    var result = date.getFullYear() + "-" +
        build(date.getMonth() + 1) + "-" +
        build(date.getDate()) + " " +
        build(date.getHours()) + ":" +
        build(date.getMinutes())+":"+build(date.getSeconds());

    function build(s) {
        if (s < 10) {
            return "0" + s;
        }
        return s;
    }

    return result;
}
$(document).ready(function () {
    var $top_userName = $("#top_userName");
    var $top_time = $("#top_time");
    $.myAjax({
        type:"post",
        url:contextPath + "/home/getMessage",
        dataType:"json",
        success:function (data) {
            $top_userName.text(data.user.realName+"("+data.user.userName+")");
            clock(data.time);
        }
    });

    function clock(time){
        $top_time.text(formatTime(time));
        setTimeout(function(){
            clock(time+1000);
        },1000);
    }
});