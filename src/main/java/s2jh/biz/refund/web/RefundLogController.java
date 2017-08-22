package s2jh.biz.refund.web;

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
import s2jh.biz.refund.entity.RefundLog;
import s2jh.biz.refund.service.RefundLogService;

import javax.servlet.http.HttpServletRequest;

@MetaData("业务模块:提现记录管理")
@Controller
@RequestMapping(value = "/admin/refund/refund-log")
public class RefundLogController extends BaseController<RefundLog, Long> {

    @Autowired
    private RefundLogService refundLogService;

    @Override
    protected BaseService<RefundLog, Long> getEntityService() {
        return refundLogService;
    }

    @ModelAttribute
    public void prepareModel(HttpServletRequest request, Model model, @RequestParam(value = "id", required = false) Long id) {
        super.initPrepareModel(request, model, id);
    }

    @MenuData("业务模块:提现记录")
    @RequiresPermissions("业务模块:提现记录")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {
        return "admin/refund/refundLog-index";
    }

    @RequiresPermissions("业务模块:提现记录")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @JsonView(JsonViews.Admin.class)
    public Page<RefundLog> findByPage(HttpServletRequest request) {
        return super.findByPage(RefundLog.class, request);
    }

    @RequestMapping(value = "/edit-tabs", method = RequestMethod.GET)
    public String editTabs(HttpServletRequest request) {
        return "admin/refund/refundLog-inputTabs";
    }

    @RequiresPermissions("业务模块:提现记录")
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editShow(Model model) {
        return "admin/refund/refundLog-inputBasic";
    }

    @RequiresPermissions("业务模块:提现记录")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public OperationResult editSave(@ModelAttribute("entity") RefundLog entity, Model model) {
        return super.editSave(entity);
    }

    @RequiresPermissions("业务模块:提现记录")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public OperationResult delete(@RequestParam("ids") Long... ids) {
        return super.delete(ids);
    }
}