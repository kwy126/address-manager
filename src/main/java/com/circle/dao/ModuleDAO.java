package com.circle.dao;

import com.circle.vo.ModuleModel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by keweiyang on 2017/6/8.
 */
@Repository
public interface ModuleDAO extends BaseDAO<ModuleModel>{

    public List<ModuleModel> findAll();

    ModuleModel findByCode(String code);
}
