package com.openclassrooms.mddapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.entity.User;
import com.openclassrooms.mddapi.repository.UserRepository;

@Service
public class AuthService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtService jwtService;

  public String authenticate(String username, String password) {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new RuntimeException("Nom d'utilisateur introuvable");
    }
    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new RuntimeException("Mot de passe incorrect");
    }
    return jwtService.generateToken(user);
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