(function($) {


    var isIE6 = $.browser.msie ? $.browser.version == 6 : false;

    $.grid = function(params) {
        var grid = new Grid(params);
        var method = new Method();

        method.getData = function() {
            return grid.params.datas;
        }
        method.render = function(data, page) {
            if (data && $.type(data) == "array") {
                grid.params.datas = data;
            }
            if (page) {
                grid.params.page = $.extend({}, grid.params.page, page);
            }

            grid.render();
            return this;
        }
        /*获得选中的行*/
        method.getChecked = function() {
            var checkboxs = grid.gridBody.tbody.find(":checked");
            var data = [];
            checkboxs.each(function() {
                var tr = $(this).parents("tr:first");
                data.push(tr.data("item"));
            });
            return data;
        }
        method.getPageNo = function(){
            return  grid.params.page.pageNo;
        }
        method.setItem = function(key,item){

        }
        return method;
    }

    function Method() {
    }

    /**
     *
     * @param params{
     *               row:{click:function() {},contextMenu:function() {}},
     *               checkbox:{headCallback:function() {},rowCallback:function() {}},
     *               page:{pageNo:1,prev:function() {},next:function() {},click:function(pageNo) {},total:3}
     *               }
     */
    function Grid(params) {
        this.params = $.extend({}, /*参数列表*/ {
            container:"",
            heads:[
                {title:"",width:"",text:"",align:"",
                    sort:{column:"",type:"" /*eg. num：数字 time：时间*/},
                    render:function(cell) {
                    }}
            ],
            columns:[
                {name:"",width:"",align:"",render:function(cell, item) {
                }}
            ],
            datas:[]
        }, params);
        this.cacheData = {};
        this.jqo = $('<div class="ui-grid"></div>');
        this.gridHead = new GridHead();
        this.gridBody = new GridBody();
        this.gridFoot = new GridFoot();
        this.jqo.append(this.gridHead.jqo).append(this.gridBody.jqo).append(this.gridFoot.jqo);
    }

    Grid.prototype.render = function() {
        this.gridHead.render(this);
        this.gridBody.render(this);
        this.gridFoot.render(this);
        var container = $("#" + this.params.container);
        container.append(this.jqo);
        this.container = container;
        var othis = this;

        /*缓存数据（减少jquery方法操作，缓存一些信息，提高效率）*/


        /*绑定事件*/
        if (this.params.row) {
            var tr = this.gridBody.tbody.find("tr");

            if ($.type(othis.params.row.click) == "function") {
                tr.click(function(e) {
                    return othis.params.row.click.call(this, e, $(this).data("item"));
                });
            }

            if ($.type(othis.params.row.contextMenu) == "function") {
                tr.contextmenu(function(e) {
                    return othis.params.row.contextMenu.call(this, e, $(this).data("item"));
                });
            }
        }


//        /*计算大小*/
//        count(othis);
//        //解决IE6 多次调用问题
//        var resizeTimer;
//        $(window).resize(function() {
//            if (resizeTimer) {
//                window.clearTimeout(resizeTimer);
//            }
//            resizeTimer = window.setTimeout(function() {
//                count(othis);
//            }, 300);
//        });
    }


    function count(othis) {
        var height = othis.container.height();
        var width = othis.container.width();
        var contentWidth = width;
        var footHeight = othis.gridFoot.jqo.height();
        var bodyHeight = height  - footHeight;
        othis.gridBody.jqo.height(bodyHeight);
        if (isIE6) {
            othis.gridFoot.jqo.css("top", height - footHeight);
        }
        var scroll = othis.gridBody.jqo.scrollTop(1).scrollTop();
        /*出现滚动条*/
        if (scroll > 0) {
            contentWidth = width - 17;  //scroll宽度为17px
            othis.gridHead.jqo.width(contentWidth);
            var thead = othis.gridBody.thead.clone();
            var ths = thead.children(":first-child").children();
            var div_ths = othis.gridBody.thead.children(":first-child").children();
            var length = ths.length;
            for (var i = 0; i < length; i++) {
                $(ths[i]).width($(div_ths[i]).width());
            }
            othis.gridHead.table.append(thead);
            othis.gridHead.jqo.show();
            othis.gridBody.thead.css("visibility","hidden");
        } else {
            othis.gridHead.jqo.width(contentWidth);
            othis.gridBody.thead.css("visibility","visible");
            othis.gridHead.table.empty();
            othis.gridHead.jqo.hide();
        }
    }


    function GridHead() {
        this.jqo = $('<div class="ui-grid-head" style="display: none;"></div>');
    }

    GridHead.prototype.render = function(grid) {
        this.jqo.empty();
        var table = $('<table></table>');
        this.table = table;
        this.jqo.append(table);
    }

    function GridBody() {
        this.jqo = $('<div class="ui-grid-body"></div>');
    }

    GridBody.prototype.render = function(grid) {
        var table = $('<table style="width: 100%;"></table>');
        this.jqo.empty();
        var thead = (function() {
            var heads = grid.params.heads;
            var thead = $('<thead  class="ui-grid-table-head"></thead>');
            var tr = $('<tr></tr>');
            table.append(thead.append(tr));
            var checkbox = grid.params.checkbox;
            if (checkbox) {
                var th = $('<th></th>');
                var checkId = grid.params.container + "_check_all";
                var checkBoxAll = $('<input type="checkbox" id=' + checkId + '>');
                th.width("25px");
                th.css("text-align", "center");
                checkBoxAll.click(function(e) {
                    var checked = $(this).prop("checked");
                    if (e.isTrigger) {
                        checked = !checked;
                    }
                    grid.gridBody.tbody.find(":checkbox").each(function() {
                        var othis = $(this);
                        othis.prop("checked", checked);
                        othis.parents("tr:first").toggleClass("ui-grid-body-tr-selected", checked);
                    });
                    if (checkbox.headCallback && $.type(checkbox.headCallback) == "function") {
                        checkbox.headCallback.call(this, checked);
                    }
                    e.stopPropagation();
                });
                th.append(checkBoxAll);
                tr.append(th);
            }

            for (var i = 0; i < heads.length; i++) {
                var th = $('<th></th>');
                var head = heads[i];
                var render = head.render;
                if (render && $.type(render) == "function") {
                    th.append(render(th));
                } else {
                    th.attr("title", head.title);
                    th.width(head.width);
                    th.css("text-align", head.align);
                    th.text(head.text);
                }
                if (i != 0) {
                    th.addClass("ui-grid-table-head-line");
                }
                if (head.sort) {
                    sort(th, head.sort.column, head.sort.type, grid);
                }
                tr.append(th);
            }
            return thead;
        })();
        var tbody = (function() {
            var tbody = $('<tbody></tbody>');
            var datas = grid.params.datas;
            var columns = grid.params.columns;
            for (var i = 0; i < datas.length; i++) {
                var _key = grid.params.container + i;
                var tr = $('<tr id="' + _key+'"></tr>');
                var item = datas[i];
                item._key = _key;
                tr.data("item", item);
                if (i % 2 == 0) {
                    tr.addClass("ui-grid-body-tr-even");
                }
                tr.hover(function() {
                    $(this).addClass("ui-grid-body-tr-hover");
                }, function() {
                    $(this).removeClass("ui-grid-body-tr-hover");
                });

                var checkbox = grid.params.checkbox;
                if (checkbox) {
                    var td = $('<td></td>');
                    var cb = $('<input type="checkbox">');
                    td.css({"width":"25px","text-align":"center"});
                    cb.click(function(e) {
                        var checked = $(this).prop("checked");
                        var row = $(this).parents("tr:first");
                        if (e.isTrigger) {
                            checked = !checked;
                        }
                        row.toggleClass("ui-grid-body-tr-selected", checked);
                        if (checkbox.rowCallback && $.type(checkbox.rowCallback) == "function") {
                            checkbox.rowCallback.call(this, checked);
                        }
                        $("#" + grid.params.container + "_check_all").prop("checked", false);
                        e.stopPropagation();
                    });
                    td.append(cb);
                    tr.append(td);
                }

                for (var j = 0; j < columns.length; j++) {
                    var td = $('<td></td>');
                    var column = columns[j];
                    var render = column.render;
                    td.css({"width":column.width,"text-align":column.align});
                    if (render && $.type(render) == "function") {
                        td.append(render(td, item));
                    } else {
                        td.html(item[column.name]);
                    }
                    tr.append(td);
                }
                tbody.append(tr)
            }
            return tbody;
        })();
        table.append(thead).append(tbody);
        this.thead = thead;
        this.tbody = tbody;
        return this.jqo.append(table);
    }

    function GridFoot() {
        this.jqo = $('<div class="ui-grid-foot"></div>');
        this.page = new Page();

        /*未来可能抽取成组件*/
        function Page() {
            this.jqo = $('<div class="ui-page"></div>');
        }

        Page.prototype.render = function(grid) {
            this.jqo.empty();
            var maxPageCount = 9;   //最小值为5，且为奇数。
            var pageNo = grid.params.page.pageNo;
            var total = grid.params.page.total;
            var prev;
            if (pageNo > 1) {
                prev = $('<a href="#" class="ui-page-prev">上一页</a>');
                prev.click(function(e) {
                    return grid.params.page.prev.call(this, pageNo, e);
                });
            } else {
                prev = $('<span class="ui-page-prev-disabled">上一页</span>');
            }
            this.jqo.append(prev);

            if (total <= maxPageCount) {
                for (var i = 1; i <= total; i++) {
                    buildSpan(i, pageNo, this.jqo);
                }
            } else {
                if (pageNo <= 3) {
                    for (var i = 1; i <= 3; i++) {
                        buildSpan(i, pageNo, this.jqo);
                    }
                    var j = maxPageCount - 1
                    for (var i = 4; i < j; i++) {
                        buildSpan(i, pageNo, this.jqo);
                    }
                    this.jqo.append('<span class="ui-page-item">...</span>');
                    buildSpan(total, pageNo, this.jqo);
                } else if (pageNo >= total - 2) {
                    buildSpan(1, pageNo, this.jqo);
                    this.jqo.append('<span class="ui-page-item">...</span>');
                    var i = total + 3 - maxPageCount;
                    for (; i <= total; i++) {
                        buildSpan(i, pageNo, this.jqo);
                    }
                } else {
                    buildSpan(1, pageNo, this.jqo);
                    this.jqo.append('<span class="ui-page-item">...</span>');
                    var i = (maxPageCount - 5) / 2;
                    for (var j = pageNo - i; j <= pageNo + i; j++) {
                        buildSpan(j, pageNo, this.jqo);
                    }
                    this.jqo.append('<span class="ui-page-item">...</span>');
                    buildSpan(total, pageNo, this.jqo);
                }
            }

            function buildSpan(i, pageNo, jqo) {
                if (i == pageNo) {
                    var span = '<span class="ui-page-item ui-page-current">' + i + '</span>';
                    jqo.append(span);
                } else {
                    var a = $('<a class="ui-page-item" href="#">' + i + '</a>');
                    (function(i, a) {
                        a.click(function() {
                            return grid.params.page.click.call(this, i);
                        });
                    })(i, a);
                    jqo.append(a);
                }
            }


            var next;
            if (pageNo <= grid.params.page.total - 1) {
                next = $('<a href="#" class="ui-page-next">下一页</a>');
                next.click(function(e) {
                    return grid.params.page.next.call(this, pageNo, e);
                });
            } else {
                next = $('<span class="ui-page-next-disabled">下一页</span>');
            }
            this.jqo.append(next);
            return this.jqo;
        };
    }

    GridFoot.prototype.render = function(grid) {
        this.jqo.empty();
        if (grid.params.page) {
            var page = this.page.render(grid);
            this.jqo.append(page);
        }
        return this.jqo;
    }


    function sort(li, column, sortType, grid) {
        li.hover(function() {
            $(this).addClass("hover");
        }, function() {
            $(this).removeClass("hover");
        });
        li.css("position", "relative");
        var i = $("<i class='ui-grid-table-head-sort'></i>");
        var cacheColumn = grid.cacheData["sortColumn"];
        if (column === cacheColumn) {
            i.show();
            if (grid.cacheData["isSortAsc"]) {
                i.addClass("ui-grid-table-head-sort-asc").attr("title", "升序");
            } else {
                i.addClass("ui-grid-table-head-sort-desc").attr("title", "降序");
            }
            delete grid.cacheData["sortColumn"];
            delete grid.cacheData["isSortAsc"];
        }
        li.append(i);
        li.click(function() {
            var asc = !i.hasClass("ui-grid-table-head-sort-asc");
            var data = grid.params.datas;
            if ("num" === sortType) {
                data = data.sort(function(a, b) {
                    if (asc) {
                        return a[column] - b[column];
                    } else {
                        return b[column] - a[column];
                    }
                });
            } else if ("time" === sortType) {
                data = data.sort(function(a, b) {
                    if (asc) {
                        return a[column] - b[column];
                    } else {
                        return b[column] - a[column];
                    }
                });
            } else {
                data = data.sort(function(a, b) {
                    if (asc) {
                        return a[column].localeCompare(b[column]);
                    } else {
                        return b[column].localeCompare(a[column]);
                    }
                });
            }
            $.extend(grid.cacheData, {sortColumn:column,isSortAsc:asc});
            grid.render(data);
        });
    }
})(jQuery);

