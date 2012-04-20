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
                    href:contextPath + "/home/addInformation",
                    width:"600px",
                    height:"300px",
                    title:"创建菜品",
                    onClosed:function () {
                        $this.removeClass("disabled");
                        getData();
                    },
                    onComplete:function () {
                        $this.removeClass("disabled");
                    }
                });
            });
            var $query = $("#query");
            var $name = $("#name");

            $name.keyup(function(e){
                if(e.keyCode ==13){
                    $query.click();
                }
            });

            $query.click(function(){
                var $this = $(this);
                if($this.hasClass("disabled")){
                    return false;
                }
                $this.addClass("disabled");
                getData();
            });
            var grid = (function () {
                var heads = [
                    {title:"编号", text:"编号"},
                    {title:"名称", text:"名称"},
                    {title:"价格", text:"价格"},
                    {title:"类别", text:"类别"},
                    {title:"单位", text:"单位"},
                    {title:"操作", text:"操作"}
                ];
                var columns = [
                    {name:"number"},
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
                    }},{name:"unit"},
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
                                url:contextPath + "/home/delInformaation/" + item.id,
                                dataType:"text",
                                success:function (data) {
                                    $this.removeClass("disabled");
                                    getData();
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
                    url:contextPath + "/home/getInformaations",
                    data:{pageNo:pageNo,name:$.trim($name.val())},
                    dataType:"json",
                    success:function (data) {
                        grid.render(data.results, {pageNo:data.pageNo, prev:function (pageNo) {
                            getData(pageNo - 1);
                        }, next:function (pageNo) {
                            getData(parseInt(pageNo) + 1);
                        }, click:function (pageNo) {
                            getData(pageNo);
                        }, total:data.totalPage});
                        $query.removeClass("disabled");
                    },error:function(){
                        $query.removeClass("disabled");
                    }
                });
            }

            getData();
        });
    </script>
</head>
<body>
<jsp:include page="top.jsp">
    <jsp:param name="top" value="0"/>
</jsp:include>
<jsp:include page="left.jsp">
    <jsp:param name="left" value="2"/>
</jsp:include>
<div class="right">
    <div class="right-top">
        <input type="button" class="button" id="create_table" value="新增菜品">
        <span>菜品名称：</span><input type="text" id="name" class="input"/>
        <input type="button" class="button" id="query" value="查询">
    </div>
    <div id="grid" class="right-grid">

    </div>
</div>
<%@include file="bottom.jsp" %>

</body>
</html>