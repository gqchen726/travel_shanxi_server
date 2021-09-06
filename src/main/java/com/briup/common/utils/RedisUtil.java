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
    private static String HOST = "ec2-3-88-74-91.compute-1.amazonaws.com";
    private static int PORT = 25360;

    private void openConnect(){
        URI uri = null;
        try {
            uri = new URI("redis://:p257e44183eec6c4afa627e187812eceb2efde632b874b6cfd4a5942e4f73a943@ec2-3-88-74-91.compute-1.amazonaws.com:25360");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (uri.equals(null)) {
            throw new RuntimeException("URI创建失败");
        }
        HostAndPort hostAndPort = new HostAndPort(HOST, PORT);
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


}
