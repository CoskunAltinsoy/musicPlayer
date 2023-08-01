package com.atmosware.musicplayer.config;

import com.atmosware.musicplayer.security.AuthEntryPointJwt;
import com.atmosware.musicplayer.security.CustomUserDetailService;
import com.atmosware.musicplayer.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    private final CustomUserDetailService customUserDetailService;
    @Autowired
    private final AuthEntryPointJwt unauthorizedHandler;

    @Autowired
    public WebSecurityConfig(
            CustomUserDetailService customUserDetailService,
            AuthEntryPointJwt unauthorizedHandler) {
        this.customUserDetailService = customUserDetailService;
        this.unauthorizedHandler = unauthorizedHandler;
    }
    @Bean
    public JwtAuthenticationFilter authenticationJwtTokenFilter() {
        return new JwtAuthenticationFilter();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(customUserDetailService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager
            (AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/register/**","/api/users/login/**").permitAll()
                        .requestMatchers("/api/users/create-demand-artist/**").hasAuthority("USER")
                        .requestMatchers("/api/users/create-approval-artist/**").hasAuthority("ADMIN")
                        .requestMatchers("/api/users/id/**").hasAuthority("ADMIN")
                        .requestMatchers("/api/users/getall").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/users/**").hasAuthority("ADMIN")
                        .requestMatchers("/api/users/change-password/**").hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers("/api/users/forgot-password/**").hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers("/api/users/reset-password/**").hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers("/api/users/follow-user/**").hasAuthority("USER")
                        .requestMatchers("/api/users/unfollow-user/**").hasAuthority("USER")
                        .requestMatchers("/api/users/follow-artist/**").hasAuthority("USER")
                        .requestMatchers("/api/users/unfollow-artist/**").hasAuthority("USER")

                        .requestMatchers("/api/albums").hasAnyAuthority("ADMIN","ARTIST")
                        .requestMatchers("/api/artists").hasAnyAuthority("ADMIN","ARTIST")
                        .requestMatchers("/api/genres").hasAuthority("ADMIN")
                        .requestMatchers("/api/songs").hasAnyAuthority("ADMIN","ARTIST")
                        .requestMatchers("/api/test/**").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
                        .requestMatchers("/api/roles/**","/api/roles").permitAll()
                        .anyRequest().authenticated());

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
