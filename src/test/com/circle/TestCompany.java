package com.circle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.circle.dto.BaiduDto;
import com.circle.dto.GaoDeDto;
import com.circle.dto.Result;
import com.circle.utils.mapper.JsonMapper;
import com.circle.utils.net.HttpUtil;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 业务流程：
 * 1. 获取access_token
 */
public class TestCompany {

    private static final String CLIENT_ID = "9273b767a98e4ad5afbb64dc17a1b829";
    private static final String CLIENT_SECRET = "12z2ArpvW5rhETtz1ko38mTzOcgPatwX";
    private static final String ACCESS_TOKEN = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJSRUFEIl0sImV4cCI6MTUxNjI2MjcxNSwiYXV0aG9yaXRpZXMiOlsiYmQzMTc0ZDNjMWEzNDYzMWExNzQyZjY2MjEyZGU2MjkiLCI1NzNkYTlhYjhkZjg0MWIyOWM2YjU3NzQxM2NlYTgzMiJdLCJqdGkiOiJmNjQyZDBkNy03ZDJhLTQwOGEtYjQ0Ni0zZDM4Mjk0NWY2NGIiLCJjbGllbnRfaWQiOiI5MjczYjc2N2E5OGU0YWQ1YWZiYjY0ZGMxN2ExYjgyOSJ9.vIQx_qMipaAkdEw1-kSjgdtzVOYbQocWYiDHLDB0x1-Cr6tyiUTX4PO3F3voV4PbXm5oMhzyCJ6LWWYUz7lJAF9HN7AWTtfEf8vcejZJ5Lw0QCQVZakxKVL7NqjFokqimeeuyPKhljlqxIaTbg6sl9tEfkkVk26_WKT_IX5EI8s";

    private static final Logger logger = LoggerFactory.getLogger(TestCompany.class);

    public static void main(String[] args) throws SQLException {
        TestCompany testCompany = new TestCompany();
        Connection conn = null;
        StringBuffer sb = new StringBuffer();

        conn = DataUtil.getConnection();


        Statement stmt = conn.createStatement();
        //使用Statement发送sql语句
        String sql = "select * from  te_company where province  is null";
        //执行sql
        ResultSet rs = stmt.executeQuery(sql);
        int i = 0;
        while (rs.next()) {

            try {
                testCompany.getPosition(rs.getString("name"), conn);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        }

    }


    /**
     * 根据专利公开号获取专利价值详情
     *
     * @param company
     */
    public void getPosition(String company, Connection con) throws ClassNotFoundException, SQLException {
        System.out.println(company);

        Map<String, String> params = new HashMap<String, String>();

        params.put("output", "json");
        params.put("query", company);
        params.put("ak", "KpbfaELGudghIgvemooWgAve");
        params.put("region", "全国");


        String str = HttpUtil.URLGet("http://api.map.baidu.com/place/v2/search", params, "UTF-8");

      /*  params.put("output", "JSON");
        params.put("address",company);
        params.put("key", "ccf1bf3d1a6294bba2dc2caf85366452");



        String str = HttpUtil.URLGet("http://restapi.amap.com/v3/geocode/geo", params, "UTF-8");
           JsonMapper mapper = new JsonMapper();
        GaoDeDto dto = mapper.fromJson(str, GaoDeDto.class);
        if (StringUtils.isNotEmpty(str)) {
            if (dto != null && dto.getGeocodes().size() == 1) {
                System.out.println(dto.getGeocodes().get(0).getProvince()+": "+ dto.getGeocodes().get(0).getCity()+":"+ dto.getGeocodes().get(0).getDistrict());

                PreparedStatement stmt = con.prepareStatement("update te_company set province = ? , city = ? , district = ?  where name = ?");
                stmt.setString(1, dto.getGeocodes().get(0).getProvince());
                stmt.setString(2, dto.getGeocodes().get(0).getCity());
                stmt.setString(3, dto.getGeocodes().get(0).getDistrict());
                stmt.setString(4, company);
                //执行sql
                stmt.executeUpdate();

           }
        }
        */
        Object o = JSON.parse(str);
        if ("ok".equals(((JSONObject) o).getString("message"))) {
            List<Result> results = JSONArray.parseArray(((JSONObject) o).getString("results"), Result.class);

            System.out.println(results.size());
            if (results.size() > 0) {
                PreparedStatement stmt = con.prepareStatement("update te_company set province = ? , city = ? , district = ?  where name = ?");
                if (StringUtils.isNotEmpty(results.get(0).getProvince())) {
                    stmt.setString(1, results.get(0).getProvince());
                } else {
                    stmt.setString(1, results.get(0).getName());
                }

                stmt.setString(2, results.get(0).getCity());
                stmt.setString(3, results.get(0).getArea());
                stmt.setString(4, company);
                //执行sql
                stmt.executeUpdate();
            }

        }


    }


}
