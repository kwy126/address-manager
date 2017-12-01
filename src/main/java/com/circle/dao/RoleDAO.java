package com.circle.dao;

import com.circle.vo.RoleModel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by keweiyang on 2017/6/12.
 */
@Repository
public interface RoleDAO extends BaseDAO<RoleModel>{

    public RoleModel findByName(String name);

    int findRolePage(String search);

    List<RoleModel> findRoleList(String search, int i, int defaultLine);

    List<RoleModel> findAll();

    RoleModel findById(long id);
}
