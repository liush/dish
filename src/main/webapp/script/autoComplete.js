/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-4-5
 * Time: 下午8:43
 * To change this template use File | Settings | File Templates.
 */
(function ($) {
    function Select() {

        var obj = $("#select_list");

        var othis = this;

        this.old_data = {};
        this.old_array = [];
        this.jqXHR = null;


        Select.prototype.init = function (param) {
            var p = $.extend({
                url:"",
                data:"",
                array:[],
                left:0,
                top:0,
                callback:function (item) {
                }
            }, param);
            if (p.array.length > 0) {
                if (this.old_array != p.array) {
                    obj.remove();
                    obj = build(p, p.array);
                    this.old_array = p.array;
                }
            } else {
                if (p.url != "" && p.data.q != this.old_data.q) {
                    if (this.old_jqXHR != null) {
                        this.old_jqXHR.abort();
                    }
                    obj.remove();
                    this.old_jqXHR = $.myAjax({url:p.url, data:p.data, dataType:"json", type:"get", success:function (data) {
                        obj = build(p, data.views);
                        othis.old_data = p.data;
                    }, error:function () {
                    }});
                }
            }
        }


        function build(p, array) {
            var ul = $('<ul id="select_list" class="select-list"></ul>');
            ul.offset({top:p.top, left:p.left});
            for (var i = 0; i < array.length; i++) {
                var item = array[i];
                var li = $('<li class="select-list-item"><span class="fn-left">' + item.name +
                    '</span><span class="fn-left span-margin-left20">' + item.unit+
                    '</span><span class="fn-right">' +item.price/100+
                    '元</span></li>');
                li.hover(
                    function () {
                        getSelect().removeClass("select-list-item-hover");
                        $(this).addClass("select-list-item-hover");
                    },
                    function () {
                        $(this).removeClass("select-list-item-hover");
                    }).click(function () {
                        p.callback(li.data("item"));
                        othis.clear();
                    });
                li.data("item", item);
                ul.append(li);
                if (i == 0) {
                    li.addClass("select-list-item-hover");
                }
            }
            $("body").append(ul);
            return ul;
        }

        function getSelect() {
            return obj.children(".select-list-item-hover");
        }

        Select.prototype.getSelected = function () {
            return getSelect().data("item");
        }

        Select.prototype.moveUp = function () {
            var selected = obj.children(".select-list-item-hover");
            selected.removeClass("select-list-item-hover");
            var prev = selected.prev();
            if (prev.length > 0) {
                prev.addClass("select-list-item-hover");
            } else {
                obj.children(":last-child").addClass("select-list-item-hover");
            }
        }
        Select.prototype.moveDown = function () {
            var selected = obj.children(".select-list-item-hover");
            selected.removeClass("select-list-item-hover");
            var next = selected.next();
            if (next.length > 0) {
                next.addClass("select-list-item-hover");
            } else {
                obj.children(":first-child").addClass("select-list-item-hover");
            }
        }

        Select.prototype.clear = function () {
            var obj = $("#select_list");
            this.old_data = {};
            this.old_array = [];
            this.jqXHR = null;
            if (obj.length > 0) {
                obj.remove();
            }
        }
    }

    $.localSelect = function () {
        var select = new Select();
        return select;
    }
})(jQuery);
