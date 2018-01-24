package com.circle;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.sql.*;
import java.util.*;

/**
 * 业务流程：
 * 1. 获取access_token
 *
 */
public class TestZhiHuiYa {

    private static final String CLIENT_ID = "9273b767a98e4ad5afbb64dc17a1b829";
    private static final String CLIENT_SECRET = "12z2ArpvW5rhETtz1ko38mTzOcgPatwX";
    private static final String ACCESS_TOKEN = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJSRUFEIl0sImV4cCI6MTUxNjI2MjcxNSwiYXV0aG9yaXRpZXMiOlsiYmQzMTc0ZDNjMWEzNDYzMWExNzQyZjY2MjEyZGU2MjkiLCI1NzNkYTlhYjhkZjg0MWIyOWM2YjU3NzQxM2NlYTgzMiJdLCJqdGkiOiJmNjQyZDBkNy03ZDJhLTQwOGEtYjQ0Ni0zZDM4Mjk0NWY2NGIiLCJjbGllbnRfaWQiOiI5MjczYjc2N2E5OGU0YWQ1YWZiYjY0ZGMxN2ExYjgyOSJ9.vIQx_qMipaAkdEw1-kSjgdtzVOYbQocWYiDHLDB0x1-Cr6tyiUTX4PO3F3voV4PbXm5oMhzyCJ6LWWYUz7lJAF9HN7AWTtfEf8vcejZJ5Lw0QCQVZakxKVL7NqjFokqimeeuyPKhljlqxIaTbg6sl9tEfkkVk26_WKT_IX5EI8s";

    private static final Logger logger = LoggerFactory.getLogger(TestZhiHuiYa.class);

    public static void main(String[] args) throws SQLException {
        TestZhiHuiYa testZhiHuiYa = new TestZhiHuiYa();
        Connection conn = null;
        StringBuffer sb = new StringBuffer();
        try {
            conn = testZhiHuiYa.connect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Statement stmt = conn.createStatement();
        //使用Statement发送sql语句
        String sql = "select open_id from  patent_from";
        //执行sql
        ResultSet rs = stmt.executeQuery(sql);
        int i = 0;
        while(rs.next()){
            i++;
            sb.append(rs.getString("open_id"));
            sb.append(",");
            if (i == 3) {
                testZhiHuiYa .getPatentValue(sb.toString(),conn);
                  i = 0;
                sb = new StringBuffer();
            }
        }

    }


    public static void getAccessToken() {
        String url = "https://con.zhihuiya.com/connector/oauth/token";
        // 封装请求头
        HashMap<String, String> headerMap = new LinkedHashMap<String, String>();
        headerMap.put("Content-Type", "application/x-www-form-urlencoded");
    }


    /**
     * 根据专利公开号获取专利价值详情
     * @param patent_number
     */
    public  void getPatentValue(String patent_number,Connection con) {
        String url = "https://api.zhihuiya.com/patent/valuation?patent_number=";
        url = url + patent_number;
        GetMethod getMethod = null;
        String response = "";
        try {
            getMethod = new GetMethod(url);
            getMethod.setRequestHeader("X-PatSnap-Version", "1.0.0");
            getMethod.setRequestHeader("authorization", ACCESS_TOKEN);
            getMethod.setRequestHeader("content-type", "application/json");
            //执行postMethod
            int statusCode = new HttpClient().executeMethod(getMethod);
            if (statusCode == HttpStatus.SC_OK) {
                response = getMethod.getResponseBodyAsString();
                System.out.println(response);
                List<PatentValue> values = JSONArray.parseArray(response, PatentValue.class);

                /*对数据库做增、删、改
                 * 1.通过Connection对象创建Statement
                 *   Statement语句的发送器，它的功能就是向数据库发送sql语句！
                 * 2.调用他的int executeUpdate(String sql),返回影响了几行
                 */
                //通过Connection 得到Statement;
                Statement stmt = con.createStatement();
                //使用Statement发送sql语句
                for (PatentValue value : values) {
                    String sql = "INSERT INTO patent_value(patent_id,patent_number,assignee,market_attractive,market_coverage,legal,technology,patent_value)"
                            + " VALUES('"+value.getPatent_id()+"','"+value.getPatent_number()+"','"+value.getAssignee()+"','"
                            +value.getMarket_attractiveness()+"','"+value.getMarket_coverage()+"','"+value.getLegal()+"','"
                            +value.getTechnology()+"','"+value.getPatent_value() + "')";
                    //执行sql
                    stmt.executeUpdate(sql);
                }

                System.out.println("信息存入数据库成功！");



            } else {
                logger.error("响应状态码 = " + getMethod.getStatusCode());
            }
        } catch (HttpException e) {
            logger.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
        } catch (IOException e) {
            logger.error("发生网络异常", e);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (getMethod != null) {
                getMethod.releaseConnection();
                getMethod = null;
            }
        }
    }

    public Connection connect() throws ClassNotFoundException, SQLException {
        /**
         * jdbc四大配置参数：
         * 1.driverClassName:com.mysql.jdbc.Driver
         * 2.url:jdbc:mysql://localhost:3306/patent
         * 3.username:root
         * 4.password:kewy126@home
         */
        Class.forName("com.mysql.jdbc.Driver");//加载驱动类(注册驱动类)
        String mySqlUrl = "jdbc:mysql://localhost:3306/patent";
        String username = "root";
        String password = "kewy126@home";

        //得到连接对象
        Connection con = DriverManager.getConnection(mySqlUrl, username, password);

        return con;

    }

}
