package cn.pancho.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author ：pancho
 * @date ：Created in 2019/4/28 10:39
 * @description :操作测试redis
 */
public class JedisClientDev {

    /**
     * 释放Jedis客户端
     *
     * @param jedis Jedis
     */
    public static void returnJedis(Jedis jedis) {
        try {
            if (jedis == null) {
                return;
            }
            jedis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 创建redis连接池
     * @author : pancho
     * @date : 2019/4/26 16:57
     * @return : redis.clients.jedis.Jedis
     */
    public static Jedis getJedis(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(300);
        config.setTestOnBorrow(true);
        config.setMaxWaitMillis(100000);


        JedisPool jedisPool = new JedisPool(config, "10.5.253.33", 6379, 5000, "111111", 6);

        return jedisPool.getResource();
    }
}
