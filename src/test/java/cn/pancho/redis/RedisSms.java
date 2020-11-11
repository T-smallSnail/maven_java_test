package cn.pancho.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * @author ：pancho
 * @date ：Created in 2019/11/25 10:37
 * @description :发送梦网短信
 */
public class RedisSms {
    /**
     * 梦网批量短信
     * @author : pancho
     * @date : 2019/9/6 15:36
     * @return : void
     */
    @Test
    public void batchSms() {

        RedisClientPro jedisPro = null;
        Jedis jedis = null;
        String str = "{\"phone\":\"15810860391 \",\"msg\":\"市气象台17时发布：今天夜间晴，北风三四级转一二级，最低气温零下5度；明天白天晴，北转南风二三间四级，最高气温5度。拨12121按2号键，可零距离听气象专家解读今日天气和未来趋势。\",\"sourceCode\":\"测试\",\"createDate\":\"20190426\"}";

        try {
            jedisPro = new RedisClientPro();
            jedis = RedisClientPro.getJedis();
            jedis.select(0);


            jedis.lpush("ready_batch_send", str);



        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            RedisClientPro.returnJedis(jedis);
        }

    }


    /**
     * 梦网单条短信
     * @author : pancho
     * @date : 2019/9/6 15:36
     * @return : void
     */
    @Test
    public void singleSms() {

        RedisClientPro jedisPro = null;
        Jedis jedis = null;
        String str = "{\"phone\":\"15810860391 \",\"msg\":\"市气象台16时发布：夜间多云，有轻雾，北转南风二三间四级，最底气温零下3度。拨12121按2号键，可零距离听气象专家解读今日天气和未来趋势。\",\"sourceCode\":\"测试\",\"createDate\":\"20190426\"}";

        try {
            jedisPro = new RedisClientPro();
            jedis = RedisClientPro.getJedis();
            jedis.select(0);

            jedis.lpush("ready_single_send" ,str);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            RedisClientPro.returnJedis(jedis);
        }

    }


    /**
     * 梦网短信
     * @author : pancho
     * @date : 2019/9/6 15:36
     * @return : void
     */
    @Test
    public void monternetSms() {

        RedisClientPro jedisPro = null;
        Jedis jedis = null;
        String str = "{\"phone\":\"15810860391 \",\"msg\":\"市气象台16时发布：夜间多云，有轻雾，北转南风二三间四级，最底气温零下-13度。拨12121按2号键，可零距离听气象专家解读今日天气和未来趋势。\",\"sourceCode\":\"测试\",\"createDate\":\"20190426\"}";

        try {
            jedisPro = new RedisClientPro();
            jedis = RedisClientPro.getJedis();
            jedis.select(0);

            jedis.lpush("ready_batch_send_10658396" ,str);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            RedisClientPro.returnJedis(jedis);
        }

    }

}
