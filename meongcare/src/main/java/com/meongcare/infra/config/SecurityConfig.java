package com.meongcare.infra.config;

import com.meongcare.common.DoubleSlashLoggingFilter;
import com.meongcare.common.jwt.JwtAuthenticationFilter;
import com.meongcare.common.jwt.JwtService;
import com.meongcare.domain.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    private static final String[] whiteList = {"/auth/**", "/actuator/**", "/swagger-ui/**", "/swagger-resources/**", "/swagger/**", "/v3/api-docs/**", "/error/**", "/notice/**", "/health"};


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> {
            web.ignoring().antMatchers(whiteList);
            web.httpFirewall(allowUrlEncodedDoubleSlashHttpFirewall());
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new DoubleSlashLoggingFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthenticationFilter(jwtService, memberRepository), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public HttpFirewall allowUrlEncodedDoubleSlashHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedDoubleSlash(true);
        return firewall;
    }
}
