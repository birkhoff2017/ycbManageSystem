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
				data-grid-search="#grid-formid-message-index">
				<div class="form-group">
					<div class="controls controls-clearfix">
						<input type="text" name="search['CN_openid_OR_form_prepay_id_OR_orderid']" class="form-control input-xlarge" placeholder="用户openid , form_id , 订单编号...">
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
			<table id="grid-formid-message-index"></table>
		</div>
	</div>
	
	<script type="text/javascript">
		$(function() {
		    $("#grid-formid-message-index").data("gridOptions", {
		        url : WEB_ROOT + '/admin/formid/message/list',
		        colModel : [ {
		            label : '流水号',
		            name : 'id',
		            hidden : true                          
		        }, {
		            label : '用户openid',
		            name : 'openid',
		            width : 255,
		            align : 'center',
		            editable: true                                                                   
		        }, {
		            label : 'form_id',
		            name : 'form_prepay_id',
		            width : 255,
		            align : 'center',
		            editable: true                                                                   
		        }, {
		            label : '类型',
		            name : 'type',
		            formatter: 'integer',
		            editable: true                                                                   
		        }, {
		            label : '订单编号',
		            name : 'orderid',
		            width : 255,
		            align : 'center',
		            editable: true                                                                   
		        }, {
		            label : '剩余次数',
		            name : 'number',
		            formatter: 'integer',
		            editable: true                                                                   
		        } ],
		        editurl : WEB_ROOT + '/admin//formid/message/edit',
		        editrulesurl : WEB_ROOT + '/admin/util/validate?clazz=${clazz}',
		        delurl : WEB_ROOT + '/admin/formid/message/delete',
		        fullediturl : WEB_ROOT + '/admin/formid/message/edit-tabs'
		    });
		});
    </script>
</body>
</html>
