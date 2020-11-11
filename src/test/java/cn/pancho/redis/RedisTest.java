package cn.pancho.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.List;
import java.util.Map;

/**
 * @author ：pancho
 * @date ：Created in 2019/5/10 10:00
 * @description :操作redis
 */
public class RedisTest {


    /**
     * 向redis添加体验用户
     *
     * @return : void
     * @author : pancho
     * @date : 2019/9/6 15:42
     */
    @Test
    public void experienceuserPhone() {

        RedisClientPro jedisPro = new RedisClientPro();
        Jedis jedis = RedisClientPro.getJedis();
        try {

            String[] phones = {"13810132294"};
            for (String phone : phones) {

                String redisKey = "usergrowth:experienceuser:phone_" + phone;
                jedis.set(redisKey, "6");
                jedis.expire(redisKey, 2592000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            RedisClientPro.returnJedis(jedis);
        }
    }



    /**
     * 使用sscan从redis的Set集合取出数据
     *
     * @return : void
     * @author : pancho
     * @date : 2019/9/6 15:37
     */
    @Test
    public void sscan() {

        JedisClientDev devJedis = new JedisClientDev();
        Jedis jedis = null;
        try {
            jedis = JedisClientDev.getJedis();
            String cursor = "0";
            do {
                ScanParams params = new ScanParams();
                params.count(2);
                ScanResult<String> set = jedis.sscan("Set", cursor, params);
                cursor = set.getCursor();
                System.out.println("cursor========" + cursor);
                List<String> result = set.getResult();
                for (String value : result) {
                    System.out.println("value==" + value);

                }
            } while (Integer.valueOf(cursor) != 0);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JedisClientDev.returnJedis(jedis);
        }

    }


    /**
     * Hash数据hscan迭代取出
     */
    @Test
    public void hscan() {
        JedisClientDev devJedis = new JedisClientDev();
        Jedis jedis = null;
        try {
            jedis = JedisClientDev.getJedis();
            String cursor = "0";
            do {
                ScanParams params = new ScanParams();
                params.count(2);
                ScanResult<Map.Entry<String, String>> hscan = jedis.hscan("user_growth_activity_untreated_t_user_growth_139_email_log_20196", cursor, params);
                cursor = hscan.getCursor();
                System.out.println("cursor========" + cursor);
                List<Map.Entry<String, String>> result = hscan.getResult();
                for (Map.Entry<String, String> map : result) {
                    System.out.println("key==" + map.getKey());
                    System.out.println("key==" + map.getValue());
                }
            } while (Integer.valueOf(cursor) != 0);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JedisClientDev.returnJedis(jedis);
        }

    }





}
