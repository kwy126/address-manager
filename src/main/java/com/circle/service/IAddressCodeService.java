package com.circle.service;

import com.circle.utils.json.JsonReturn;
import com.circle.vo.AddressCodeModel;
import com.circle.vo.AddressModel;

import java.io.IOException;
import java.util.List;


/**
 * Created by keweiyang on 2017/6/4.
 */
public interface IAddressCodeService extends IBaseService<AddressCodeModel> {
    JsonReturn insertBatch(List<AddressCodeModel> lists);

    JsonReturn build();

    /**
     * 对地址库进行整理
     * @return
     */
    JsonReturn reorganize();

    JsonReturn add(String address, String level) throws IOException;

    String parse(AddressModel model) throws Exception;

    JsonReturn parse(String address);

    JsonReturn updateAddressCode();

    void translate();

    void translateByLevel(int level);

    void translateByLevelAndType(int level, int type);


}
