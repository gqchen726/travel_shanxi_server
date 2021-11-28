package com.briup.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;

import java.net.URI;

@Component
public class RedisUtil {

    private final Logger logger = LoggerFactory.getLogger(RedisUtil.class);
    private static Jedis jedis;
//    private static String HOST = "ec2-3-88-74-91.compute-1.amazonaws.com";
//    private static int PORT = 25360;
    private static String HOST = "110.42.157.115";
    private static int PORT = 6379;

    private void openConnect(){
        URI uri = null;
        try {
//            uri = new URI("redis://:p257e44183eec6c4afa627e187812eceb2efde632b874b6cfd4a5942e4f73a943@ec2-3-88-74-91.compute-1.amazonaws.com:25360");
            uri = new URI("redis://110.42.157.115:6379");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (uri.equals(null)) {
            throw new RuntimeException("URI创建失败");
        }
//        HostAndPort hostAndPort = new HostAndPort(HOST, PORT);
        jedis = new Jedis(uri);
        String ping = jedis.ping();
        logger.info("redis 连接测试: {}",ping);
    }

    public Jedis getConnect(){
        if(null == jedis){
//            jedis = new Jedis();
            openConnect();
        }
        return jedis;
    }

    public boolean put(String key,String value) {
        Jedis connect = getConnect();
        try {
            connect.set(key,value);
            connect.expire(key,300);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    public Object get(String key) {
        Jedis connect = getConnect();
        if (connect.exists(key)) {
            return connect.get(key);
        }
        return null;
    }


}
