package com.example.beautylab.security;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService; //inyeccion de dependencias del servicio de JWT para poder validar los tokens en cada peticion

    public JwtAuthenticationFilter(JwtService jwtService){ //inyeccion de dependencias
        this.jwtService = jwtService;//se inyecta el servicio de JWT para poder validar los tokens en cada peticion
     }

     @Override
     protected void doFilterInternal(HttpServletRequest request, //metodo que se ejecuta para cada peticion, es el encargado de validar el token y marcar al usuario como autenticado si el token es valido
                                     HttpServletResponse response,
                                     FilterChain filterChain)
        throws ServletException, IOException{ //este metodo se ejecuta para cada peticion, es el encargado de validar el token y marcar al usuario como autenticado si el token es valido

            System.out.println("Procesando: " + request.getRequestURI());
            final String authHeader = request.getHeader("Authorization"); //obtener el header de autorizacion, se espera que el token venga en el header "Authorization" con el formato "Bearer <token>"

            //1. Si no hay token, dejar pasar la peticion al siguiente filtro
            if(authHeader ==null || !authHeader.startsWith("Bearer ")){//si no hay token o el token no empieza con "Bearer", se deja pasar la peticion sin autenticar
                filterChain.doFilter(request, response);//dejar pasar la peticion al siguiente filtro
                return;
            }

            //2. Si hay token, extraerlo y validar
            String token = authHeader.substring(7); //remover "Bearer "
            String username = jwtService.extractUsername(token);//extraer el username del token, si el token es invalido, este metodo lanzara una excepcion y no se ejecutara el resto del codigo

            //3. Si el usuario existe y no está autenticado, se marca como autenticado (en este caso no se implementa la parte de cargar roles y permisos)
            if(username !=null && SecurityContextHolder.getContext().getAuthentication() == null){ //si el usuario no está autenticado
                List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(jwtService.extractRole(token)));
                UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(username, null, authorities);//crear un token de autenticacion con el username y los roles extraidos del token JWT, en este caso no se valida contra la base de datos, se asume que si el token es valido, el usuario es valido
                SecurityContextHolder.getContext().setAuthentication(authToken);//marcar como autenticado
            }

            filterChain.doFilter(request, response); //dejar pasar la peticion al siguiente filtro
    }
}
