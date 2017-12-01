package com.circle.controller;

import com.circle.intercept.RandomValidateCode;
import com.circle.service.IAccountService;
import com.circle.util.SessionKey;
import com.circle.util.json.JsonHelper;
import com.circle.util.json.JsonReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by keweiyang on 2017/6/4.
 */
@Controller
public class LoginController {

    @Autowired
    private IAccountService accountService;

    @RequestMapping(value = "/0/findVerifyCode")
    public void findVerifyCode(HttpServletRequest request, HttpServletResponse response) {
        // 因为返回的图片是通过字节流返回的，所以返回值是void
        // HttpServletRequest  HttpServletRequest是spring
        RandomValidateCode.getRandcode(request, response);

    }

    @RequestMapping(value = "/0/login", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JsonReturn login(@RequestParam String userName, @RequestParam String password, @RequestParam String verify, HttpServletRequest request, HttpServletResponse response) {

        // RequestParam 如果参数没值就会报错
        //HttpServletResponse是springmvc自带的参数
        //1. 验证图形验证码是否正确
        if (StringUtils.isEmpty(verify)) {
            return JsonReturn.buildFailure("验证码为空！");
        }
        // 获取验证码
        Object code = request.getSession().getAttribute(SessionKey.VALIDATE_CODE);
        if (!verify.equalsIgnoreCase(String.valueOf(code))) {
            return JsonReturn.buildFailure("验证码输入错误！");
        }

        return accountService.findByUserName(userName, password, request);
    }



    @RequestMapping(value = "/0/check")
    @ResponseBody
    public String checkUuid(@RequestParam String acctId) {
        return JsonHelper.object2Str(accountService.getByUuid(Integer.valueOf(acctId)));
    }

    @RequestMapping(value = "/0/toLogin")
    public String toLogin() {

        return "static/login";
    }
    @RequestMapping(value = "/0/toRegister")
    public String register() {

        return "register";
    }

    @RequestMapping(value = "/0/toFindPassword")
    public String findPasswd() {
        return "find_password";
    }

}
