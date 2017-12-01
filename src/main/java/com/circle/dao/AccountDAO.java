package com.circle.dao;

import com.circle.vo.AccountModel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by keweiyang on 2017/6/4.
 */
@Repository
public interface AccountDAO extends BaseDAO<AccountModel> {

    public AccountModel findByUserNameAndPassword(String userName, String password);

    public AccountModel findByUserName(String userName);

    List<AccountModel> findAccountList(String search, int i, int defaultLine);

    int findAccountPage(String search);

    void delAccount(Integer integer);

    void setPassword(String password, String name);
}
