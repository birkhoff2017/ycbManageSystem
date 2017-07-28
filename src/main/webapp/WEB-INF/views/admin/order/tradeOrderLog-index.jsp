<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
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
						<input type="text" name="search['CN_orderid_OR_borrowShopName_OR_returnShopName']" class="form-control input-xlarge" placeholder="订单id , 借出商铺名 , 归还商铺名...">
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
		$(function() {
		    $("#grid-order-trade-order-log-index").data("gridOptions", {
		        url : WEB_ROOT + '/admin/order/trade-order-log/list',
		        colModel : [ {
		            label : '流水号',
		            name : 'id',
		            hidden : true                          
		        }, {
		            label : '订单id',
		            name : 'orderid',
		            width : 32,
		            align : 'center',
		            editable: true                                                                   
		        }, {
		            label : '订单金额',
		            name : 'price',
		            formatter: 'number',
		            editable: true                                                                   
		        }, {
		            label : '用户',
		            name : 'bizUser',
		            width : 200,
		            align : 'center',
		            editable: true                                                                   
		        }, {
		            label : '平台',
		            name : 'platform',
		            formatter: 'integer',
		            editable: true                                                                   
		        }, {
		            label : '状态',
		            name : 'status',
		            formatter: 'integer',
		            editable: true                                                                   
		        }, {
		            label : '借出商铺id',
		            name : 'borrowShop',
		            width : 200,
		            align : 'center',
		            editable: true                                                                   
		        }, {
		            label : '归还商铺id',
		            name : 'returnShop',
		            width : 200,
		            align : 'center',
		            editable: true                                                                   
		        }, {
		            label : '借出商铺名',
		            name : 'borrowShopName',
		            width : 255,
		            align : 'center',
		            editable: true                                                                   
		        }, {
		            label : '归还商铺名',
		            name : 'returnShopName',
		            width : 255,
		            align : 'center',
		            editable: true                                                                   
		        }, {
		            label : '借出设备id',
		            name : 'borrowShopStation',
		            width : 200,
		            align : 'center',
		            editable: true                                                                   
		        }, {
		            label : '归还设备id',
		            name : 'returnShopStation',
		            width : 200,
		            align : 'center',
		            editable: true                                                                   
		        }, {
		            label : '借出时所在城市',
		            name : 'borrowCity',
		            width : 255,
		            align : 'center',
		            editable: true                                                                   
		        }, {
		            label : '归还时所在城市',
		            name : 'returnCity',
		            width : 255,
		            align : 'center',
		            editable: true                                                                   
		        }, {
		            label : '已退款金额',
		            name : 'refunded',
		            formatter: 'number',
		            editable: true                                                                   
		        }, {
		            label : '租金',
		            name : 'usefee',
		            formatter: 'number',
		            editable: true                                                                   
		        }, {
		            label : '已付款',
		            name : 'paid',
		            formatter: 'number',
		            editable: true                                                                   
		        } ],
		        postData: {
		           "search['FETCH_bizUser']" : "INNER",
		           "search['FETCH_borrowShopStation']" : "LEFT",
		           "search['FETCH_returnShopStation']" : "LEFT"
		        },
		        editurl : WEB_ROOT + '/admin//order/trade-order-log/edit',
		        editrulesurl : WEB_ROOT + '/admin/util/validate?clazz=${clazz}',
		        delurl : WEB_ROOT + '/admin/order/trade-order-log/delete',
		        fullediturl : WEB_ROOT + '/admin/order/trade-order-log/edit-tabs'
		    });
		});
    </script>
</body>
</html>
