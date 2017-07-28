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
				data-grid-search="#grid-shop-shop-index">
				<div class="form-group">
					<div class="controls controls-clearfix">
						<input type="text" name="search['CN_name_OR_province_OR_city']" class="form-control input-xlarge" placeholder="name , 省份 , 城市...">
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
			<table id="grid-shop-shop-index"></table>
		</div>
	</div>
	
	<script type="text/javascript">
		$(function() {
		    $("#grid-shop-shop-index").data("gridOptions", {
		        url : WEB_ROOT + '/admin/shop/shop/list',
		        colModel : [ {
		            label : '流水号',
		            name : 'id',
		            hidden : true                          
		        }, {
		            label : 'name',
		            name : 'name',
		            width : 255,
		            align : 'center',
		            editable: true                                                                   
		        }, {
		            label : '省份',
		            name : 'province',
		            width : 255,
		            align : 'center',
		            editable: true                                                                   
		        }, {
		            label : '城市',
		            name : 'city',
		            width : 255,
		            align : 'center',
		            editable: true                                                                   
		        }, {
		            label : '区域',
		            name : 'area',
		            width : 255,
		            align : 'center',
		            editable: true                                                                   
		        }, {
		            label : '地址',
		            name : 'locate',
		            width : 255,
		            align : 'center',
		            editable: true                                                                   
		        }, {
		            label : '人均消费',
		            name : 'cost',
		            formatter: 'integer',
		            editable: true                                                                   
		        }, {
		            label : '手机',
		            name : 'phone',
		            width : 255,
		            align : 'center',
		            editable: true                                                                   
		        }, {
		            label : 'logo',
		            name : 'logo',
		            width : 255,
		            align : 'center',
		            editable: true                                                                   
		        }, {
		            label : '轮播图',
		            name : 'carousel',
		            width : 255,
		            align : 'center',
		            editable: true                                                                   
		        }, {
		            label : '业态',
		            name : 'type',
		            formatter: 'integer',
		            editable: true                                                                   
		        }, {
		            label : '状态',
		            name : 'status',
		            formatter: 'integer',
		            editable: true                                                                   
		        } ],
		        editurl : WEB_ROOT + '/admin//shop/shop/edit',
		        editrulesurl : WEB_ROOT + '/admin/util/validate?clazz=${clazz}',
		        delurl : WEB_ROOT + '/admin/shop/shop/delete',
		        fullediturl : WEB_ROOT + '/admin/shop/shop/edit-tabs'
		    });
		});
    </script>
</body>
</html>
