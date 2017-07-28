package s2jh.biz.shop.support.web;

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
import s2jh.biz.shop.entity.Shop;
import s2jh.biz.shop.service.ShopService;

import javax.servlet.http.HttpServletRequest;

@MetaData("商铺管理")
@Controller
@RequestMapping(value = "/admin/shop/shop")
public class ShopController extends BaseController<Shop,Long> {

    @Autowired
    private ShopService shopService;

    @Override
    protected BaseService<Shop, Long> getEntityService() {
        return shopService;
    }
    
    @ModelAttribute
    public void prepareModel(HttpServletRequest request, Model model, @RequestParam(value = "id", required = false) Long id) {
        super.initPrepareModel(request, model, id);
    }
    
    @MenuData("业务模块:商铺")
    @RequiresPermissions("商铺")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {
        return "admin/shop/shop-index";
    }   
    
    @RequiresPermissions("商铺")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @JsonView(JsonViews.Admin.class)
    public Page<Shop> findByPage(HttpServletRequest request) {
        return super.findByPage(Shop.class, request);
    }
    
    @RequestMapping(value = "/edit-tabs", method = RequestMethod.GET)
    public String editTabs(HttpServletRequest request) {
        return "admin/shop/shop-inputTabs";
    }

    @RequiresPermissions("商铺")
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editShow(Model model) {
        return "admin/shop/shop-inputBasic";
    }

    @RequiresPermissions("商铺")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public OperationResult editSave(@ModelAttribute("entity") Shop entity, Model model) {
        return super.editSave(entity);
    }

    @RequiresPermissions("商铺")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public OperationResult delete(@RequestParam("ids") Long... ids) {
        return super.delete(ids);
    }
}