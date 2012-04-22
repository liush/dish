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
            var consumeId = $.trim($("#consumeId").val());
            var $money = $("#money").focus();
            var $q = $("#q");
            var $dish_price = $("#dish_price");
            var $water_price = $("#water_price");
            var $other_price = $("#other_price");
            var $pay = $("#pay");
            var $discount = $("#discount");
            var $total_price = $("#total_price");
            var $actualPrice = $("#actualPrice");
            var $change = $("#change");
            var $print = $("#print");

            $print.click(function () {
                var $this = $(this);
                if ($this.hasClass("disabled")) {
                    return false;
                }
                $this.addClass("disabled");
                window.open(contextPath + "/home/print?consumeId="+consumeId);
//                $.myAjax({
//                    type:"post",
//                    url:contextPath + "/home/print",
//                    data:{consumeId:consumeId},
//                    dataType:"json",
//                    success:function (data) {
//
//                    },
//                    error:function () {
//                        alert("操作失败，请联系管理员。");
//                    }
//
//                });
                $this.removeClass("disabled");
            });

            $discount.change(function () {
                var $this = $(this);
                var val = $this.val();
                setActualPrice(val);
            });

            $money.keyup(function (e) {
                if (e.keyCode == 13) {
                    $pay.click();
                } else {
                    $change.text($money.val() - $actualPrice.text());
                }
            });

            function setActualPrice(discount) {
                var dish_price = parseFloat($dish_price.text());
                var actualPrice = Math.round(discount * dish_price / 10) + parseFloat($water_price.text()) + parseFloat($other_price.text());
                $actualPrice.text(actualPrice);
                $change.text($money.val() - actualPrice);
            }

            $pay.click(function () {
                var $this = $(this);
                if ($this.hasClass("disabled")) {
                    return false;
                }
                var money = $.trim($money.val());
                if (money == "") {
                    alert("请输入客户付款金额");
                    $money.focus();
                    return false;
                }
                var newData = grid.getData();
                var length = newData.length;
                if (length == 0) {
                    alert("没有任何菜品，请添加。");
                    $q.focus();
                    return false;
                }
                var data = {};
                $this.addClass("disabled");
                data.consumeId = consumeId;
                data.money = money;
                data.dishPrice = $dish_price.text();
                data.waterPrice = $water_price.text();
                data.otherPrice = $other_price.text();
                data.totalPrice = $total_price.text();
                data.discount = $discount.val();
                data.actualPrice = $actualPrice.text();
                $.myAjax({
                    type:"post",
                    url:contextPath + "/home/payDish",
                    data:data,
                    dataType:"json",
                    success:function (data) {
                        $this.removeClass("disabled");
                        if (data[DishConst.RESULT] == DishConst.SUCCESS) {
                            location.href = contextPath + "/home/";
                        } else if (data[DishConst.RESULT] == ConsumeController.MONEY_ERROR) {
                            alert("输入价格错误，请重新输入。");
                        } else if (data[DishConst.RESULT] == ConsumeController.MONEY_SMALL) {
                            alert("实收款小于应收款，请仔细确认。");
                        }
                    }, error:function () {
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
        <span class="span-margin-right20">人数：${consume.peopleNum}</span>
    </div>
    <div id="grid" class="grid">
    </div>
    <div class="bottom-div">
        <span class="span-margin-left20">菜金合计：</span><span id="dish_price">${consume.dishPrice/100}元</span>
        <span class="span-margin-left20">酒水合计：</span><span id="water_price">${consume.waterPrice/100}元</span>
        <span class="span-margin-left20">其他合计：</span><span id="other_price">${consume.otherPrice/100}元</span>
        <span class="span-margin-left20">金额合计：</span><span id="total_price">${consume.totalPrice/100}元</span>
    </div>
    <div class="bottom-div">
        <span>折扣：</span><select id="discount">
        <option value="10">无折扣</option>
        <option value="9.5">不开发票,9.5折</option>
        <option value="7.8">特殊折扣,7.8折</option>
    </select>
        <span class="span-margin-left20">应收款：</span><span id="actualPrice">${consume.totalPrice/100}</span>元
        <span class="span-margin-left20">实收款：</span><input type="text" class="input" id="money">元
        <span class="span-margin-left20">找零：</span><span id="change"></span>元
    </div>
    <div class="bottom-div" style="text-align: center;">
        <input type="button" class="button" value="收款" id="pay">
        <input type="button" class="button" value="打印小票" id="print">
        <input type="button" class="button" value="返回" id="back">
    </div>
</div>
<input type="hidden" id="consumeId" value="${consume.id}">
</body>
</html>