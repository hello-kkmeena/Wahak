package com.wahak.config;

import com.wahak.service.RiderUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;


/**
 * @author krishna.meena
 */

@Configuration
public class SecurityConfig {

    private final  RiderAuthenticationFilter riderAuthenticationFilter;
    private final RiderUserService riderUserService;


    public SecurityConfig(RiderAuthenticationFilter jwtAuthenticationFilter, RiderUserService userDetailsService) {
        this.riderAuthenticationFilter = jwtAuthenticationFilter;
        this.riderUserService = userDetailsService;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return  new ProviderManager(List.of(new DaoAuthenticationProvider() {{
            setUserDetailsService(riderUserService);
            setPasswordEncoder(passwordEncoder());
        }}));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/chalak/sendOtp").permitAll()
                        .requestMatchers("/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(riderAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
