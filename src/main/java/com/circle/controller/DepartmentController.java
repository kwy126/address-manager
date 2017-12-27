package com.circle.controller;

import com.circle.service.IDepartmentService;
import com.circle.utils.json.JsonReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by keweiyang on 2017/6/8.
 */
@Controller
@RequestMapping(value = "/department")
public class DepartmentController extends AbstractController {

    @Autowired
    private IDepartmentService service;

    @ResponseBody
    @RequestMapping(value = "add")
    public JsonReturn addDepartment(String name, String description, HttpServletRequest request) {
        return service.addDepartment(name, description, acctName(request));
    }

    @ResponseBody
    @RequestMapping(value = "findDepartmentList")
    public JsonReturn findDepartmentList(@RequestParam String search, @RequestParam int page, HttpServletRequest request) {
        return service.findDepartmentList(search, page, acctName(request));
    }

    @ResponseBody
    @RequestMapping(value = "findDepartmentPage")
    public JsonReturn findDepartmentPage(@RequestParam String search, @RequestParam int page, HttpServletRequest request) {
        return service.findDepartmentPage(search, page, acctName(request));
    }

}
