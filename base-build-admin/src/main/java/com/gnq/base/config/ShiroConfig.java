package com.gnq.base.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.*;

@Slf4j
@Configuration
@ConditionalOnProperty(name = "shiro.switch", havingValue = "on", matchIfMissing = true)
public class ShiroConfig {

    @Bean("credentialMatcher")
    public CredentialMatcher credentialMatcher() {
        return new CredentialMatcher();
    }

    @Bean
    public ShiroRealm scxShiroRealm(){
        ShiroRealm scxShiroRealm = new ShiroRealm();
        scxShiroRealm.setCredentialsMatcher(credentialMatcher());
        return scxShiroRealm;
    }

//    @Bean
//    public HashedCredentialsMatcher hashedCredentialsMatcher() {
//        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
//        hashedCredentialsMatcher.setHashAlgorithmName("md5");
//        hashedCredentialsMatcher.setHashIterations(3);
//        return hashedCredentialsMatcher;
//    }

    @Bean
    public SecurityManager securityManager() {
        List<Realm> realmList = new ArrayList<>();
//        realmList.add(phoneRealm());
//        realmList.add(scxShiroRealm());
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealms(realmList);
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> filterChainDefinitionMap = new HashMap<>();

        log.info("*************加载scxFormAuthenticationFilter*************");
        filterChainDefinitionMap.put("", "");

        log.info("*************加载anon*************");
//        filterChainDefinitionMap.put("/user/register","anon");
//        filterChainDefinitionMap.put("/user/login","scxFormAuthenticationFilter");
        Map<String, Filter> filterMap = new LinkedHashMap<>();
//        filterMap.put("", shiroFormAuthenticationFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

//    @Bean("scxFormAuthenticationFilter")
//    public ScxFormAuthenticationFilter shiroFormAuthenticationFilter() {
//        return new ScxFormAuthenticationFilter();
//    }

//    @Bean
//    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
//        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
//        advisorAutoProxyCreator.setProxyTargetClass(true);
//        return advisorAutoProxyCreator;
//    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}