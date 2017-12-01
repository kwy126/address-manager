package com.circle.dao;

import com.circle.vo.AccountModel;
import com.circle.vo.StaticsModel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by keweiyang on 2017/6/4.
 */
@Repository
public interface StaticsDAO extends BaseDAO<StaticsModel> {
    void insert(StaticsModel model);

    StaticsModel getByRegion(String region, int type);

    List<StaticsModel> getAllStatics(int type);

}
