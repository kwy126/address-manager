package com.circle;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author KeWeiYang
 * @date 2018/3/5 9:16
 * TODO
 */
public class DataUtil {


    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");//加载驱动类(注册驱动类)
            String mySqlUrl = "jdbc:mysql://localhost:3306/address?useUnicode=true&amp;characterEncoding=UTF-8";
            String username = "root";
            String password = "kewy126@home";

            //得到连接对象
            con = DriverManager.getConnection(mySqlUrl, username, password);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return con;

    }

}
