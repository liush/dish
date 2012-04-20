<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 12-3-25
  Time: 下午12:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>谷穗缘餐饮系统</title>
    <%@include file="common.jsp"%>
    <script type="text/javascript">
        $(document).ready(function() {
            var $form = $("#login_form");
            var $userName = $("#userName");
            var $password = $("#password");
            $("#login_form").submit(function() {
                var userName = $.trim($userName.val());
                if (userName == "") {
                    alert("用户名不能为空。");
                    return false;
                }
                var password = $.trim($password.val());
                if (password == "") {
                    alert("密码不能为空。");
                    return false;
                }
                return true;
            });

        });
    </script>
</head>
<body>
<div class="body">
    <div class="fn-left" style="width: 680px;">
        <img src="${resources}/images/logo.jpg">
    </div>
    <div class="fn-right">
        <div class="login">
            <div class="login-top"></div>
            <form id="login_form" action="${contextPath}/login"  method="post">
                <table class="login-table">
                    <tbody>
                    <tr>
                        <td style="text-align: right;">用户名：</td>
                        <td><input type="text" id="userName" name="userName" class="input"></td>
                    </tr>
                    <tr>
                        <td style="text-align: right;">密码：</td>
                        <td><input type="password" id="password" name="password" class="input"></td>
                    </tr>
                    </tbody>
                </table>
                <div class="login-bottom">
                    <input type="submit" class="button" value="登录">
                </div>
            </form>
        </div>
    </div>
    <div class="fn-clear"></div>
</div>
</body>
</html>