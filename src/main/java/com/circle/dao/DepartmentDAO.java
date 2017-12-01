package com.circle.dao;

import com.circle.vo.DepartmentModel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by keweiyang on 2017/6/8.
 */
@Repository
public interface DepartmentDAO extends BaseDAO<DepartmentModel> {
    List<DepartmentModel> findDepartmentList(String search, int page, int defaultLine);

    int findDepartmentPage(String search);

    DepartmentModel findById(long deptId);

    List<DepartmentModel> findAllDepartment();
}
