<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<form:form class="form-horizontal form-bordered form-label-stripped form-validation"
		action="${ctx}/admin/formid/message/edit" method="post" modelAttribute="entity"
		data-editrulesurl="${ctx}/admin/util/validate?clazz=${clazz}">
		<form:hidden path="id" />
		<form:hidden path="version" />
		<div class="form-actions">
			<button class="btn blue" type="submit" data-grid-reload="#grid-formid-message-index">
				<i class="fa fa-check"></i> 保存
			</button>
			<button class="btn green" type="submit" data-grid-reload="#grid-formid-message-index" data-post-dismiss="modal">保存并关闭
			</button>
			<button class="btn default" type="button" data-dismiss="modal">取消</button>
		</div>
		<div class="form-body">
	        <div class="row">
	            <div class="col-md-6">
					<div class="form-group">
						<label class="control-label">用户openid</label>
						<div class="controls">
			                <form:input path="openid" class="form-control"/>
						</div>
					</div>
	            </div>
	        </div>
	        <div class="row">
	            <div class="col-md-6">
					<div class="form-group">
						<label class="control-label">form_id</label>
						<div class="controls">
			                <form:input path="form_prepay_id" class="form-control"/>
						</div>
					</div>
	            </div>
	        </div>
	        <div class="row">
	            <div class="col-md-6">
					<div class="form-group">
						<label class="control-label">类型</label>
						<div class="controls">
			                <form:input path="type" class="form-control"/>
						</div>
					</div>
	            </div>
	        </div>
	        <div class="row">
	            <div class="col-md-6">
					<div class="form-group">
						<label class="control-label">订单编号</label>
						<div class="controls">
			                <form:input path="orderid" class="form-control"/>
						</div>
					</div>
	            </div>
	        </div>
	        <div class="row">
	            <div class="col-md-6">
					<div class="form-group">
						<label class="control-label">剩余次数</label>
						<div class="controls">
			                <form:input path="number" class="form-control"/>
						</div>
					</div>
	            </div>
	        </div>
		</div>
		<div class="form-actions right">
			<button class="btn blue" type="submit" data-grid-reload="#grid-formid-message-index">
				<i class="fa fa-check"></i> 保存
			</button>
			<button class="btn green" type="submit" data-grid-reload="#grid-formid-message-index" data-post-dismiss="modal">保存并关闭
			</button>
			<button class="btn default" type="button" data-dismiss="modal">取消</button>
		</div>
	</form:form>
</body>
</html>