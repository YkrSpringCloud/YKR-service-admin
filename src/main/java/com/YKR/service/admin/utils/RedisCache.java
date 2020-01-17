package com.YKR.service.admin.utils;

import com.YKR.service.admin.context.ApplicationContextHolder;
import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Author:ykr
 * Date:2020/1/16
 * Description:redis缓存工具类
 */
public class RedisCache implements Cache {
    private static final Logger logger=LoggerFactory.getLogger(RedisCache.class);
    private final ReadWriteLock readWriteLock=new ReentrantReadWriteLock();
    private final String id;//cache instance id
    private RedisTemplate redisTemplate;
    private static final long EXPIRE_TIME_IN_MINUTES=30; //redis过期时间
    public RedisCache(String id) {
        if (id==null){
            try {
                throw new IllegalAccessException("Cache instances require an ID");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        this.id=id;
        logger.debug("创建了mybatis的redis缓存"+id);
        System.out.print("\n\n创建了mybatis的redis缓存\n\n");

    }
    @Override
    public String getId() {
        return id;
    }
    @Override
    public void putObject(Object o, Object o1) {
       try{
           RedisTemplate redisTemplate = getRedisTemplate();
           //redisTemplate进行value操作（key-string）
           ValueOperations opsForValue = redisTemplate.opsForValue();
           //进行字符串操作，放入key和value，redis过期时间=EXPIRE_TIME_IN_MINUTES
           opsForValue.set(o, o1, EXPIRE_TIME_IN_MINUTES, TimeUnit.MINUTES);
           logger.debug("redis cache "+id+" put key: "+o.toString()+" value: "+o1.toString());
           System.out.print("\n\nredis cache\n\n");
       } catch (Throwable t){
           logger.error("Redis put failed",t);
           System.out.print("\n\nRedis put failed\n\n");
       }
    }
    @Override
    public Object getObject(Object o) {
        try{
            RedisTemplate redisTemplate = getRedisTemplate();
            ValueOperations opsForValue = redisTemplate.opsForValue();
            logger.debug("Get cached query result from redis");
            System.out.print("\n\nGet cached query result from redis\n\n");
            //得到key的value
            return opsForValue.get(o);
        }catch(Throwable t){
            logger.error("Redis get failed,fail over to db",t);
            System.out.print("\n\nRedis get failed,fail over to db\n\n");
            return null;
        }
    }
    @Override
    @SuppressWarnings("unchecked")
    public Object removeObject(Object o) {
       try{
           RedisTemplate redisTemplate = getRedisTemplate();
           //删除这个key
           redisTemplate.delete(o);
           logger.debug("remove cached query result from redis");
           System.out.print("\n\nremove cached query result from redis\n\n");
           return null;
       } catch (Throwable t){
           logger.error("Redis remove failed",t);
           System.out.print("\n\nRedis remove failed\n\n");
       }
       return null;
    }
    @Override
    public void clear() {
        RedisTemplate redisTemplate = getRedisTemplate();
        redisTemplate.execute((RedisCallback) connection -> {
            connection.flushDb();
            return null;
        });
        logger.debug("Clear all the cached query result from redis");
        System.out.print("\n\nClear all the cached query result from redis\n\n");

    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

    private RedisTemplate getRedisTemplate() {
        if (redisTemplate==null){
            redisTemplate= ApplicationContextHolder.getBean("redisTemplate");
        }
        return redisTemplate;

    }
}
