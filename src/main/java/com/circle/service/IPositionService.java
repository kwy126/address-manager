package com.circle.service;

import com.circle.utils.json.JsonReturn;
import com.circle.vo.PositionModel;

/**
 * Created by keweiyang on 2017/6/8.
 */
public interface IPositionService extends IBaseService<PositionModel> {

    JsonReturn findPositionListInfo(int page, long deptId, String searchValue, String acctName);

    JsonReturn addPosition(long deptId, String name, String desc, String acctName);

    JsonReturn findPositionPage(int page, long deptId, String searchValue, String acctName);

    JsonReturn deletePosition(long id, String acctName);

    JsonReturn findPositionById(long id);

    JsonReturn modifyPosition(long id, long deptId, String name, String desc, String acctName);

    JsonReturn findPositionByDeptId(long deptId);


}

