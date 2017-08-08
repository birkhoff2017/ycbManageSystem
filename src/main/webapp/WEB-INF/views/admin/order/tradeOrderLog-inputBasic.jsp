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
		action="${ctx}/admin/order/trade-order-log/edit" method="post" modelAttribute="entity"
		data-editrulesurl="${ctx}/admin/util/validate?clazz=${clazz}">
		<form:hidden path="id" />
		<form:hidden path="version" />
		<div class="form-actions">
			<button class="btn blue" type="submit" data-grid-reload="#grid-order-trade-order-log-index">
				<i class="fa fa-check"></i> 保存
			</button>
			<button class="btn green" type="submit" data-grid-reload="#grid-order-trade-order-log-index" data-post-dismiss="modal">保存并关闭
			</button>
			<button class="btn default" type="button" data-dismiss="modal">取消</button>
		</div>
		<div class="form-body">
	        <div class="row">
	            <div class="col-md-6">
					<div class="form-group">
						<label class="control-label">订单id</label>
						<div class="controls">
			                <form:input path="orderid" class="form-control"/>
						</div>
					</div>
	            </div>
	        </div>
	        <div class="row">
	            <div class="col-md-6">
					<div class="form-group">
						<label class="control-label">订单金额</label>
						<div class="controls">
			                <form:input path="price" class="form-control"/>
						</div>
					</div>
	            </div>
	        </div>
	        <div class="row">
	            <div class="col-md-6">
					<div class="form-group">
						<label class="control-label">用户</label>
						<div class="controls">
			                <form:hidden path="bizUser.id" class="form-control" data-select2-type="remote"
								data-url="${ctx}/admin/shop/biz-user/list" data-display="${bizUser.openid}"
								data-query="search['CN_openid']" />
			                <form:input path="bizUser.id" class="form-control"/>			                
						</div>
					</div>
	            </div>
	        </div>
	        <div class="row">
	            <div class="col-md-6">
					<div class="form-group">
						<label class="control-label">平台</label>
						<div class="controls">
			                <form:input path="platform" class="form-control"/>
						</div>
					</div>
	            </div>
	        </div>
	        <div class="row">
	            <div class="col-md-6">
					<div class="form-group">
						<label class="control-label">状态</label>
						<div class="controls">
			                <form:input path="status" class="form-control"/>
						</div>
					</div>
	            </div>
	        </div>
	        <div class="row">
	            <div class="col-md-6">
					<div class="form-group">
						<label class="control-label">借出商铺id</label>
						<div class="controls">
			                <form:hidden path="borrowShop.id" class="form-control" data-select2-type="remote"
								data-url="${ctx}/admin/shop/shop/list" data-display="${shop.id}"
								data-query="search['CN_name']" />
			                <form:input path="borrowShop.id" class="form-control"/>
						</div>
					</div>
	            </div>
	        </div>
	        <div class="row">
	            <div class="col-md-6">
					<div class="form-group">
						<label class="control-label">归还商铺id</label>
						<div class="controls">
			                <form:hidden path="returnShop.id" class="form-control" data-select2-type="remote"
								data-url="${ctx}/admin/shop/shop/list" data-display="${shop.id}"
								data-query="search['CN_name']" />
			                <form:input path="returnShop.id" class="form-control"/>			                
						</div>
					</div>
	            </div>
	        </div>
	        <div class="row">
	            <div class="col-md-6">
					<div class="form-group">
						<label class="control-label">借出商铺名</label>
						<div class="controls">
			                <form:input path="borrowShopName" class="form-control"/>
						</div>
					</div>
	            </div>
	        </div>
	        <div class="row">
	            <div class="col-md-6">
					<div class="form-group">
						<label class="control-label">归还商铺名</label>
						<div class="controls">
			                <form:input path="returnShopName" class="form-control"/>
						</div>
					</div>
	            </div>
	        </div>
	        <div class="row">
	            <div class="col-md-6">
					<div class="form-group">
						<label class="control-label">借出设备id</label>
						<div class="controls">
			                <form:hidden path="borrowStation.id" class="form-control" data-select2-type="remote"
								data-url="${ctx}/admin/station/station/list" data-display="${borrowStation.id}"
								data-query="search['CN_title']" />
			                <form:input path="borrowStation.id" class="form-control"/>			                
						</div>
					</div>
	            </div>
	        </div>
	        <div class="row">
	            <div class="col-md-6">
					<div class="form-group">
						<label class="control-label">归还设备id</label>
						<div class="controls">
			                <form:hidden path="returnStation.id" class="form-control" data-select2-type="remote"
								data-url="${ctx}/admin/station/station/list" data-display="${returnStation.id}"
								data-query="search['CN_title']" />
			                <form:input path="returnStation.id" class="form-control"/>			                
						</div>
					</div>
	            </div>
	        </div>
	        <div class="row">
	            <div class="col-md-6">
					<div class="form-group">
						<label class="control-label">借出商铺站点id</label>
						<div class="controls">
			                <form:hidden path="borrowShopStation.id" class="form-control" data-select2-type="remote"
								data-url="${ctx}/admin/shop/shop-station/list" data-display="${borrowShopStation.id}"
								data-query="search['CN_title']" />
			                <form:input path="borrowShopStation.id" class="form-control"/>			                
						</div>
					</div>
	            </div>
	        </div>
	        <div class="row">
	            <div class="col-md-6">
					<div class="form-group">
						<label class="control-label">归还商铺站点id</label>
						<div class="controls">
			                <form:hidden path="returnShopStation.id" class="form-control" data-select2-type="remote"
								data-url="${ctx}/admin/shop/shop-station/list" data-display="${returnShopStation.id}"
								data-query="search['CN_title']" />
			                <form:input path="returnShopStation.id" class="form-control"/>			                
						</div>
					</div>
	            </div>
	        </div>
	        <div class="row">
	            <div class="col-md-6">
					<div class="form-group">
						<label class="control-label">借出时所在城市</label>
						<div class="controls">
			                <form:input path="borrowCity" class="form-control"/>
						</div>
					</div>
	            </div>
	        </div>
	        <div class="row">
	            <div class="col-md-6">
					<div class="form-group">
						<label class="control-label">归还时所在城市</label>
						<div class="controls">
			                <form:input path="returnCity" class="form-control"/>
						</div>
					</div>
	            </div>
	        </div>
	        <div class="row">
	            <div class="col-md-6">
					<div class="form-group">
						<label class="control-label">已退款金额</label>
						<div class="controls">
			                <form:input path="refunded" class="form-control"/>
						</div>
					</div>
	            </div>
	        </div>
	        <div class="row">
	            <div class="col-md-6">
					<div class="form-group">
						<label class="control-label">租金</label>
						<div class="controls">
			                <form:input path="usefee" class="form-control"/>
						</div>
					</div>
	            </div>
	        </div>
	        <div class="row">
	            <div class="col-md-6">
					<div class="form-group">
						<label class="control-label">已付款</label>
						<div class="controls">
			                <form:input path="paid" class="form-control"/>
						</div>
					</div>
	            </div>
	        </div>
		</div>
		<div class="form-actions right">
			<button class="btn blue" type="submit" data-grid-reload="#grid-order-trade-order-log-index">
				<i class="fa fa-check"></i> 保存
			</button>
			<button class="btn green" type="submit" data-grid-reload="#grid-order-trade-order-log-index" data-post-dismiss="modal">保存并关闭
			</button>
			<button class="btn default" type="button" data-dismiss="modal">取消</button>
		</div>
	</form:form>
</body>
</html>