package com.openclassrooms.mddapi.dto;

import lombok.Data;

@Data
public class UserDTO {
  private String username;
  private String email;
  private String password; // Ne pas inclure le mot de passe en clair
}