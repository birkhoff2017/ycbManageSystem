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
import s2jh.biz.station.entity.Station;
import s2jh.biz.station.service.StationService;

import javax.servlet.http.HttpServletRequest;

@MetaData("设备表管理")
@Controller
@RequestMapping(value = "/admin/station/station")
public class StationController extends BaseController<Station,Long> {

    @Autowired
    private StationService stationService;

    @Override
    protected BaseService<Station, Long> getEntityService() {
        return stationService;
    }
    
    @ModelAttribute
    public void prepareModel(HttpServletRequest request, Model model, @RequestParam(value = "id", required = false) Long id) {
        super.initPrepareModel(request, model, id);
    }
    
    @MenuData("业务模块:设备表")
    @RequiresPermissions("设备表")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {
        return "admin/station/station-index";
    }   
    
    @RequiresPermissions("设备表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @JsonView(JsonViews.Admin.class)
    public Page<Station> findByPage(HttpServletRequest request) {
        return super.findByPage(Station.class, request);
    }
    
    @RequestMapping(value = "/edit-tabs", method = RequestMethod.GET)
    public String editTabs(HttpServletRequest request) {
        return "admin/station/station-inputTabs";
    }

    @RequiresPermissions("设备表")
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editShow(Model model) {
        return "admin/station/station-inputBasic";
    }

    @RequiresPermissions("设备表")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public OperationResult editSave(@ModelAttribute("entity") Station entity, Model model) {
        return super.editSave(entity);
    }

    @RequiresPermissions("设备表")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public OperationResult delete(@RequestParam("ids") Long... ids) {
        return super.delete(ids);
    }
}