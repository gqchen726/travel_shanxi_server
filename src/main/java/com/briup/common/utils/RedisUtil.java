package com.briup.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
@Component
public class RedisUtil {

    private Logger logger = LoggerFactory.getLogger(RedisUtil.class);
    private static Jedis jedis;
    private static String HOST = "localhost";

    private void openConnect(){
        Jedis jedis = new Jedis("ec2-3-88-74-91.compute-1.amazonaws.com");
        String ping = jedis.ping();
        logger.info(ping);
    }

    public Jedis getConnect(){
        if(null == jedis){
            jedis = new Jedis();
        }
        return jedis;
    }


}
