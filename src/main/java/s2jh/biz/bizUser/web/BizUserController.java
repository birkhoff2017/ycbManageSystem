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
import s2jh.biz.bizUser.entity.BizUser;
import s2jh.biz.bizUser.service.BizUserService;

import javax.servlet.http.HttpServletRequest;

@MetaData("客户账户管理")
@Controller
@RequestMapping(value = "/admin/bizUser/biz-user")
public class BizUserController extends BaseController<BizUser, Long> {

    @Autowired
    private BizUserService bizUserService;

    @Override
    protected BaseService<BizUser, Long> getEntityService() {
        return bizUserService;
    }

    @ModelAttribute
    public void prepareModel(HttpServletRequest request, Model model, @RequestParam(value = "id", required = false) Long id) {
        super.initPrepareModel(request, model, id);
    }

    @MenuData("业务模块:客户账户")
    @RequiresPermissions("业务模块:客户账户")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {
        return "admin/bizUser/bizUser-index";
    }

    @RequiresPermissions("业务模块:客户账户")
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

    @RequiresPermissions("业务模块:客户账户")
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editShow(Model model) {
        return "admin/bizUser/bizUser-inputBasic";
    }

    @RequiresPermissions("业务模块:客户账户")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public OperationResult editSave(@ModelAttribute("entity") BizUser entity, Model model) {
        return super.editSave(entity);
    }

    @RequiresPermissions("业务模块:客户账户")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public OperationResult delete(@RequestParam("ids") Long... ids) {
        return super.delete(ids);
    }

}