<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="left">
    <ul class="left-menu">
        <li class="left-menu-item <c:if test='${param.left == 0}'>left-menu-item-selected</c:if>"><a
            href="${contextPath}/home/">用户管理</a></li>
        <li class="left-menu-item <c:if test='${param.left == 1}'>left-menu-item-selected</c:if>"><a
            href="${contextPath}/home/tables">餐桌管理</a></li>
        <li class="left-menu-item <c:if test='${param.left == 2}'>left-menu-item-selected</c:if>"><a
            href="${contextPath}/home/information">菜品管理</a></li>
    </ul>
</div>