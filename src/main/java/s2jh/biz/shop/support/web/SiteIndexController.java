package s2jh.biz.shop.support.web;

import com.google.common.collect.Maps;
import lab.s2jh.core.exception.ServiceException;
import lab.s2jh.core.service.BaseService;
import lab.s2jh.core.util.HttpClientUtils;
import lab.s2jh.core.util.ImageUtils;
import lab.s2jh.core.util.JsonUtils;
import lab.s2jh.core.util.WXPayUtil;
import lab.s2jh.core.web.BaseController;
import lab.s2jh.core.web.filter.WebAppContextInitFilter;
import lab.s2jh.core.web.util.ServletUtils;
import lab.s2jh.core.web.view.OperationResult;
import lab.s2jh.module.auth.entity.User;
import lab.s2jh.module.auth.entity.User.AuthTypeEnum;
import lab.s2jh.module.auth.service.UserService;
import lab.s2jh.module.sys.service.SmsVerifyCodeService;
import lab.s2jh.support.service.DynamicConfigService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import s2jh.biz.shop.entity.SiteUser;
import s2jh.biz.shop.service.SiteUserService;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Controller
@RequestMapping(value = "/w")
public class SiteIndexController extends BaseController<SiteUser, Long> {

    private final Logger logger = LoggerFactory.getLogger(SiteIndexController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private SiteUserService siteUserService;

    @Autowired
    private SmsVerifyCodeService smsVerifyCodeService;

    @Autowired
    private DynamicConfigService dynamicConfigService;

    @Override
    protected BaseService<SiteUser, Long> getEntityService() {
        return siteUserService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String wwwIndex(Model model) {
        return "w/index";
    }

    @RequestMapping(value = "/password/reset", method = RequestMethod.GET)
    public String restPasswordShow(Model model) {
        return "w/password-reset";
    }

    @RequestMapping(value = "/password/reset", method = RequestMethod.POST)
    @ResponseBody
    public OperationResult passwordResetSmsValidate(HttpServletRequest request, SiteUser entity, Model model, @RequestParam("mobile") String mobile,
                                                    @RequestParam("smsCode") String smsCode, @RequestParam(value = "newpasswd", required = false) String newpasswd) {
        if (smsVerifyCodeService.verifySmsCode(request, mobile, smsCode)) {
            User user = userService.findByAuthTypeAndAuthUid(AuthTypeEnum.SYS, mobile);
            if (user == null) {
                return OperationResult.buildFailureResult("号码尚未注册", "NoUser");
            }
            if (StringUtils.isBlank(newpasswd)) {
                return OperationResult.buildSuccessResult("短信验证码校验成功", "SmsOK");
            } else {
                //更新密码失效日期为6个月后
                user.setCredentialsExpireTime(new DateTime().plusMonths(6).toDate());
                userService.save(user, newpasswd);
                return OperationResult.buildSuccessResult("密码重置成功，您可以马上使用新设定密码登录系统啦", "ResetOK");
            }
        } else {
            return OperationResult.buildFailureResult("短信验证码不正确");
        }
    }

    @RequestMapping(value = "/image/upload", method = RequestMethod.GET)
    public String imageUploadShow() {
        return "w/imageUpload";
    }

    private static final String TEMP_PHOTO_FILE_PATH = "/temp/photo/";

    @RequestMapping(value = "/image/upload/temp", method = RequestMethod.POST)
    @ResponseBody
    public OperationResult imageUploadTemp(@RequestParam("photo") CommonsMultipartFile photo, HttpServletRequest request) {
        if (photo != null && !photo.isEmpty()) {
            try {
                String rootDir = WebAppContextInitFilter.getInitedWebContextRealPath();
                String fileName = UUID.randomUUID() + "_" + photo.getOriginalFilename();
                File dir = new File(rootDir + TEMP_PHOTO_FILE_PATH);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                String photoFilePath = TEMP_PHOTO_FILE_PATH + fileName;
                logger.debug("Saving file: {}", rootDir + photoFilePath);
                File photoFile = new File(rootDir + photoFilePath);
                photo.transferTo(photoFile);

                BufferedImage bi = ImageIO.read(photoFile);
                int srcWidth = bi.getWidth(); // 源图宽度  
                int srcHeight = bi.getHeight(); // 源图高度  

                Map<String, Object> userdata = Maps.newHashMap();
                userdata.put("width", srcWidth);
                userdata.put("height", srcHeight);
                userdata.put("src", photoFilePath);
                return OperationResult.buildSuccessResult("图片上传成功", userdata);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return OperationResult.buildFailureResult("图片上传失败");
    }

    @RequestMapping(value = "/image/crop", method = RequestMethod.POST)
    @ResponseBody
    public OperationResult imageCrop(HttpServletRequest request, @RequestParam("bigImage") String bigImage,
                                     @RequestParam(value = "x", required = false) Integer x, @RequestParam(value = "y", required = false) Integer y,
                                     @RequestParam(value = "w", required = false) Integer w, @RequestParam(value = "h", required = false) Integer h,
                                     @RequestParam(value = "size", required = false) Integer size) throws IOException {
        try {
            String rootDir = WebAppContextInitFilter.getInitedWebContextRealPath();
            String bigImagePath = rootDir + bigImage;
            //判断是否需要先进行裁剪处理
            if (x != null && w != null && w > 0) {
                //裁剪图片
                ImageUtils.cutImage(bigImagePath, bigImagePath, x, y, w, h);
                if (size != null) {
                    //缩放到统一大小
                    ImageUtils.zoomImage(bigImagePath, bigImagePath, size, size);
                }
            }
            File photoFile = new File(bigImagePath);
            String path = ServletUtils.writeUploadFile(new FileInputStream(photoFile), photoFile.getName(), photoFile.length());
            if (StringUtils.isNotBlank(path)) {
                return OperationResult.buildSuccessResult("图片提交成功", path);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return OperationResult.buildFailureResult("图片处理失败");
    }

    @RequestMapping(value = "/image/upload/kind-editor", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> imageUpload(HttpServletRequest request, @RequestParam("imgFile") CommonsMultipartFile fileUpload) {
        Map<String, Object> retMap = Maps.newHashMap();
        try {
            if (fileUpload != null && !fileUpload.isEmpty()) {
                String path = ServletUtils.writeUploadFile(fileUpload.getInputStream(), fileUpload.getOriginalFilename(), fileUpload.getSize());
                if (StringUtils.isNotBlank(path)) {
                    String imgViewUrlPrefix = ServletUtils.getReadFileUrlPrefix();
                    retMap.put("error", 0);
                    retMap.put("url", imgViewUrlPrefix + path);
                    retMap.put("path", path);
                    return retMap;
                }
            }
        } catch (IOException e) {
            throw new ServiceException("Upload file error", e);
        }
        retMap.put("error", 1);
        retMap.put("message", "图片处理失败");
        return retMap;
    }

    @RequestMapping(value = "/file/upload/single", method = RequestMethod.POST)
    @ResponseBody
    public OperationResult singleFileUpload(HttpServletRequest request, @RequestParam("fileUpload") CommonsMultipartFile fileUpload) {
        try {
            if (fileUpload != null && !fileUpload.isEmpty()) {
                String path = ServletUtils.writeUploadFile(fileUpload.getInputStream(), fileUpload.getOriginalFilename(), fileUpload.getSize());
                if (StringUtils.isNotBlank(path)) {
                    return OperationResult.buildSuccessResult("文件提交成功", path);
                }
            }
        } catch (IOException e) {
            throw new ServiceException("Upload file error", e);
        }
        return OperationResult.buildFailureResult("文件处理失败");
    }

    // 根据用户code获取用户openid和session_key
    @RequestMapping(value = "/getUserOpenId", method = RequestMethod.GET)
    @ResponseBody
    public String getUserOpenId(@RequestParam String code) {
//        String param = "appid=wx66b477313e0c6dad&secret=a5affc5f337a86880a29a09ca62b56ae&js_code" + code + "&grant_type=authorization_code";
        Map<String, Object> param = new HashedMap();
        param.put("appid", "wx66b477313e0c6dad");
        param.put("secret", "a5affc5f337a86880a29a09ca62b56ae");
        param.put("js_code", code);
        param.put("grant_type", "authorization_code");
        return HttpClientUtils.doGet("https://api.weixin.qq.com/sns/jscode2session", param);
    }

    // 用户支付
    @RequestMapping(value = "/payment", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> payment(@RequestParam String openid) throws UnsupportedEncodingException {
        Map reqMap = new HashedMap();
        reqMap.put("appid", "wx66b477313e0c6dad");//小程序id
        reqMap.put("mch_id", "1230000109");//商户号
        reqMap.put("nonce_str", WXPayUtil.getNonce_str());
        reqMap.put("body", new String("body".getBytes("UTF-8")));
        reqMap.put("openid", openid);
        reqMap.put("out_trade_no", "1111");//订单号
        reqMap.put("total_fee", 1); //订单总金额，单位为分
        reqMap.put("spbill_create_ip", "192.168.0.1"); //用户端ip
        reqMap.put("notify_url", "192.168.0.1"); //通知地址
        reqMap.put("trade_type", "JSAPI"); //trade_type=JSAPI时（即公众号支付），此参数必传，此参数为微信用户在商户对应appid下的唯一标识
        String reqStr = WXPayUtil.map2Xml(reqMap);
        String resultXml = HttpClientUtils.doPost("https://api.mch.weixin.qq.com/pay/unifiedorder", reqStr);

        //解析微信返回串 如果状态成功 则返回给前端
        //成功
        Map<String, Object> resultMap = new TreeMap<>(
                new Comparator<String>() {
                    public int compare(String obj1, String obj2) {
                        // 升序排序
                        return obj1.compareTo(obj2);
                    }
                });
        resultMap.put("appId", System.getenv("appid"));
        resultMap.put("nonceStr", WXPayUtil.getNonceStr(resultXml));//解析随机字符串
        resultMap.put("package", "prepay_id=" + WXPayUtil.getPrepayId(resultXml));
        resultMap.put("signType", "MD5");
        resultMap.put("timeStamp", String.valueOf((System.currentTimeMillis() / 1000)));//时间戳
        String paySign = WXPayUtil.getSign(resultMap);
        resultMap.put("paySign", paySign);
        return resultMap;
    }

    //获取支付信息
    @RequestMapping("/getPayInformation")
    public static Map<String, Object> getPayInformation(@RequestParam("orderId") String orderId
    ) throws UnsupportedEncodingException, DocumentException {
        Map<String, Object> reqMap = new TreeMap<String, Object>(
                new Comparator<String>() {
                    public int compare(String obj1, String obj2) {
                        // 升序排序
                        return obj1.compareTo(obj2);
                    }
                });
//        String authDataJson = JSONArray.toJSONString(AVUser.getCurrentUser().get("authData"));
//        JSONObject jsonObject = JSON.parseObject(authDataJson);
//        jsonObject.get("lc_weapp");
//        JSONObject j2 = JSON.parseObject(jsonObject.get("lc_weapp").toString());
//        String openId = (String) j2.get("openid");
//        AVQuery<Order> query = AVObject.getQuery(Order.class);
//        Order order = query.get(orderId);
        reqMap.put("appid", "");//小程序id
        reqMap.put("mch_id", "");//商户号
        reqMap.put("nonce_str", WXPayUtil.getNonce_str());
        reqMap.put("body", new String("body".getBytes("UTF-8")));
        reqMap.put("openid", "");
        reqMap.put("out_trade_no", "");//订单号
        reqMap.put("total_fee", 1); //订单总金额，单位为分
        reqMap.put("spbill_create_ip", "192.168.0.1"); //用户端ip
        reqMap.put("notify_url", System.getenv("notify_url")); //通知地址
        reqMap.put("trade_type", System.getenv("trade_type")); //trade_type=JSAPI时（即公众号支付），此参数必传，此参数为微信用户在商户对应appid下的唯一标识
        String reqStr = WXPayUtil.map2Xml(reqMap);
        String resultXml = HttpClientUtils.doPost("", reqMap);
        System.out.println("微信请求返回:" + resultXml);
        //解析微信返回串 如果状态成功 则返回给前端
        if (WXPayUtil.getReturnCode(resultXml) != null && WXPayUtil.getReturnCode(resultXml).equals("SUCCESS")) {
            //成功
            Map<String, Object> resultMap = new TreeMap<>(
                    new Comparator<String>() {
                        public int compare(String obj1, String obj2) {
                            // 升序排序
                            return obj1.compareTo(obj2);
                        }
                    });
            resultMap.put("appId", System.getenv("appid"));
            resultMap.put("nonceStr", WXPayUtil.getNonceStr(resultXml));//解析随机字符串
            resultMap.put("package", "prepay_id=" + WXPayUtil.getPrepayId(resultXml));
            resultMap.put("signType", "MD5");
            resultMap.put("timeStamp", String.valueOf((System.currentTimeMillis() / 1000)));//时间戳
            String paySign = WXPayUtil.getSign(resultMap);
            resultMap.put("paySign", paySign);
            return resultMap;
        }
        return reqMap;
    }

    // 小程序接口测试接口
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ResponseBody
    public String getTest() {
        Map<String, Object> shopsMap = new HashedMap();
        List<Map> shops = new ArrayList<>();
        Map<String, Object> shop = new HashedMap();
        shop.put("id", "123");
        shop.put("name","商铺11111");
        shop.put("address", "朝阳");
        shop.put("longitude", "100.242081");
        shop.put("latitude", "26.874501");
        Map<String, Object> shop1 = new HashedMap();
        shop1.put("id", "12345");
        shop1.put("name","商铺22222");
        shop1.put("address", "朝阳1");
        shop1.put("longitude", "100.242081");
        shop1.put("latitude", "26.874501");
        shops.add(shop);
        shops.add(shop1);
        shopsMap.put("shops", shops);
        return JsonUtils.writeValueAsString(shopsMap);
    }
}
