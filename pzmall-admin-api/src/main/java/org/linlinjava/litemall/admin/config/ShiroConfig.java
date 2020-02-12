package org.linlinjava.litemall.admin.config;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.linlinjava.litemall.admin.shiro.AdminAuthorizingRealm;
import org.linlinjava.litemall.admin.shiro.AdminWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public Realm realm() {
        return new AdminAuthorizingRealm();
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        filterChainDefinitionMap.put("/adminapi/auth/login", "anon");
        filterChainDefinitionMap.put("/adminapi/auth/code", "anon");
        filterChainDefinitionMap.put("/adminapi/auth/401", "anon");
        filterChainDefinitionMap.put("/adminapi/auth/index", "anon");
        filterChainDefinitionMap.put("/adminapi/auth/403", "anon");
        filterChainDefinitionMap.put("/adminapi/storage/fetch/**", "anon");
        filterChainDefinitionMap.put("/adminapi/storage/create/**", "anon");
        filterChainDefinitionMap.put("/adminapi/storage/create/**", "anon");
        filterChainDefinitionMap.put("/adminapi/outsideuser/listarea/**", "anon");
        filterChainDefinitionMap.put("/adminapi/outsideuser/liststreet/**", "anon");
        filterChainDefinitionMap.put("/adminapi/outsideuser/listcommunity/**", "anon");

        filterChainDefinitionMap.put("/adminapi/knowledge/download", "anon");

        filterChainDefinitionMap.put("/adminapi/**", "authc");
        shiroFilterFactoryBean.setLoginUrl("/adminapi/auth/401");
        shiroFilterFactoryBean.setSuccessUrl("/adminapi/auth/index");
        shiroFilterFactoryBean.setUnauthorizedUrl("/adminapi/auth/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public SessionManager sessionManager() {
        AdminWebSessionManager defaultWebSessionManager = new AdminWebSessionManager();
        defaultWebSessionManager.setGlobalSessionTimeout(1000 * 60 * 60 * 24);// 会话过期时间，单位：毫秒(在无操作时开始计时)--->一分钟,用于测试
        defaultWebSessionManager.setSessionValidationSchedulerEnabled(true);
        defaultWebSessionManager.setSessionIdCookieEnabled(true);
        return defaultWebSessionManager;
//        return new AdminWebSessionManager();
    }

    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm());
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor =
                new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public static DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }
}
