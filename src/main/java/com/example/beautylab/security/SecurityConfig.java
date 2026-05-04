package com.example.beautylab.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
            .csrf(csrf->csrf.disable()) // Desactivacion CSRF porque es una API REST
            .authorizeHttpRequests(auth ->auth
                .requestMatchers("/api/usuarios/registro").permitAll()//registro publico
                .anyRequest().authenticated() // Cualquier otra ruta requiere autenticacion
            );
        return http.build();

    }

}
