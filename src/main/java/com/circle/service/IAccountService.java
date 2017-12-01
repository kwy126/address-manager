package com.circle.service;

import com.circle.util.json.JsonReturn;
import com.circle.vo.AccountModel;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by keweiyang on 2017/6/4.
 */
public interface IAccountService extends IBaseService<AccountModel> {

    public JsonReturn findByUserName(String userName, String password, HttpServletRequest request);

    JsonReturn addAccout(String user, String nick, String pass, String s);

    JsonReturn findAccountList(String search, int page, String s);

    JsonReturn findAccountPage(String search, int page, String s);

    JsonReturn findRole(String acctName);

    JsonReturn addAccountRole(long id, String account, boolean add, String s);

    JsonReturn delAccount(Integer integer);

    JsonReturn modifyPass(String password, String name);
}
