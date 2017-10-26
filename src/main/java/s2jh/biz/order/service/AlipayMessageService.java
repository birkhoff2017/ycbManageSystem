package s2jh.biz.order.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayOpenPublicMessageSingleSendRequest;
import com.alipay.api.response.AlipayOpenPublicMessageSingleSendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import s2jh.biz.bizUser.entity.BizUser;
import s2jh.biz.constant.GlobalConfig;
import s2jh.biz.order.entity.TradeOrderLog;
import s2jh.biz.util.JsonUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Huo on 2017/9/21.
 * <p>
 * 用于在用户借用电池，归还电池，借出失败时向用户发送通知
 */
@Service
public class AlipayMessageService {

    private static final Logger logger = LoggerFactory.getLogger(AlipayMessageService.class);

    /**
     * 用户归还成功后，调用发送消息的接口给用户发送归还成功的通知
     *
     * @param lastTime  用户借用时长
     * @param useFeeStr 费用
     * @param order     订单
     */
    public void sendReturnMessage(String lastTime, String useFeeStr, TradeOrderLog order) {
        AlipayClient alipayClient = new DefaultAlipayClient(GlobalConfig.ALIPAY_SERVER_URL, GlobalConfig.ALIPAY_APP_ID, GlobalConfig.ALIPAY_PRIVATE_KEY, GlobalConfig.ALIPAY_FORMAT, GlobalConfig.ALIPAY_CHARSET, GlobalConfig.ALIPAY_ALIPAYPUBLIC_KEY, GlobalConfig.ALIPAY_SIGNTYPE);
        AlipayOpenPublicMessageSingleSendRequest request = new AlipayOpenPublicMessageSingleSendRequest();

        //返回给那个用户的支付宝用户编号
        BizUser bizUser = order.getBizUser();
        String openid = bizUser.getOpenid();

        //顶部色条的色值
        String headColor = "#000000";

        //点击消息后承接页的地址
        String url = GlobalConfig.ALIPAY_NOTIFY_URL + "/oauth/userInfoOauth";

        //底部链接描述文字，如“查看详情”
        String actionName = "查看详情";

        //当前文字颜色
        String firstColor = "#000000";
        //模板中占位符的值
        String firstValue = "你已成功归还云充吧充电宝。";
        //归还地点
        //keyword1
        String keyword1Color = "#000000";
        String keyword1Value = order.getReturnShopName();
        //归还时间
        //keyword2
        String keyword2Color = "#000000";
        // 归还时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String keyword2Value = "";
        try {
            keyword2Value = sdf.format(order.getReturnTime());
        }catch (Exception e){
            keyword2Value = sdf.format(new Date());
        }
        //租用时长
        //keyword3
        String keyword3Color = "#000000";

        //订单编号
        //keyword4
        String keyword4Color = "#000000";
        String keyword4Value = order.getOrderid();

        //remark
        String remarkColor = "#32cd32";
        String remarkValue = "此次租借产生费用" + useFeeStr + "元。如有疑问，请致电400-629-0808";

        Map<String, Object> keyword1 = new LinkedHashMap<>();
        keyword1.put("color", keyword1Color);
        keyword1.put("value", keyword1Value);
        Map<String, Object> keyword2 = new LinkedHashMap<>();
        keyword2.put("color", keyword2Color);
        keyword2.put("value", keyword2Value);
        Map<String, Object> keyword3 = new LinkedHashMap<>();
        keyword3.put("color", keyword3Color);
        keyword3.put("value", lastTime);
        Map<String, Object> keyword4 = new LinkedHashMap<>();
        keyword4.put("color", keyword4Color);
        keyword4.put("value", keyword4Value);
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
        context.put("keyword2", keyword2);
        context.put("keyword3", keyword3);
        context.put("keyword4", keyword4);
        context.put("first", first);
        context.put("remark", remark);
        Map<String, Object> template = new LinkedHashMap<>();
        template.put("template_id", GlobalConfig.ALIPAY_SEND_RETURN_MESSAGE);
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
            logger.error("向用户发送归还成功的消息失败");
            if (response != null) {
                logger.error("错误代码：" + response.getCode() + "错误信息：" + response.getMsg() +
                        "错误子代码" + response.getSubCode() + "错误子信息：" + response.getSubMsg());
            }
        }
    }
}
