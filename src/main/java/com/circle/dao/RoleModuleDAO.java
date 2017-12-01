package com.circle.dao;

import com.circle.vo.RoleModuleModel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by keweiyang on 2017/6/15.
 */
@Repository
public interface RoleModuleDAO extends BaseDAO<RoleModuleModel> {
    RoleModuleModel findByRoleLabelByModuleCode(String roleLabel, String moduleCode);

    public List<RoleModuleModel> findMySecureValid(String modules, String acctName);

    void setRoleSecureValid(RoleModuleModel roleModuleModel);
}
