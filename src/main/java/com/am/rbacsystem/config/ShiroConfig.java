package com.am.rbacsystem.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.am.rbacsystem.realm.CustomRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    //1.配置shiroFilter过滤器
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean filterFactoryBean=new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(securityManager);
        Map map=new LinkedHashMap();
        map.put("/login.html","anon");
        map.put("/user/doLogin","anon");
        map.put("/css/**","anon");
        map.put("/js/**","anon");
        map.put("/logout","logout");
        map.put("/**","authc");
        filterFactoryBean.setFilterChainDefinitionMap(map);
        filterFactoryBean.setLoginUrl("login.html");
        filterFactoryBean.setUnauthorizedUrl("403.html");
        return filterFactoryBean;
    }
    //2.配置安全管理器
    @Bean
    public SecurityManager securityManager(CustomRealm realm){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        return  securityManager;
    }

    //3.配置realm
    @Bean
    public CustomRealm customRealm(HashedCredentialsMatcher matcher,MemoryConstrainedCacheManager cacheManager){
        CustomRealm realm=new CustomRealm();
        realm.setCredentialsMatcher(matcher);
        realm.setCacheManager(cacheManager);
        return  realm;
    }

    //4.配置密码比较器,注入到realm中
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher matcher=new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(2);
        return  matcher;
    }
    //5.配置缓存器，注入到realm中
    @Bean
    public MemoryConstrainedCacheManager memoryConstrainedCacheManager(){
        MemoryConstrainedCacheManager cacheManager=new MemoryConstrainedCacheManager();
        return  cacheManager;
    }

    //6.shiro——thymeleaf支持
    @Bean
    public ShiroDialect shiroDialect(){
        ShiroDialect shiroDialect=new ShiroDialect();
        return  shiroDialect;
    }


    //7针对类进行aop代理，因为shiro在使用注解的方式来实现对 请求进行验证
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator proxyCreator=new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    //8.是注解生效的通知
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor advisor=new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return  advisor;
    }
}
