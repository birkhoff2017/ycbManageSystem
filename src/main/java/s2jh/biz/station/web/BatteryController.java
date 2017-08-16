package s2jh.biz.station.web;

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
import s2jh.biz.station.entity.Battery;
import s2jh.biz.station.service.BatteryService;

import javax.servlet.http.HttpServletRequest;

@MetaData("业务模块:充电宝电池管理")
@Controller
@RequestMapping(value = "/admin/station/battery")
public class BatteryController extends BaseController<Battery,Long> {

    @Autowired
    private BatteryService batteryService;

    @Override
    protected BaseService<Battery, Long> getEntityService() {
        return batteryService;
    }
    
    @ModelAttribute
    public void prepareModel(HttpServletRequest request, Model model, @RequestParam(value = "id", required = false) Long id) {
        super.initPrepareModel(request, model, id);
    }
    
    @MenuData("业务模块:充电宝电池")
    @RequiresPermissions("业务模块:充电宝电池")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {
        return "admin/station/battery-index";
    }   
    
    @RequiresPermissions("业务模块:充电宝电池")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @JsonView(JsonViews.Admin.class)
    public Page<Battery> findByPage(HttpServletRequest request) {
        return super.findByPage(Battery.class, request);
    }
    
    @RequestMapping(value = "/edit-tabs", method = RequestMethod.GET)
    public String editTabs(HttpServletRequest request) {
        return "admin/station/battery-inputTabs";
    }

    @RequiresPermissions("业务模块:充电宝电池")
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editShow(Model model) {
        return "admin/station/battery-inputBasic";
    }

    @RequiresPermissions("业务模块:充电宝电池")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public OperationResult editSave(@ModelAttribute("entity") Battery entity, Model model) {
        return super.editSave(entity);
    }

    @RequiresPermissions("业务模块:充电宝电池")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public OperationResult delete(@RequestParam("ids") Long... ids) {
        return super.delete(ids);
    }
}