package com.openclassrooms.mddapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.AuthResponseDto;
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

  /**
   * Authentifie un utilisateur en vérifiant son identifiant (username ou email)
   * et son mot de passe.
   * 
   * @param usernameOrEmail Identifiant ou email de l'utilisateur
   * @param password        Mot de passe de l'utilisateur
   * @return AuthResponseDto Contient le token et un message de réponse
   */
  public AuthResponseDto authenticate(String usernameOrEmail, String password) {
    User user = userRepository.findByUsername(usernameOrEmail);
    if (user == null) {
      user = userRepository.findByEmail(usernameOrEmail);
      if (user == null) {
        throw new RuntimeException("Aucun compte trouvé avec cet identifiant ou email");
      }
    }
    // Vérification du mot de passe
    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new RuntimeException("Mot de passe incorrect");
    }
    String token = jwtService.generateToken(user);
    return new AuthResponseDto("success", "Connexion réussie", token, user.getUsername());
  }

  /**
   * Enregistre un nouvel utilisateur dans le système.
   * 
   * @param username Nom d'utilisateur
   * @param password Mot de passe
   * @param email    Email de l'utilisateur
   * @return AuthResponseDto Contient le token et un message de réponse
   */
  public AuthResponseDto register(String username, String password, String email) {
    if (userRepository.findByEmail(email) != null) {
      throw new RuntimeException("L'utilisateur existe déjà");
    }

    // Création d'un nouvel utilisateur
    User newUser = new User();
    newUser.setUsername(username);
    newUser.setPassword(passwordEncoder.encode(password)); // Mot de passe encodé
    newUser.setEmail(email);

    userRepository.save(newUser);

    String token = jwtService.generateToken(newUser);
    return new AuthResponseDto("success", "Inscription réussie", token, username);
  }

}
