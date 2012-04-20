<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 12-3-31
  Time: 下午11:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>谷穗缘餐饮系统</title>
    <%@include file="../common.jsp" %>
    <script type="text/javascript">
        $(document).ready(function () {
            var $use = $("#use");
            var $free = $("#free");
            var $percent = $("#percent");
            (function buildPage(data) {
                var ul = $("#table-list");
                var length = data.length;
                var use = 0;
                for (var i = 0; i < length; i++) {
                    var item = data[i];
                    var li = $('<li class="table-list-item"></li>');
                    var html = '<div>桌号：' + item.tableNo + '</div>';
                    if (item.consumeId != "") {
                        use++;
                        html += '<div>就餐人数：' + item.peopleNum +
                                '</div><div>客人正在就餐。</div>';
                        li.addClass("table-list-item-green");
                    }
                    li.append(html);
                    li.data("item", item);
                    ul.append(li);
                }
                $use.text(use);
                $free.text(length - use);
                $percent.text(Math.round(use * 1000 / length) / 10 + "%");
            })(${tables});

            $(document).click(function (e) {
                if ($(e.target).parents("#right_menu").length == 0) {
                    $("#right_menu").remove();
                }
            });

            var $table = $(".table-list-item");
            $table.contextmenu(
                    function (e) {
                        var item = $(this).data("item");
                        buildRightMenu(e.pageX, e.pageY, item);
                        return false;
                    }).click(function () {
                        var $this = $(this);
                        if ($this.hasClass("disabled")) {
                            return false;
                        }
                        var consumeId = $this.data("consumeId");
                        if (consumeId) {
                            $this.addClass("disabled");
                            getConsume(consumeId, function (consume) {
                                showConsumeMessage(consume);
                                $this.removeClass("disabled");
                            })
                        }

                    });
            function getConsume(id, callback) {
                $.myAjax({
                    url:contextPath + "/home/getConsume",
                    data:{id:id},
                    type:"get",
                    success:function (data) {
                        callback(data);
                    }, error:function () {
                        alert("获取餐桌消费信息异常,请联系管理员。");
                    }
                });
            }

            function showConsumeMessage(consume) {
                //todo 构造餐桌消费信息显示列
            }


            function buildRightMenu(x, y, item) {
                $("#right_menu").remove();
                var ul = $('<ul id="right_menu" class="right-menu"></ul>');
                ul.css({left:x, top:y});
                var orderDish_li = $('<li class="right-menu-item">点菜</li>');
                var pay_li = $('<li class="right-menu-item">结算金额</li>');
                var details_li = $('<li class="right-menu-item">编辑</li>');
                var turn_li = $('<li class="right-menu-item">转台</li>');
                if (item.consumeId == "") {
                    orderDish_li.click(function () {
                        ul.remove();
                        location.href = contextPath + "/home/showOrderDish/" + item.tableNo;
                    });
                    ul.append(orderDish_li);
                } else {
                    pay_li.click(function () {
                        ul.remove();
                        location.href = contextPath + "/home/showPayDish/" + item.consumeId;
                    });
                    details_li.click(function () {
                        ul.remove();
                        location.href = contextPath + "/home/showEditDish/" + item.consumeId;
                    });
                    turn_li.click(function () {
                        ul.remove();
                        $.colorbox({
                            overlayClose:false,
                            opacity:0.3,
                            transition:"none",
                            html:$("#turn-div").html(),
                            width:"400px",
                            height:"150px",
                            title:"转台",
                            onClosed:function () {
                                location.reload();
                            },
                            onComplete:function () {
                                $("#turn").click(function () {
                                    var $this = $(this);
                                    if ($this.hasClass("disabled")) {
                                        return false;
                                    }
                                    var tableNo = $.trim($("#tableNo").val());
                                    var tip = $("#tip");
                                    if (tableNo == "") {
                                        tip.html("<em>请输入要转入的桌号。</em>");
                                        return false;
                                    }
                                    $this.addClass("disabled");
                                    $.myAjax({
                                        type:"post",
                                        url:contextPath + "/home/turnTable",
                                        data:{tableNo:tableNo,consumeId:item.consumeId},
                                        dataType:"json",
                                        success:function (data) {
                                            $this.removeClass("disabled");
                                            if(data[DishConst.RESULT] == ConsumeController.TABLE_EXISTS_CONSUME){
                                                tip.html("<em>要转入的餐桌已经有客人在用餐，请重新输入桌号。</em>");
                                            }else if(data[DishConst.RESULT] == DishConst.FAIL){
                                                tip.html("<em>桌号不存在，请重新输入桌号。</em>");
                                            }else{
                                                top.$.colorbox.close();
                                            }
                                        },
                                        error:function () {
                                            $this.removeClass("disabled");
                                            alert("操作失败，请联系管理员。");
                                        }

                                    });
                                });
                            }
                        });
                    });
                    ul.append(pay_li).append(details_li).append(turn_li);
                }
                $("body").append(ul);
            }

        });
    </script>
</head>
<body>
<jsp:include page="top.jsp">
    <jsp:param name="top" value="0"/>
</jsp:include>
<div>
    <ul id="table-list" class="table-list">
    </ul>
    <div class="fn-clear"></div>
    <div class="body-bottom">
        使用：<span id="use"></span>空闲：<span id="free"></span>使用率：<span id="percent"></span>
    </div>
</div>
<div style="display: none;">
    <div id="turn-div">
        <div id="tip" class="tip"></div>
        <div style="text-align: center;"><span>请输入要转向的桌号：</span><input class="input" type="text" id="tableNo"/></div>
        <div style="text-align: center;margin: 10px;"><input type="button" class="button" id="turn" value="确定"></div>
    </div>
</div>
<%@include file="bottom.jsp" %>
</body>
</html>