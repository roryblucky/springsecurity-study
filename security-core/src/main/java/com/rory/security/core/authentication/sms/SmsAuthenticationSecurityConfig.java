package com.rory.security.core.authentication.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class SmsAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private AuthenticationSuccessHandler successAuthenticationHandler;

    @Autowired
    private AuthenticationFailureHandler failureAuthenticationHandler;

    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        SmsAuthenticationFilter authenticationFilter = new SmsAuthenticationFilter();
        authenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        authenticationFilter.setAuthenticationSuccessHandler(successAuthenticationHandler);
        authenticationFilter.setAuthenticationFailureHandler(failureAuthenticationHandler);

        SmsAuthenticationProvider smsAuthenticationProvider = new SmsAuthenticationProvider();
        smsAuthenticationProvider.setUserDetailsService(userDetailsService);

        http.authenticationProvider(smsAuthenticationProvider)
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

//    private <T> T getDependency(ApplicationContext applicationContext, Class<T> dependencyType) {
//        try {
//            T dependency = applicationContext.getBean(dependencyType);
//            return dependency;
//        } catch (NoSuchBeanDefinitionException e) {
//            throw new IllegalStateException("SpringSocialConfigurer depends on " + dependencyType.getName() +". No single bean of that type found in application context.", e);
//        }
//    }
}
