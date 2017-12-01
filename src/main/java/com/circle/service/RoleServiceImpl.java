package com.circle.service;

import com.alibaba.druid.util.StringUtils;
import com.circle.constant.PageConstant;
import com.circle.dao.ModuleDAO;
import com.circle.dao.RoleDAO;
import com.circle.dao.RoleModuleDAO;
import com.circle.util.CompareUtil;

import com.circle.util.date.DateTimeUtil;
import com.circle.util.json.JsonReturn;
import com.circle.util.pageutil.PageUtils;
import com.circle.util.random.IdGenerator;
import com.circle.vo.ModuleModel;
import com.circle.vo.RoleModel;
import com.circle.vo.RoleModuleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by keweiyang on 2017/6/12.
 */
@Service
@Transactional
public class RoleServiceImpl extends BaseService<RoleModel> implements IRoleService {

    @Autowired
    private RoleDAO dao;

    @Autowired
    private ModuleDAO moduleDAO;

    @Autowired
    private RoleModuleDAO roleModuleDAO;

    public static final int TYPE_FIND = 1;
    public static final int TYPE_DELETE = 2;
    public static final int TYPE_MODIFY = 3;
    public static final int TYPE_ADD = 4;


    public JsonReturn findRoleList(String search, int page, String s) {
        List<RoleModel> roleModelList = dao.findRoleList(search, (page - 1) * PageConstant.DEFAULT_LINE, PageConstant.DEFAULT_LINE);
        if (CollectionUtils.isEmpty(roleModelList)) {
            return JsonReturn.buildFailure("未获取相关数据！");

        }

        return JsonReturn.buildSuccess(roleModelList);

    }

    public JsonReturn findRolePage(String search, int page, String s) {
        int count = dao.findRolePage(search);
        return JsonReturn.buildSuccess(PageUtils.calculatePage(page, count, PageConstant.DEFAULT_LINE));
    }

    public JsonReturn findByName(String name) {

        if (StringUtils.isEmpty(name)) {
            return JsonReturn.buildFailure("非法操作！");
        }
        RoleModel model = dao.findByName(name);
        if (model == null) {
            return JsonReturn.buildFailure("角色对象不存在！");
        }
        return JsonReturn.buildSuccess("角色对象已存在！");
    }

    public JsonReturn addRole(String name, String desc, String s) {
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(desc) || StringUtils.isEmpty(s)) {
            return JsonReturn.buildFailure("添加失败！");
        }
        RoleModel model = dao.findByName(name);
        if (CompareUtil.isNotEmpty(model)) {
            return JsonReturn.buildFailure("添加失败，角色名称重复");
        }
        model = new RoleModel();
        model.setRoleName(name);
        model.setRoleDescription(desc);
        model.setRoleLabel(IdGenerator.getId());
        model.setCreateTime(DateTimeUtil.getCurrentTime());
        model.setCreator(s);
        model.setTimestamp(new Timestamp(DateTimeUtil.getCurrentTime().getTime()));
        dao.create(model);

        return JsonReturn.buildSuccess("添加成功！");
    }

    public JsonReturn setRoleSecureValid(long role, String code, int type, boolean add) {
        ModuleModel moduleModel = moduleDAO.findByCode(code);
        if (CompareUtil.isEmpty(moduleModel)) {
            return JsonReturn.buildFailure("操作失败，模块不存在！");
        }

        RoleModel roleModel = dao.findById(role);
        if (CompareUtil.isEmpty(roleModel)) {
            return JsonReturn.buildFailure("操作失败，角色不存在！");
        }

        RoleModuleModel roleModuleModel = roleModuleDAO.findByRoleLabelByModuleCode(roleModel.getRoleLabel(), moduleModel.getModuleCode());
        if (CompareUtil.isEmpty(roleModuleModel)) {
            roleModuleModel = new RoleModuleModel();
            roleModuleModel.setRoleLabel(roleModel.getRoleLabel());
            roleModuleModel.setModuleCode(code);
            roleModuleModel.setMosuleSuperCode(moduleModel.getModuleSuperCode());
            if (type == TYPE_FIND) {
                roleModuleModel.setFinds(1);
                roleModuleModel.setAdds(0);
                roleModuleModel.setDeletes(0);
                roleModuleModel.setModifys(0);
            } else if (type == TYPE_DELETE) {
                roleModuleModel.setDeletes(1);
                roleModuleModel.setFinds(0);
                roleModuleModel.setAdds(0);
                roleModuleModel.setModifys(0);
            } else if (type == TYPE_ADD){
                roleModuleModel.setAdds(1);
                roleModuleModel.setFinds(0);
                roleModuleModel.setDeletes(0);
                roleModuleModel.setModifys(0);
            } else{
                roleModuleModel.setModifys(1);
                roleModuleModel.setAdds(0);
                roleModuleModel.setDeletes(0);
                roleModuleModel.setFinds(0);
            }


            roleModuleModel.setTimestamp(new Timestamp(DateTimeUtil.getCurrentTime().getTime()));
            roleModuleDAO.create(roleModuleModel);
            return JsonReturn.buildSuccess("添加成功！");
        }else{
            roleModuleDAO.setRoleSecureValid(setRoleSecureValid(type,roleModuleModel,add));
            return JsonReturn.buildSuccess("修改成功！");
        }





    }

    public RoleModuleModel setRoleSecureValid(int type, RoleModuleModel roleModuleModel, boolean add) {
        if (type == TYPE_FIND) {
            if (add) {
                roleModuleModel.setFinds(1);
            }else{
                roleModuleModel.setFinds(0);
            }


        } else if (type == TYPE_DELETE) {
            if (add) {
                roleModuleModel.setDeletes(1);
            }else{
                roleModuleModel.setDeletes(0);
            }

        } else if (type == TYPE_ADD){
            if (add) {
                roleModuleModel.setAdds(1);
            }else{
                roleModuleModel.setAdds(0);
            }

        } else{
            if (add) {
                roleModuleModel.setModifys(1);
            }else{
                roleModuleModel.setModifys(0);
            }
        }
        roleModuleModel.setTimestamp(new Timestamp(DateTimeUtil.getCurrentTime().getTime()));
        return roleModuleModel;
    }


}
