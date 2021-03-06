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
				data-grid-search="#grid-bizUser-biz-user-auth-index">
				<div class="form-group">
					<div class="controls controls-clearfix">
						<input type="text" name="search['CN_identityType_OR_identifier_OR_credential']" class="form-control input-xlarge" placeholder="账户类型 , 账户值 , 凭据...">
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
			<table id="grid-bizUser-biz-user-auth-index"></table>
		</div>
	</div>
	
	<script type="text/javascript">
		$(function() {
		    $("#grid-bizUser-biz-user-auth-index").data("gridOptions", {
		        url : WEB_ROOT + '/admin/bizUser/biz-user-auth/list',
		        colModel : [ {
		            label : '流水号',
		            name : 'id',
		            hidden : true                          
		        }, {
		            label : '用户表',
		            name : 'userId',
		            editable: true                                                                   
		        }, {
		            label : '账户类型',
		            name : 'identityType',
		            width : 255,
		            align : 'center',
		            editable: true                                                                   
		        }, {
		            label : '账户值',
		            name : 'identifier',
		            width : 255,
		            align : 'center',
		            editable: true                                                                   
		        }, {
		            label : '凭据',
		            name : 'credential',
		            width : 255,
		            align : 'center',
		            editable: true                                                                   
		        } ],
		        editurl : WEB_ROOT + '/admin//bizUser/biz-user-auth/edit',
		        editrulesurl : WEB_ROOT + '/admin/util/validate?clazz=${clazz}',
		        delurl : WEB_ROOT + '/admin/bizUser/biz-user-auth/delete',
		        fullediturl : WEB_ROOT + '/admin/bizUser/biz-user-auth/edit-tabs'
		    });
		});
    </script>
</body>
</html>
