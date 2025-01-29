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

  /**
   * Génère un token JWT pour un utilisateur donné.
   * 
   * @param user L'utilisateur pour lequel le token est généré
   * @return Le token JWT généré
   */
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

  /**
   * Valide un token JWT et récupère le nom d'utilisateur.
   * 
   * @param token Le token JWT à valider
   * @return Le nom d'utilisateur associé au token, ou null si le token est
   *         invalide
   */
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
