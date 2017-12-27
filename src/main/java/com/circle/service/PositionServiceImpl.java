package com.circle.service;

import com.circle.constant.PageConstant;
import com.circle.dao.DepartmentDAO;
import com.circle.dao.PositionDAO;
import com.circle.utils.CompareUtil;
import com.circle.utils.json.JsonReturn;
import com.circle.utils.pageutil.PageUtils;
import com.circle.utils.time.ClockUtil;
import com.circle.vo.DepartmentModel;
import com.circle.vo.PositionModel;
import org.apache.commons.lang3.StringUtils;
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
public class PositionServiceImpl extends BaseService<PositionModel> implements IPositionService {

    @Autowired
    public PositionDAO dao = null;

    @Autowired
    public DepartmentDAO departmentDAO = null;

    public JsonReturn findPositionListInfo(int page, long deptId, String searchValue, String acctName) {
        List<PositionModel> pmList = dao.findPositionListInfo(deptId, searchValue, (page - 1) * PageConstant.DEFAULT_LINE, PageConstant.DEFAULT_LINE);
        if (CollectionUtils.isEmpty(pmList))
            return JsonReturn.buildFailure("未获取到数据!");
        for (PositionModel model : pmList) {
            model.setTimestamp(new Timestamp(ClockUtil.currentTimeMillis()));
        }
        return JsonReturn.buildSuccess(pmList);
    }

    public JsonReturn addPosition(long deptId, String name, String desc, String acctName) {
        if (StringUtils.isEmpty(name))
            return JsonReturn.buildFailure("添加失败,名称为空!");
        DepartmentModel departmentModel = departmentDAO.findById(deptId);
        if (CompareUtil.isEmpty(departmentModel))
            return JsonReturn.buildFailure("添加失败, 部门不存在!");
        PositionModel model = new PositionModel();
        model.setPoDepartment(String.valueOf(deptId));
        model.setPoName(name);
        model.setCreator(acctName);
        model.setTimestamp(new Timestamp(ClockUtil.currentTimeMillis()));
        model.setPoDescription(desc);
        model.setCreateTime(new Timestamp(ClockUtil.currentTimeMillis()));
        dao.create(model);
        return JsonReturn.buildSuccess("职位添加成功!");
    }

    public JsonReturn findPositionPage(int page, long deptId, String searchValue, String acctName) {
        int count = dao.findPositionPage(deptId, searchValue, (page - 1) * PageConstant.DEFAULT_LINE, PageConstant.DEFAULT_LINE);
        return JsonReturn.buildSuccess(PageUtils.calculatePage(page, count, PageConstant.DEFAULT_LINE));
    }

    public JsonReturn deletePosition(long id, String acctName) {
        return null;
    }

    public JsonReturn findPositionById(long id) {
        PositionModel position = dao.findByUuid(id);
        if (CompareUtil.isEmpty(position))
            return JsonReturn.buildFailure("源数据不存在!");
        return JsonReturn.buildSuccess(position);
    }

    public JsonReturn modifyPosition(long id, long deptId, String name, String desc, String acctName) {
        PositionModel position = dao.findByUuid(id);
        if (CompareUtil.isEmpty(position))
            return JsonReturn.buildFailure("源数据不存在!");
        DepartmentModel dept = departmentDAO.findById(deptId);
        if (CompareUtil.isEmpty(dept))
            return JsonReturn.buildFailure("修改失败, 部门不存在!");
        position.setPoName(name);
        position.setPoDescription(desc);
        position.setPoDepartment(String.valueOf(deptId));
        dao.modifyPosition(position);
        return JsonReturn.buildSuccess("修改成功!");
    }

    public JsonReturn findPositionByDeptId(long deptId) {
        List<PositionModel> list = dao.findByDeptId(deptId);
        if (CollectionUtils.isEmpty(list))
            return JsonReturn.buildFailure("该部门下不存在职位!");
        return JsonReturn.buildSuccess(list);
    }
}
