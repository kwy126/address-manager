package com.circle.util.random;

import java.text.SimpleDateFormat;
import java.util.Date;

public class IdGenerator {
    public static String getId(){
        String id="";
        //获取当前时间戳
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        String temp = sf.format(new Date());
        //获取6位随机数
        int random=(int) ((Math.random()+1)*100000);
        id=temp+random;
        return id;
    }
}
