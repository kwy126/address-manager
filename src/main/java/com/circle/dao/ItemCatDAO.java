package com.circle.dao;

import com.circle.vo.ItemCatModel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by keweiyang on 2017/6/27.
 */
@Repository
public interface ItemCatDAO extends BaseDAO<ItemCatModel>{

    List<ItemCatModel> getCatList(Long id);

    List<ItemCatModel> getCatListByParentId(long parentId);


}
