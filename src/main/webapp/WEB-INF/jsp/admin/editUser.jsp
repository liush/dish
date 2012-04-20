<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>谷穗缘餐饮系统</title>
    <%@include file="../common.jsp" %>
    <script type="text/javascript">
        var isEdit = "${isEdit}";
        $(document).ready(function () {
            var $userName = $("#userName");
            var $password = $("#password");
            var $confirmPwd = $("#confirmPwd");
            var $realName = $("#realName");
            var $userNameTip = $userName.parent().next();
            var $passwordTip = $password.parent().next();
            var $confirmPwdTip = $confirmPwd.parent().next();
            var $realNameTip = $realName.parent().next();

            $userName.blur(function () {
                var $this = $(this);
                var value = $.trim($this.val());
                if (value == "") {
                    $userNameTip.text("请输入用户名。");
                    return false;
                }
                $userNameTip.empty();
            });
            $password.blur(function () {
                var $this = $(this);
                var value = $.trim($this.val());
                if (value == "") {
                    $passwordTip.text("请输入用密码。");
                    return false;
                }
                $passwordTip.empty();
            });
            $confirmPwd.blur(function () {
                var $this = $(this);
                var value = $.trim($this.val());
                if (value == "") {
                    $confirmPwdTip.text("请输入用密码。");
                    return false;
                }
                if ($password.val() != value) {
                    $confirmPwdTip.text("两次输入的密码不一致，请重新输入。");
                    return false;
                }
                $confirmPwdTip.empty();
            });
            $realName.blur(function () {
                var $this = $(this);
                var value = $.trim($this.val());
                if (value == "") {
                    $realNameTip.text("请输入姓名。");
                    return false;
                }
                $realNameTip.empty();
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
                    var data = $form.serializeJson();
                    if(isEdit !=""){
                       data.isEdit = isEdit;
                    }
                    $.ajax({
                        type:"post",
                        url:contextPath + "/home/addUser",
                        data:data,
                        dataType:"json",
                        success:function (data) {
                            $this.removeClass("disabled");
                            if (data[DishConst.RESULT] == UserController.USER_EXISTS) {
                                alert("用户名已存在，请修改后提交。");
                            } else {
                                top.$.colorbox.close();
                            }
                        },
                        error:function () {
                            $this.removeClass("disabled");
                            alert("添加用户异常，请与管理员联系。");
                        }
                    });
                }
            })
        });
    </script>
</head>
<body>
<form>
    <table class="form-table">
        <tbody>
        <tr>
            <td class="form-table-name">用户名：</td>
            <td class="form-table-value"><input type="text" class="input" name="userName" id="userName"
                                                value="${user.userName}"></td>
            <td class="form-table-tip">&nbsp;</td>
        </tr>
        <tr>
            <td class="form-table-name">密码：</td>
            <td class="form-table-value"><input type="password" class="input" name="password" id="password"></td>
            <td class="form-table-tip">&nbsp;</td>
        </tr>
        <tr>
            <td class="form-table-name">确认密码：</td>
            <td class="form-table-value"><input type="password" class="input" id="confirmPwd"></td>
            <td class="form-table-tip">&nbsp;</td>
        </tr>
        <tr>
            <td class="form-table-name">姓名：</td>
            <td class="form-table-value"><input type="text" class="input" name="realName" id="realName"
                                                value="${user.realName}"></td>
            <td class="form-table-tip">&nbsp;</td>
        </tr>
        <tr>
            <td class="form-table-name">用户类型：</td>
            <td class="form-table-value"><select name="userType" id="userType" class="">
                <option value="1"<c:if test="${user.userType == 1}">checked</c:if>>收银员</option>
                <option value="2" <c:if test="${user.userType == 2}">checked</c:if>>大堂经理</option>
                <option value="3" <c:if test="${user.userType == 3}">checked</c:if>>店长</option>
            </select></td>
            <td class="form-table-tip">&nbsp;</td>
        </tr>
        </tbody>
    </table>
    <div style="padding-left: 100px;">
        <input type="button" class="button" id="create" value="确定">
        <input type="button" class="button" onclick="javascript:top.$.colorbox.close();" value="关闭">
    </div>
</form>
</body>
</html>