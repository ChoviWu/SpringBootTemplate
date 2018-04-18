package org.choviwu.example.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.crazycake.shiro.RedisManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.DefaultRedisCachePrefix;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCachePrefix;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by ChoviWu on 2018/04/10
 * Description:Redis配置
 */
@Configuration
public class RedisConfig {

    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.timeout}")
    private int timeOut;
    @Value("${spring.redis.database}")
    private int database;


    @Bean("redisTemplate")
    public RedisTemplate<String, String> stringRedisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate(jedisConnectionFactory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        //序列化
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setKeySerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    /**
     * redis 管理
     * @return
     */
    @Bean
    public RedisManager redisManager(){
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setExpire(10*3600);
        redisManager.setPassword(password);
        return redisManager;
    }
    //Cache
    @Bean
    public RedisCacheManager cacheManager(RedisTemplate redisTemplate){
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        RedisCachePrefix prefix = new DefaultRedisCachePrefix();
        prefix.prefix("");
        cacheManager.setCachePrefix(prefix);
        return cacheManager;
    }

    //Jedis连接池配置
    @Bean
    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(15);
        poolConfig.setMaxTotal(120);
        poolConfig.setMinIdle(5);
        poolConfig.setMinEvictableIdleTimeMillis(0);
        poolConfig.setMaxWaitMillis(30000);
        return poolConfig;
    }

    /**
     * redis  连接工厂
     * @param config
     * @return
     */
    @Bean
    public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig config){
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
        connectionFactory.setDatabase(database);
        connectionFactory.setHostName(host);
        connectionFactory.setPassword(password);
        connectionFactory.setPort(port);
        connectionFactory.setPoolConfig(config);
        connectionFactory.setTimeout(timeOut);
        return connectionFactory;
    }

    @Bean
    public JedisPool jedisPool(JedisPoolConfig jedisPoolConfig){
        JedisPool jedisPool = new JedisPool(jedisPoolConfig,host,port,timeOut,password);
        return jedisPool;
    }

}
