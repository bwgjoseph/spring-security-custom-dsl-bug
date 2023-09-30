package com.bwgjoseph.springsecuritycustomdslbug;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

public class DummyDsl extends AbstractHttpConfigurer<DummyDsl, HttpSecurity> {
    @Override
    public void init(HttpSecurity http) throws Exception {
        http
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    }

    public static DummyDsl dummyDsl() {
        return new DummyDsl();
    }

    public HttpSecurity build() {
        return super.getBuilder();
    }
}
