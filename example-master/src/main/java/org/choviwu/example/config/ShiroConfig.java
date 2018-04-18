package org.choviwu.example.config;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.*;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.config.ShiroConfiguration;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.choviwu.example.shiro.MyRealm;
import org.choviwu.example.shiro.ShiroFilterMap;
import org.choviwu.example.shiro.ShiroPermissionFilter;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ChoviWu on 2018/04/10
 * Description:
 */
@Configuration
public class ShiroConfig extends ShiroConfiguration {
    /**
     * 登录凭证
     * @return
     */
    @Bean
    public AuthorizingRealm authorizingRealm(RedisCacheManager redisCacheManager){
        AuthorizingRealm authorizingRealm = new MyRealm();
        authorizingRealm.setCacheManager(redisCacheManager);
        return authorizingRealm;
    }


    /**
     * 静态资源权限过滤
     * @return
     */
    @Bean
    public ShiroFilterMap filterMap(){
        return new ShiroFilterMap();
    }

    /**
     * Cookie
     * @return
     */
    @Bean
    public SimpleCookie cookie(){
        SimpleCookie cookie = new SimpleCookie();
        cookie.setHttpOnly(true);
        cookie.setMaxAge(7*3600);
        cookie.setPath("/");
        return cookie;
    }

    @Bean
    public RememberMeManager rememberMeManager(Cookie cookie){
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        rememberMeManager.setCookie(cookie);
//        rememberMeManager.setCipherKey(Base64.getDecoder().decode("xxxxx"));//解密
        return rememberMeManager;
    }

    /**
     * 保证实现了Shiro内部lifecycle函数的bean执行
     */
    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 安全管理器
     * @param authorizingRealm   凭证
     * @param rememberMeManager  记住凭证
     * @param cacheManager   缓存管理
     * @return
     */
    @Bean
    public SecurityManager securityManager(
            AuthorizingRealm authorizingRealm,
            RememberMeManager rememberMeManager,
            SessionManager sessionManager,
            CacheManager cacheManager){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRememberMeManager(rememberMeManager);
        securityManager.setRealm(authorizingRealm);
        securityManager.setSessionManager(sessionManager);
        securityManager.setCacheManager(cacheManager);
        return securityManager;
    }


    /**
     * shiro 过滤器管理bean  用于对所有的资源url过滤
     * @param securityManager
     * @param filterMap
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager,
                                                         ShiroFilterMap filterMap,
                                                         ShiroPermissionFilter permissionFilter){
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setLoginUrl("/login");
        filterFactoryBean.setUnauthorizedUrl("/refuse");
        filterFactoryBean.setSecurityManager(securityManager);
        List<String> filters = filterMap.getAnon();
        Map map = new HashMap();
        //过滤静态资源
        for (String str: filters){
            map.put(str,"anon");
        }
        //过滤数据库需要配置的公共资源 从DB查取
        filterFactoryBean.setFilterChainDefinitionMap(map);
        Map<String,Filter> filterMaps = new HashMap<>();
        filterMaps.put("permissionFilter",permissionFilter);
        filterFactoryBean.setFilters(filterMaps);
        return filterFactoryBean;
    }

    /**
     * redis缓存
     * @param redisManager
     * @return
     */
    @Bean("cacheManager")
    public RedisCacheManager cacheManager(RedisManager redisManager){
        RedisCacheManager cacheManager = new RedisCacheManager();
        cacheManager.setRedisManager(redisManager);
        return cacheManager;
    }

    /**
     * 整合redis shiro web缓存机制
     * @param redisManager
     * @return
     */
    @Bean
    public RedisSessionDAO redisSessionDAO(RedisManager redisManager){
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager);
        return redisSessionDAO;
    }

    /**
     * web Session管理 redis shiro web缓存
     * @param cookie
     * @param redisSessionDAO
     * @return
     */
    @Bean
    public DefaultWebSessionManager sessionManager(Cookie cookie,RedisSessionDAO redisSessionDAO){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionIdCookie(cookie);
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setSessionDAO(redisSessionDAO);
        return sessionManager;
    }

    /**
     * 启用所有的自动代理 注解方式 扫描 aop
     * @return
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator  advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }


}
