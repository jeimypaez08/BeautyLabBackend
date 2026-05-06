package com.example.beautylab.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter; //inyeccion de dependencias del filtro de autenticacion JWT para que se ejecute en cada peticion y valide el token

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{//configuracion de seguridad, se define que rutas son publicas y cuales requieren autenticacion, ademas se agrega el filtro de autenticacion JWT para que se ejecute en cada peticion y valide el token
       return http 
            .csrf(csrf->csrf.disable()) // Desactivacion CSRF porque es una API REST
             .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))// Configuracion de sesiones, se establece que no se creen sesiones porque se usara JWT para autenticar
            .authorizeHttpRequests(auth ->auth // Configuracion de autorizacion, se definen las rutas publicas y las rutas que requieren autenticacion
                
                .requestMatchers(HttpMethod.POST,"/api/usuarios/registro").permitAll()//registro publico
                .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()//login publico  
                .anyRequest().authenticated() // Cualquier otra ruta requiere autenticacion
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)// Agregar el filtro de autenticacion JWT antes del filtro de autenticacion por username y password
            .build();
    }

}
