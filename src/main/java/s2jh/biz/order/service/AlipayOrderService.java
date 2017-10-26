package s2jh.biz.order.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.ZhimaMerchantOrderRentCompleteRequest;
import com.alipay.api.response.ZhimaMerchantOrderRentCompleteResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import s2jh.biz.constant.GlobalConfig;
import s2jh.biz.order.entity.TradeOrderLog;
import s2jh.biz.util.JsonUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Huo on 2017/9/22.
 */
@Service
public class AlipayOrderService {

    private final Logger logger = LoggerFactory.getLogger(AlipayOrderService.class);

    /**
     * 在用户归还电池后，调用支付宝的完结订单接口完结订单
     *
     * @param order 订单
     * @param usefee
     */
    public boolean completeOrder(TradeOrderLog order, BigDecimal usefee,String payAmountType) {
        AlipayClient alipayClient = new DefaultAlipayClient(GlobalConfig.ALIPAY_SERVER_URL, GlobalConfig.ALIPAY_APP_ID, GlobalConfig.ALIPAY_PRIVATE_KEY, GlobalConfig.ALIPAY_FORMAT, GlobalConfig.ALIPAY_CHARSET, GlobalConfig.ALIPAY_ALIPAYPUBLIC_KEY, GlobalConfig.ALIPAY_SIGNTYPE);
        ZhimaMerchantOrderRentCompleteRequest request = new ZhimaMerchantOrderRentCompleteRequest();
        //获得信用借还订单支付宝的订单编号
        String orderNo = order.getOrderNo();
        //物品归还时间
        String restoreTime = "";
        try {
            restoreTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getReturnTime());
        }catch (Exception e){
            restoreTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        }
        //String payAmountType = "RENT";
        //payAmount 需要支付的金额
        String payAmount = usefee.toString();
        //restoreShopName 物品归还门店名称
        String restoreShopName = order.getReturnShopName();

        Map<String, Object> bizContentMap = new LinkedHashMap<>();
        bizContentMap.put("order_no", orderNo);
        bizContentMap.put("product_code", GlobalConfig.ALIPAY_PRODUCT_CODE);
        bizContentMap.put("restore_time", restoreTime);
        bizContentMap.put("pay_amount_type", payAmountType);
        bizContentMap.put("pay_amount", payAmount);
        bizContentMap.put("restore_shop_name", restoreShopName);

        request.setBizContent(JsonUtils.writeValueAsString(bizContentMap));
        ZhimaMerchantOrderRentCompleteResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            logger.error(e.getMessage());
        }
        if (response == null || !response.isSuccess()) {
            logger.error("信用借还订单完结失败" + orderNo);
            if (response != null) {
                logger.error("错误代码：" + response.getCode() + "错误信息：" + response.getMsg() +
                        "错误子代码" + response.getSubCode() + "错误子信息：" + response.getSubMsg());
            }
            return false;
        }
        return true;
    }
}
