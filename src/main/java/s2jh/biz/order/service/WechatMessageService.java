package s2jh.biz.order.service;

import lab.s2jh.core.pagination.GroupPropertyFilter;
import lab.s2jh.core.pagination.PropertyFilter;
import lab.s2jh.core.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import s2jh.biz.cache.RedisService;
import s2jh.biz.constant.GlobalConfig;
import s2jh.biz.formid.entity.Message;
import s2jh.biz.formid.service.MessageService;
import s2jh.biz.order.entity.WechatTemplateMsg;
import s2jh.biz.util.HttpRequest;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Huo on 2017/10/21.
 */
@Service
public class WechatMessageService {
    private final Logger logger = LoggerFactory.getLogger(WechatMessageService.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private RedisService redisService;

    @Value("${appID}")
    private String appID;

    @Value("${appSecret}")
    private String appSecret;



    /**
     * 归还成功 推送消息
     *
     * @param openid
     * @param formid
     * @param returnTime
     * @param address
     * @param lastTime
     * @param useFeeStr
     * @param orderid
     */
    public void returnBackSendTMessage(String openid, String formid, String returnTime, String address, String lastTime, String useFeeStr, String orderid) {
        TreeMap<String, TreeMap<String, String>> params = new TreeMap<String, TreeMap<String, String>>();
        //根据具体模板参数组装
        WechatTemplateMsg wechatTemplateMsg = new WechatTemplateMsg();
        wechatTemplateMsg.setTemplate_id(GlobalConfig.TEMPLATE_RENTURN_BACK_SUCCESS);
        wechatTemplateMsg.setTouser(openid);
        wechatTemplateMsg.setPage("/pages/user/user");
        wechatTemplateMsg.setForm_id(formid);
        params.put("keyword1", WechatTemplateMsg.item(returnTime, "#000000"));//归还时间
        params.put("keyword2", WechatTemplateMsg.item(address, "#000000"));//归还地点
        params.put("keyword3", WechatTemplateMsg.item(lastTime, "#000000"));//租借时长
        params.put("keyword4", WechatTemplateMsg.item(useFeeStr, "#000000"));//产生费用
        params.put("keyword5", WechatTemplateMsg.item(orderid, "#000000"));//订单号
        params.put("keyword6", WechatTemplateMsg.item("", "#000000"));//备注
        wechatTemplateMsg.setData(params);
        String data = JsonUtils.writeValueAsString(wechatTemplateMsg);
        //发送请求
        try {
            String token = this.getAccessToken();
            String msgUrl = GlobalConfig.WX_SEND_TEMPLATE_MESSAGE + "?access_token=" + token;
            String msgResult = HttpRequest.sendPost(msgUrl, data);  //发送post请求
            Map<String, Object> msgResultMap = JsonUtils.readValue(msgResult);
            Integer errcode = (Integer) msgResultMap.get("errcode");
            String errmsg = (String) msgResultMap.get("errmsg");
            if (0 == errcode) {
                //result = true;
                logger.info("模板消息发送成功errorCode:{" + errcode + "},errmsg:{" + errmsg + "}");
            } else {
                logger.info("模板消息发送失败errorCode:{" + errcode + "},errmsg:{" + errmsg + "}");
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    /**
     * 根据用户的openid获取到formid
     *
     * @param openid 用户的openid
     * @return
     */
    public Message getFormIdByOpenid(String openid) {
        GroupPropertyFilter groupPropertyFilter = GroupPropertyFilter.buildDefaultAndGroupFilter();
        groupPropertyFilter.forceAnd(new PropertyFilter(PropertyFilter.MatchType.EQ, "openid", openid));
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        List<Message> messageList = this.messageService.findByFilters(groupPropertyFilter, sort, 1);

        if (null == messageList || messageList.size() == 0) {
            return null;
        }

        return messageList.get(0);
    }
}
