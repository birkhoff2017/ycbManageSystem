package s2jh.biz.order.service;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.pagination.GroupPropertyFilter;
import lab.s2jh.core.pagination.PropertyFilter;
import lab.s2jh.core.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import s2jh.biz.bizUser.entity.BizUser;
import s2jh.biz.bizUser.service.BizUserService;
import s2jh.biz.cache.RedisService;
import s2jh.biz.feeStrategy.entity.FeeStrategy;
import s2jh.biz.feeStrategy.service.FeeStrategyService;
import s2jh.biz.formid.service.MessageService;
import s2jh.biz.order.dao.TradeOrderLogDao;
import s2jh.biz.order.entity.TradeOrderLog;
import s2jh.biz.shop.entity.Shop;
import s2jh.biz.shop.entity.ShopStation;
import s2jh.biz.shop.service.ShopStationService;
import s2jh.biz.station.dao.StationDao;
import s2jh.biz.station.entity.Station;
import s2jh.biz.station.service.StationService;
import s2jh.biz.util.JsonUtils;
import s2jh.biz.util.TimeUtil;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TradeOrderLogService extends BaseService<TradeOrderLog, Long> {

    private final Logger logger = LoggerFactory.getLogger(TradeOrderLogService.class);

    @Autowired
    private TradeOrderLogDao tradeOrderLogDao;

    @Autowired
    private BizUserService bizUserService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private StationService stationService;

    @Autowired
    private StationDao stationDao;

    @Autowired
    private ShopStationService shopStationService;

    @Autowired
    private FeeStrategyService feeStrategyService;

    @Autowired
    private AlipayMessageService alipayMessageService;

    @Autowired
    private AlipayOrderService alipayOrderService;

    @Autowired
    private WechatMessageService wechatMessageService;

    @Autowired
    private WeChatOrderService weChatOrderService;

    @Autowired
    private RedisService redisService;


    @Value("${wxRefundTemplateId}")
    private String wxRefundTemplateId;

    @Value("${wxLostBankPowerTemplateId}")
    private String wxLostBankPowerTemplateId;

    @Override
    protected BaseDao<TradeOrderLog, Long> getEntityDao() {
        return tradeOrderLogDao;
    }

    /**
     * 手动取消订单
     * <p>
     * 先更新订单信息，按照完结订单的逻辑更新订单信息
     * <p>
     * 对支付宝订单和小程序订单分别做不同处理
     *
     * @param orderid
     */
    public String cancelOrder(String orderid) {
        //查询出需要取消的订单
        TradeOrderLog tradeOrderLog = this.findTradeOrderLogByOrderId(orderid);

        //更新订单信息
        tradeOrderLog.setLastModifiedBy("SYS:ycbManageSystem");
        tradeOrderLog.setLastModifiedDate(new Date());
        //更新订单的状态为3，归还
        tradeOrderLog.setStatus(3);
        //更新用户的使用费用
        tradeOrderLog.setUsefee(BigDecimal.valueOf(0));
        //更新用户的归还时间
        tradeOrderLog.setReturnTime(new Date());
        //更新订单的归还店铺return_shop_id
        tradeOrderLog.setReturnShop(tradeOrderLog.getBorrowShop());
        //更新订单的中间表信息return_shop_station_id
        tradeOrderLog.setReturnShopStation(tradeOrderLog.getBorrowShopStation());
        //更新订单表中的return_station_id
        tradeOrderLog.setReturnStation(tradeOrderLog.getBorrowStation());
        //更新订单表中的return_station_name
        tradeOrderLog.setReturnShopName(tradeOrderLog.getBorrowShopName());
        //更新订单信息
        this.save(tradeOrderLog);

        Integer platform = tradeOrderLog.getPlatform();
        //判断是哪个平台的订单就调用哪个平台的消息推送service，2为支付宝的订单，3为小程序的订单
        if (3 == platform) {
            BizUser bizUser = tradeOrderLog.getBizUser();
            // 电池弹出失败自动退款至用户余额
            //获取到该笔订单用户的押金
            BigDecimal paid = tradeOrderLog.getPrice();
            //获取用户可用余额信息
            BigDecimal usablemoney = bizUser.getUsablemoney();
            //获取用户的押金
            BigDecimal deposit = bizUser.getDeposit();
            //用户的押金减掉该笔订单支付的金额
            BigDecimal subtract = deposit.subtract(paid);
            bizUser.setDeposit(subtract);
            //用户的可用余额加上该笔订单支付的金额
            BigDecimal add = usablemoney.add(paid);
            bizUser.setUsablemoney(add);
            //更新用户信息
            bizUser.setLastModifiedBy("SYS:return_back");
            bizUser.setLastModifiedDate(new Date());
            bizUserService.save(bizUser);

            // 推送归还成功消息
            weChatOrderService.sendReturnSuccessMessage("0秒", "0元", tradeOrderLog);
            return "取消订单成功";
        } else if (2 == platform) {
            //完结信用借还订单，费用为0
            alipayOrderService.completeOrder(tradeOrderLog, BigDecimal.valueOf(0),"RENT");
            //发送订单完结的消息
            alipayMessageService.sendReturnMessage("0秒", "0", tradeOrderLog);
            return "取消订单成功";
        }

        return "取消订单失败";
    }


    /**
     * 查询出所有商铺
     *
     * @param content
     * @return
     */
    public String findStations(String content) {

        //从redis中取出所有设备编号的json格式的字符串
        String stationIdList = this.redisService.getKeyValue("STATION_ID_LIST");
        //当在redis中没有查询到存储了所有设备编号的json格式的字符串的时候，需要先去数据库中查询
        if (null == stationIdList || "".equals(stationIdList)) {
            //查询出所有设备的信息来
            List<Station> stationList = this.stationDao.findAll();
            //新建一个list用来存储所有设备编号
            List<String> sidList = new ArrayList<>();
            for (Station station : stationList) {
                Long sid = station.getSid();
                String sidString = sid.toString();
                sidList.add(sidString);
            }
            //把所有设备编号转为json格式
            stationIdList = JsonUtils.writeValueAsString(sidList);
            //把所有设备编号放入redis中
            this.redisService.setKeyValue("STATION_ID_LIST", stationIdList);
        }
        //将存储着所有设备编号的json格式的字符串转换为对象
        List<String> sidList = JsonUtils.readValueAsList(stationIdList);
        //新建一个list用来存储返回去的数据
        List<String> list = new ArrayList();
        //遍历所有设备的sid,如果以传过来的信息开头就放入list中
        for (String sid : sidList) {
            String sidString = sid.toString();
            if (sidString.startsWith(content)) {
                list.add(sidString);
            }
        }
        String result = JsonUtils.writeValueAsString(list);
        return result;
    }

    /**
     * 根据订单号查询订单
     *
     * @return
     */
    private TradeOrderLog findTradeOrderLogByOrderId(String orderid) {
        //单一条件，filterName规则为   枚举类型的比较符号 _ 字段名称
        PropertyFilter propertyFilter = new PropertyFilter(TradeOrderLog.class, PropertyFilter.MatchType.EQ.toString() + "_orderid", orderid);
        List<TradeOrderLog> tradeOrderLogs = this.findByFilter(propertyFilter);
        TradeOrderLog tradeOrderLog = tradeOrderLogs.get(0);
        return tradeOrderLog;
    }

    /**
     * 全额退押金
     *
     * @param orderId
     * @param returnTime
     * @param returnStation
     * @return
     */
    public String refundAllDeposit(String orderId, String returnTime, String returnStation) {
        //查询出需要退款的订单
        TradeOrderLog tradeOrderLog = this.findTradeOrderLogByOrderId(orderId);

        //更新订单信息
        tradeOrderLog.setLastModifiedBy("SYS:returnback");
        tradeOrderLog.setLastModifiedDate(new Date());
        //更新订单的状态为7	'手动退押金'
        tradeOrderLog.setStatus(7);
        //更新用户的使用费用
        tradeOrderLog.setUsefee(BigDecimal.valueOf(0));
        //更新用户的归还时间
        Integer integer = Integer.valueOf(returnTime);
        Long l = System.currentTimeMillis() + integer * 60 * 60 * 1000;
        tradeOrderLog.setReturnTime(new Date(l));
        //更新订单的归还店铺return_shop_id
        //根据设备id查询对应的ycb_mcs_shop_station
        //当查询出来的shop_station不为null的时候，处理下面的逻辑
        ShopStation shopStation = this.findShopStationByStationId(returnStation);
        if (shopStation != null) {
            Shop shop = shopStation.getShop();
            tradeOrderLog.setReturnShop(shop);
            //更新订单的中间表信息return_shop_station_id
            tradeOrderLog.setReturnShopStation(shopStation);
            //更新订单表中的return_station_id
            tradeOrderLog.setReturnStation(shopStation.getStation());
            //更新订单表中的return_station_name
            tradeOrderLog.setReturnShopName(shop.getName());
        }

        //更新订单信息
        this.save(tradeOrderLog);

        Integer platform = tradeOrderLog.getPlatform();
        //判断是哪个平台的订单就调用哪个平台的消息推送service，2为支付宝的订单，3为小程序的订单
        if (3 == platform) {
            BizUser bizUser = tradeOrderLog.getBizUser();
            // 电池弹出失败自动退款至用户余额
            //获取到该笔订单用户的押金
            BigDecimal paid = tradeOrderLog.getPrice();
            //获取用户可用余额信息
            BigDecimal usablemoney = bizUser.getUsablemoney();
            //获取用户的押金
            BigDecimal deposit = bizUser.getDeposit();
            //用户的押金减掉该笔订单支付的金额
            BigDecimal subtract = deposit.subtract(paid);
            bizUser.setDeposit(subtract);
            //用户的可用余额加上该笔订单支付的金额
            BigDecimal add = usablemoney.add(paid);
            bizUser.setUsablemoney(add);
            //更新用户信息
            bizUser.setLastModifiedBy("SYS:return_back");
            bizUser.setLastModifiedDate(new Date());
            bizUserService.save(bizUser);

            // 推送归还成功消息
            weChatOrderService.sendReturnSuccessMessage("0秒", "0元", tradeOrderLog);
            return "全额退押金成功";
        } else if (2 == platform) {
            //完结信用借还订单，费用为0
            alipayOrderService.completeOrder(tradeOrderLog, BigDecimal.valueOf(0),"RENT");
            //发送订单完结的消息
            alipayMessageService.sendReturnMessage("0秒", "0", tradeOrderLog);
            return "全额退押金成功";
        }
        return "全额退押金失败";
    }

    /**
     * 根据shopid查询ShopStation
     *
     * @param stationid
     * @return
     */
    private ShopStation findShopStationByStationId(String stationid) {

        PropertyFilter stationFilter = new PropertyFilter(Station.class, PropertyFilter.MatchType.EQ.toString() + "_sid", stationid);
        List<Station> stationList = this.stationService.findByFilter(stationFilter);
        //如果没有查询出设备来，直接返回null
        if (null == stationList || stationList.size() == 0) {
            return null;
        }
        //得到根据设备号查询出来设备
        Station stationFound = stationList.get(0);
        //查询shop_station的信息
        GroupPropertyFilter groupPropertyFilter = GroupPropertyFilter.buildDefaultAndGroupFilter();
        groupPropertyFilter.forceAnd(new PropertyFilter(PropertyFilter.MatchType.EQ, "station", stationFound));
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        List<ShopStation> shopStationList = this.shopStationService.findByFilters(groupPropertyFilter, sort, 10);
        //如果没有查到中间表的信息，直接返回null
        if (null == shopStationList || shopStationList.size() == 0) {
            return null;
        }
        //获取到shop_station的信息,因为已经按照id逆序排列，所以取最后一个就好
        ShopStation shopStation = shopStationList.get(0);
        //判断这
        return shopStation;
    }

    /**
     * 退部分押金
     *
     * @param orderId
     * @param returnTime
     * @param returnStation
     * @param charger
     * @param chargingCable
     * @return
     */
    public String refundDeposit(String orderId, String returnTime, String returnStation, String charger, String chargingCable) {
        TradeOrderLog tradeOrderLog = this.findTradeOrderLogByOrderId(orderId);

        // 租借时长
        Long duration = 0L;
        //获取到订单所属平台
        Integer platform = tradeOrderLog.getPlatform();
        // 计算订单使用金额
        //获取到用户归还时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date returnDate = null;
        try {
            returnDate = format.parse(returnTime);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("解析前端传回的用户归还时间失败！！！");
        }
        duration = (returnDate.getTime() - tradeOrderLog.getBorrowTime().getTime()) / 1000;
        //获取收费策略
        ShopStation borrowShopStation = tradeOrderLog.getBorrowShopStation();
        FeeStrategy feeSettings = borrowShopStation.getFeeSettings();

        BigDecimal usefee = feeStrategyService.calUseFee(feeSettings, duration);
        //只有当不是支付宝平台的订单的时候才执行该业务逻辑
        BigDecimal price = tradeOrderLog.getPrice();
        BigDecimal refund = price.subtract(usefee);
        //用户支付的金额不能大于押金！！！
        if (price.compareTo(usefee) < 0) {
            usefee = price;
            refund = BigDecimal.ZERO;
        }
        //只有当不是支付宝平台的订单的时候，需要更新用户的账户信息
        BizUser bizUser = tradeOrderLog.getBizUser();
        if (platform != 2) {
            // 更新用户账户信息(归还成功，订单押金退还到余额)
            //更新用户的账户信息
            bizUser.setLastModifiedBy("SYS:return_back");
            bizUser.setLastModifiedDate(new Date());
            //用户的可用余额加上退款金额
            BigDecimal usablemoney = bizUser.getUsablemoney();
            BigDecimal add = usablemoney.add(refund);
            bizUser.setUsablemoney(add);
            //用户的押金减掉该笔订单的押金
            BigDecimal deposit = bizUser.getDeposit();
            BigDecimal subtract = deposit.subtract(price);
            bizUser.setDeposit(subtract);
            //更新用户的信息
            bizUserService.save(bizUser);
        }
        // 更新订单信息 订单状态由借出->归还

        //更新订单信息
        tradeOrderLog.setLastModifiedBy("SYS:returnback");
        tradeOrderLog.setLastModifiedDate(new Date());
        //更新订单的状态为93	'管理员已手动退款（归还失败）'	和客服沟通，需要照片留底
        tradeOrderLog.setStatus(93);
        //更新用户的使用费用
        tradeOrderLog.setUsefee(usefee);
        //更新用户的归还时间
        tradeOrderLog.setReturnTime(returnDate);
        //更新订单的归还店铺return_shop_id
        //当查询出来的shop_station不为null的时候，处理下面的逻辑
        ShopStation shopStation = this.findShopStationByStationId(returnStation);
        if (null != shopStation) {
            Shop shop = shopStation.getShop();
            tradeOrderLog.setReturnShop(shop);
            //更新订单的中间表信息return_shop_station_id
            tradeOrderLog.setReturnShopStation(shopStation);
            //更新订单表中的return_station_id
            Station station = shopStation.getStation();
            tradeOrderLog.setReturnStation(station);
            //更新订单表中的return_station_name
            tradeOrderLog.setReturnShopName(shop.getName());
        }
        //更新订单信息
        this.save(tradeOrderLog);

        //判断是哪个平台的订单就调用哪个平台的消息推送service，2为支付宝的订单，3为小程序的订单
        // 推送归还成功消息
        String durationString = TimeUtil.timeToString(duration);
        String useFeeStr = usefee + "元";
        if (3 == platform) {
            this.weChatOrderService.sendReturnSuccessMessage(durationString, useFeeStr, tradeOrderLog);
            return "退部分押金成功";
        } else if (2 == platform) {
            //信用借还完结订单
            alipayOrderService.completeOrder(tradeOrderLog, usefee,"RENT");
            // 推送归还成功消息
            alipayMessageService.sendReturnMessage(durationString, useFeeStr, tradeOrderLog);
            return "退部分押金成功";
        }
        return "退部分押金失败";
    }

    /**
     * 用户丢失充电宝
     *
     * @param orderid
     * @param lostTime
     * @return
     */
    public String lostPowerBank(String orderid, String lostTime) {
        TradeOrderLog tradeOrderLog = this.findTradeOrderLogByOrderId(orderid);
        //更新订单信息
        tradeOrderLog.setLastModifiedBy("SYS:lostPowerBank");
        tradeOrderLog.setLastModifiedDate(new Date());
        //更新订单的状态为92	'租金已扣完(未归还)'
        tradeOrderLog.setStatus(92);
        //更新用户的使用费用，为该笔订单的押金
        //获取该笔订单的押金
        BigDecimal price = tradeOrderLog.getPrice();
        //将该笔订单的使用费用修改为押金，即使用费用为全部押金
        tradeOrderLog.setUsefee(price);
        //更新订单信息
        this.save(tradeOrderLog);
        //获取到用户丢失时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取到用户的租借时间
        Date borrowTime = tradeOrderLog.getBorrowTime();
        String useTime = "";
        try {
            Date lostDate = format.parse(lostTime);
            Long use = (lostDate.getTime() - borrowTime.getTime())/1000;
            useTime = TimeUtil.timeToString(use);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("解析前端传回的用户归还时间失败！！！");
        }
        Integer platform = tradeOrderLog.getPlatform();
        //判断是哪个平台的订单就调用哪个平台的消息推送service，2为支付宝的订单，3为小程序的订单
        if (3 == platform) {
            BizUser bizUser = tradeOrderLog.getBizUser();
            // 充电宝丢失，扣除该笔订单全部押金
            //获取到该笔订单用户的押金
            BigDecimal paid = price;
            //获取用户的押金
            BigDecimal deposit = bizUser.getDeposit();
            //用户的押金减掉该笔订单支付的金额
            BigDecimal subtract = deposit.subtract(paid);
            bizUser.setDeposit(subtract);
            //更新用户信息
            bizUser.setLastModifiedBy("SYS:return_back");
            bizUser.setLastModifiedDate(new Date());
            bizUserService.save(bizUser);
            // 推送归还成功消息
            weChatOrderService.sendReturnSuccessMessage(useTime, paid + "元", tradeOrderLog);
            return "丢失充电宝处理成功";
        } else if (2 == platform) {
            //完结信用借还订单，费用为0
            alipayOrderService.completeOrder(tradeOrderLog, price,"DAMAGE");
            //发送订单完结的消息
            alipayMessageService.sendReturnMessage(useTime, price.toString(), tradeOrderLog);
            return "丢失充电宝处理成功";
        }
        return "丢失充电宝处理失败";
    }

    /**
     * 手动退还多收的钱
     *
     * @param orderid
     * @param extraMoney
     * @return
     */
    public String refundExtraMoney(String orderid, String extraMoney) {
        TradeOrderLog tradeOrderLog = this.findTradeOrderLogByOrderId(orderid);
        //获取到用户的使用费用
        BigDecimal usefee = tradeOrderLog.getUsefee();
        //订单中用户的使用费用减去退还的费用
        Double extraMoneyDouble = Double.parseDouble(extraMoney);
        BigDecimal extraMoneyBigDecimal = BigDecimal.valueOf(extraMoneyDouble);
        BigDecimal subtract = usefee.subtract(extraMoneyBigDecimal);
        //修改订单中用户的使用费用
        tradeOrderLog.setUsefee(subtract);
        //获取到该笔订单的refundedUsable已退款至可用余额
        BigDecimal refundedUsable = tradeOrderLog.getRefundedUsable();
        //原 已退款至可用余额 加上这次退款到可用余额的金额
        BigDecimal add1 = refundedUsable.add(extraMoneyBigDecimal);
        //修改订单中的 已退款余额
        tradeOrderLog.setRefundedUsable(add1);
        //保存修改后的订单
        this.save(tradeOrderLog);

        Integer platform = tradeOrderLog.getPlatform();
        //判断是哪个平台的订单就怎样处理业务逻辑，2为支付宝的订单，3为小程序的订单
        BigDecimal price = tradeOrderLog.getPrice();
        if (3 == platform) {
            BizUser bizUser = tradeOrderLog.getBizUser();
            //获取用户的可用余额
            BigDecimal usablemoney = bizUser.getUsablemoney();
            //用户的可用余额加上退款的金额
            BigDecimal add = usablemoney.add(extraMoneyBigDecimal);
            //更改用户的余额
            bizUser.setUsablemoney(add);
            //更新用户信息
            this.bizUserService.save(bizUser);

            return "手动退押金成功";
        } else if (2 == platform) {
            //因为已经在外部退款给用户，这里没有业务逻辑
            return "手动退押金成功";
        }
        return "手动退押金失败";
    }
}
