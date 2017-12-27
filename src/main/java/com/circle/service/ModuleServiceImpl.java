package com.circle.service;

import com.circle.constant.LoginState;
import com.circle.dao.AccountDAO;
import com.circle.dao.ModuleDAO;
import com.circle.dao.RoleDAO;
import com.circle.dao.RoleModuleDAO;
import com.circle.dto.ModuleDto;
import com.circle.utils.CompareUtil;
import com.circle.utils.json.JsonReturn;
import com.circle.vo.AccountModel;
import com.circle.vo.ModuleModel;
import com.circle.vo.RoleModel;
import com.circle.vo.RoleModuleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by keweiyang on 2017/6/8.
 */
@Service
@Transactional
public class ModuleServiceImpl extends BaseService<ModuleModel> implements IModuleService {
    @Autowired
    private ModuleDAO dao;

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private RoleModuleDAO roleModuleDAO;

    @Autowired
    private AccountDAO accountDAO;

    public JsonReturn findMenu() {
        List<ModuleModel> modelList = dao.findAll();
        if (CollectionUtils.isEmpty(modelList)) {
            error("未获取系统菜单");
            return JsonReturn.buildFailure("未获取系统菜单");
        }
        for (ModuleModel moduleModel : modelList) {

            System.out.println(moduleModel.toString());
        }
        return JsonReturn.buildSuccess(modelList);

    }

    public JsonReturn findAllModuleByRoleId(long roleId) {

        RoleModel roleModel = roleDAO.findById(roleId);
        if (CompareUtil.isEmpty(roleModel)) {
            return JsonReturn.buildFailure("未获取角色信息！");
        }

        List<ModuleModel> moduleModels = dao.findAll();
        if (moduleModels.isEmpty()) {
            return JsonReturn.buildFailure("未获取模块信息！");
        }
        List<ModuleDto> infoList = new ArrayList<ModuleDto>();
        for (ModuleModel mm : moduleModels) {
            ModuleDto dto = new ModuleDto();
            dto.setId(mm.getModuleId());
            dto.setName(mm.getModuleName());
            dto.setCode(mm.getModuleCode());
            dto.setSuperCode(mm.getModuleSuperCode());
            dto.setUrl(mm.getModulePage());
            dto.setLevel(mm.getModuleLevel());
            RoleModuleModel roleModuleModel = roleModuleDAO.findByRoleLabelByModuleCode(roleModel.getRoleLabel(), mm.getModuleCode());
            if (CompareUtil.isNotEmpty(roleModuleModel)) {
                dto.setAdd(roleModuleModel.getAdds() == 1);
                dto.setDel(roleModuleModel.getDeletes() == 1);
                dto.setModify(roleModuleModel.getModifys() == 1);
                dto.setFind(roleModuleModel.getFinds() == 1);

            }
            infoList.add(dto);

        }
        return JsonReturn.buildSuccess(infoList);
    }

    public JsonReturn findModuleParameter(String moduleCode, String s) {

        if (StringUtils.isEmpty(s)) {

            return JsonReturn.buildFailure(LoginState.UNLOGIN);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        AccountModel accountModel = accountDAO.findByUserName(s);
        if (CompareUtil.isEmpty(accountModel))
            return JsonReturn.buildFailure(map);
        map.put("account", accountModel.getAcctNickName());
        if ("0".equals(moduleCode)) {
            map.put("find", true);
            // 判断是否是超级管理员
            map.put("add", accountModel.getAcctSuper() == 1 ? true : false);
            map.put("del", accountModel.getAcctSuper() == 1 ? true : false);
            map.put("modify", accountModel.getAcctSuper() == 1 ? true : false);
            return JsonReturn.buildSuccess(map);
        }
        ModuleModel moduleModel = dao.findByCode(moduleCode);
        if (CompareUtil.isEmpty(moduleModel))
            return JsonReturn.buildFailure("非法操作!");
        map.put("moduleName", moduleModel.getModuleName());
        // 获取当前模块名与上级模块名, 前台做为面包绡显示
        map = findModuleName(map, moduleCode, moduleModel);
        boolean add = false, del = false, modify = false, find = false;
        List<RoleModuleModel> secureValidList = roleModuleDAO.findMySecureValid(moduleCode, s);
        if (accountModel.getAcctSuper()==0 && !secureValidList.isEmpty()) {
            for (RoleModuleModel rm : secureValidList) {
                if (rm.getAdds()==1)
                    add = true;
                if (rm.getDeletes()==1)
                    del = true;
                if (rm.getModifys()==1)
                    modify = true;
                if (rm.getFinds()==1)
                    find = true;
            }
        }
        map.put("add", accountModel.getAcctSuper()==1 ? true : add);
        map.put("del", accountModel.getAcctSuper()==1 ? true : del);
        map.put("modify", accountModel.getAcctSuper()==1 ? true : modify);
        map.put("find", accountModel.getAcctSuper()==1 ? true : find);
        return JsonReturn.buildSuccess(map);
    }

    /*
    * 获取当前模块名称以及上级模块名称
     */
    public Map<String, Object> findModuleName(Map<String, Object> map, String moduleCode, ModuleModel moduleModel) {
        moduleModel = dao.findByCode(moduleModel.getModuleSuperCode());
        if (CompareUtil.isNotEmpty(moduleModel)) {
            map.put("superModuleName", moduleModel.getModuleName());
            map.put("code", moduleModel.getModuleCode());
        }
        return map;
    }

    public Map analysisModuleArray(String[] modules) {

        Map<String, Object> params = new HashMap<String, Object>();
        if (modules.length < 1){
            return params;
        }else if (modules.length == 1){
            params.put("ids", 0);
            params.put("params", modules[0]);
            return params;
        }else{
            for(int i=0;i<modules.length;i++) {
                params.put("ids", i);
                params.put("params",modules[i]);
            }
            return params;
        }

    }
}
