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

    public static final String ALIPAY_APP_ID = "2017090508571123";
    public static final String ALIPAY_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCM4kS4HEhBfenXfGGoedkkh/Hw++egEKjTwWzUsqToud1JjPIEp76kCUY+SBoF047AUHSJSKzmyI2FaOALiAjU4CnHmkuyOe5EJvwmzbtFHJd3Lixjjj9eD/VmvyuhJcAJSDYlRPo2Y0iFVw5OASoVPKz3k7Zrd9Y4AtJt7v+TyUvpUs9VxTA7JGRuEb4fGXFGoQHAbmekCTELQqpDvMcbGZxvDNMiQKfaTun+n9xMAmMsv8XEv+gzddmwJXVlOYyYGm46mPAvOCYC+mKiX9n76sX2FDcHL8XPTfVl1vm7poxHjLFdMPyIvkXtvIaP+3AbTsCpNZpVBOeNOyNbU3btAgMBAAECggEAOV93/sY9wrYFQeT8XEDq5g6av7nGVRATwNCXgnMR51ixvl8wvCaX43OHcJuqRU8nZjgdjT7UctOfloHkI64NcqurOAck5nrjBkweCatkpTuEj1t1ZkGqmqLDHEmj+edGskD/E+RoRTffc7XFNI0S1z0zb0tvUYqm6X50taJL4KHQn9/SuIj0AizwJ9YutFlyS7mTGXmrk695npGynIItGZXDcJWRYJPG2NBclBcGlhc9saJSeuzQU/NdF0NhXCEB4GOBHqZaqQSqm2+rPmdJq1XulN80rkrXfmBXK6QWZaR3jVOPrWsBhHSNNGRs+YplbiK6XcJJ/wSKR8ZDMhwjXQKBgQDU7bxGwvwn/ON/JVYULgiEox2w+2Gw7oPSq/65nKvwORz55mNIfIdtqOQsOtjPVHeTyMOx7hEO9kBddqYpaBPdHnYvGgpR0HEoo9AA98p6DH7p7xrfPJR0X5wtTcyrLt2viD2pZWJyUD5sv2Hlg6pnzlDsATpKRi3jOCZrX7W8swKBgQCpYcbLaCIH5Ym0gGRaQY95fr+JGlWEJ+72s6STz1qky7mnaa4eCT+RflLG2oYdu4vPS7xskk8DSG86fpL2x03RdKjiXJ4RfUu6Gi/j7yRLWr/qKHiffwbOyDN9ENMazcpqEVaPj2ujRT8YpF4WmRWteCTurKzrgJ/P8pXdflUN3wKBgQCG6FWY8+N1pomxPkOqp3voJb0jLDK6OnS1HxpWGcjqfBU61FjlTVk1BoQC7gn+WsMklFhBPNf8BdPEI++lXnbva2Rv9WzCZ3G60QdyBY15BL/IkiERfb2FeipmndtmPlxg4c5HrJ/SU7sphZyOIXzvJk4pKaH3+z0JsMLO1yY7dwKBgHXUvi2MgHgL11MJKkabawrzAJ2JA0qPWS6n4+qrifwweTOcUfM1LdKyFTWemUQfzu/jWO4SjgLTpgUEPoqMNXdrd30ZPyEdj8ukIgKnvxDoza9AfkE+Zeb94Mu1I5Z26MQG0bfG5vNK3czvs0B3OFxXwyGCYDzoWsSXlmfiVZrzAoGBAMddIl7Uu96tAkV/Q+GTRsv4pPwS1+2VKkLMTrwX+I3SfTiEmHbMIRX/o5qwoiDIDG6FNqs5aIOKAEluB8UFmLMBhU70hxwryPBDFdb96T+XkextVP62y37qF9uYOhT5OHOP5KDMszN2zVj/DoRx0vKpMYp9jPl+7Mp27jAOGeVZ";
    public static final String ALIPAY_FORMAT = "json";
    public static final String ALIPAY_CHARSET = "UTF-8";
    public static final String ALIPAY_ALIPAYPUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsTxfNRULHDpq8CyAsYNl2Tew9dcqAyt6AnLuBveM1Szf9uEYxyz/2Ut1RAgZCoPx3OT/vimudiXFFLnCTogvEsL5CxlWQuggFxVvsJRqxVWLFkGfVzDT/AgJDMU6Mhh+NpbodLoeMBKdF7j7rCGDYQJEb4yoY0o9A4N2oGE6PxKY5awz+xmGxxKTwffCzeeHpEi7txk5RtnGpgnbng8K9uluEyOyrmY0amLIm9DXAWq7a2Gc5H9lbDQJci600I0PsevOatQ0KI7NVsNVVypN0dqE3BiDI80ZVxLDjRd+cH+O7pAM/ZCQQToNL/WHAI6368nyLOBc2XmBEi/h86i/8wIDAQAB";
    public static final String ALIPAY_SIGNTYPE = "RSA2";
    public static final String ALIPAY_SERVER_URL = "https://openapi.alipay.com/gateway.do";
    public static final String ALIPAY_SEND_BORROW_MESSAGE = "24b75e2d6fb442798d0d8565f02f9a1b";
    public static final String ALIPAY_SEND_ERROR_MESSAGE = "fe6e474648a34949a053cd374cf0cfb5";
    public static final String ALIPAY_SEND_RETURN_MESSAGE = "9bc0cc74b6fc4949a7fcd9736f812469";
    public static final String ALIPAY_PRODUCT_CODE = "w1010100000000002858";
    //回调域名，例如：http://www.duxinyuan.top
    public static final String ALIPAY_NOTIFY_URL = "https://www.x.yunchongba.com";
}
