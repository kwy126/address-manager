package com.circle.dao;

import com.circle.vo.PositionModel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by keweiyang on 2017/6/27.
 */
@Repository
public interface PositionDAO extends BaseDAO<PositionModel> {

    public List<PositionModel> findPositionListInfo(long deptId, String search, int i, int defaultLine);

    public int findPositionPage(long deptId, String searchValue, int i, int defaultLine);

    public List<PositionModel> findByDeptId(long deptId);

    public void deleteByDepartment(long deptId);

    public PositionModel findByUuid(long id);

    public void modifyPosition(PositionModel model);


}