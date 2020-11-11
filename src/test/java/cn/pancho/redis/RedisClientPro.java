package cn.pancho.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * @author ：pancho
 * @date ：Created in 2019/4/26 16:42
 * @description :redisTest
 */
public class RedisClientPro {








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
     *
     * @return : redis.clients.jedis.Jedis
     * @author : pancho
     * @date : 2019/4/26 16:57
     */
    public static Jedis getJedis() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(300);
        config.setTestOnBorrow(true);
        config.setMaxWaitMillis(100000);


        String[] sentinelAddr = "172.29.165.38:26379,172.29.165.38:26380,172.29.165.39:26379".split(",");
        Set<String> addrSet = new HashSet<String>();
        for (String addr : sentinelAddr) {
            addrSet.add(addr);
        }

        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool("mymaster", addrSet, config, 2000, 1000, "iamtony", 6, "vgop_scheduler_test");
        return jedisSentinelPool.getResource();
    }
}
