<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<div class="row search-form-default">
    <div class="col-md-12">
        <form method="get" class="form-inline form-validation form-search form-search-init control-label-sm"
              data-grid-search="#grid-station-battery-index">
            <div class="form-group">
                <div class="controls controls-clearfix">
                    <input type="text" name="search['CN_rfid_OR_orderLog.orderid']" class="form-control input-xlarge"
                           placeholder="电池ID , 订单号...">
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
        <table id="grid-station-battery-index"></table>
    </div>
</div>

<script type="text/javascript">
    $(function () {
        $("#grid-station-battery-index").data("gridOptions", {
            url: WEB_ROOT + '/admin/station/battery/list',
            colModel: [{
                label: '流水号',
                name: 'id',
                hidden: true
            }, {
                label: '电池唯一标识',
                name: 'rfid',
                width: 80,
                align: 'center',
                editable: false
            }, {
                label: '所在设备',
                name: 'station.id',
                width: 80,
                index: 'station',
                align: 'center',
                editable: false
            }, {
                label: '所在设备槽位',
                name: 'slot',
                formatter: 'integer',
                editable: false
            }, {
                label: '最近所属订单',
                name: 'orderLog.orderid',
                width: 120,
                index: 'orderLog',
                editable: false
            }, {
                label: '电池状态',
                name: 'status',
                formatter: 'integer',
                editable: false
            }, {
                label: '电池类型',
                name: 'battType',
                width: 40,
                align: 'center',
                editable: false
            }, {
                label: '颜色',
                name: 'colorid',
                formatter: 'integer',
                editable: false
            }, {
                label: '充电线类型',
                name: 'cable',
                formatter: 'integer',
                editable: false
            }, {
                label: '电量',
                name: 'power',
                formatter: 'integer',
                editable: false
            }, {
                label: '电压',
                name: 'voltage',
                formatter: 'integer',
                editable: false
            }, {
                label: '电流',
                name: 'currentval',
                formatter: 'integer',
                editable: false
            }, {
                label: '温度',
                name: 'temperature',
                formatter: 'integer',
                editable: false
            }, {
                label: '是否损坏',
                name: 'isdamage',
                formatter: 'checkbox',
                editable: false
            }, {
                label: '是否带充电头',
                name: 'adapter',
                formatter: 'checkbox',
                editable: false
            }, {
                label: '按键是否按下',
                name: 'batteryKey',
                formatter: 'checkbox',
                editable: false
            }, {
                label: '防拆保护是否打开',
                name: 'broke',
                formatter: 'checkbox',
                editable: false
            }, {
                label: '是否在充电',
                name: 'chargesta',
                formatter: 'checkbox',
                editable: false
            }, {
                label: '同步时间',
                name: 'syncTime',
                formatter: 'timestamp',
                editable: false
            }, {
                label: '最后归还时间',
                name: 'lastBackTime',
                formatter: 'timestamp',
                editable: false
            }, {
                label: '电池设备信息',
                name: 'devInfo',
                width: 200,
                align: 'center',
                editable: false,
                hidden: true
            }],
            postData: {
                "search['FETCH_station']": "LEFT",
                "search['FETCH_orderLog']": "LEFT"
            },
            editurl: WEB_ROOT + '/admin//station/battery/edit',
            editrulesurl: WEB_ROOT + '/admin/util/validate?clazz=${clazz}',
            delurl: WEB_ROOT + '/admin/station/battery/delete',
            fullediturl: WEB_ROOT + '/admin/station/battery/edit-tabs'
        });
    });
</script>
</body>
</html>
