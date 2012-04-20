<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>谷穗缘餐饮系统</title>
    <%@include file="../common.jsp" %>
    <script type="text/javascript">
        $(document).ready(function () {
            var $create_table = $("#create_table");
            $create_table.click(function () {
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
                    href:contextPath + "/home/addTable",
                    width:"600px",
                    height:"200px",
                    title:"创建餐桌",
                    onClosed:function () {
                        $this.removeClass("disabled");
                        getData();
                    },
                    onComplete:function () {
                        $this.removeClass("disabled");
                    }
                });
            });

            var grid = (function () {
                var heads = [
                    {title:"桌号", text:"桌号"},
                    {title:"人数", text:"人数"},
                    {title:"操作", text:"操作"}
                ];
                var columns = [
                    {name:"tableNo"},
                    {name:"peopleNum"},
                    {render:function (cell, item) {
                        var del = $('<a href="#">删除</a>');
                        del.click(function () {
                            var $this = $(this);
                            if ($this.hasClass("disabled")) {
                                return false;
                            }
                            $this.addClass("disabled");
                            if (!confirm("确定要删除吗？")) {
                                return false;
                            }
                            $.myAjax({
                                type:"get",
                                url:contextPath + "/home/delTable/" + item.tableNo,
                                dataType:"text",
                                success:function (data) {
                                    $this.removeClass("disabled");
                                    if(data =="true"){
                                        getData();
                                    } else{
                                        alert("该餐桌有客人正在就餐，不能删除。");
                                    }

                                }, error:function () {
                                    $this.removeClass("disabled");
                                }
                            });
                            return false;
                        });
                        return del;
                    }}
                ];
                var grid = $.grid({
                    container:"grid",
                    heads:heads,
                    columns:columns
                });
                return grid;
            })();

            function getData(pageNo) {
                if (!pageNo) {
                    pageNo = 1;
                }
                $.myAjax({
                    type:"get",
                    url:contextPath + "/home/getTables",
                    data:{pageNo:pageNo},
                    dataType:"json",
                    success:function (data) {
                        grid.render(data);
                    }
                });
            }
            grid.render(${tables});
        });
    </script>
</head>
<body>
<jsp:include page="top.jsp">
    <jsp:param name="top" value="0"/>
</jsp:include>
<jsp:include page="left.jsp">
    <jsp:param name="left" value="1"/>
</jsp:include>
<div class="right">
    <div class="right-top">
        <input type="button" class="button" id="create_table" value="新增餐桌">
    </div>
    <div id="grid" class="right-grid">

    </div>
</div>
<%@include file="bottom.jsp" %>

</body>
</html>