package com.gnq.base.config;

import com.gnq.base.consts.ConstParams;
import com.gnq.base.domain.ShiroUrl;
import com.gnq.base.mapper.ShiroUrlMapper;
import com.gnq.base.shiro.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j


@Configuration
@ConditionalOnProperty(name = "shiro.switch", havingValue = "on", matchIfMissing = true)
public class ShiroConfig {

    @Resource
    private ShiroUrlMapper shiroUrlMapper;

    @Bean("credentialMatcher")
    public CredentialMatcher credentialMatcher() {
        return new CredentialMatcher();
    }

    @Bean
    public ShiroRealm baseShiroRealm(){
        ShiroRealm baseShiroRealm = new ShiroRealm();
        baseShiroRealm.setCredentialsMatcher(credentialMatcher());
        return baseShiroRealm;
    }

    @Bean
    public PhoneRealm phoneRealm(){
        PhoneRealm phoneRealm = new PhoneRealm();
//        phoneRealm.setCacheManager(shiroCacheManager());
        return phoneRealm;
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
        realmList.add(phoneRealm());
        realmList.add(baseShiroRealm());
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealms(realmList);
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        List<ShiroUrl> shiroUrls = shiroUrlMapper.selectList(null);
        List<ShiroUrl> limitFilterUrls = shiroUrls.stream().filter(e -> ConstParams.AUTH_LIMIT_FILTER_STR.equals(e.getFilterType())).collect(Collectors.toList());
        List<ShiroUrl> baseFilterUrls = shiroUrls.stream().filter(e -> ConstParams.AUTH_BASE_FILTER_STR.equals(e.getFilterType())).collect(Collectors.toList());
        List<ShiroUrl> anonFilterUrls = shiroUrls.stream().filter(e -> ConstParams.AUTH_ANON_FILTER_STR.equals(e.getFilterType())).collect(Collectors.toList());

        Map<String, String> filterChainDefinitionMap = new HashMap<>();

        log.info("************* load limitAuthenticationFilter *************");
        for(ShiroUrl shiroUrl : limitFilterUrls) {
            filterChainDefinitionMap.put(shiroUrl.getUrl(), ConstParams.AUTH_LIMIT_FILTER_STR);
        }

        log.info("************* load baseAuthenticationFilter *************");
        for(ShiroUrl shiroUrl : baseFilterUrls) {
            filterChainDefinitionMap.put(shiroUrl.getUrl(), ConstParams.AUTH_BASE_FILTER_STR);
        }

        log.info("************* load anon *************");
        for(ShiroUrl shiroUrl : anonFilterUrls) {
            filterChainDefinitionMap.put(shiroUrl.getUrl(), ConstParams.AUTH_ANON_FILTER_STR);
        }

        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put(ConstParams.AUTH_LIMIT_FILTER_STR, limitAuthenticationFilter());
        filterMap.put(ConstParams.AUTH_BASE_FILTER_STR, baseAuthenticationFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean("baseAuthenticationFilter")
    public BaseAuthenticationFilter baseAuthenticationFilter(){
        return new BaseAuthenticationFilter();
    }

    @Bean("limitAuthenticationFilter")
    public LimitAuthenticationFilter limitAuthenticationFilter(){
        return new LimitAuthenticationFilter();
    }


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