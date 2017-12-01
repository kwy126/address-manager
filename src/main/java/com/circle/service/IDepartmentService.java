package com.circle.service;

import com.circle.util.json.JsonReturn;
import com.circle.vo.DepartmentModel;

/**
 * Created by keweiyang on 2017/6/8.
 */
public interface IDepartmentService extends IBaseService<DepartmentModel> {

    public JsonReturn addDepartment(String name, String description, String acctName);

    JsonReturn findDepartmentList(String search, int page, String s);

    JsonReturn findDepartmentPage(String search, int page, String s);

    JsonReturn findAllDepartment();
}

