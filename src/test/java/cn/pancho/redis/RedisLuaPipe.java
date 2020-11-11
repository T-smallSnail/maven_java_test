package cn.pancho.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：pancho
 * @date ：Created in 2019/10/24 18:10
 * @description :测试redis的lua脚本
 */
public class RedisLuaPipe {


    @Test
    public void pipeTest(){
        Jedis jedis = null;
        Pipeline pipelined = null;
        String shaKey = "{sms}_stra_check_lua_sha_key";
        try{
            jedis = JedisClientDev.getJedis();
            jedis.select(1);
            //String sha = jedis.get(shaKey);
            String sha ="a1e43d1352e732440997640ca9e74a40885a29fc";
            pipelined = jedis.pipelined();

            List<String> list = new ArrayList<>();
            list.add("000000");
            //list.add("18630028413");
            //list.add("15810860391");


            for (String phone:list){
                pipelined.evalsha(sha, 5, phone,"1", "0", "102", "1");

            }
            List<Object> objects = pipelined.syncAndReturnAll();
            System.out.println(objects.size()+"====");

            for (int i = 0; i <list.size() ; i++) {
                System.out.println(list.get(i)+"======"+objects.get(i).toString());
                //pipelined.set(list.get(i),"1111111");
            }
            pipelined.sync();

        }catch(Exception e){
            e.printStackTrace();
            System.out.println("==========");
        }finally {
            JedisClientDev.returnJedis(jedis);
        }

    }

    @Test
    public void myTest(){
        Jedis jedis = null;
        Pipeline pipelined = null;

        try{
            jedis = JedisClientDev.getJedis();
            jedis.select(1);
            pipelined = jedis.pipelined();


            String eval = "redis.call('HSET',KEYS[2],KEYS[1],'1'); redis.call('HDEL',KEYS[3],KEYS[1]);";
            pipelined.eval(eval, 3, "234", "in", "out");

            pipelined.sync();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("==========");
        }finally {
            JedisClientDev.returnJedis(jedis);
        }
    }
}
