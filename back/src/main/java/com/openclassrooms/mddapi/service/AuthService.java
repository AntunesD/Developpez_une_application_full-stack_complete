package com.openclassrooms.mddapi.service;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.entity.User;
import com.openclassrooms.mddapi.repository.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class AuthService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  private final Key SECRET_KEY = Keys.hmacShaKeyFor("votre_clé_secrète_longue_et_sécurisée".getBytes());

  public String authenticate(String username, String password) {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new RuntimeException("Nom d'utilisateur introuvable");
    }
    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new RuntimeException("Mot de passe incorrect");
    }
    return generateToken(user);
  }

  private String generateToken(User user) {
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

  public String register(String username, String password, String email) {

    // Vérifier si l'utilisateur existe déjà
    if (userRepository.findByEmail(email) != null) {
      throw new RuntimeException("L'utilisateur existe déjà");
    }

    // Créer un nouvel utilisateur
    User newUser = new User();
    newUser.setUsername(username);
    newUser.setPassword(passwordEncoder.encode(password)); // Encoder le mot de passe
    newUser.setEmail(email); // Ajouter l'email

    // Enregistrer l'utilisateur dans la base de données
    userRepository.save(newUser);

    return "Utilisateur enregistré avec succès"; // Message de succès
  }
}