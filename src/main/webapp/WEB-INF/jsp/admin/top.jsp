<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="body">
<div class="top">
    <div class="top-tip">您好,<span id="top_userName" style="color: blue;"></span>,欢迎登录谷穗缘餐饮系统。现在时刻：<span id="top_time" style="color: #1e90ff;"></span><a
           style="margin: auto 10px;" href="${contextPath}/home/exit">[退出]</a>
    </div>
    <div class="fn-clear"></div>
    <ul class="top-menu">

        <li class="top-menu-item <c:if test='${param.top == 0}'>top-menu-item-selected</c:if>"><a href="${contextPath}/home/">基本资料</a></li>
        <li class="top-menu-item <c:if test='${param.top == 1}'>top-menu-item-selected</c:if>"><a href="${contextPath}/home/showConsumes">营业概况</a></li>
    </ul>
</div>