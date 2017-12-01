package com.circle.controller;

import com.circle.util.SessionKey;
import com.circle.vo.AccountModel;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * Created by keweiyang on 2017/6/8.
 */
public class AbstractController implements Serializable {
    public String acctName(HttpServletRequest request) {
        AccountModel account = (AccountModel) request.getSession().getAttribute(SessionKey.LOGIN_USER_INFO);
        return account == null ? "" : account.getAcctName();
    }
}
