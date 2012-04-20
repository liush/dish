<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>谷穗缘餐饮系统</title>
    <%@include file="../common.jsp" %>
    <script type="text/javascript">
        $(document).ready(function () {
            var $number = $("#number");
            var $name = $("#name");
            var $numberTip = $number.parent().next();
            var $nameTip = $name.parent().next();
            var $price = $("#price");
            var $priceTip = $price.parent().next();
            var $unit = $("#unit");
            var $unitTip = $price.parent().next();
            $number.blur(function () {
                var $this = $(this);
                var value = $.trim($this.val());
                if (value == "") {
                    $numberTip.text("请输入编号。");
                    return false;
                }
                $numberTip.empty();
            });
            $name.blur(function () {
                var $this = $(this);
                var value = $.trim($this.val());
                if (value == "") {
                    $nameTip.text("请输入名称。");
                    return false;
                }
                $nameTip.empty();
            });
            $price.blur(function () {
                var $this = $(this);
                var value = $.trim($this.val());
                if (value == "") {
                    $priceTip.text("请输入价格。");
                    return false;
                }
                $priceTip.empty();
            });
            $unit.blur(function () {
                var $this = $(this);
                var value = $.trim($this.val());
                if (value == "") {
                    $unitTip.text("请输入单位。");
                    return false;
                }
                $unitTip.empty();
            });

            var $form = $("form");
            var inputs = $form.find(".input");
            var tips = $form.find(".form-table-tip");

            $("#create").click(function () {
                var $this = $(this);
                if ($this.hasClass("disabled")) {
                    return false;
                }
                inputs.blur();
                var isCommit = true;
                tips.each(function () {
                    var value = $.trim($(this).text());
                    if (value != "") {
                        isCommit = false;
                        return false;
                    }
                });
                if (isCommit) {
                    $this.addClass("disabled");
                    $.ajax({
                        type:"post",
                        url:contextPath + "/home/addInformation",
                        data:$form.serialize(),
                        dataType:"json",
                        success:function (data) {
                            $this.removeClass("disabled");
                            if (data[DishConst.RESULT] == UserController.TABLE_EXISTS) {
                                alert("餐桌号已存在，请修改后提交。");
                            } else {
                               top.$.colorbox.close();
                            }
                        },
                        error:function () {
                            $this.removeClass("disabled");
                            alert("添加餐桌异常，请与管理员联系。");
                        }
                    });
                }
            });

        });
    </script>
</head>
<body>
<form>
    <table class="form-table">
        <tbody>
        <tr>
            <td class="form-table-name">名称：</td>
            <td class="form-table-value"><input type="text" class="input" name="name" id="name"></td>
            <td class="form-table-tip">&nbsp;</td>
        </tr>
        <tr>
            <td class="form-table-name">编号：</td>
            <td class="form-table-value"><input type="text" class="input" name="number" id="number"></td>
            <td class="form-table-tip">&nbsp;</td>
        </tr>
        <tr>
            <td class="form-table-name">价格：</td>
            <td class="form-table-value"><input type="text" class="input" name="price" id="price"></td>
            <td class="form-table-tip">&nbsp;</td>
        </tr>
        <tr>
            <td class="form-table-name">单位：</td>
            <td class="form-table-value"><input type="text" class="input" name="unit" id="unit"></td>
            <td class="form-table-tip">&nbsp;</td>
        </tr>
        <tr>
            <td class="form-table-name">类型：</td>
            <td class="form-table-value">
                <select name="type" id="type">
                    <option value="1">菜</option>
                    <option value="2">酒水</option>
                    <option value="3">其他</option>
                </select>
            </td>
            <td class="form-table-tip">&nbsp;</td>
        </tr>
        </tbody>
    </table>
    <div style="text-align: center">
        <input type="button" class="button" id="create" value="确定">
    </div>
</form>
</body>
</html>