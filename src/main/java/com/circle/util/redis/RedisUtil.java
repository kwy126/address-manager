package com.circle.util.redis;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
    public static final JedisPool JEDIS_POOL;

    static {
        JedisPoolConfig config = new JedisPoolConfig();//redis连接池配置对象
        config.setMaxTotal(32);//最大连接数
        config.setMaxIdle(6);//闲置最大连接数
        config.setMinIdle(0);//闲置最小连接数
        config.setMaxWaitMillis(15000);//到达最大连接数后，调用者阻塞时间
        config.setMinEvictableIdleTimeMillis(300000);//连接空闲的最小时间，可能被移除
        config.setSoftMinEvictableIdleTimeMillis(-1);//连接空闲的最小时间，多余最小闲置连接的将被移除
        config.setNumTestsPerEvictionRun(3);//设置每次检查闲置的个数
        config.setTestOnBorrow(false);//申请连接时，是否检查连接有效
        config.setTestOnReturn(false);//返回连接时，是否检查连接有效
        config.setTestWhileIdle(false);//空闲超时,是否执行检查有效
        config.setTimeBetweenEvictionRunsMillis(60000);//空闲检查时间
        config.setBlockWhenExhausted(true);//当连接数耗尽，是否阻塞

        //连接池配置对象+ip+port+timeout+password+dbname
        JEDIS_POOL = new JedisPool(config,"127.0.0.1",6379,15000,null,1);
    }
}
