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
		action="${ctx}/admin/feeStrategy/fee-strategy/edit" method="post" modelAttribute="entity"
		data-editrulesurl="${ctx}/admin/util/validate?clazz=${clazz}">
		<form:hidden path="id" />
		<form:hidden path="version" />
		<div class="form-actions">
			<button class="btn blue" type="submit" data-grid-reload="#grid-feeStrategy-fee-strategy-index">
				<i class="fa fa-check"></i> 保存
			</button>
			<button class="btn green" type="submit" data-grid-reload="#grid-feeStrategy-fee-strategy-index" data-post-dismiss="modal">保存并关闭
			</button>
			<button class="btn default" type="button" data-dismiss="modal">取消</button>
		</div>
		<div class="form-body">
	        <div class="row">
	            <div class="col-md-6">
					<div class="form-group">
						<label class="control-label">策略名称</label>
						<div class="controls">
			                <form:input path="name" class="form-control"/>
						</div>
					</div>
	            </div>
	        </div>
	        <div class="row">
	            <div class="col-md-6">
					<div class="form-group">
						<label class="control-label">意外借出免费时长</label>
						<div class="controls">
			                <form:input path="freeTime" class="form-control"/>
						</div>
					</div>
	            </div>
	        </div>
	        <div class="row">
	            <div class="col-md-6">
					<div class="form-group">
						<label class="control-label">意外借出免费时长单位</label>
						<div class="controls">
			                <form:input path="freeUnit" class="form-control"/>
							<select name="clazz" class="form-control" required="true" placeholder="璁板綍鏁版嵁瀵硅薄" data-cascade-name="property"
									data-cascade-url="${ctx}/admin/aud/revision-entity/properties">
								<c:forEach items="${clazzMapping}" var="item">
									<option value="${item.key}">${item.value}</option>
								</c:forEach>
							</select>
						</div>
					</div>
	            </div>
	        </div>
	        <div class="row">
	            <div class="col-md-6">
					<div class="form-group">
						<label class="control-label">固定收费时长</label>
						<div class="controls">
			                <form:input path="fixedTime" class="form-control"/>
						</div>
					</div>
	            </div>
	        </div>
	        <div class="row">
	            <div class="col-md-6">
					<div class="form-group">
						<label class="control-label">固定收费时长单位</label>
						<div class="controls">
			                <form:input path="fixedUnit" class="form-control"/>
						</div>
					</div>
	            </div>
	        </div>
	        <div class="row">
	            <div class="col-md-6">
					<div class="form-group">
						<label class="control-label">固定费用</label>
						<div class="controls">
			                <form:input path="fixed" class="form-control"/>
						</div>
					</div>
	            </div>
	        </div>
	        <div class="row">
	            <div class="col-md-6">
					<div class="form-group">
						<label class="control-label">超出计费</label>
						<div class="controls">
			                <form:input path="fee" class="form-control"/>
						</div>
					</div>
	            </div>
	        </div>
	        <div class="row">
	            <div class="col-md-6">
					<div class="form-group">
						<label class="control-label">超出计费时长单位</label>
						<div class="controls">
			                <form:input path="feeUnit" class="form-control"/>
						</div>
					</div>
	            </div>
	        </div>
	        <div class="row">
	            <div class="col-md-6">
					<div class="form-group">
						<label class="control-label">最高收费时长</label>
						<div class="controls">
			                <form:input path="maxFeeTime" class="form-control"/>
						</div>
					</div>
	            </div>
	        </div>
	        <div class="row">
	            <div class="col-md-6">
					<div class="form-group">
						<label class="control-label">最高收费时长单位</label>
						<div class="controls">
			                <form:input path="maxFeeUnit" class="form-control"/>
						</div>
					</div>
	            </div>
	        </div>
	        <div class="row">
	            <div class="col-md-6">
					<div class="form-group">
						<label class="control-label">最高收费金额</label>
						<div class="controls">
			                <form:input path="maxFee" class="form-control"/>
						</div>
					</div>
	            </div>
	        </div>
		</div>
		<div class="form-actions right">
			<button class="btn blue" type="submit" data-grid-reload="#grid-feeStrategy-fee-strategy-index">
				<i class="fa fa-check"></i> 保存
			</button>
			<button class="btn green" type="submit" data-grid-reload="#grid-feeStrategy-fee-strategy-index" data-post-dismiss="modal">保存并关闭
			</button>
			<button class="btn default" type="button" data-dismiss="modal">取消</button>
		</div>
	</form:form>
</body>
</html>