package com.openclassrooms.mddapi.service;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

  private final Key SECRET_KEY = Keys.hmacShaKeyFor("votre_clé_secrète_longue_et_sécurisée".getBytes());

  public String generateToken(User user) {
    try {
      Date now = new Date();
      Date expiryDate = new Date(System.currentTimeMillis() + 86400000);

      String token = Jwts.builder()
          .subject(user.getUsername())
          .issuedAt(now)
          .expiration(expiryDate)
          .signWith(SECRET_KEY)
          .compact();

      return token;

    } catch (Exception e) {
      System.err.println("Erreur lors de la génération du token: " + e.getMessage());
      e.printStackTrace();
      throw new RuntimeException("Erreur lors de la génération du token", e);
    }
  }

  public String validateTokenAndGetUsername(String token) {
    try {
      SecretKey secretKey = Keys.hmacShaKeyFor("votre_clé_secrète_longue_et_sécurisée".getBytes());
      Claims claims = Jwts.parser()
          .verifyWith(secretKey)
          .build()
          .parseSignedClaims(token)
          .getPayload();

      return claims.getSubject();
    } catch (Exception e) {
      return null;
    }
  }
}