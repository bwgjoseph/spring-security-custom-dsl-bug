package com.bwgjoseph.springsecuritycustomdslbug;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity(debug = true)
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain docsFilterChain(HttpSecurity http) throws Exception {
        return http
            // .apply(DummyDsl.dummyDsl())
            // .and()
            .build();
    }
}
