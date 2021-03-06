package s2jh.biz.bizUser.web;

import com.fasterxml.jackson.annotation.JsonView;
import lab.s2jh.core.annotation.MenuData;
import lab.s2jh.core.annotation.MetaData;
import lab.s2jh.core.service.BaseService;
import lab.s2jh.core.web.BaseController;
import lab.s2jh.core.web.json.JsonViews;
import lab.s2jh.core.web.view.OperationResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import s2jh.biz.bizUser.entity.BizUserInfo;
import s2jh.biz.bizUser.service.BizUserInfoService;

import javax.servlet.http.HttpServletRequest;

@MetaData("客户详情管理")
@Controller
@RequestMapping(value = "/admin/bizUser/biz-user-info")
public class BizUserInfoController extends BaseController<BizUserInfo, Long> {

    @Autowired
    private BizUserInfoService bizUserInfoService;

    @Override
    protected BaseService<BizUserInfo, Long> getEntityService() {
        return bizUserInfoService;
    }

    @ModelAttribute
    public void prepareModel(HttpServletRequest request, Model model, @RequestParam(value = "id", required = false) Long id) {
        super.initPrepareModel(request, model, id);
    }

    @MenuData("业务模块:客户详情")
    @RequiresPermissions("业务模块:客户详情")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {
        return "admin/bizUser/bizUserInfo-index";
    }

    @RequiresPermissions("业务模块:客户详情")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @JsonView(JsonViews.Admin.class)
    public Page<BizUserInfo> findByPage(HttpServletRequest request) {
        return super.findByPage(BizUserInfo.class, request);
    }

    @RequestMapping(value = "/edit-tabs", method = RequestMethod.GET)
    public String editTabs(HttpServletRequest request) {
        return "admin/bizUser/bizUserInfo-inputTabs";
    }

    @RequiresPermissions("业务模块:客户详情")
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editShow(Model model) {
        return "admin/bizUser/bizUserInfo-inputBasic";
    }

    @RequiresPermissions("业务模块:客户详情")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public OperationResult editSave(@ModelAttribute("entity") BizUserInfo entity, Model model) {
        return super.editSave(entity);
    }

    @RequiresPermissions("业务模块:客户详情")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public OperationResult delete(@RequestParam("ids") Long... ids) {
        return super.delete(ids);
    }
}