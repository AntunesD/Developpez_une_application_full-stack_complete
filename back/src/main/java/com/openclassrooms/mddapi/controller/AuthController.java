package com.openclassrooms.mddapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.openclassrooms.mddapi.dto.AuthResponseDto;
import com.openclassrooms.mddapi.dto.LoginDto;
import com.openclassrooms.mddapi.dto.RegisterDto;
import com.openclassrooms.mddapi.service.AuthService;

/**
 * Contrôleur pour l'authentification et l'inscription des utilisateurs.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private AuthService authService;

  /**
   * Authentifie un utilisateur avec son identifiant (nom d'utilisateur ou email)
   * et son mot de passe.
   * 
   * @param userDto Contient les informations de connexion.
   * @return Un token JWT en cas de succès, une erreur sinon.
   */
  @PostMapping("/login")
  public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto userDto) {
    try {
      return ResponseEntity.ok(authService.authenticate(userDto.getUsernameOrEmail(), userDto.getPassword()));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(
          new AuthResponseDto("error", e.getMessage(), null, null));
    }
  }

  /**
   * Inscrit un nouvel utilisateur.
   * 
   * @param userDto Contient les informations d'inscription.
   * @return Un token JWT en cas de succès, une erreur sinon.
   */
  @PostMapping("/register")
  public ResponseEntity<AuthResponseDto> register(@RequestBody RegisterDto userDto) {
    try {
      return ResponseEntity.ok(authService.register(userDto.getUsername(), userDto.getPassword(), userDto.getEmail()));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(
          new AuthResponseDto("error", e.getMessage(), null, null));
    }
  }
}
