package com.circle.service;

import com.circle.constant.LoginState;
import com.circle.constant.PageConstant;
import com.circle.dao.AccountDAO;
import com.circle.dao.AccountRoleDAO;
import com.circle.dao.RoleDAO;
import com.circle.dto.RoleDto;
import com.circle.utils.SessionKey;
import com.circle.utils.json.JsonReturn;
import com.circle.utils.object.ObjectUtil;
import com.circle.utils.pageutil.PageUtils;
import com.circle.utils.time.ClockUtil;
import com.circle.vo.AccountModel;
import com.circle.vo.AccountRoleModel;
import com.circle.vo.RoleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by keweiyang on 2017/6/4.
 */
@Service
@Transactional
public class AccountServiceImpl extends BaseService<AccountModel> implements IAccountService {
    @Autowired
    private AccountDAO dao = null;

    @Autowired
    private RoleDAO roleDAO = null;

    @Autowired
    private AccountRoleDAO accountRoleDAO = null;

    public JsonReturn findByUserName(String userName, String password, HttpServletRequest request) { if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            error("\t失败，用户名或密码为空");
            return JsonReturn.buildFailure("非法操作！");
        }

        AccountModel model = dao.findByUserName(userName);
        if (model == null) {
            warning("\t登录失败，用户不存在；{}", userName);
            return JsonReturn.buildFailure("帐号或密码不正确！");
        }

        if (!model.getAcctPassword().equals(password)) {
            warning("\t登录失败，密码不正确：username: {},password:{}", userName, password);
            return JsonReturn.buildFailure("帐号或密码不正确！");
        }

        // 将成功
        request.getSession().setAttribute(SessionKey.LOGIN_USER_INFO, model);
        info("\t登录成功：{}", userName);
        return JsonReturn.buildSuccess(model);
    }

    public JsonReturn addAccout(String user, String nick, String pass, String s) {
        if (StringUtils.isEmpty(user) || StringUtils.isEmpty(nick) || StringUtils.isEmpty(pass)) {
            return JsonReturn.buildFailure("添加失败！");
        }
        AccountModel model = dao.findByUserName(user);
        if (ObjectUtil.isNotEmpty(model)) {
            return JsonReturn.buildFailure("添加失败，用户名重复");
        }
        model = new AccountModel();
        model.setAcctName(user);
        model.setAcctNickName(nick);
        model.setAcctPassword(pass);
        model.setAcctState(0);
        model.setAcctSuper(0);
        model.setCreateTime(ClockUtil.currentDate());
        model.setCreator(s);
        model.setTimestamp(new Timestamp(ClockUtil.currentTimeMillis()));

        dao.create(model);

        return JsonReturn.buildSuccess("添加成功！");
    }

    public JsonReturn findAccountList(String search, int page, String s) {
        List<AccountModel> accountModelList = dao.findAccountList(search, (page - 1) * PageConstant.DEFAULT_LINE, PageConstant.DEFAULT_LINE);
        if (CollectionUtils.isEmpty(accountModelList)) {
            return JsonReturn.buildFailure("未获取相关数据！");
        }
        return JsonReturn.buildSuccess(accountModelList);
    }

    public JsonReturn findAccountPage(String search, int page, String s) {
        int count = dao.findAccountPage(search);
        return JsonReturn.buildSuccess(PageUtils.calculatePage(page,count,PageConstant.DEFAULT_LINE));
    }

    public JsonReturn findRole(String acctName) {
        List<RoleModel> roleModels = roleDAO.findAll();
        if (ObjectUtil.isEmpty(roleModels)) {
            return JsonReturn.buildFailure("未获取到角色信息！");
        }
        List<RoleDto> dtoList = new ArrayList<RoleDto>();
        for (RoleModel roleModel : roleModels) {
            RoleDto dto = new RoleDto();
            dto.setId(roleModel.getRoleId());
            dto.setRoleName(roleModel.getRoleName());
            dto.setAcctName(acctName);
            List<AccountRoleModel> accountRoleModelList = accountRoleDAO.findByAcctNameAndRoleLabel(acctName, roleModel.getRoleLabel());
            if (accountRoleModelList.isEmpty()) {
                dto.setOpt(false);
            }else {
                dto.setOpt(true);
            }

            dtoList.add(dto);
        }

        return JsonReturn.buildSuccess(dtoList);
    }

    public JsonReturn addAccountRole(long id, String account, boolean add, String s) {
        RoleModel roleModel = roleDAO.findById(id);
        if (ObjectUtil.isEmpty(roleModel)) {
            return JsonReturn.buildFailure("操作失败，数据源不存在");
        }
        if (!add) {
            accountRoleDAO.delByAcctNameAndRoleLabel(account, roleModel.getRoleLabel());
            return JsonReturn.buildSuccess("删除成功！");
        }
        if (!ObjectUtil.isNotEmpty(accountRoleDAO.findByAcctNameAndRoleLabel(account, roleModel.getRoleLabel()))) {
            return JsonReturn.buildFailure("添加失败，重复添加！");
        }
        AccountRoleModel accountRoleModel = new AccountRoleModel();
        accountRoleModel.setAcctName(account);
        accountRoleModel.setRoleLabel(roleModel.getRoleLabel());
        accountRoleModel.setTimestamp(new Timestamp(ClockUtil.currentTimeMillis()));
        accountRoleDAO.create(accountRoleModel);
        return JsonReturn.buildSuccess("添加成功！");
    }

    public JsonReturn delAccount(Integer integer) {
        if (integer == null) {
            return JsonReturn.buildFailure("操作失败！");
        }
        dao.delAccount(integer);
        return JsonReturn.buildSuccess("用户删除成功！");
    }

    public JsonReturn modifyPass(String password, String name) {
        if (com.alibaba.druid.util.StringUtils.isEmpty(name)) {
            return JsonReturn.buildFailure(LoginState.UNLOGIN);
        }
        dao.setPassword(password, name);
        return JsonReturn.buildSuccess("修改密码成功！");
    }

}
