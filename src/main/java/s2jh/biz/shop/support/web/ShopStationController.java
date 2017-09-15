package s2jh.biz.shop.support.web;

import com.fasterxml.jackson.annotation.JsonView;
import lab.s2jh.core.annotation.MenuData;
import lab.s2jh.core.annotation.MetaData;
import lab.s2jh.core.service.BaseService;
import lab.s2jh.core.web.BaseController;
import lab.s2jh.core.web.json.JsonViews;
import lab.s2jh.core.web.view.OperationResult;
import lab.s2jh.module.auth.entity.User;
import lab.s2jh.module.auth.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import s2jh.biz.feeStrategy.entity.FeeStrategy;
import s2jh.biz.feeStrategy.service.FeeStrategyService;
import s2jh.biz.shop.entity.Shop;
import s2jh.biz.shop.entity.ShopStation;
import s2jh.biz.shop.service.ShopService;
import s2jh.biz.shop.service.ShopStationService;
import s2jh.biz.station.entity.Station;
import s2jh.biz.station.service.StationService;

import javax.servlet.http.HttpServletRequest;

@MetaData("商铺站点管理")
@Controller
@RequestMapping(value = "/admin/shop/shop-station")
public class ShopStationController extends BaseController<ShopStation, Long> {

    @Autowired
    private ShopStationService shopStationService;

    @Autowired
    private StationService stationService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private FeeStrategyService feeStrategyService;

    @Autowired
    private UserService userService;

    @Override
    protected BaseService<ShopStation, Long> getEntityService() {
        return shopStationService;
    }

    @ModelAttribute
    public void prepareModel(HttpServletRequest request, Model model, @RequestParam(value = "id", required = false) Long id) {
        if (!StringUtils.isEmpty(request.getParameter("station.sid"))) {
            model.addAttribute("stationsid", request.getParameter("station.sid"));
        }
        if (!StringUtils.isEmpty(request.getParameter("shop.id"))) {
            model.addAttribute("shopid", request.getParameter("shop.id"));
        }
        if (!StringUtils.isEmpty(request.getParameter("feeSettings.id"))) {
            model.addAttribute("feeSettingsid", request.getParameter("feeSettings.id"));
        }
        if (!StringUtils.isEmpty(request.getParameter("admin.id"))) {
            model.addAttribute("adminid", request.getParameter("admin.id"));
        }
        super.initPrepareModel(request, model, id);
    }

    @Override
    protected ShopStation buildDetachedBindingEntity(Long id) {
        return shopStationService.findDetachedOne(id, "station");
    }

    @MenuData("业务模块:商铺站点")
    @RequiresPermissions("业务模块:商铺站点")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {
        return "admin/shopStation/shopStation-index";
    }

    @RequiresPermissions("业务模块:商铺站点")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @JsonView(JsonViews.Admin.class)
    public Page<ShopStation> findByPage(HttpServletRequest request) {
        return super.findByPage(ShopStation.class, request);
    }

    @RequestMapping(value = "/edit-tabs", method = RequestMethod.GET)
    public String editTabs(HttpServletRequest request) {
        return "admin/shopStation/shopStation-inputTabs";
    }

    @RequiresPermissions("业务模块:商铺站点")
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editShow(Model model) {
        return "admin/shopStation/shopStation-inputBasic";
    }

    @RequiresPermissions("业务模块:商铺站点")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public OperationResult editSave(@ModelAttribute("entity") ShopStation entity, Model model) {
        Station station = null;
        Shop shop = null;
        FeeStrategy feeStrategy = null;
        User admin = null;
        if (null != model.asMap().get("stationsid")) {
            station = stationService.findFirstByProperty("sid", Long.valueOf(model.asMap().get("stationsid").toString()));
        }
        if (null != model.asMap().get("shopid")) {
            shop = shopService.findOne(Long.valueOf(model.asMap().get("shopid").toString()));
        }
        if (null != model.asMap().get("feeSettingsid")) {
            feeStrategy = feeStrategyService.findOne(Long.valueOf(model.asMap().get("feeSettingsid").toString()));
        }
        if (null != model.asMap().get("adminid")) {
            admin = userService.findOne(Long.valueOf(model.asMap().get("adminid").toString()));
        }
        entity.setStation(station);
        entity.setShop(shop);
        entity.setFeeSettings(feeStrategy);
        entity.setAdmin(admin);
        return super.editSave(entity);
    }

    @RequiresPermissions("业务模块:商铺站点")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public OperationResult delete(@RequestParam("ids") Long... ids) {
        return super.delete(ids);
    }
}