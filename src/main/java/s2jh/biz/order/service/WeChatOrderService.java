package s2jh.biz.order.service;

import lab.s2jh.module.auth.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s2jh.biz.bizUser.entity.BizUser;
import s2jh.biz.feeStrategy.service.FeeStrategyService;
import s2jh.biz.formid.entity.Message;
import s2jh.biz.formid.service.MessageService;
import s2jh.biz.order.entity.TradeOrderLog;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhuhui on 17-8-16.
 */
@Service
public class WeChatOrderService {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private FeeStrategyService feeStrategyService;

    @Autowired
    private WechatMessageService wechatMessageService;

    /**
     * 推送归还成功消息
     *
     * @param lastTime
     * @param useFeeStr
     * @param order
     */
    public void sendReturnSuccessMessage(String lastTime, String useFeeStr, TradeOrderLog order) {
        // 归还时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String returnTime = sdf.format(new Date());
        // 归还地点
        String address = order.getReturnShopName();
        // 订单号
        String orderid = order.getOrderid();
        //获取prepay_id
        BizUser bizUser = order.getBizUser();
        String openid = bizUser.getOpenid();
        Message formMessage = this.wechatMessageService.getFormIdByOpenid(openid);
        if (null != formMessage) {
            //使用prepay_id推送消息
            wechatMessageService.returnBackSendTMessage(formMessage.getOpenid(), formMessage.getForm_prepay_id(), returnTime, address, lastTime, useFeeStr, orderid);
            //减掉prepay_id的使用次数，如果为0 直接删除
            Integer number = formMessage.getNumber();
            if (1 >= number) {
                this.messageService.delete(formMessage);
            } else {
                formMessage.setLastModifiedBy("SYS:ycbManageSystem");
                formMessage.setLastModifiedDate(new Date());
                formMessage.setNumber(number-1);
                this.messageService.save(formMessage);
            }
        } else {
            logger.info("orderId:" + order.getOrderid() + "该条退款消息推送失败！没有可用的form_id了");
        }
    }

}
