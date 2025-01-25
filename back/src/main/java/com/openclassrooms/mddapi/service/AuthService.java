package com.openclassrooms.mddapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.AuthResponseDto;
import com.openclassrooms.mddapi.dto.UserProfilDTO;
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

  public AuthResponseDto authenticate(String usernameOrEmail, String password) {
    User user = userRepository.findByUsername(usernameOrEmail);
    if (user == null) {
      user = userRepository.findByEmail(usernameOrEmail);
      if (user == null) {
        throw new RuntimeException("Aucun compte trouvé avec cet identifiant ou email");
      }
    }
    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new RuntimeException("Mot de passe incorrect");
    }
    String token = jwtService.generateToken(user);
    return new AuthResponseDto("success", "Connexion réussie", token, user.getUsername());
  }

  public AuthResponseDto register(String username, String password, String email) {
    if (userRepository.findByEmail(email) != null) {
      throw new RuntimeException("L'utilisateur existe déjà");
    }

    User newUser = new User();
    newUser.setUsername(username);
    newUser.setPassword(passwordEncoder.encode(password));
    newUser.setEmail(email);

    userRepository.save(newUser);

    String token = jwtService.generateToken(newUser);
    return new AuthResponseDto("success", "Inscription réussie", token, username);
  }

  public UserProfilDTO getUserProfile(String username) {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      user = userRepository.findByUsername(username);
      if (user == null) {
        throw new RuntimeException("Utilisateur non trouvé");
      }
    }

    // Mapper les thèmes en une liste de chaînes (par exemple, noms de thèmes)
    List<String> themeNames = user.getThemes().stream()
        .map(theme -> theme.getTitle())
        .collect(Collectors.toList());

    return new UserProfilDTO(user.getUsername(), user.getEmail(), themeNames);
  }

}