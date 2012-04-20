<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>谷穗缘餐饮系统</title>
    <%@include file="../common.jsp" %>
    <script type="text/javascript" src="${resources}/script/autoComplete.js"></script>
    <script type="text/javascript" src="${resources}/script/dish.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            var $q = $("#q").focus();
            var consumeId = $.trim($("#consumeId").val());
            var $peopleNum = $("#peopleNum");
            var $sure = $("#sure");
            var $dish_price = $("#dish_price");
            var $water_price = $("#water_price");
            var $other_price = $("#other_price");
            var $total_price = $("#total_price");
            $sure.click(function () {
                var $this = $(this);
                if ($this.hasClass("disabled")) {
                    return false;
                }

                var peopleNum = $.trim($peopleNum.val());
                if (peopleNum == "") {
                    alert("请输入就餐人数。");
                    $peopleNum.focus();
                    return false;
                }
                var datas = grid.getData();
                var length = datas.length;
                if (length == 0) {
                    alert("没有任何菜品，请添加。");
                    $q.focus();
                    return false;
                }
                var data = {};
                data.tableNo = "${consume.tableNo}";
                data.peopleNum = peopleNum;
                data.consumeId = consumeId;
                data.dishPrice = $dish_price.text();
                data.waterPrice = $water_price.text();
                data.otherPrice = $other_price.text();
                data.totalPrice = $total_price.text();
                data.actualPrice = data.totalPrice;
                $this.addClass("disabled");
                $.myAjax({
                    type:"post",
                    url:contextPath + "/home/orderDish",
                    data:data,
                    dataType:"json",
                    success:function (data) {
                        if (data[DishConst.RESULT] == DishConst.SUCCESS) {
                            location.href = contextPath + "/home/";
                        } else if (data[DishConst.RESULT] == ConsumeController.TABLE_EXISTS_CONSUME) {
                            alert("该餐桌已经有人在用餐了，请修改桌号。")
                        }
                        $this.removeClass("disabled");
                    },
                    error:function () {
                        alert("开单失败，请联系管理员。");
                        $this.removeClass("disabled");
                    }
                });
            });

            grid.render(${details});
        });
    </script>
</head>
<body>
<div class="body">
    <div class="search-div">
        <span class="span-margin-right20">桌号：${consume.tableNo}</span>
        <span class="span-margin-right20">菜品检索：<input type="text" class="input" id="q"></span>
        <span class="span-margin-right20">人数：<input type="text" class="input" id="peopleNum"
                                                    value="${consume.peopleNum}"></span>
    </div>
    <div id="grid" class="grid">
    </div>
    <div class="bottom-div">
        <span class="span-margin-left20">菜金合计：</span><span id="dish_price">${consume.dishPrice/100}元</span>
        <span class="span-margin-left20">酒水合计：</span><span id="water_price">${consume.waterPrice/100}元</span>
        <span class="span-margin-left20">其他合计：</span><span id="other_price">${consume.otherPrice/100}元</span>
        <span class="span-margin-left20">金额合计：</span><span id="total_price">${consume.totalPrice/100}元</span>
    </div>
    <div class="bottom-div" style="text-align: center;">
        <input type="button" class="button" value="确定" id="sure">
        <input type="button" class="button" value="返回" id="back">
    </div>
</div>
<input type="hidden" id="consumeId" value="${consume.id}">
</body>
</html>