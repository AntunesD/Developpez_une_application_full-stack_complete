package com.openclassrooms.mddapi.dto;

import lombok.Data;

@Data
public class AuthResponseDto {
  private String status;
  private String message;
  private String token;
  private String username;

  public AuthResponseDto(String status, String message, String token, String username) {
    this.status = status;
    this.message = message;
    this.token = token;
    this.username = username;
  }

}