package cn.pancho.redis;

import config.PrintEnvironment;
import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author ：pancho
 * @date ：Created in 2020/8/11 10:38
 * @description :redis集群
 */
public class JedisCluterClient {


    /**
     * @return : void
     * @author : pancho
     * @date : 2020/8/13 10:21
     */
    @Test
    public void llen() {
        JedisCluster jedisCluster = getJedisCluster();

        String userReadyKey = "mop_message_quartz:{mop_sys_mkt_activity_batch}_message_target_user_ready_99";
        String userIntercepted = "mop_message_quartz:{mop_sys_mkt_activity_batch}_message_target_user_intercepted_99";

        String userPushedKey = "mop_message_quartz:mop_sys_mkt_activity_{batch}_message_target_user_pushed_99";
        String msgBatchKey = "ready_{batch}_send_1065837";


        Long llen = jedisCluster.llen(msgBatchKey);
        System.out.println("llen==" + llen);


        jedisCluster.close();
    }


    /**
     * 测试集群下的pipe管道操作
     *
     * @return : void
     * @author : pancho
     * @date : 2020/8/13 10:20
     */
    @Test
    public void pipe() {
        JedisCluster jedisCluster = getJedisCluster();


        List<String> SLOT_IMPORT_KEY = new ArrayList();
        SLOT_IMPORT_KEY.add("{sms}");


        List<String> argvList = new ArrayList();
        argvList.add("15810860391");
        argvList.add("1");
        argvList.add("1");
        argvList.add("1393");
        argvList.add("1");

        Object eval = jedisCluster.evalsha("36f614de69dc8c324ec57e65c925a3ffe72fcd2f", SLOT_IMPORT_KEY, argvList);
        System.out.println("eval===" + eval.toString());

        jedisCluster.close();
    }

    public JedisCluster getJedisCluster() {
        String cluster_addr = "47.104.184.185:6384,47.104.184.185:6385,47.104.184.185:6386,47.104.184.185:6387,47.104.184.185:6388,47.104.184.185:6389";
        String passWord = "111111";
        String clientName = "pancho";

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMinIdle(100);
        config.setMaxIdle(500);
        config.setMaxTotal(1000);
        config.setMinEvictableIdleTimeMillis(30000);
        config.setTimeBetweenEvictionRunsMillis(60000);
        config.setSoftMinEvictableIdleTimeMillis(-1);
        config.setTestOnBorrow(true);
        config.setTestWhileIdle(true);
        config.setTestOnReturn(false);
        config.setMaxWaitMillis(5000);


        String[] clusterAddr = cluster_addr.split(",");
        Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
        String[] ipPortArr = null;
        for (String addr : clusterAddr) {
            PrintEnvironment.queue.offer("连接redis集群地址：" + addr);
            ipPortArr = addr.split(":");
            jedisClusterNodes.add(new HostAndPort(ipPortArr[0], Integer.parseInt(ipPortArr[1])));
        }

        JedisCluster jedisCluster = new JedisCluster(jedisClusterNodes, 5000, 5000, 5, passWord, clientName, config);

        return jedisCluster;

    }


}
