package com.chencorp.ufit.security;

import com.chencorp.ufit.filter.AuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig {

    @Autowired
    private AuthTokenFilter authTokenFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // pastikan csrf disable jika menggunakan stateless
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/service/login", "/service/register").permitAll() // hanya satu route login
                .anyRequest().authenticated() // route lainnya perlu autentikasi
            )
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // stateless session

        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class); // pastikan filter token diterapkan setelah login
        return http.build();
    }
}

