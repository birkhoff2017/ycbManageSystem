package s2jh.biz.order.web;

import com.fasterxml.jackson.annotation.JsonView;
import lab.s2jh.core.annotation.MenuData;
import lab.s2jh.core.annotation.MetaData;
import lab.s2jh.core.service.BaseService;
import lab.s2jh.core.web.BaseController;
import lab.s2jh.core.web.json.JsonViews;
import lab.s2jh.core.web.view.OperationResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import s2jh.biz.order.entity.TradeOrderLog;
import s2jh.biz.order.service.TradeOrderLogService;

import javax.servlet.http.HttpServletRequest;

@MetaData("业务模块:订单管理")
@Controller
@RequestMapping(value = "/admin/order/trade-order-log")
public class TradeOrderLogController extends BaseController<TradeOrderLog, Long> {

    private final Logger logger = LoggerFactory.getLogger(TradeOrderLogController.class);

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

    @MenuData("业务模块:订单")
    @RequiresPermissions("业务模块:订单")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {
        return "admin/order/tradeOrderLog-index";
    }

    @RequiresPermissions("业务模块:订单")
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

    @RequiresPermissions("业务模块:订单")
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editShow(Model model) {
        return "admin/order/tradeOrderLog-inputBasic";
    }

    @RequiresPermissions("业务模块:订单")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public OperationResult editSave(@ModelAttribute("entity") TradeOrderLog entity, Model model) {
        return super.editSave(entity);
    }

    @RequiresPermissions("业务模块:订单")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public OperationResult delete(@RequestParam("ids") Long... ids) {
        return super.delete(ids);
    }

    /**
     * 订单撤销，当用户扫码创建完订单但电池未弹出时使用
     *
     * @param orderid 订单编号
     */
    //@RequiresPermissions("业务模块:管理员手动退款")
    @RequestMapping(value = "/cancelOrder", method = RequestMethod.POST)
    @ResponseBody
    public String cancelOrder(@RequestParam("orderid") String orderid) {
        return this.tradeOrderLogService.cancelOrder(orderid);
    }

    /**
     * 全额退款押金
     *
     * @param orderId       订单编号
     * @param returnTime    归还时间
     * @param returnStation 归还时间
     */
    @RequestMapping(value = "/refundAllDeposit", method = RequestMethod.POST)
    @ResponseBody
    public String refundAllDeposit(
            @RequestParam("order_id") String orderId,
            @RequestParam("return_time") String returnTime,
            @RequestParam("return_station") String returnStation
    ) {
        return this.tradeOrderLogService.refundAllDeposit(orderId, returnTime, returnStation);
    }

    /**
     * 返回所有设备的编号
     */
    @RequestMapping(value = "/findStations", method = RequestMethod.POST)
    @ResponseBody
    public String allStations(@RequestParam("content") String content) {
        return this.tradeOrderLogService.findStations(content);
    }

    /**
     * 手动退部分押金到用户余额
     */
    @RequestMapping(value = "/refundDeposit", method = RequestMethod.POST)
    @ResponseBody
    public String refundDeposit(@RequestParam("order_id") String orderId,
                                @RequestParam("return_time") String returnTime,
                                @RequestParam("return_station") String returnStation,
                                @RequestParam("charger") String charger,
                                @RequestParam("charging_cable") String chargingCable
    ) {
        return this.tradeOrderLogService.refundDeposit(orderId, returnTime, returnStation, charger, chargingCable);
    }

    /**
     * 处理遗失充电宝的情况
     * 对于支付宝订单，向支付宝发送赔偿请求
     * 对于小程序订单，扣除押金
     *
     * @param orderid
     */
    @RequestMapping(value = "/lostPowerBank", method = RequestMethod.POST)
    @ResponseBody
    public String lostPowerBank(@RequestParam("order_id") String orderid, @RequestParam("lost_time") String lostTime) {
        return this.tradeOrderLogService.lostPowerBank(orderid, lostTime);
    }

    @RequestMapping(value = "/refundExtraMoney", method = RequestMethod.POST)
    @ResponseBody
    public String refundExtraMoney(@RequestParam("order_id") String orderid, @RequestParam("extra_money") String extraMoney) {
        return this.tradeOrderLogService.refundExtraMoney(orderid,extraMoney);
    }


}