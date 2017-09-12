<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form:form class="form-horizontal form-bordered form-label-stripped form-validation"
           action="${ctx}/admin/shop/shop-station/edit" method="post" modelAttribute="entity"
           data-editrulesurl="${ctx}/admin/util/validate?clazz=${clazz}">
    <form:hidden path="id"/>
    <form:hidden path="version"/>
    <div class="form-actions">
        <button class="btn blue" type="submit" data-grid-reload="#grid-shop-shop-station-index">
            <i class="fa fa-check"></i> 保存
        </button>
        <button class="btn green" type="submit" data-grid-reload="#grid-shop-shop-station-index"
                data-post-dismiss="modal">保存并关闭
        </button>
        <button class="btn default" type="button" data-dismiss="modal">取消</button>
    </div>
    <div class="form-body">
        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label class="control-label">商铺</label>
                    <div class="controls">
                        <form:hidden path="shop.id" class="form-control" data-select2-type="remote"
                                     data-url="${ctx}/admin/shop/shop/list" data-display="${shop.id}"
                                     data-query="search['EQ_id']"/>
                        <form:input path="shop.name" class="form-control"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label class="control-label">机器</label>
                    <div class="controls">
                        <form:hidden path="station.id" class="form-control" data-select2-type="remote"
                                     data-url="${ctx}/admin/station/station/list" data-display="${station.sid}"
                                     data-query="search['EQ_sid']"/>
                        <form:input path="station.sid" class="form-control"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label class="control-label">设备码</label>
                    <div class="controls">
                        <form:input path="lbsid" class="form-control"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label class="control-label">商铺名</label>
                    <div class="controls">
                        <form:input path="title" class="form-control"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label class="control-label">设备具体摆放位置</label>
                    <div class="controls">
                        <form:input path="position" class="form-control"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label class="control-label">地址</label>
                    <div class="controls">
                        <form:input path="address" class="form-control"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label class="control-label">经度</label>
                    <div class="controls">
                        <form:input path="longitude" class="form-control"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label class="control-label">纬度</label>
                    <div class="controls">
                        <form:input path="latitude" class="form-control"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label class="control-label">费用设置</label>
                    <div class="controls">
                        <form:hidden path="feeSettings.id" class="form-control" data-select2-type="remote"
                                     data-url="${ctx}/admin/feeStrategy/fee-strategy/list" data-display="${admin.name}"
                                     data-query="search['CN_name']"/>
                        <form:input path="feeSettings.name" class="form-control"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label class="control-label">销售</label>
                    <div class="controls">
                        <form:hidden path="admin.id" class="form-control" data-select2-type="remote"
                                     data-url="${ctx}/admin/auth/user/list" data-display="${admin.display}"
                                     data-query="search['CN_nickName']"/>
                        <form:input path="admin.nickName" class="form-control"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label class="control-label">设备状态</label>
                    <div class="controls">
                        <form:input path="status" class="form-control"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="form-actions right">
        <button class="btn blue" type="submit" data-grid-reload="#grid-shop-shop-station-index">
            <i class="fa fa-check"></i> 保存
        </button>
        <button class="btn green" type="submit" data-grid-reload="#grid-shop-shop-station-index"
                data-post-dismiss="modal">保存并关闭
        </button>
        <button class="btn default" type="button" data-dismiss="modal">取消</button>
    </div>
</form:form>
</body>
</html>