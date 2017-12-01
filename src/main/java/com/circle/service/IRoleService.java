package com.circle.service;

import com.circle.util.json.JsonReturn;
import com.circle.vo.RoleModel;

/**
 * Created by keweiyang on 2017/6/12.
 */
public interface IRoleService extends IBaseService<RoleModel>{
    JsonReturn findRoleList(String search, int page, String s);

    JsonReturn findRolePage(String search, int page, String s);

    JsonReturn addRole(String name, String desc, String s);

    JsonReturn setRoleSecureValid(long role, String code, int type, boolean add);
}
