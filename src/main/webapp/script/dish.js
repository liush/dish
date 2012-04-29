var grid;
$(document).ready(function () {
    var consumeId = $.trim($("#consumeId").val());
    var $q = $("#q").focus();
    var select = $.localSelect();
    var $dish_price = $("#dish_price");
    var $water_price = $("#water_price");
    var $other_price = $("#other_price");
    var $total_price = $("#total_price");
    var $back = $("#back");
    var $discount = $("#discount");

    grid = (function () {
        var heads = [
            {title:"名称", text:"名称"},
            {title:"规格", text:"规格"},
            {title:"价格", text:"价格"},
            {title:"类别", text:"类别"},
            {title:"备注", text:"备注"},
            {title:"数量", text:"数量"},
            {title:"小计", text:"小计"},
            {title:"操作", text:"操作"}
        ];
        var columns = [
            {name:"name"},
            {name:"unit"},
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
            {render:function (cell, item) {
                return "";
            }},
            {render:function (cell, item) {
                if (!item.count) {
                    item.count = 1;
                }
                var input = $('<input id="' + item.detailId + '" type="text" class="input" value="' + item.count + '">');

                input.keydown(function (e) {
                    if (e.keyCode == 13) {
                        $q.focus();
                    }
                });
                input.blur(function () {
                    var $this = $(this);
                    var val = $this.val();
                    if(item.detailId){
                        updateDetail(consumeId,item.detailId,val);
                    }else{
                        insertDetail(consumeId,item.informationId,val);
                    }
                });
                return input;
            }},
            {render:function (cell, item) {
                var span = $('<span>' + item.price * item.count / 100 + '元</span>');
                return span;
            }},
            {render:function (cell, item) {
                var a = $('<a href="#">删除</a>');
                a.click(function () {
                    var $this = $(this);
                    if ($this.hasClass("disabled")) {
                        return false;
                    }
                    $this.addClass("disabled");
                    delDetail(consumeId, item.detailId);
                    $q.focus();
                });
                return a;
            }}
        ];
        var grid = $.grid({
            container:"grid",
            heads:heads,
            columns:columns
        });
        return grid;
    })();
    $q.keyup(function (e) {
        var $this = $(this);
        var offset = $this.offset();
        var value = $.trim($this.val());
        if (value != "") {
            select.init({url:contextPath + "/home/queryInformation", data:{q:value}, callback:checkCallBack, top:offset.top + $this.outerHeight(), left:offset.left});
            if (e.keyCode == 13) {
                checkCallBack(select.getSelected());
                return false;
            } else {
                if (e.keyCode == 38) {
                    select.moveUp();
                    return false;
                } else if (e.keyCode == 40) {
                    select.moveDown();
                    return false;
                }
            }
        } else {
            select.clear();
        }
    });
    $back.click(function () {
        var $this = $(this);
        if ($this.hasClass("disabled")) {
            return false;
        }
        $this.addClass("disabled");
        location.href = contextPath + "/home/";
        $this.removeClass("disabled");
    });

    function checkCallBack(item) {
        if (item != null) {
            var data = grid.getData();
            data.push(item);
            grid.render(data);
            $("#" + item.detailId).select();
            $q.val("");
            select.clear();
        }
    }

    function countMoney(data) {
        var dish_price = 0;
        var water_price = 0;
        var other_price = 0;
        for (var i = 0; i < data.length; i++) {
            var item = data[i];
            var input = $("#" + item.detailId);
            var n = input.val();
            var type = item.type;
            if (type == Information.DISH) {
                dish_price += n * item.price;
            }
            if (type == Information.WATER) {
                water_price += n * item.price;
            }
            if (type == Information.OTHER) {
                other_price += n * item.price;
            }
        }
        var total_price = dish_price + water_price + other_price;
        $dish_price.text(dish_price / 100 + "元");
        $water_price.text(water_price / 100 + "元");
        $other_price.text(other_price / 100 + "元");
        $total_price.text(total_price / 100 + "元");
    }

    function insertDetail(consumeId, informationId, count) {
        var data = {consumeId:consumeId, type:ConsumeController.CREATE};
        data.informationId = informationId;
        data.count = count;
        $.myAjax({
            type:"post",
            url:contextPath + "/home/updateDetails",
            data:data,
            dataType:"text",
            success:function (data) {
                getData();
            }, error:function () {
                alert("添加失败，请与管理员联系。");
            }
        });
    }

    function delDetail(consumeId, detailId) {
        $.myAjax({
            type:"post",
            url:contextPath + "/home/updateDetails",
            data:{type:2, consumeId:consumeId, detailId:detailId,type:ConsumeController.DELETE},
            success:function (data) {
                getData();
            }, error:function () {
                alert("删除失败，请与管理员联系。");
            }
        });
    }

    function updateDetail(consumeId, detailId, count) {
        $.myAjax({
            type:"post",
            url:contextPath + "/home/updateDetails",
            data:{type:1, consumeId:consumeId, detailId:detailId, count:count,type:ConsumeController.UPDATE},
            success:function (data) {
                getData();
            }, error:function () {
                alert("修改失败，请与管理员联系。");
            }
        });
    }

    function getData() {
        $.myAjax({
            type:"post",
            url:contextPath + "/home/getConsumeDetails",
            data:{consumeId:consumeId},
            dataType:"json",
            success:function (data) {
                grid.render(data);
                countMoney(data);
                try {
                    if ($discount.length > 0) {
                        var $actualPrice = $("#actualPrice");
                        var $change = $("#change");
                        var discount = $discount.val();
                        var dish_price = parseFloat($dish_price.text());
                        var actualPrice = Math.round(discount * dish_price / 10) + parseFloat($water_price.text()) + parseFloat($other_price.text());
                        $actualPrice.text(actualPrice);
                        $change.text(-actualPrice);
                    }
                } catch (e) {
                }
            },
            error:function () {
                alert("操作失败，请联系管理员。");
            }
        });
    }
});