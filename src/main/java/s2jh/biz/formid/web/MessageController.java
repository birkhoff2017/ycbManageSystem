package s2jh.biz.formid.web;

import javax.servlet.http.HttpServletRequest;

import lab.s2jh.core.annotation.MenuData;
import lab.s2jh.core.annotation.MetaData;
import lab.s2jh.core.service.BaseService;
import lab.s2jh.core.web.BaseController;
import lab.s2jh.core.web.view.OperationResult;
import lab.s2jh.core.web.json.JsonViews;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import s2jh.biz.formid.entity.Message;
import s2jh.biz.formid.service.MessageService;

import com.fasterxml.jackson.annotation.JsonView;

@MetaData("业务模块:推送消息管理")
@Controller
@RequestMapping(value = "/admin/formid/message")
public class MessageController extends BaseController<Message,Long> {

    @Autowired
    private MessageService messageService;

    @Override
    protected BaseService<Message, Long> getEntityService() {
        return messageService;
    }
    
    @ModelAttribute
    public void prepareModel(HttpServletRequest request, Model model, @RequestParam(value = "id", required = false) Long id) {
        super.initPrepareModel(request, model, id);
    }
    
    @MenuData("业务模块:推送消息")
    @RequiresPermissions("业务模块:推送消息")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {
        return "admin/formid/message-index";
    }   
    
    @RequiresPermissions("业务模块:推送消息")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @JsonView(JsonViews.Admin.class)
    public Page<Message> findByPage(HttpServletRequest request) {
        return super.findByPage(Message.class, request);
    }
    
    @RequestMapping(value = "/edit-tabs", method = RequestMethod.GET)
    public String editTabs(HttpServletRequest request) {
        return "admin/formid/message-inputTabs";
    }

    @RequiresPermissions("业务模块:推送消息")
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editShow(Model model) {
        return "admin/formid/message-inputBasic";
    }

    @RequiresPermissions("业务模块:推送消息")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public OperationResult editSave(@ModelAttribute("entity") Message entity, Model model) {
        return super.editSave(entity);
    }

    @RequiresPermissions("业务模块:推送消息")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public OperationResult delete(@RequestParam("ids") Long... ids) {
        return super.delete(ids);
    }
}