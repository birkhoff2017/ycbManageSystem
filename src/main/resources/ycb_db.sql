DROP TABLE IF EXISTS `mcs_fee_strategy`;
CREATE TABLE IF NOT EXISTS `mcs_fee_strategy` (
  `id` bigint(11) unsigned NOT NULL auto_increment,
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '收费策略名称',
  `fee` varchar(200) NOT NULL DEFAULT '' COMMENT '收费策略详情 fixed_time 固定收费时间 fixed_unit固定收费时间单位 fixed 固定时间费用 fee 超时计费费用 fee_unit超时计费单位 max_fee_time 最高收费时间 max_fee_unit 最高收费单位 max_fee 最高收费费用 free_time 意外借出免费时间 free_unit 意外借出免费单位',
  primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `mcs_user`;
CREATE TABLE IF NOT EXISTS `mcs_user` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `openid` varchar(50) NOT NULL comment '用户openid(微信,支付宝等其他平台)',
  `platform` tinyint(1) unsigned NOT NULL DEFAULT '0' comment '0:微信, 1:支付宝',
  `usablemoney` decimal(8,2) unsigned NOT NULL DEFAULT '0.00' comment '账户余额',
  `deposit` decimal(8,2) unsigned NOT NULL DEFAULT '0.00' comment '押金',
  `refund` decimal(8,2) unsigned NOT NULL DEFAULT '0.00' comment '待退款数目',
  `up` int(10) NOT NULL DEFAULT '0' comment '未知字段',
  `unsubscribe` tinyint(4) NOT NULL COMMENT '该用户是否已经取消关注 1 为取消 0为未取消',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户注册时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `openid` (`openid`),
  KEY `platform` (`platform`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `mcs_userinfo`;
CREATE TABLE IF NOT EXISTS `mcs_userinfo` (
  `id` int(11) NOT NULL,
  `openid` varchar(255) NOT NULL COMMENT '微信用户openid',
  `nickname` varchar(255) NOT NULL COMMENT '微信用户昵称',
  `sex` tinyint(1) NOT NULL COMMENT '性别 男1 女0',
  `city` varchar(255) NOT NULL COMMENT '用户所在城市',
  `province` varchar(255) NOT NULL COMMENT '省份',
  `country` varchar(127) NOT NULL COMMENT '国家',
  `headimgurl` varchar(255) NOT NULL COMMENT '微信用户头像',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `language` varchar(255) NOT NULL COMMENT '用户的语言',
  `subscribe_time` bigint(20) NOT NULL COMMENT '用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间',
  `unionid` varchar(255) NOT NULL COMMENT '只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段',
  `remark` varchar(255) NOT NULL COMMENT '公众号运营者对粉丝的备注',
  `groupid` varchar(255) NOT NULL COMMENT '用户所在的分组ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';

DROP TABLE IF EXISTS `mcs_refund_log`;
CREATE TABLE IF NOT EXISTS `mcs_refund_log` (
  `id` bigint(11) unsigned NOT NULL auto_increment,
  `uid` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '用户ID',
  `refund` decimal(8,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '申请提现金额',
  `refunded` decimal(8,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '实际提现金额',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '1申请 2退款完成',
  `request_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '申请时间',
  `refund_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '退款时间',
  `detail` text NOT NULL COMMENT '退款订单详情',
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`),
  KEY `status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `mcs_tradelog`;
CREATE TABLE IF NOT EXISTS `mcs_tradelog` (
  `orderid` varchar(32) NOT NULL COMMENT '订单id',
  `price` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '订单金额',
  `customer` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '用户ID',
  `platform` tinyint(1) unsigned NOT NULL DEFAULT '0' comment '0:微信, 1:支付宝',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '订单状态 cfg.inc.php 定义 1 已支付 2 借出 3 归还 4 提现 5 第一次确认 6 提醒归还 7 手动退押金 10 充电头/线借出 84 异常断电 85 机械手报警 86 电池卡住 87 四轴无响应 88 机器工作中 89 主控无确认 90 主控无应答 95 充电头槽口有问题 96 超时自动退款 97 借出失败 98 租金已扣完 100 管理员待支付 101 管理员直接支付 102 异常支付',
  `lastupdate` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最后更新时间',
  `battery_id` bigint(11) unsigned NOT NULL DEFAULT '0' COMMENT '电池ID',
  `shop_type` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '业态',
  `seller_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '代理id',
  `cable` tinyint(1) unsigned NOT NULL DEFAULT '2' COMMENT '线类型 默认是二合一的线类型  3:typec',
  `sceneid` int(32) unsigned NOT NULL DEFAULT '0' COMMENT '借出该电池的充电站',
  `return_station` int(32) unsigned NOT NULL DEFAULT '0' COMMENT '归还的充电站',
  `borrow_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '借出的时间',
  `return_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '归还的时间',
  `borrow_station_name` varchar(255) NOT NULL DEFAULT '' COMMENT '借出电池的充电站名称',
  `return_station_name` varchar(255) NOT NULL DEFAULT '' COMMENT '归还电池的充电站名称',
  `borrow_shop_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '借出电池的商铺ID',
  `return_shop_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '归还电池的商铺ID',
  `borrow_shop_station_id` int(11) unsigned NOT NULL  DEFAULT '0' COMMENT '借出电池的商铺站点ID',
  `return_shop_station_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '归还电池的商铺站点ID',
  `borrow_city` varchar(100) NOT NULL DEFAULT '' COMMENT '借出时所在的城市',
  `return_city` varchar(100) NOT NULL DEFAULT '' COMMENT '归还时所在的城市',
  `borrow_device_ver` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '借出电池的机器版本',
  `return_device_ver` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '归还电池的机器版本',
  `paid` decimal(8,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '用户支付金额',
  `refundno` tinyint(1) NOT NULL DEFAULT '0' COMMENT '这个订单提现的状态 -1 账户余额,无法微信退款 -2退款完毕',
  `refunded` decimal(8,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '退款金额',
  `usefee` decimal(8,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '用户使用完电池后需要支付的金额',
  UNIQUE KEY `orderid` (`orderid`),
  KEY `status` (`status`),
  KEY `customer` (`customer`),
  KEY `platform` (`platform`),
  KEY `sceneid` (`sceneid`),
  KEY `borrow_time` (`borrow_time`),
  KEY `return_station` (`return_station`),
  KEY `return_time` (`return_time`),
  KEY `borrow_shop_station_id` (`borrow_shop_station_id`),
  KEY `return_shop_station_id` (`return_shop_station_id`),
  KEY `borrow_shop_id` (`borrow_shop_id`),
  KEY `return_shop_id` (`return_shop_id`),
  KEY `borrow_city` (`borrow_city`),
  KEY `return_city` (`return_city`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `mcs_tradeinfo`;
CREATE TABLE IF NOT EXISTS `mcs_tradeinfo` (
  `orderid` varchar(32) NOT NULL COMMENT '订单id',
  `fee_strategy` varchar(200) NOT NULL DEFAULT '' COMMENT '收费策略详情',
  `batt_type` varchar(1) NOT NULL DEFAULT '0' COMMENT '电池类型',
  UNIQUE KEY `orderid` (`orderid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单信息表';

DROP TABLE IF EXISTS `mcs_battery`;
CREATE TABLE IF NOT EXISTS `mcs_battery` (
  `id` bigint(11) unsigned NOT NULL,
  `rfid` varchar(32) NOT NULL DEFAULT '' COMMENT '标识电池的射频电路id  唯一的',
  `stationid` mediumint(8) unsigned NOT NULL COMMENT '电池所在充电站的id',
  `colorid` tinyint(4) unsigned NOT NULL COMMENT '颜色id',
  `power` tinyint(4) unsigned NOT NULL COMMENT '电池电量 百分比数字',
  `slot` tinyint(1) unsigned NOT NULL COMMENT '电池所在充电站槽口',
  `isdamage` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '大机器电池状态值, 见tower ',
  `tamper` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '防拆开关 0 未打开, 1 打开防拆开关',
  `voltage` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '电压',
  `adapter` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否存在充电头适配器  1存在 0不存在',
  `cable` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否存在充电线 0不存在 1存在',
  `temperature` int(2) unsigned NOT NULL DEFAULT '0' COMMENT '电池温度',
  `batt_type` varchar(1) NOT NULL DEFAULT '0' COMMENT '电池类型和线类型',
  `orderid` varchar(32) NOT NULL DEFAULT '' COMMENT '电池上一次订单 id',
  `status` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '状态 0可借出 1已借出 2 借出后同步',
  `sync_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最近同步的时间',
  `exception_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '异常时间点, 跟status匹配, 即出现status异常状态的时间点, 目前仅用到 2 借出后同步',
  `heart_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT 'mini机器心跳带上的电池的时间点, 仅做调试参考使用',
  PRIMARY KEY (`id`),
  KEY `rfid` (`rfid`),
  KEY `tamper` (`tamper`),
  KEY `stationid` (`stationid`,`colorid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `mcs_station`;
CREATE TABLE IF NOT EXISTS `mcs_station` (
  `id` mediumint(8) unsigned NOT NULL auto_increment,
  `title` varchar(30) NOT NULL DEFAULT '' COMMENT '充电站名称',
  `address` varchar(100) NOT NULL DEFAULT '' COMMENT '充电站地址',
  `mac` varchar(20) NOT NULL COMMENT '机器物理地址 标识一台机器',
  `channelid` char(19) NOT NULL COMMENT '百度push 根据channelid向机器推送消息',
  `total` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '电池总数',
  `usable` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '充电站可借电池数量',
  `usable_battery` varchar(255) NOT NULL DEFAULT '[]' COMMENT '充电站可借电池数量及类型（兼容type-c）',
  `empty` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '充电站可返还电池数量',
  `battery_adapter` tinyint(1) unsigned DEFAULT '0' COMMENT '充电头适配器数量',
  `slotstatus` char(90) NOT NULL DEFAULT '0' COMMENT '长度为槽位数, 每个槽位一个字符，每位代表卡槽，0表示卡槽正常,其它表示异常',
  `sportstatus` varchar(8) NOT NULL DEFAULT '0' COMMENT '8位数字，每位表示机器的一个地方是否正常',
  `sensor_status_1` varchar(1000) NOT NULL DEFAULT '0' COMMENT '每个槽位两个字符用16进制表示代表8位整数, 槽位间用-相连',
  `sensor_status_2` varchar(1000) NOT NULL DEFAULT '0' COMMENT '每个槽位两个字符用16进制表示代表8位整数, 槽位间用-相连',
  `colorcount` varchar(200) NOT NULL DEFAULT '' COMMENT '充电站里含有颜色－机器个数集合',
  `machine` tinyint(1) NOT NULL DEFAULT '0' COMMENT '机器锁定状态',
  `sdcard` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'sd卡显示',
  `adaptercount` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT 'iphone4充电头数量',
  `adaptercount2` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT 'iphone5及其以上充电头状态 0没有 3少量 4足量 >=100故障',
  `cable` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '充电线状态  0没有 3少量 4足量 >=100故障',
  `maincontrol` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '主控板状态 0正常 其它不正常',
  `sync_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '同步时间',
  `bgimg` varchar(100) NOT NULL DEFAULT '' COMMENT '背景图',
  `version` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '安卓软件版本',
  `device_ver` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '充电机器版本 ',
  `status` varchar(10) NOT NULL DEFAULT '0' COMMENT '这台机器的状况 暂时4位储存,每位对应一种状态: 右通道 左通道 RFID 断电',
  `error_man` text NOT NULL COMMENT '这台机器的维护人员信息',
  `heartbeat_rate` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '心跳存活率, 百分比',
  `power_on_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '今天开机累计时长, 单位秒',
  PRIMARY KEY (`id`),
  UNIQUE KEY `mac` (`mac`),
  KEY `mac_2` (`mac`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `mcs_shop_station`;
CREATE TABLE IF NOT EXISTS `mcs_shop_station` (
  `id` mediumint(8) unsigned NOT NULL auto_increment,
  `shopid` int(11) NOT NULL COMMENT '所属商铺id',
  `station_id` int(11) NOT NULL COMMENT '绑定设备id',
  `lbsid` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '百度地图云检索id',
  `title` varchar(30) NOT NULL DEFAULT '' COMMENT '充电站名称',
  `desc` varchar(100) NOT NULL DEFAULT '' COMMENT '描述',
  `address` varchar(100) NOT NULL DEFAULT '' COMMENT '充电站地址',
  `longitude` varchar(30) NOT NULL DEFAULT '' COMMENT '经度',
  `latitude` varchar(30) NOT NULL DEFAULT '' COMMENT '纬度',
  `fee_settings` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '收费策略id',
  `seller_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '绑定商户的id',
  `error_man` text NOT NULL COMMENT '这台机器的维护人员信息',
  `status` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '0: 未启用 1: 启用',
  PRIMARY KEY (`id`),
  KEY `status` (`status`),
  KEY `shopid` (`shopid`),
  KEY `station_id` (`station_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `mcs_shop`;
CREATE TABLE IF NOT EXISTS `mcs_shop` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商铺id',
  `name` varchar(255)  NOT NULL COMMENT '商铺名称',
  `province` varchar(128)  NOT NULL COMMENT '所在省份',
  `city` varchar(128)  NOT NULL COMMENT '所在城市',
  `area` varchar(128)  NOT NULL COMMENT '所在区域',
  `locate` varchar(255)  NOT NULL COMMENT '商铺位置',
  `cost` int(11) NOT NULL COMMENT '人均花费',
  `phone` char(20) NOT NULL COMMENT '电话号码',
  `stime` varchar(255) NOT NULL COMMENT '商铺开始营业时间',
  `etime` varchar(255)  NOT NULL COMMENT '商铺结束营业时间',
  `logo` varchar(1000)  NOT NULL COMMENT '商铺logo',
  `carousel` varchar(1000)  NOT NULL COMMENT '商铺轮播图',
  `type` tinyint(4) NOT NULL COMMENT '商铺类型',
  `status` int(11) NOT NULL COMMENT '标识这条记录',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8  COMMENT='商铺表' AUTO_INCREMENT=1;


DROP TABLE IF EXISTS `mcs_shop_type`;
CREATE TABLE IF NOT EXISTS `mcs_shop_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商铺类型id',
  `type` varchar(255) NOT NULL COMMENT '商铺类型名字',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商铺类型表' AUTO_INCREMENT=1 ;

DROP TABLE IF EXISTS `mcs_admin`;
CREATE TABLE `mcs_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(10) unsigned NOT NULL COMMENT '用户id',
  `username` varchar(255) NOT NULL COMMENT '登录名',
  `name` varchar(255) NOT NULL COMMENT '真实姓名',
  `pwd` varchar(255) NOT NULL COMMENT '加密后密码',
  `salt` char(8) NOT NULL COMMENT '加密随机盐值',
  `email` varchar(255) NOT NULL COMMENT '公司邮箱',
  `company` varchar(255) NOT NULL COMMENT '公司名称',
  `role_id` int(11) NOT NULL COMMENT '用户角色',
  `login_error` tinyint(3) NOT NULL COMMENT '登录错误次数 (超过失败次数锁定账户，成功就刷新为0)',
  `update_time` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `status` int(11) NOT NULL COMMENT '状态(-1删除，0申请，1申请通过,2账户被锁定,3申请被拒绝)',
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 COMMENT='权限控制权限表';

DROP TABLE IF EXISTS `mcs_admin_city`;
CREATE TABLE IF NOT EXISTS `mcs_admin_city` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_id` int(11) NOT NULL COMMENT '管理员id',
  `city` text NOT NULL COMMENT '管理员所负责的城市集合',
  `status` int(11) NOT NULL COMMENT '状态(0 申请中，1通过)',
  `update_time` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
  PRIMARY KEY (`id`),
  KEY `admin_id` (`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员所负责的城市表' AUTO_INCREMENT=1 ;

DROP TABLE IF EXISTS `mcs_admin_role`;
CREATE TABLE `mcs_admin_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role` varchar(255) NOT NULL COMMENT '管理员角色',
  `access` text NOT NULL COMMENT '用户权限json字符串',
  `global_search` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '搜索功能全局权限标志位',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '角色创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员角色表' AUTO_INCREMENT=1 ;

DROP TABLE IF EXISTS `mcs_admin_shop`;
CREATE TABLE `mcs_admin_shop` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_id` int(11) NOT NULL COMMENT '管理员id',
  `shop_id` int(11) NOT NULL COMMENT '管理员负责的商铺id',
  `status` int(11) NOT NULL COMMENT '状态',
  `update_time` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
  PRIMARY KEY (`id`),
  KEY `admin_id` (`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员所负责的商铺表' AUTO_INCREMENT=1 ;
