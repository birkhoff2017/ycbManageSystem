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
				data-grid-search="#grid-feeStrategy-fee-strategy-index">
				<div class="form-group">
					<div class="controls controls-clearfix">
						<input type="text" name="search['CN_name']" class="form-control input-xlarge" placeholder="策略名称...">
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
			<table id="grid-feeStrategy-fee-strategy-index"></table>
		</div>
	</div>
	
	<script type="text/javascript">
		$(function() {
		    $("#grid-feeStrategy-fee-strategy-index").data("gridOptions", {
		        url : WEB_ROOT + '/admin/feeStrategy/fee-strategy/list',
		        colModel : [ {
		            label : '流水号',
		            name : 'id',
		            hidden : true                          
		        }, {
		            label : '策略名称',
		            name : 'name',
		            width : 255,
		            align : 'center',
		            editable: true                                                                   
		        }, {
		            label : '意外借出免费时长',
		            name : 'freeTime',
		            editable: true                                                                   
		        }, {
		            label : '意外借出免费时长单位',
		            name : 'freeUnit',
		            editable: true                                                                   
		        }, {
		            label : '固定收费时长',
		            name : 'fixedTime',
		            editable: true                                                                   
		        }, {
		            label : '固定收费时长单位',
		            name : 'fixedUnit',
		            editable: true                                                                   
		        }, {
		            label : '固定费用',
		            name : 'fixed',
		            formatter: 'number',
		            editable: true                                                                   
		        }, {
		            label : '超出计费',
		            name : 'fee',
		            formatter: 'number',
		            editable: true                                                                   
		        }, {
		            label : '超出计费时长单位',
		            name : 'feeUnit',
		            editable: true                                                                   
		        }, {
		            label : '最高收费时长',
		            name : 'maxFeeTime',
		            editable: true                                                                   
		        }, {
		            label : '最高收费时长单位',
		            name : 'maxFeeUnit',
		            editable: true                                                                   
		        }, {
		            label : '最高收费金额',
		            name : 'maxFee',
		            formatter: 'number',
		            editable: true                                                                   
		        } ],
		        editurl : WEB_ROOT + '/admin//feeStrategy/fee-strategy/edit',
		        editrulesurl : WEB_ROOT + '/admin/util/validate?clazz=${clazz}',
		        delurl : WEB_ROOT + '/admin/feeStrategy/fee-strategy/delete',
		        fullediturl : WEB_ROOT + '/admin/feeStrategy/fee-strategy/edit-tabs'
		    });
		});
    </script>
</body>
</html>
