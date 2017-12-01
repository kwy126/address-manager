package com.circle.controller;

import com.circle.service.IModuleService;
import com.circle.service.IRoleService;
import com.circle.util.json.JsonReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by keweiyang on 2017/6/12.
 */
@Controller
@RequestMapping(value = "role")
public class RoleController extends AbstractController {

    @Autowired
    private IRoleService service;

    @Autowired
    private IModuleService moduleService;

    @RequestMapping(value = "addRole")
    @ResponseBody
    public JsonReturn addRole(@RequestParam String name, @RequestParam String desc, HttpServletRequest request) {

        return service.addRole(name, desc, acctName(request));
    }

    @ResponseBody
    @RequestMapping(value = "findRoleList")
    public JsonReturn findAccountList(@RequestParam String search, @RequestParam int page, HttpServletRequest request) {
        return service.findRoleList(search, page, acctName(request));
    }

    @ResponseBody
    @RequestMapping(value = "findRolePage")
    public JsonReturn findAccountPage(@RequestParam String search, @RequestParam int page, HttpServletRequest request) {
        return service.findRolePage(search, page, acctName(request));
    }

    @ResponseBody
    @RequestMapping(value = "setRoleSecureValid")
    public JsonReturn setRoleSecureValid(@RequestParam long role, @RequestParam String code, @RequestParam int type,
                                         @RequestParam boolean add) {
        return service.setRoleSecureValid(role, code, type, add);
    }
}
