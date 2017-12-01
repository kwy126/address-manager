package com.circle.service;

import com.circle.util.json.JsonReturn;
import com.circle.vo.ModuleModel;

/**
 * Created by keweiyang on 2017/6/8.
 */
public interface IModuleService extends IBaseService<ModuleModel> {

    public JsonReturn findMenu();

    JsonReturn findAllModuleByRoleId(long roleId);

    JsonReturn findModuleParameter(String moduleCode, String s);
}
