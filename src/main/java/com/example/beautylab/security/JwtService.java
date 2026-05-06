package com.example.beautylab.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${JWT.SECRET}")
    private String SECRET;

    private  SecretKey getKey(){
         //convertir el SECRET en una llave real para HS256
         return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    //metodo para generar token
    public String generarToken(String username){
        return Jwts.builder()
        .setSubject(username)//define nombre de usuario dentro del token
        .setIssuedAt(new Date(System.currentTimeMillis()))//define la fecha de creacion
        .setExpiration(new Date(System.currentTimeMillis()+ 1000 * 60 * 60 * 10 ))
        .signWith(getKey())//metodo de encriptacion
        .compact();//metodo de compactacion

    }

    //metodo para extraer el username del token
    public String extractUsername(String token){
        return Jwts.parserBuilder()
        .setSigningKey(getKey())
        .build()
        .parseClaimsJws(token)// Aquí se procesa la firma y el token
        .getBody()// Aquí obtiene el cuerpo (Claims)
        .getSubject(); // Aquí obtiene el valor del "subject", que es el username
        
    }
}
