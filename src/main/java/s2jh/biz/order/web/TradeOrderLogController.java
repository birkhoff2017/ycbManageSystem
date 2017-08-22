package s2jh.biz.order.web;

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
import s2jh.biz.order.entity.TradeOrderLog;
import s2jh.biz.order.service.TradeOrderLogService;

import javax.servlet.http.HttpServletRequest;

@MetaData("业务模块:订单表管理")
@Controller
@RequestMapping(value = "/admin/order/trade-order-log")
public class TradeOrderLogController extends BaseController<TradeOrderLog, Long> {

    @Autowired
    private TradeOrderLogService tradeOrderLogService;

    @Override
    protected BaseService<TradeOrderLog, Long> getEntityService() {
        return tradeOrderLogService;
    }

    @ModelAttribute
    public void prepareModel(HttpServletRequest request, Model model, @RequestParam(value = "id", required = false) Long id) {
        super.initPrepareModel(request, model, id);
    }

    @MenuData("业务模块:订单表")
    @RequiresPermissions("业务模块:订单表")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {
        return "admin/order/tradeOrderLog-index";
    }

    @RequiresPermissions("业务模块:订单表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @JsonView(JsonViews.Admin.class)
    public Page<TradeOrderLog> findByPage(HttpServletRequest request) {
        return super.findByPage(TradeOrderLog.class, request);
    }

    @RequestMapping(value = "/edit-tabs", method = RequestMethod.GET)
    public String editTabs(HttpServletRequest request) {
        return "admin/order/tradeOrderLog-inputTabs";
    }

    @RequiresPermissions("业务模块:订单表")
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editShow(Model model) {
        return "admin/order/tradeOrderLog-inputBasic";
    }

    @RequiresPermissions("业务模块:订单表")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public OperationResult editSave(@ModelAttribute("entity") TradeOrderLog entity, Model model) {
        return super.editSave(entity);
    }

    @RequiresPermissions("业务模块:订单表")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public OperationResult delete(@RequestParam("ids") Long... ids) {
        return super.delete(ids);
    }
}