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
				data-grid-search="#grid-refund-refund-log-index">
				<div class="form-group">
					<div class="controls controls-clearfix">
						<input type="text" name="search['CN_detail']" class="form-control input-xlarge" placeholder="退款描述...">
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
			<table id="grid-refund-refund-log-index"></table>
		</div>
	</div>
	
	<script type="text/javascript">
		$(function() {
		    $("#grid-refund-refund-log-index").data("gridOptions", {
		        url : WEB_ROOT + '/admin/refund/refund-log/list',
		        colModel : [ {
		            label : '流水号',
		            name : 'id',
		            hidden : true                          
		        }, {
		            label : '用户id',
		            name : 'bizUser.id',
		            width : 200,
		            align : 'center',
		            editable: true                                                                   
		        }, {
		            label : '提现金额',
		            name : 'refund',
		            formatter: 'currency',
                    align : 'center',
		            editable: true                                                                   
		        }, {
		            label : '已退款金额',
		            name : 'refunded',
		            formatter: 'currency',
                    align : 'center',
		            editable: true                                                                   
		        }, {
		            label : '状态',
		            name : 'status',
		            formatter: 'integer',
		            editable: true                                                                   
		        }, {
		            label : '退款描述',
		            name : 'detail',
		            width : 255,
		            align : 'center',
		            editable: true                                                                   
		        } ],
		        postData: {
		           "search['FETCH_bizUser']" : "INNER"
		        },
		        editurl : WEB_ROOT + '/admin//refund/refund-log/edit',
		        editrulesurl : WEB_ROOT + '/admin/util/validate?clazz=${clazz}',
		        delurl : WEB_ROOT + '/admin/refund/refund-log/delete',
		        fullediturl : WEB_ROOT + '/admin/refund/refund-log/edit-tabs'
		    });
		});
    </script>
</body>
</html>
