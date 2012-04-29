<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8"/>
    <title>谷穗缘餐饮系统</title>
    <style type="text/css">
        body {
            width: 48mm;
            border: 0;
            margin: 0;
            padding: 0;
            font-size: 12px;
        }

        .top {
            font-size: 16px;
            text-align: center;
            margin-bottom: 3px;
        }

        .line {
            height: 1px;
            background: #000;
            margin-bottom: 2px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            border-spacing: 0;
        }

        table th, td {
            font-weight: normal;
            border: 0;
            padding: 0;
            margin: 0;
            text-align: right;
        }
    </style>
</head>
<body>
<div class="top">谷穗缘</div>
<div class="top">消费清单</div>
<div>欢迎光临谷穗缘</div>
<div class="line"></div>
<div>单号：${consume.orderNo}</div>
<div>时间：${time}</div>
<div class="line"></div>
<#if dishes?size!=0>
<table style="width: 100%;">
    <thead>
    <tr>
        <th style="text-align: left">菜名</th>
        <th style="width: 30px">数量</th>
        <th style="width: 40px">单价</th>
        <th style="width: 40px;">小计</th>
    </tr>
    </thead>
    <tbody>
        <#list dishes as item>
        <tr>
            <td style="text-align: left">${item.name}</td>
            <td>${item.count}</td>
            <td>${item.price/100}</td>
            <td>${item.count?number*item.price/100}</td>
        </tr>
        </#list>
    <tr>
        <td style="text-align: left">合计</td>
        <td>${dishCount}</td>
        <td></td>
        <td>${consume.dishPrice/100}</td>
    </tr>
    </tbody>
</table>
<div class="line"></div>
</#if>
<#if waters?size!=0>
<table style="width: 100%;">
    <thead>
    <tr>
        <th style="text-align: left">酒水</th>
        <th style="width: 30px">数量</th>
        <th style="width: 40px">单价</th>
        <th style="width: 40px;">小计</th>
    </tr>
    </thead>
    <tbody>
        <#list waters as item>
        <tr>
            <td style="text-align: left">${item.name}</td>
            <td>${item.count}</td>
            <td>${item.price/100}</td>
            <td>${item.count?number*item.price/100}</td>
        </tr>
        </#list>
    <tr>
        <td style="text-align: left">合计</td>
        <td>${waterCount}</td>
        <td></td>
        <td>${consume.waterPrice/100}</td>
    </tr>
    </tbody>
</table>
<div class="line"></div>
</#if>
<#if others?size!=0>
<table style="width: 100%;">
    <thead>
    <tr>
        <th style="text-align: left">其他</th>
        <th style="width: 30px">数量</th>
        <th style="width:40px">单价</th>
        <th style="width: 40px;">小计</th>
    </tr>
    </thead>
    <tbody>
        <#list others as item>
        <tr>
            <td style="text-align: left">${item.name}</td>
            <td>${item.count}</td>
            <td>${item.price/100}</td>
            <td>${item.count?number*item.price/100}</td>
        </tr>
        </#list>
    <tr>
        <td style="text-align: left">合计</td>
        <td>${otherCount}</td>
        <td></td>
        <td>${consume.otherPrice/100}</td>
    </tr>
    </tbody>
</table>
<div class="line"></div>
</#if>

<div>消费合计：${consume.totalPrice/100}</div>
<#if consume.discount !=0 && consume.discount!=10>
<div>折扣：${consume.discount}折</div>
</#if>
<div>应收款：${consume.actualPrice/100}</div>
<#--<div>实收款：${consume.money/100}</div>-->
<#--<div>找零：${(consume.money - consume.actualPrice)/100}</div>-->
<div class="line"></div>
<div>电话：5989877</div>
<script type="text/javascript">
    window.print();
</script>
</body>
</html>