package s2jh.biz.order.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayOpenPublicMessageSingleSendRequest;
import com.alipay.api.request.ZhimaMerchantOrderRentCancelRequest;
import com.alipay.api.response.AlipayOpenPublicMessageSingleSendResponse;
import com.alipay.api.response.ZhimaMerchantOrderRentCancelResponse;
import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.pagination.GroupPropertyFilter;
import lab.s2jh.core.pagination.PropertyFilter;
import lab.s2jh.core.service.BaseService;
import lab.s2jh.core.util.JsonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import s2jh.biz.bizUser.entity.BizUser;
import s2jh.biz.bizUser.service.BizUserService;
import s2jh.biz.cache.RedisService;
import s2jh.biz.constant.GlobalConfig;
import s2jh.biz.formid.dao.MessageDaoImpl;
import s2jh.biz.formid.entity.Message;
import s2jh.biz.order.dao.TradeOrderLogDao;
import s2jh.biz.order.entity.TradeOrderLog;
import s2jh.biz.order.entity.WechatTemplateMsg;
import s2jh.biz.util.HttpRequest;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class TradeOrderLogService extends BaseService<TradeOrderLog, Long> {

    private final Logger logger = LoggerFactory.getLogger(TradeOrderLogService.class);

    @Autowired
    private TradeOrderLogDao tradeOrderLogDao;

    @Autowired
    private BizUserService bizUserService;


    @Autowired
    private MessageDaoImpl messageDao;

    @Autowired
    private RedisService redisService;

    @Value("${appID}")
    private String appID;

    @Value("${appSecret}")
    private String appSecret;

    @Value("${wxRefundTemplateId}")
    private String wxRefundTemplateId;

    @Override
    protected BaseDao<TradeOrderLog, Long> getEntityDao() {
        return tradeOrderLogDao;
    }

    /**
     * 手动退订单款，对支付宝订单和小程序订单分别做不同处理
     *
     * @param ids
     */
    public void refundOrders(Long[] ids) {
        GroupPropertyFilter groupPropertyFilter = GroupPropertyFilter.buildDefaultAndGroupFilter();
        groupPropertyFilter.forceAnd(new PropertyFilter(PropertyFilter.MatchType.IN, "id", ids));
        List<TradeOrderLog> orderLogList = this.findByFilters(groupPropertyFilter);

        if (CollectionUtils.isNotEmpty(orderLogList)) {
            for (TradeOrderLog order : orderLogList) {
                String orderid = order.getOrderid();
                System.out.println("需要退款的订单的编号为：" + orderid);
                //判断是小程序订单还是支付宝订单,2为支付宝的订单，3为小程序的订单
                Integer alipayPlatform = 2;
                Integer weixinPlatform = 3;
                BizUser bizUser = order.getBizUser();
                if (alipayPlatform.equals(order.getPlatform())) {
                    //调用支付宝撤销订单接口撤销订单
                    String orderNo = order.getOrderNo();
                    String openid = bizUser.getOpenid();
                    this.cancelCreditOrder(orderNo, openid, orderid);
                } else if (weixinPlatform.equals(order.getPlatform())) {
                    //退款到用户账户
                    this.refundWeixinOrder(bizUser, order);
                }
            }
        }
    }

    /**
     * 进行手动退款时，取消支付宝已经生成的信用借还订单
     *
     * @param orderNo 信用借还订单号
     * @param openid  该笔订单对应的用户的openid
     * @param orderid 该笔订单的orderid
     */
    private void cancelCreditOrder(String orderNo, String openid, String orderid) {
        AlipayClient alipayClient = new DefaultAlipayClient(GlobalConfig.ALIPAY_SERVER_URL, GlobalConfig.ALIPAY_APP_ID, GlobalConfig.ALIPAY_PRIVATE_KEY, GlobalConfig.ALIPAY_FORMAT, GlobalConfig.ALIPAY_CHARSET, GlobalConfig.ALIPAY_ALIPAYPUBLIC_KEY, GlobalConfig.ALIPAY_SIGNTYPE);
        ZhimaMerchantOrderRentCancelRequest request = new ZhimaMerchantOrderRentCancelRequest();
        Map<String, Object> bizContentMap = new LinkedHashMap<>();
        bizContentMap.put("order_no", orderNo);
        bizContentMap.put("product_code", GlobalConfig.ALIPAY_PRODUCT_CODE);
        request.setBizContent(JsonUtils.writeValueAsString(bizContentMap));
        ZhimaMerchantOrderRentCancelResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            logger.error(e.getMessage());
        }
        if (response == null || !response.isSuccess()) {
            logger.error("信用借还订单取消失败" + orderNo);
            if (response != null) {
                logger.error("错误代码：" + response.getCode() + "错误信息：" + response.getMsg() +
                        "错误子代码" + response.getSubCode() + "错误子信息：" + response.getSubMsg());
            }
        } else {
            //订单取消成功，向用户推送消息
            this.sendCancelCreditOrderMessage(openid, orderid);
        }
    }

    /**
     * 取消订单成功，发送租借失败的通知给用户
     *
     * @param openid  根据openid向用户发送消息
     * @param orderid 该笔订单的订单编号
     */
    private void sendCancelCreditOrderMessage(String openid, String orderid) {
        AlipayClient alipayClient = new DefaultAlipayClient(GlobalConfig.ALIPAY_SERVER_URL, GlobalConfig.ALIPAY_APP_ID, GlobalConfig.ALIPAY_PRIVATE_KEY, GlobalConfig.ALIPAY_FORMAT, GlobalConfig.ALIPAY_CHARSET, GlobalConfig.ALIPAY_ALIPAYPUBLIC_KEY, GlobalConfig.ALIPAY_SIGNTYPE);
        AlipayOpenPublicMessageSingleSendRequest request = new AlipayOpenPublicMessageSingleSendRequest();

        //顶部色条的色值
        String headColor = "#000000";

        //点击消息后承接页的地址为附近充电宝站点列表页，带上return_type的目的是
        //进入附近充电宝站点列表页而不是弹出电池借用成功的弹出框
        String url = GlobalConfig.ALIPAY_NOTIFY_URL + "/loading.html?return_type=test";
        //底部链接描述文字，如“查看详情”
        String actionName = "查看详情";

        //当前文字颜色
        String firstColor = "#000000";
        //模板中占位符的值
        String firstValue = "信用借还订单取消成功，点击详情重新租借";

        //keyword1
        String keyword1Color = "#000000";

        //remark
        String remarkColor = "#32cd32";
        String remarkValue = "信用借还订单取消成功，您可点击详情重新租借。如有疑问，请致电：4006290808";

        Map<String, Object> keyword1 = new LinkedHashMap<>();
        keyword1.put("color", keyword1Color);
        keyword1.put("value", orderid);
        Map<String, Object> first = new LinkedHashMap<>();
        first.put("color", firstColor);
        first.put("value", firstValue);
        Map<String, Object> remark = new LinkedHashMap<>();
        remark.put("color", remarkColor);
        remark.put("value", remarkValue);
        Map<String, Object> context = new LinkedHashMap<>();
        context.put("head_color", headColor);
        context.put("url", url);
        context.put("action_name", actionName);
        context.put("keyword1", keyword1);
        context.put("first", first);
        context.put("remark", remark);
        Map<String, Object> template = new LinkedHashMap<>();
        template.put("template_id", GlobalConfig.ALIPAY_SEND_ERROR_MESSAGE);
        template.put("context", context);
        Map<String, Object> bizContentMap = new LinkedHashMap<>();
        bizContentMap.put("to_user_id", openid);
        bizContentMap.put("template", template);
        request.setBizContent(JsonUtils.writeValueAsString(bizContentMap));
        AlipayOpenPublicMessageSingleSendResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            logger.error(e.getMessage());
        }
        if (response == null || !response.isSuccess()) {
            logger.error("向用户发送借用失败的消息出错");
            if (response != null) {
                logger.error("错误代码：" + response.getCode() + "错误信息：" + response.getMsg() +
                        "错误子代码" + response.getSubCode() + "错误子信息：" + response.getSubMsg());
            }
        }
    }

    /**
     * 退款该笔订单的金额到用户账户，
     * 将订单中将用户使用费用置为0
     * 向用户发送退款成功的消息
     *
     * @param bizUser 待退款用户
     * @param order 退款订单
     */
    private void refundWeixinOrder(BizUser bizUser, TradeOrderLog order) {
        //更新用户可用余额信息
        BigDecimal usablemoney = bizUser.getUsablemoney();
        BigDecimal usefee = order.getUsefee();
        usablemoney = usablemoney.add(usefee);
        bizUser.setUsablemoney(usablemoney);
        bizUserService.save(bizUser);
        //将订单中用户的使用费用置为0
        order.setUsefee(BigDecimal.ZERO);
        this.save(order);
        //向用户发送退款成功的消息
        String openid = bizUser.getOpenid();
        this.sendRefundMessage(openid, usefee);
    }


    /**
     * 向微信用户发送退款消息
     *
     * @param openid
     * @param usefee
     */
    private void sendRefundMessage(String openid, BigDecimal usefee) {

        //如果有可用的message
        Message usableMessage = messageDao.getUsableMessage(openid);
        if (null != usableMessage) {
            //使用form_id推送消息
            this.refundSendTemplate(openid, wxRefundTemplateId, usableMessage, usefee);
        }
    }

    /**
     * 微信退款通知
     *
     * @param openid     向谁推送消息
     * @param templateid 推送消息的模板
     * @param message    获取推送消息用的form_id
     * @param usefee     退款金额
     */
    private void refundSendTemplate(String openid, String templateid, Message message, BigDecimal usefee) {
        //根据具体模板参数组装
        WechatTemplateMsg wechatTemplateMsg = new WechatTemplateMsg();
        wechatTemplateMsg.setTemplate_id(templateid);
        wechatTemplateMsg.setTouser(openid);
        wechatTemplateMsg.setPage("/pages/user/user"); //跳转页面
        wechatTemplateMsg.setForm_id(message.getForm_prepay_id());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String requestTime = format.format(new Date());
        TreeMap<String, TreeMap<String, String>> params = new TreeMap<String, TreeMap<String, String>>();
        //详细内容
//        params.put("first", WechatTemplateMsg.item("您的退款已经退到余额", "#000000"));
        //退款原因
        params.put("keyword1", WechatTemplateMsg.item("系统故障", "#000000"));
        //退款时间
        params.put("keyword2", WechatTemplateMsg.item(requestTime, "#000000"));
        //退款金额
        params.put("keyword3", WechatTemplateMsg.item(usefee + "元", "#000000"));
        //账户余额
//        params.put("keyword4", WechatTemplateMsg.item(usablemoney + "元", "#000000"));
        //备注
        params.put("keyword4", WechatTemplateMsg.item("退款至余额的钱可以在下次充电时使用", "#000000"));

        wechatTemplateMsg.setData(params);
        String data = JsonUtils.writeValueAsString(wechatTemplateMsg);
        //发送请求
        try {
            String token = this.getAccessToken();
            String msgUrl = GlobalConfig.WX_SEND_TEMPLATE_MESSAGE + "?access_token=" + token;
            String msgResult = HttpRequest.sendPost(msgUrl, data);  //发送post请求
            if (StringUtils.isEmpty(msgResult)) {
                logger.info("模板消息发送失败OPENID=: " + openid);
            } else {
                Map<String, Object> msgResultMap = JsonUtils.readValue(msgResult);
                Integer errcode = (Integer) msgResultMap.get("errcode");
                String errmsg = (String) msgResultMap.get("errmsg");
                if (0 == errcode) {
                    logger.info("模板消息发送成功errorCode:{" + errcode + "},errmsg:{" + errmsg + "}");
                } else {
                    logger.info("模板消息发送失败errorCode:{" + errcode + "},errmsg:{" + errmsg + "}");
                }
                //如果此时的剩余使用次数为1 直接删除
                if (message.getNumber() <= 1) {
                    //清除本条数据
                    this.messageDao.deleteMessageById(message.getId());
                    //清除该用户过期数据
                    this.messageDao.deleteMessageByOpenid(openid);
                } else {
                    //更新prepay_id的使用次数
                    message.setLastModifiedBy("SYS:message");
                    this.messageDao.updateMessageNumberById(message.getId(),message.getNumber());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("模板消息发送失败OPENID=: " + openid);
        }
    }

    /**
     * 获取accessToken
     */
    public String getAccessToken() {
        String ACCESS_TOKEN = redisService.getKeyValue("ACCESS_TOKEN");
        if (StringUtils.isEmpty(ACCESS_TOKEN)) {
            String param = "grant_type=client_credential&appid=" + appID + "&secret=" + appSecret;
            try {
                String tokenInfo = HttpRequest.sendGet(GlobalConfig.WX_ACCESS_TOKEN_URL, param);
                Map<String, Object> tokenInfoMap = JsonUtils.readValue(tokenInfo);
                String accessToken = tokenInfoMap.get("access_token").toString();
                Long expiresIn = Long.valueOf(tokenInfoMap.get("expires_in").toString());
                // 将accessToken存入Redis,存放时间为7200秒
                redisService.setKeyValueTimeout("ACCESS_TOKEN", accessToken.trim(), expiresIn);
                return accessToken.trim();
            } catch (Exception e) {
                logger.error(e.getMessage());
                return null;
            }
        } else {
            return ACCESS_TOKEN.trim();
        }
    }


}
