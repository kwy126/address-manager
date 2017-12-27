package com.circle.service;

import com.circle.dao.ItemCatDAO;
import com.circle.dto.TreeNodeDto;
import com.circle.utils.json.JsonReturn;
import com.circle.vo.ItemCatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by keweiyang on 2017/6/28.
 */
@Service
@Transactional
public class ItemCatServiceImpl extends BaseService<ItemCatModel> implements IItemCatService{

    @Autowired
    private ItemCatDAO dao = null;

    public JsonReturn getCatList(Long id) {

        List<ItemCatModel> itemCatModelList= dao.getCatList(id);
        List<TreeNodeDto> resultList = new ArrayList<TreeNodeDto>();
        for (ItemCatModel model : itemCatModelList) {
            TreeNodeDto dto = new TreeNodeDto();
            dto.setId(model.getId() + "");
            dto.setText(model.getName());
            resultList.add(dto);
        }

        return JsonReturn.buildSuccess(resultList);
    }

}
