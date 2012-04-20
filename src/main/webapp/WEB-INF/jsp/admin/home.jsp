<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 12-4-2
  Time: 下午3:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>谷穗缘餐饮系统</title>
    <%@include file="../common.jsp" %>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#create_user").click(function () {
                var $this = $(this);
                if ($this.hasClass("disabled")) {
                    return false;
                }
                $this.addClass("disabled");
                $.colorbox({
                    overlayClose:false,
                    opacity:0.3,
                    transition:"none",
                    iframe:true,
                    fastIframe:false,
                    href:contextPath + "/home/editUser",
                    width:"600px",
                    height:"300px",
                    title:"创建用户",
                    onClosed:function () {
                        getData();
                    },
                    onComplete:function () {
                        $this.removeClass("disabled");
                    }
                });
            });

            var grid = (function () {
                var heads = [
                    {title:"用户名", text:"用户名"},
                    {title:"姓名", text:"姓名"},
                    {title:"类型", text:"类型"},
                    {title:"操作", text:"操作"}
                ];
                var columns = [
                    {name:"userName"},
                    {name:"realName"},
                    {render:function (cell, item) {
                        var type = item.userType;
                        if (type == User.CASH) {
                            return "收银员";
                        }
                        if (type == User.MANAGER) {
                            return "大堂经理";
                        }
                        if (type == User.SHOPKEEPER) {
                            return "店长";
                        }
                        if (type == User.ADMIN) {
                            return "管理员";
                        }
                        return "";
                    }},
                    {render:function (cell, item) {
                        var div = $('<div></div>');
                        var type = item.userType;
                        if (type != User.ADMIN) {
                            var edit = $('<a class="span-margin-right20" href="#">编辑</a>');
                            var del = $('<a href="#">删除</a>');
                            edit.click(function () {
                                var $this = $(this);
                                if ($this.hasClass("disabled")) {
                                    return false;
                                }
                                $this.addClass("disabled");
                                $.colorbox({
                                    overlayClose:false,
                                    opacity:0.3,
                                    transition:"none",
                                    iframe:true,
                                    fastIframe:false,
                                    href:contextPath + "/home/editUser?userName=" + item.userName,
                                    width:"600px",
                                    height:"300px",
                                    title:"编辑用户",
                                    onClosed:function () {
                                        $this.removeClass("disabled");
                                        getData();
                                    },
                                    onComplete:function () {
                                        $this.removeClass("disabled");
                                    }
                                });
                                return false;
                            });
                            del.click(function () {
                                var $this = $(this);
                                if ($this.hasClass("disabled")) {
                                    return false;
                                }
                                $this.addClass("disabled");
                                if (!confirm("确定要删除此用户吗？")) {
                                    return false;
                                }
                                $.myAjax({
                                    type:"get",
                                    url:contextPath + "/home/delUser/" + item.userName,
                                    dataType:"text",
                                    success:function (data) {
                                        $this.removeClass("disabled");
                                        if (data == "true") {
                                            getData();
                                        } else {
                                            alert("管理员用户无法删除。")
                                        }
                                    }, error:function () {
                                        $this.removeClass("disabled");
                                    }
                                });
                                return false;
                            });
                            div.append(edit).append(del);
                        }
                        return div;
                    }}
                ];
                var grid = $.grid({
                    container:"grid",
                    heads:heads,
                    columns:columns
                });
                return grid;
            })();

            function getData() {
                $.myAjax({
                    type:"get",
                    url:contextPath + "/home/getUsers",
                    dataType:"json",
                    success:function (data) {
                        grid.render(data);
                    }
                });
            }

            grid.render(${users});
        });
    </script>
</head>
<body>
<jsp:include page="top.jsp">
    <jsp:param name="top" value="0"/>
</jsp:include>
<jsp:include page="left.jsp">
    <jsp:param name="left" value="0"/>
</jsp:include>
<div class="right">
    <div class="right-top">
        <input type="button" class="button" id="create_user" value="新增用户">
    </div>
    <div id="grid" class="right-grid">

    </div>
</div>
<%@include file="bottom.jsp" %>
</body>
</html>