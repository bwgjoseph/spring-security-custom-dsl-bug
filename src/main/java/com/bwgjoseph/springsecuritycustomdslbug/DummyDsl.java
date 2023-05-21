package com.bwgjoseph.springsecuritycustomdslbug;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

public class DummyDsl extends AbstractHttpConfigurer<DummyDsl, HttpSecurity> {
    @Override
    public void init(HttpSecurity http) throws Exception {
        http.formLogin(AbstractHttpConfigurer::disable);
    }

    public static DummyDsl dummyDsl() {
        return new DummyDsl();
    }
}
