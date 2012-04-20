<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>谷穗缘餐饮系统</title>
    <%@include file="../common.jsp" %>
    <script type="text/javascript">
        $(document).ready(function () {
            var $tableNo = $("#tableNo");
            var $tableNoTip = $tableNo.parent().next();
            $tableNo.blur(function () {
                var $this = $(this);
                var value = $.trim($this.val());
                if (value == "") {
                    $tableNoTip.text("请输入桌号。");
                    return false;
                }
                $tableNoTip.empty();
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
                        url:contextPath + "/home/addTable",
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
            <td class="form-table-name">桌号：</td>
            <td class="form-table-value"><input type="text" class="input" name="tableNo" id="tableNo"></td>
            <td class="form-table-tip">&nbsp;</td>
        </tr>
        </tbody>
    </table>
    <div style="padding-left: 100px;">
        <input type="button" class="button" id="create" value="确定">
    </div>
</form>
</body>
</html>