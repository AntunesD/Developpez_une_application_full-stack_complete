package com.openclassrooms.mddapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
  private String username;
  private String email;
}