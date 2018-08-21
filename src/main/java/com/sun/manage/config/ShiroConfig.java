package com.sun.manage.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.sun.manage.service.sys.ShiroDbRealm;
import com.sun.manage.web.shiro.ShiroLoginFilter;
import com.sun.manage.web.shiro.ShiroPermsFilter;


/**
 * Created by Administrator on 2016/12/8.
 */
@Configuration
public class ShiroConfig {
    /**
     * FilterRegistrationBean
     * @return
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*");
        filterRegistration.setDispatcherTypes(DispatcherType.REQUEST);
        return filterRegistration;
    }

    /**
     * @see ShiroFilterFactoryBean
     * @return
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);
        bean.setLoginUrl("/login");
//        bean.setUnauthorizedUrl("/unauthor");//对注解权限无效

        Map<String, Filter>filters = new HashMap();
        filters.put("perms", new ShiroPermsFilter());
        filters.put("login", new ShiroLoginFilter());
        filters.put("anon", new AnonymousFilter());
        bean.setFilters(filters);

        Map<String, String> chains = new HashMap();
        chains.put("/favicon.ico", "anon");
        chains.put("/js/**", "anon");
        chains.put("/login", "anon");
        chains.put("/test", "login,perms");
        chains.put("/admin/**", "anon");
        chains.put("/task/**", "anon");
//        chains.put("/admin/**", "login,perms");
        chains.put("/**", "login,perms[manage]");//配置perms[manage]：需要manage的权限
        bean.setFilterChainDefinitionMap(chains);
        return bean;
    }
    /**
     * 缓存管理器
     * @return
     */
    @Bean(value="ehCacheManager")
    public EhCacheManager ehCacheManager() {
        EhCacheManager cacheManager = new EhCacheManager();                
        cacheManager.setCacheManagerConfigFile("classpath:cache/ehcache-shiro.xml");
        return cacheManager;
    }
    /**
     * @see org.apache.shiro.mgt.SecurityManager
     * @return
     */
    @Bean(name="securityManager")
    public DefaultWebSecurityManager securityManager(@Qualifier("ehCacheManager") EhCacheManager  ehCacheManager,@Qualifier("cookieRememberMeManager") CookieRememberMeManager cookieRememberMeManager) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(userRealm(ehCacheManager));
        // 设置rememberMe管理器
        manager.setRememberMeManager(cookieRememberMeManager);
     
        return manager;
    }
    /**
     * cookie对象;
     * 
     * @return
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        // 这个参数是cookie的名称，对应前端的checkbox 的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        // <!-- 记住我cookie生效时间30天（259200） ,单位秒;-->
        simpleCookie.setMaxAge(259200);
        return simpleCookie;
    }

    /**
     * 记住我管理器 cookie管理对象;
     * 
     * @return
     */
    @Bean(name = "cookieRememberMeManager")
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        return cookieRememberMeManager;
    }
    /**
     * 开启shiro aop注解支持. 使用代理方式;所以需要开启代码支持; Controller才能使用@RequiresPermissions
     * 
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            @Qualifier("securityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

//    /**
//     * @see
//     * @return
//     */
//    @Bean(name="sessionManager")
//    @DependsOn(value= "shrioRedisCacheManager")
//    public DefaultWebSessionManager defaultWebSessionManager(ShrioRedisCacheManager redisCacheManager) {
//        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
//        sessionManager.getSessionIdCookie().setPath("/");
//        sessionManager.getSessionIdCookie().setName("SHOP_CAR_SSIONID");
//        sessionManager.setCacheManager(redisCacheManager);
//        sessionManager.setGlobalSessionTimeout(1800000);
//        sessionManager.setDeleteInvalidSessions(true);
//        sessionManager.setSessionValidationSchedulerEnabled(true);
//        sessionManager.setDeleteInvalidSessions(true);
//        sessionManager.setSessionIdCookieEnabled(true);
//        return sessionManager;
//    }

    /**
     * @see --->AuthorizingRealm
     * @return
     */
    @Bean
    public ShiroDbRealm userRealm(EhCacheManager  ehCacheManager) {
        ShiroDbRealm userRealm = new ShiroDbRealm();
        // 设置缓存管理器
        userRealm.setCacheManager(ehCacheManager);
        return userRealm;
    }

    /**
     * 必须注释掉 不然会导致service层的注解事务配置失效
     */
    /*@Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }*/
}
