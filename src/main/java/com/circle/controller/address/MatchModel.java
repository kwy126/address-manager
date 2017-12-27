package com.circle.controller.address;


import com.circle.dao.BaseDAO;

import java.util.List;
import java.util.Map;

public class MatchModel<T> {

    public void match(Map<String, String> map, List<T> list,BaseDAO dao) {
        if (map.size() < 1) {
            System.out.println("地址解析不出来！");
        }

        String road = map.get("road");
        String vil = map.get("vil");
        String town = map.get("town");
        String area = map.get("area");
        String city = map.get("city");
        String province = map.get("province");



    }
}
