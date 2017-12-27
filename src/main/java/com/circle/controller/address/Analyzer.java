package com.circle.controller.address;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.circle.utils.io.URLResourceUtil;
import com.circle.utils.redis.RedisUtil;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.DicAnalysis;
import org.nlpcn.commons.lang.tire.domain.Forest;
import org.nlpcn.commons.lang.tire.library.Library;
import redis.clients.jedis.Jedis;

public class Analyzer {

    public static final String PATHNAME = "";

    public static Map<String, String> getToken(String content) {
        Map<String, String> map = new HashMap<String, String>();

        try {
            Forest forest =  Library.makeForest(URLResourceUtil.asStream("classpath://userLibrary.dic"));
            Result result = DicAnalysis.parse(content, forest);
            for (Term term : result) {
                if ("province".equals(term.getNatureStr())) {
                    map.put(term.getNatureStr(),term.getName() );
                } else if ("city".equals(term.getNatureStr())) {
                    map.put(term.getNatureStr(),term.getName() );
                } else if ("area".equals(term.getNatureStr())) {
                    map.put(term.getNatureStr(),term.getName() );
                } else if ("town".equals(term.getNatureStr())) {
                    map.put(term.getNatureStr(),term.getName() );
                } else if ("vil".equals(term.getNatureStr())) {
                    map.put(term.getNatureStr(),term.getName() );
                } else if ("road".equals(term.getNatureStr())) {
                    map.put(term.getNatureStr(),term.getName() );
                } else if ("other".equals(term.getNatureStr())) {
                    map.put(term.getNatureStr(),term.getName() );
                } else{
                    System.out.println("未定义！");
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


}
