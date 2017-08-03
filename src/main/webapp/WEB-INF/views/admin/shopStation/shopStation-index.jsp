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
				data-grid-search="#grid-shop-shop-station-index">
				<div class="form-group">
					<div class="controls controls-clearfix">
						<input type="text" name="search['CN_title_OR_position_OR_address']" class="form-control input-xlarge" placeholder="商铺名 , 设备具体摆放位置 , 地址...">
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
			<table id="grid-shop-shop-station-index"></table>
		</div>
	</div>
	
	<script type="text/javascript">
		$(function() {
		    $("#grid-shop-shop-station-index").data("gridOptions", {
		        url : WEB_ROOT + '/admin/shop/shop-station/list',
		        colModel : [ {
		            label : '流水号',
		            name : 'id',
		            hidden : true                          
		        }, {
		            label : '关联商铺',
		            name : 'shop',
		            width : 200,
		            align : 'center',
		            editable: true                                                                   
		        }, {
		            label : '关联设备',
		            name : 'station',
		            width : 200,
		            align : 'center',
		            editable: true                                                                   
		        }, {
		            label : '设备码',
		            name : 'lbsid',
		            editable: true                                                                   
		        }, {
		            label : '商铺名',
		            name : 'title',
		            width : 255,
		            align : 'center',
		            editable: true                                                                   
		        }, {
		            label : '设备具体摆放位置',
		            name : 'position',
		            width : 255,
		            align : 'center',
		            editable: true                                                                   
		        }, {
		            label : '地址',
		            name : 'address',
		            width : 255,
		            align : 'center',
		            editable: true                                                                   
		        }, {
		            label : '经度',
		            name : 'longitude',
		            width : 255,
		            align : 'center',
		            editable: true                                                                   
		        }, {
		            label : '纬度',
		            name : 'latitude',
		            width : 255,
		            align : 'center',
		            editable: true                                                                   
		        }, {
		            label : '费用设置',
		            name : 'feeSettings',
		            formatter: 'integer',
		            editable: true                                                                   
		        }, {
		            label : '销售',
		            name : 'admin',
		            width : 200,
		            align : 'center',
		            editable: true                                                                   
		        }, {
		            label : '设备状态',
		            name : 'status',
		            formatter: 'integer',
		            editable: true                                                                   
		        } ],
		        postData: {
		           "search['FETCH_shop']" : "LEFT",
		           "search['FETCH_station']" : "LEFT",
		           "search['FETCH_admin']" : "LEFT"
		        },
		        editurl : WEB_ROOT + '/admin//shop/shop-station/edit',
		        editrulesurl : WEB_ROOT + '/admin/util/validate?clazz=${clazz}',
		        delurl : WEB_ROOT + '/admin/shop/shop-station/delete',
		        fullediturl : WEB_ROOT + '/admin/shop/shop-station/edit-tabs'
		    });
		});
    </script>
</body>
</html>
