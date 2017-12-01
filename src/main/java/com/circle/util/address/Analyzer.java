package com.circle.util.address;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.circle.util.redis.RedisUtil;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.DicAnalysis;
import org.nlpcn.commons.lang.tire.domain.Forest;
import org.nlpcn.commons.lang.tire.library.Library;
import redis.clients.jedis.Jedis;

public class Analyzer {

    public static Map<String, String> getToken(String content,String pathname) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            Forest forest = Library.makeForest(pathname);
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

    public static void main(String[] args) {
        Map<String, String> map = Analyzer.getToken("浙江省宁波市鄞州首南街道前周村毛家漕工业区2号楼", "C:\\tools\\workspace\\taobao-manager\\library\\userLibrary.dic");

        Jedis jedis = RedisUtil.JEDIS_POOL.getResource();

        jedis.hmset("1", map);

        Iterator<Map.Entry<String, String>> entries = map.entrySet().iterator();

        if (map.size() < 1) {
            System.out.println("地址解析不出来！");
        }

        while (entries.hasNext()) {

            Map.Entry<String, String> entry = entries.next();

            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
    }
}
