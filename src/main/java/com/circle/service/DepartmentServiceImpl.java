package com.circle.service;

import com.alibaba.druid.util.StringUtils;
import com.circle.constant.PageConstant;
import com.circle.dao.DepartmentDAO;
import com.circle.util.date.DateTimeUtil;
import com.circle.util.json.JsonReturn;
import com.circle.util.pageutil.PageUtils;
import com.circle.vo.DepartmentModel;
import com.circle.vo.DepartmentState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by keweiyang on 2017/6/8.
 */
@Service
@Transactional
public class DepartmentServiceImpl extends BaseService<DepartmentModel> implements IDepartmentService {

    @Autowired
    private DepartmentDAO dao;

    public JsonReturn addDepartment(String name, String description, String acctName) {
        info("新增部门信息...");
        if (StringUtils.isEmpty(name)) {
            error("\t新增部门信息失败，检测到传入的name为空；操作者：{}", acctName);
            return JsonReturn.buildFailure("添加失败，部门名称不能为空！");

        }
        DepartmentModel departmentModel = new DepartmentModel(name, new Timestamp(DateTimeUtil.getCurrentTime().getTime()),acctName, description, DepartmentState.normal.getState(),new Timestamp(DateTimeUtil.getCurrentTime().getTime()));
        dao.create(departmentModel);
        info("\t新增部门信息成功：新增的信息为：{}", departmentModel.toString());
        return JsonReturn.buildSuccess("部门添加成功！");
    }

    public JsonReturn findDepartmentList(String search, int page, String s) {
        List<DepartmentModel> departmentModelList = dao.findDepartmentList(search, (page - 1) * PageConstant.DEFAULT_LINE, PageConstant.DEFAULT_LINE);
        if (CollectionUtils.isEmpty(departmentModelList)) {
            return JsonReturn.buildFailure("未获取相关数据！");

        }

        return JsonReturn.buildSuccess(departmentModelList);
    }

    public JsonReturn findDepartmentPage(String search, int page, String s) {
        int count = dao.findDepartmentPage(search);
        return JsonReturn.buildSuccess(PageUtils.calculatePage(page,count,PageConstant.DEFAULT_LINE));
    }

    public JsonReturn findAllDepartment() {
        return JsonReturn.buildSuccess(dao.findAllDepartment());
    }

}
