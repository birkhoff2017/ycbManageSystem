package s2jh.biz.constant;

/**
 * duxinyaun
 */
public class GlobalConfig {

    public GlobalConfig() {
    }

    public static final String WX_CREATORDER_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";

    public static final String WX_OPENID_URL = "https://api.weixin.qq.com/sns/jscode2session";

    public static final String WX_UNIFIEDORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    public static final String APICLIENT_CERT_P12 = "apiclient_cert.p12";

    public static final String WX_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";

    //发送模板消息接口
    public static final String WX_SEND_TEMPLATE_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send";

    public static final String ALIPAY_APP_ID = "";
    public static final String ALIPAY_PRIVATE_KEY = "";
    public static final String ALIPAY_FORMAT = "json";
    public static final String ALIPAY_CHARSET = "UTF-8";
    public static final String ALIPAY_ALIPAYPUBLIC_KEY = "";
    public static final String ALIPAY_SIGNTYPE = "RSA2";
    public static final String ALIPAY_SERVER_URL = "https://openapi.alipay.com/gateway.do";
    public static final String ALIPAY_SEND_BORROW_MESSAGE = "24b75e2d6fb442798d0d8565f02f9a1b";
    public static final String ALIPAY_SEND_ERROR_MESSAGE = "fe6e474648a34949a053cd374cf0cfb5";
    public static final String ALIPAY_SEND_RETURN_MESSAGE = "9bc0cc74b6fc4949a7fcd9736f812469";
    public static final String ALIPAY_PRODUCT_CODE = "w1010100000000002858";
    //回调域名，例如：http://www.duxinyuan.top
    public static final String ALIPAY_NOTIFY_URL = "https://www.x.yunchongba.com";
}
