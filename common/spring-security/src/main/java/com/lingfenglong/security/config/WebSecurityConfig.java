package com.lingfenglong.security.config;

import com.lingfenglong.security.custom.PasswordEncoderImpl;
import com.lingfenglong.security.filter.TokenAuthenticationFilter;
import com.lingfenglong.security.filter.TokenLoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig implements WebSecurityConfigurer<WebSecurity> {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoderImpl passwordEncoder;
    @Autowired
    public WebSecurityConfig(UserDetailsService userDetailsServiceImpl, PasswordEncoderImpl passwordEncoder) {
        this.userDetailsService = userDetailsServiceImpl;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return passwordEncoder;
    }

    @Bean
    public AuthenticationManager myAuthenticationManager(AuthenticationManagerBuilder builder) throws Exception {
        builder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
        return builder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        http
                .authenticationManager(authenticationManager)
                .csrf(AbstractHttpConfigurer::disable)  // 关闭csrf跨站请求伪造
                .cors(Customizer.withDefaults())        // 开启跨域以便前端调用接口
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/admin/system/index/login").permitAll()   // 指定某些接口不需要通过验证即可访问
                        .anyRequest().authenticated()   // 其它所有接口需要认证才能访问
                );
//                // TokenAuthenticationFilter放到UsernamePasswordAuthenticationFilter的前面
//                // 这样做就是为了除了登录的时候去查询数据库外，其他时候都用token进行认证
//                .addFilterBefore(new TokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
//                .addFilter(new TokenLoginFilter(authenticationManager))
//                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS));   // 禁用session

        return http.build();
    }

    @Override
    public void init(WebSecurity builder) throws Exception {

    }

    @Override
    public void configure(WebSecurity builder) throws Exception {
        builder
                .ignoring().requestMatchers("/favicon.ico","/swagger-resources/**", "/webjars/**",
                        "/v2/**", "/swagger-ui.html/**", "/doc.html");
    }
}
