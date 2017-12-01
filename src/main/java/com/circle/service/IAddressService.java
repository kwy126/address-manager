package com.circle.service;

import com.circle.util.json.JsonReturn;
import com.circle.vo.AddressModel;

import java.util.List;


/**
 * Created by keweiyang on 2017/6/4.
 */
public interface IAddressService extends IBaseService<AddressModel> {
    JsonReturn insertBatch(List<AddressModel> lists);

    JsonReturn findAddressList(String search, int page, int pageSize, String state, int type, String s);

    /**
     *  Semantic ui 列表显示
     * @param search
     * @param page
     * @return
     */
    JsonReturn findAddressListByPage(String search, int page, int state, int type);

    /**
     *  Semantic Ui 列表显示
     * @param search
     * @param page
     * @return
     */
    JsonReturn findAddressPage(String search, int page, int state, int type);

    JsonReturn parseAddress(Integer[] ids, String operator);

    JsonReturn deleteAddress(Integer[] ids);

    JsonReturn parseAllAddress(int type, String operator);

    JsonReturn getStatics(int type);

    JsonReturn getDataPrecision(int type, Integer functionOrRegion, String time);

    JsonReturn getChartStatics(int type);

    List<AddressModel> getDataByTypeAndTime(int type, String time);
}
