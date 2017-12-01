package com.circle.controller;

import com.circle.service.IAccountService;
import com.circle.util.SessionKey;
import com.circle.util.json.JsonReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by keweiyang on 2017/6/12.
 */
@Controller
@RequestMapping(value = "account")
public class AccountController extends AbstractController{

    @Autowired
    private IAccountService service;

    @RequestMapping(value = "addAccount")
    @ResponseBody
    public JsonReturn addAccount(@RequestParam String user, @RequestParam String nick, @RequestParam String pass, HttpServletRequest request) {

        return service.addAccout(user, nick, pass, acctName(request));
    }

    @ResponseBody
    @RequestMapping(value = "findAccountList")
    public JsonReturn findAccountList(@RequestParam String search, @RequestParam int page, HttpServletRequest request) {
        return service.findAccountList(search, page, acctName(request));
    }

    @ResponseBody
    @RequestMapping(value = "findAccountPage")
    public JsonReturn findAccountPage(@RequestParam String search, @RequestParam int page, HttpServletRequest request) {
        return service.findAccountPage(search, page, acctName(request));
    }

    @ResponseBody
    @RequestMapping(value = "findRole")
    public JsonReturn findRole(@RequestParam String acctName) {
        return service.findRole(acctName);
    }

    @ResponseBody
    @RequestMapping(value = "addAccountRole")
    public JsonReturn addAccountRole(@RequestParam long id, @RequestParam String account, @RequestParam boolean add, HttpServletRequest request) {
        return service.addAccountRole(id, account, add, acctName(request));
    }

    @ResponseBody
    @RequestMapping(value = "delAccount")
    public JsonReturn delAccount(@RequestParam String  id) {
        return service.delAccount(Integer.valueOf(id));
    }

    @ResponseBody
    @RequestMapping(value="modifyPass")
    public JsonReturn modifyPass(@RequestParam String password, HttpServletRequest request) {
        return service.modifyPass(password,acctName(request));
    }

    @ResponseBody
    @RequestMapping(value = "exit")
    public JsonReturn exit(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(SessionKey.LOGIN_USER_INFO);
        return JsonReturn.buildSuccess("登出成功！");
    }
}
