package s2jh.biz.bizUser.web;

import com.fasterxml.jackson.annotation.JsonView;
import lab.s2jh.core.annotation.MenuData;
import lab.s2jh.core.annotation.MetaData;
import lab.s2jh.core.service.BaseService;
import lab.s2jh.core.util.JsonUtils;
import lab.s2jh.core.web.BaseController;
import lab.s2jh.core.web.json.JsonViews;
import lab.s2jh.core.web.view.OperationResult;
import lab.s2jh.module.sys.service.DataDictService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import s2jh.biz.bizUser.entity.BizUser;
import s2jh.biz.bizUser.service.BizUserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@MetaData("客户管理")
@Controller
@RequestMapping(value = "/admin/bizUser/biz-user")
public class BizUserController extends BaseController<BizUser, Long> {

    @Autowired
    private BizUserService bizUserService;

    @Autowired
    private DataDictService dataDictService;

    @Autowired
    private RedisTemplate<String, String> template;

    private ValueOperations<String, String> operations;

    @Override
    protected BaseService<BizUser, Long> getEntityService() {
        return bizUserService;
    }

    @ModelAttribute
    public void prepareModel(HttpServletRequest request, Model model, @RequestParam(value = "id", required = false) Long id) {
        super.initPrepareModel(request, model, id);
    }

    @MenuData("业务模块:客户管理")
    @RequiresPermissions("业务模块:客户管理")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {
        return "admin/bizUser/bizUser-index";
    }

    @RequiresPermissions("客户管理")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @JsonView(JsonViews.Admin.class)
    public Page<BizUser> findByPage(HttpServletRequest request) {
        return super.findByPage(BizUser.class, request);
    }

    @RequestMapping(value = "/edit-tabs", method = RequestMethod.GET)
    public String editTabs(HttpServletRequest request) {
        return "admin/bizUser/bizUser-inputTabs";
    }

    @RequiresPermissions("客户管理")
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editShow(Model model) {
        return "admin/bizUser/bizUser-inputBasic";
    }

    @RequiresPermissions("客户管理")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public OperationResult editSave(@ModelAttribute("entity") BizUser entity, Model model) {
        return super.editSave(entity);
    }

    @RequiresPermissions("客户管理")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public OperationResult delete(@RequestParam("ids") Long... ids) {
        return super.delete(ids);
    }

    @MetaData("登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestParam("code") String code,
                        @RequestParam("encryptedData") String encryptedData,
                        @RequestParam("iv") String iv) {
        template.setValueSerializer(new JacksonJsonRedisSerializer<>(String.class));
        operations = template.opsForValue();
        Map<String, Object> bacMap = new HashedMap();
        Map<String, Object> data = new HashedMap();
        data.put("session", "dsagvafasdadsads");
        bacMap.put("data", data);
        bacMap.put("code", 0);
        bacMap.put("msg", "成功");
        // 通过微信接口获取session
        // 根据openid检索数据库，不存在新建用户
        // 设置session 时长5分钟过期
        operations.set("session", "test", 5, TimeUnit.MINUTES);
        return JsonUtils.writeValueAsString(bacMap);
    }
}