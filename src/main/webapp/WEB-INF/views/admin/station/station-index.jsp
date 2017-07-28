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
				data-grid-search="#grid-station-station-index">
				<div class="form-group">
					<div class="controls controls-clearfix">
						<input type="text" name="search['CN_mac_OR_slotstatus_OR_title']" class="form-control input-xlarge" placeholder="Mac地址 , 槽位状态 , 设备名...">
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
			<table id="grid-station-station-index"></table>
		</div>
	</div>
	
	<script type="text/javascript">
		$(function() {
		    $("#grid-station-station-index").data("gridOptions", {
		        url : WEB_ROOT + '/admin/station/station/list',
		        colModel : [ {
		            label : '流水号',
		            name : 'id',
		            hidden : true                          
		        }, {
		            label : 'Mac地址',
		            name : 'mac',
		            width : 255,
		            align : 'center',
		            editable: true                                                                   
		        }, {
		            label : '可借数',
		            name : 'usable',
		            formatter: 'integer',
		            editable: true                                                                   
		        }, {
		            label : '可还数',
		            name : 'empty',
		            formatter: 'integer',
		            editable: true                                                                   
		        }, {
		            label : '槽位状态',
		            name : 'slotstatus',
		            width : 255,
		            align : 'center',
		            editable: true                                                                   
		        }, {
		            label : '线类型',
		            name : 'cable',
		            formatter: 'integer',
		            editable: true                                                                   
		        }, {
		            label : '设备名',
		            name : 'title',
		            width : 255,
		            align : 'center',
		            editable: true                                                                   
		        }, {
		            label : '设备版本',
		            name : 'deviceVer',
		            formatter: 'integer',
		            editable: true                                                                   
		        }, {
		            label : '心跳率',
		            name : 'heartbeatRate',
		            formatter: 'integer',
		            editable: true                                                                   
		        }, {
		            label : '开机时长',
		            name : 'powerOnTime',
		            editable: true                                                                   
		        } ],
		        editurl : WEB_ROOT + '/admin//station/station/edit',
		        editrulesurl : WEB_ROOT + '/admin/util/validate?clazz=${clazz}',
		        delurl : WEB_ROOT + '/admin/station/station/delete',
		        fullediturl : WEB_ROOT + '/admin/station/station/edit-tabs'
		    });
		});
    </script>
</body>
</html>
