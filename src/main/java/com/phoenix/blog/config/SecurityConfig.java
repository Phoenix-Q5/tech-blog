package com.phoenix.blog.config;

import com.phoenix.blog.model.JwtPair;
import com.phoenix.blog.service.JwtService;
import com.phoenix.blog.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtService jwtService;

    @Bean
    SecurityFilterChain filter(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/api/public/**", "/actuator/**").permitAll()
                        .anyRequest().authenticated())
                .oauth2Login(o -> o
                        .successHandler(this::successHandler))
                .build();
    }

    private void successHandler(HttpServletRequest req, HttpServletResponse res,
                                Authentication auth) throws IOException {

        JwtPair pair = jwtService.issueTokens(auth);
        CookieUtil.send(pair, res);

        res.sendRedirect("http://localhost:5173");  // back to React
    }

}
