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
              data-grid-search="#grid-station-station-index">
            <div class="form-group">
                <div class="controls controls-clearfix">
                    <input type="text" name="search['CN_mac_OR_route_OR_ccid']" class="form-control input-xlarge"
                           placeholder="Mac地址 , 通信模块 , ccid...">
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
    $(function () {
        $("#grid-station-station-index").data("gridOptions", {
            url: WEB_ROOT + '/admin/station/station/list',
            colModel: [{
                label: '机器ID',
                name: 'sid',
                width: 100,
                align: 'center',
                hidden: false,
                editable: true
            },  {
                label: '设备名',
                name: 'title',
                width: 120,
                align: 'center',
                editable: true
            }, {
                label: 'Mac地址',
                name: 'mac',
                width: 120,
                align: 'center',
                editable: false
            }, {
                label : '网络状态',
                name : 'netStatus',
                align : 'center',
                width : 50,
                formatter: 'select',
                searchoptions: {
                    value: Util.getCacheDictDatasByType("Station_Net_Status")
                },
                editable: false,
                hidden: true
            }, {
                label: '可借数',
                name: 'usable',
                formatter: 'integer',
                editable: false
            }, {
                label: '可还数',
                name: 'empty',
                formatter: 'integer',
                editable: false
            }, {
                label: '总数',
                name: 'total',
                formatter: 'integer',
                editable: false
            }, {
                label: '可用电池',
                name: 'usableBattery',
                width: 140,
                align: 'right',
                editable: false
            }, {
                label: '槽位状态',
                name: 'slotstatus',
                width: 120,
                align: 'center',
                editable: false
            }, {
                label: '开机时长',
                name: 'powerOnTime',
                width: 120,
                editable: false
            }, {
                label: '同步时间',
                name: 'syncTime',
                formatter: 'timestamp',
                width: 120,
                editable: false
            }, {
                label: '断电时间',
                name: 'lastPowerOffTime',
                formatter: 'timestamp',
                width: 120,
                editable: false
            }, {
                label: '心跳周期',
                name: 'heartCycle',
                formatter: 'integer',
                editable: true
            }, {
                label: '心跳率',
                name: 'heartbeatRate',
                formatter: 'integer',
                editable: false
            }, {
                label: '设备版本',
                name: 'deviceVer',
                formatter: 'integer',
                editable: true
            }, {
                label: '通信模块',
                name: 'route',
                width: 40,
                align: 'center',
                editable: false,
                hidden: true
            }, {
                label: 'ccid',
                name: 'ccid',
                width: 120,
                align: 'center',
                editable: false,
                hidden: true
            }, {
                label : '同步策略',
                name : 'syncSetting',
                align : 'center',
                width : 50,
                formatter: 'select',
                searchoptions: {
                    value: Util.getCacheDictDatasByType("Special_Sync_Setting")
                },
                editable: true
            }, {
                label: '备注',
                name: 'note',
                width: 120,
                align: 'center',
                editable: true
            }],
            editurl: WEB_ROOT + '/admin//station/station/edit',
            editrulesurl: WEB_ROOT + '/admin/util/validate?clazz=${clazz}',
            delurl: WEB_ROOT + '/admin/station/station/delete',
            fullediturl: WEB_ROOT + '/admin/station/station/edit-tabs',
            operations:
                function (itemArray) {
                    var $alterIpAndPort = $('<li data-position="multi" data-toolbar="show"><a href="javascript:;"> <i class="fa fa-print"></i>修改机器同步IP和port</a></li>');
                    $alterIpAndPort.children("a").bind("click", function (e) {
                        alert("请确认是否要修给设备的同步IP和port！");
                        loading = true;
                        e.preventDefault();
                        var $grid = $(this).closest(".ui-jqgrid").find(".ui-jqgrid-btable:first");
                        var url = WEB_ROOT + "/admin/station/station/alterIpAndPort?ids=";
                        var rowDatas = $grid.jqGrid("getSelectedRowdatas");
                        if (rowDatas.length == 0) {
                            alert("请选择想要修改的设备！");
                            return;
                        }
                        for (i = 0; i < rowDatas.length; i++) {
                            var rowData = rowDatas[i];
                            url += rowData['id'] + ",";
                        }
                        $.post(url, "", function () {
                        });
                    });
                    itemArray.push($alterIpAndPort);
                }
        });
    });
</script>
</body>
</html>
