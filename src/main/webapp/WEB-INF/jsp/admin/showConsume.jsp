<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>谷穗缘餐饮系统</title>
    <%@include file="../common.jsp" %>
    <script type="text/javascript">
        $(document).ready(function () {
            var grid = (function () {
                var heads = [
                    {title:"桌号", text:"桌号"},
                    {title:"就餐人数", text:"就餐人数"},
                    {title:"总金额", text:"总金额"},
                    {title:"折扣", text:"折扣"},
                    {title:"应收款", text:"应收款"},
                    {title:"实收款", text:"实收款"},
                    {title:"状态", text:"状态"},
                    {title:"日期", text:"日期"},
                    {title:"操作", text:"操作"}
                ];
                var columns = [
                    {name:"tableNo"},
                    {name:"peopleNum"},
                    {render:function (cell, item) {
                        return item.totalPrice / 100 + "元";
                    }},
                    {name:"discount"},
                    {render:function (cell, item) {
                        return item.actualPrice / 100 + "元";
                    }},
                    {render:function (cell, item) {
                        return item.money / 100 + "元";
                    }},
                    {render:function (cell, item) {
                        var type = item.type;
                        if (type == Consume.NO_PAY) {
                            return "<span style='color: blue;'>未结算</span>";
                        }
                        if (type == Consume.PAY) {
                            return "已结算";
                        }
                        if (type == Consume.REVERSE_PAY) {
                            return "<span style='color: red;'>反结算</span>";
                        }
                        return "";
                    }},
                    {render:function (cell, item) {
                        return formatTime(item.createTime);
                    }},
                    {render:function (cell, item) {
                        var a = $('<a href="#">详情</a>');
                        a.click(function () {
                            var $this = $(this);
                            if ($this.hasClass("disabled")) {
                                return false;
                            }
                            $this.addClass("disabled");
                            location.href = contextPath + "/home/showConsumeDetails/" + item.id;
                            $this.removeClass("disabled");
                            return false;
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

            function getData(pageNo) {
                if (!pageNo) {
                    pageNo = 1;
                }
                $.myAjax({
                    type:"get",
                    url:contextPath + "/home/getConsume",
                    data:{pageNo:pageNo},
                    success:function (data) {
                        grid.render(data.results, {pageNo:data.pageNo, prev:function (pageNo) {
                            getData(pageNo - 1);
                        }, next:function (pageNo) {
                            getData(parseInt(pageNo) + 1);
                        }, click:function (pageNo) {
                            getData(pageNo);
                        }, total:data.totalPage});
                    },
                    error:function () {
                    }
                });
            }

            getData();
        });
    </script>
</head>
<body>
<jsp:include page="top.jsp">
    <jsp:param name="top" value="1"/>
</jsp:include>
<div id="grid" style="margin-top: 2px;">

</div>
<%@include file="bottom.jsp" %>
</body>
</html>