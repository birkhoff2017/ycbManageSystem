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
				data-grid-search="#grid-bizUser-biz-user-index">
				<div class="form-group">
					<div class="controls controls-clearfix">
						<input type="text" name="search['CN_openid']" class="form-control input-xlarge" placeholder="用户openid...">
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
			<table id="grid-bizUser-biz-user-index"></table>
		</div>
	</div>
	
	<script type="text/javascript">
		$(function() {
		    $("#grid-bizUser-biz-user-index").data("gridOptions", {
		        url : WEB_ROOT + '/admin/bizUser/biz-user/list',
		        colModel : [ {
		            label : '流水号',
		            name : 'id',
		            hidden : true                          
		        }, {
		            label : '用户openid',
		            name : 'openid',
                    align : 'center',
                    width : 80,
		            editable: false
		        }, {
		            label : '用户所属平台',
		            name : 'platform',
                    align : 'center',
                    width : 50,
                    formatter: 'select',
                    searchoptions: {
                        value: Util.getCacheDictDatasByType("Platform")
                    },
		            editable: true                                                                   
		        }, {
		            label : '账户余额',
		            name : 'usablemoney',
                    align : 'center',
                    formatter: 'currency',
		            editable: true
		        }, {
		            label : '押金',
		            name : 'deposit',
                    align : 'center',
                    formatter: 'currency',
		            editable: true
		        }, {
		            label : '待退款',
		            name : 'refund',
                    align : 'center',
                    formatter: 'currency',
		            editable: true
		        } ],
		        editurl : WEB_ROOT + '/admin//bizUser/biz-user/edit',
		        editrulesurl : WEB_ROOT + '/admin/util/validate?clazz=${clazz}',
		        delurl : WEB_ROOT + '/admin/bizUser/biz-user/delete',
		        fullediturl : WEB_ROOT + '/admin/bizUser/biz-user/edit-tabs'
		    });
		});
    </script>
</body>
</html>
