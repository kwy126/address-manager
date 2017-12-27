package com.circle.controller;

import com.circle.service.IModuleService;
import com.circle.utils.json.JsonReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by keweiyang on 2017/6/15.
 */
@Controller
@RequestMapping(value = "module")
public class ModuleController extends AbstractController{

    @Autowired
    private IModuleService service;

    @RequestMapping(value = "/findMenu")
    @ResponseBody
    public JsonReturn findMenu() {
        return service.findMenu();
    }

    @RequestMapping(value = "/findAllModule")
    @ResponseBody
    public JsonReturn findAllModule(@RequestParam long roleId) {
        return service.findAllModuleByRoleId(roleId);
    }

    @ResponseBody
    @RequestMapping(value = "findModuleParameter")
    public JsonReturn findModuleParameter(@RequestParam String moduleCode, HttpServletRequest request) {
        return service.findModuleParameter(moduleCode, acctName(request));
    }


}
