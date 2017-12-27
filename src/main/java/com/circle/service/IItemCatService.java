package com.circle.service;

import com.circle.utils.json.JsonReturn;
import com.circle.vo.ItemCatModel;

/**
 * Created by keweiyang on 2017/6/28.
 */
public interface IItemCatService extends IBaseService<ItemCatModel> {

    JsonReturn getCatList(Long id);
}
