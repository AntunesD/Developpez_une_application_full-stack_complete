package com.openclassrooms.mddapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.openclassrooms.mddapi.dto.AuthResponseDto;
import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.dto.UserProfilDTO;
import com.openclassrooms.mddapi.service.UserService;

/**
 * Contrôleur pour la gestion du profil utilisateur.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

  @Autowired
  private UserService userService;

  /**
   * Récupère le profil de l'utilisateur connecté.
   * 
   * @param authentication Informations sur l'utilisateur authentifié.
   * @return Le profil utilisateur ou une erreur.
   */
  @GetMapping("")
  public ResponseEntity<UserProfilDTO> getUserProfile(Authentication authentication) {
    String username = authentication.getName();
    try {
      return ResponseEntity.ok(userService.getUserProfile(username));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(null);
    }
  }

  /**
   * Met à jour le profil de l'utilisateur connecté.
   * 
   * @param userDTO        Données mises à jour du profil.
   * @param authentication Informations sur l'utilisateur authentifié.
   * @return Le profil mis à jour ou une erreur.
   */
  @PatchMapping("")
  public ResponseEntity<AuthResponseDto> updateUser(@RequestBody UserDTO userDTO,
      Authentication authentication) {
    String username = authentication.getName();
    try {
      AuthResponseDto updatedProfile = userService.updateUserProfile(username, userDTO);
      return ResponseEntity.ok(updatedProfile);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(null);
    }
  }
}
