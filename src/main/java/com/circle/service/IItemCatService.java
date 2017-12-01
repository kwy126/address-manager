package com.circle.service;

import com.circle.util.json.JsonReturn;
import com.circle.vo.ItemCatModel;

import java.util.List;

/**
 * Created by keweiyang on 2017/6/28.
 */
public interface IItemCatService extends IBaseService<ItemCatModel> {

    JsonReturn getCatList(Long id);
}
