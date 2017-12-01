package com.circle.dao;

import com.circle.vo.AccountRoleModel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by keweiyang on 2017/6/4.
 */
@Repository
public interface AccountRoleDAO extends BaseDAO<AccountRoleModel> {

    public List<AccountRoleModel> findByAcctNameAndRoleLabel(String acctName, String roleLabel);

    void delByAcctNameAndRoleLabel(String account, String roleLabel);
}
