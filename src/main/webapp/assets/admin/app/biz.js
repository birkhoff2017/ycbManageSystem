var Biz = function () {

    var CacheDatas = {};
    return {
        init: function () {
        },
        // delete 替换 util getCacheDictDatasByType
        // getPlatform: function () {
        //     if (CacheDatas.Platform == undefined) {
        //         var url = WEB_ROOT + "/admin/shop/biz-user/getPlatform";
        //         $("body").ajaxJsonSync(url, {}, function (data) {
        //             var options = data;
        //             options[''] = '';
        //             CacheDatas.Platform = options;
        //         })
        //     }
        //     return CacheDatas.Platform;
        // }
    }
}();