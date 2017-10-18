<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<div class="row search-form-default">
    <div class="col-md-12">
        <form method="get" class="form-inline form-validation form-search form-search-init control-label-sm"
              data-grid-search="#grid-order-trade-order-log-index">
            <div class="form-group">
                <div class="controls controls-clearfix">
                    <input type="text" name="search['CN_orderid_OR_borrowShopName_OR_returnShopName']"
                           class="form-control input-xlarge" placeholder="订单id , 借出商铺名 , 归还商铺名...">
                </div>
            </div>
            <div class="form-group search-group-btn">
                <button class="btn green" type="submmit">
                    <i class="m-icon-swapright m-icon-white"></i>&nbsp; 查&nbsp;询
                </button>
                <button class="btn default" type="reset">
                    <i class="fa fa-undo"></i>&nbsp; 重&nbsp;置
                </button>
            </div>
        </form>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <table id="grid-order-trade-order-log-index"></table>
    </div>
</div>

<script type="text/javascript">
    $(function () {
        $("#grid-order-trade-order-log-index").data("gridOptions", {
            url: WEB_ROOT + '/admin/order/trade-order-log/list',
            colModel: [{
                label: '流水号',
                name: 'id',
                hidden: true
            }, {
                label: '订单id',
                name: 'orderid',
                width: 200,
                align: 'center',
                editable: false
            }, {
                label: '用户openid',
                name: 'bizUser.openid',
                width: 200,
                index: 'bizUser',
                align: 'center',
                editable: false
            }, {
                label: '订单金额',
                name: 'price',
                formatter: 'currency',
                align: 'center',
                editable: false
            }, {
                label: '平台',
                name: 'platform',
                align: 'center',
                width: 80,
                formatter: 'select',
                searchoptions: {
                    value: Util.getCacheDictDatasByType("Platform")
                },
                editable: false
            }, {
                label: '状态',
                name: 'status',
                align: 'center',
                width: 100,
                formatter: 'select',
                searchoptions: {
                    value: Util.getCacheDictDatasByType("Trade_Order_Status")
                },
                editable: true
            }, {
                label: '电池ID',
                name: 'borrowBattery',
                width: 100,
                align: 'center',
                editable: false
            }, {
                label: '借出商铺id',
                name: 'borrowShop.id',
                index: 'borrowShop',
                align: 'center',
                width: 100,
                editable: false
            }, {
                label: '借出商铺名',
                name: 'borrowShopName',
                width: 100,
                align: 'center',
                editable: false
            }, {
                label: '借出设备id',
                name: 'borrowStation.sid',
                index: 'borrowStation',
                align: 'center',
                width: 100,
                editable: false
            }, {
                label: '借出商铺站点id',
                name: 'borrowShopStation.id',
                index: 'borrowShopStation',
                align: 'center',
                width: 100,
                editable: false
            }, {
                label: '借出时所在城市',
                name: 'borrowCity',
                width: 80,
                align: 'center',
                editable: false
            }, {
                label: '归还商铺id',
                name: 'returnShop.id',
                index: 'returnShop',
                align: 'center',
                width: 100,
                editable: false
            }, {
                label: '归还商铺名',
                name: 'returnShopName',
                width: 100,
                align: 'center',
                editable: false
            }, {
                label: '归还设备id',
                name: 'returnStation.sid',
                index: 'returnStation',
                align: 'center',
                width: 100,
                editable: false
            }, {
                label: '归还商铺站点id',
                name: 'returnShopStation.id',
                index: 'returnShopStation',
                align: 'center',
                width: 100,
                editable: false
            }, {
                label: '归还时所在城市',
                name: 'returnCity',
                width: 80,
                align: 'center',
                editable: false
            }, {
                label: '提现金额',
                name: 'refunded',
                formatter: 'currency',
                editable: false
            }, {
                label: '已退款至可用余额',
                name: 'refundedUsable',
                formatter: 'currency',
                editable: false
            }, {
                label: '租金',
                name: 'usefee',
                formatter: 'currency',
                editable: false
            }, {
                label: '已付款',
                name: 'paid',
                formatter: 'currency',
                editable: false
            }, {
                label: '借出时间',
                name: 'borrowTime',
                formatter: 'timestamp',
                width: 130,
                editable: false
            }, {
                label: '归还时间',
                name: 'returnTime',
                formatter: 'timestamp',
                width: 130,
                editable: false
            }, {
                label : '信用借还的订单号',
                name : 'orderNo',
                width : 150,
                align : 'center',
                editable: false
            }, {
                label : '支付宝的资金流水号',
                name : 'alipayFundOrderNo',
                width : 150,
                align : 'center',
                editable: false
            } ],
            postData: {
                "search['FETCH_bizUser']": "INNER",
                "search['FETCH_borrowShop']": "LEFT",
                "search['FETCH_returnShop']": "LEFT",
                "search['FETCH_borrowStation']": "LEFT",
                "search['FETCH_returnStation']": "LEFT",
                "search['FETCH_borrowShopStation']": "LEFT",
                "search['FETCH_returnShopStation']": "LEFT"
            },
            editurl: WEB_ROOT + '/admin//order/trade-order-log/edit',
            editrulesurl: WEB_ROOT + '/admin/util/validate?clazz=${clazz}',
            delurl: WEB_ROOT + '/admin/order/trade-order-log/delete',
            fullediturl: WEB_ROOT + '/admin/order/trade-order-log/edit-tabs',
	    operations: function (itemArray) {
                var $select = $('<li data-position="multi" data-toolbar="show"><a href="javascript:;"> <i class="fa fa-print"></i>手动退款</a></li>');
                $select.children("a").bind("click", function (e) {
                    alert("请确认是否要退款！");
                    loading = true;
                    e.preventDefault();
                    var $grid = $(this).closest(".ui-jqgrid").find(".ui-jqgrid-btable:first");
                    var url = WEB_ROOT + "/admin/order/trade-order-log/refund?ids=";
                    var rowDatas = $grid.jqGrid("getSelectedRowdatas");
                    if (rowDatas.length == 0) {
                        alert("请选择想要退款的订单！");
                        return;
                    }
                    for (i = 0; i < rowDatas.length; i++) {
                        var rowData = rowDatas[i];
                        url += rowData['id'] + ",";
                    }
                    $.post(url, "", function () {
                    });
                });
                itemArray.push($select);
            }
        });
    });
</script>
</body>
</html>
