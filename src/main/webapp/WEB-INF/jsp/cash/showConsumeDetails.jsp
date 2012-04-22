<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>谷穗缘餐饮系统</title>
    <%@include file="../common.jsp" %>
    <script type="text/javascript">
        $(document).ready(function () {
            var grid = (function () {
                var heads = [
                    {title:"名称", text:"名称"},
                    {title:"价格", text:"价格"},
                    {title:"类别", text:"类别"},
                    {title:"数量", text:"数量"},
                    {title:"备注", text:"备注"},
                    {title:"小计", text:"小计"}
                ];
                var columns = [
                    {name:"name"},
                    {render:function (cell, item) {
                        return item.price / 100 + "元";
                    }},
                    {render:function (cell, item) {
                        var type = item.type;
                        if (type == Information.DISH) {
                            return "菜";
                        }
                        if (type == Information.WATER) {
                            return "酒水";
                        }
                        if (type == Information.OTHER) {
                            return "其他";
                        }
                        return "";
                    }},
                    {name:"count"},
                    {render:function (cell, item) {
                        return "";
                    }},
                    {render:function (cell, item) {
                        var span = $('<span>' + item.price * item.count / 100 + '元</span>');
                        return span;
                    }}
                ];
                var grid = $.grid({
                    container:"grid",
                    heads:heads,
                    columns:columns
                });
                return grid;
            })();
            grid.render(${details});
            $("#back").click(function () {
                var $this = $(this);
                if ($this.hasClass("disabled")) {
                    return false;
                }
                $this.addClass("disabled");
                location.href = contextPath + "/home/showConsumes";
                $this.removeClass("disabled");
            })

        });
    </script>
</head>
<body>
<div class="search-div">
    <span class="span-margin-right20">桌号：${consume.tableNo}</span>
    <span class="span-margin-right20">人数：${consume.peopleNum}</span>
</div>
<div id="grid"></div>
<div class="bottom-div">
    <span>菜金合计：</span><span class="span-margin-right20">${consume.dishPrice/100}元</span>
    <span>酒水合计：</span><span class="span-margin-right20">${consume.waterPrice/100}元</span>
    <span>其他合计：</span><span class="span-margin-right20">${consume.otherPrice/100}元</span>
    <span>总计：</span><span class="span-margin-right20">${consume.totalPrice/100}元</span>
    <span>折扣：</span><span class="span-margin-right20">${consume.discount}折</span>
    <span>应收款：</span><span class="span-margin-right20">${consume.actualPrice/100}元</span>
    <span>实收款：</span><span class="span-margin-right20">${consume.money/100}元</span>
    <span>找零：</span><span class="span-margin-right20">${(consume.money - consume.actualPrice)/100}元</span>
</div>
<div style="text-align: center"><input type="button" value="返回" class="button" id="back"></div>
</body>
</html>