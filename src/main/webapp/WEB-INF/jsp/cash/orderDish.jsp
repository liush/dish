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
            var $order = $("#order");
            var $peopleNum = $("#peopleNum");
            var $tableNo = $("#tableNo");
            var $dish_price = $("#dish_price");
            var $water_price = $("#water_price");
            var $other_price = $("#other_price");
            var $total_price = $("#total_price");
            $order.click(function () {
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
                var tableNo = $.trim($tableNo.val());
                if (tableNo == "") {
                    alert("请输入桌号。");
                    $tableNo.focus();
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
                data.tableNo = tableNo;
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
        });
    </script>
</head>
<body>
<div class="body">
    <div class="search-div">
        <span class="span-margin-right20">桌号：<input type="text" class="input" id="tableNo" value="${tableNo}"></span>
        <span class="span-margin-right20">菜品检索：<input type="text" class="input" id="q"></span>
        <span class="span-margin-right20">人数：<input type="text" class="input" id="peopleNum"></span>
    </div>
    <div id="grid" class="grid">
    </div>
    <div class="bottom-div">
        <span>菜金合计：</span><span class="span-margin-right20" id="dish_price">0元</span>
        <span>酒水合计：</span><span class="span-margin-right20" id="water_price">0元</span>
        <span>其他合计：</span><span class="span-margin-right20" id="other_price">0元</span>
        <span>总计：</span><span class="span-margin-right20" id="total_price">0元</span>
    </div>
    <div class="bottom-div" style="text-align: center;">
        <input type="button" class="button" value="开单" id="order">
        <input type="button" class="button" value="返回" id="back">
    </div>
</div>
<input type="hidden" id="consumeId" value="${id}">
</body>
</html>