var Biz = function () {

    var CacheDatas = {};
    return {
        init: function () {
        },
        imgViewFormatter : function(cellValue, options, rowdata) {
            return "<img class=\"img_thumbnail\"  src=\"" + cellValue + "\" width=\"100%\" >";
        },
    }
}();