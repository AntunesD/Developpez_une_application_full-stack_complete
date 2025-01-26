package com.openclassrooms.mddapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.AuthResponseDto;
import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.dto.UserProfilDTO;
import com.openclassrooms.mddapi.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

  @Autowired
  private UserService userService;

  // Récupérer le profil de l'utilisateur
  @GetMapping("")
  public ResponseEntity<UserProfilDTO> getUserProfile(Authentication authentication) {
    String username = authentication.getName();
    try {
      return ResponseEntity.ok(userService.getUserProfile(username));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(null);
    }
  }

  // Mettre à jour le profil de l'utilisateur
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
