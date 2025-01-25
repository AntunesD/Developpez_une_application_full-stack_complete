package com.openclassrooms.mddapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.AuthResponseDto;
import com.openclassrooms.mddapi.dto.LoginDto;
import com.openclassrooms.mddapi.dto.RegisterDto;
import com.openclassrooms.mddapi.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto userDto) {
    try {
      return ResponseEntity.ok(authService.authenticate(userDto.getUsernameOrEmail(), userDto.getPassword()));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(
          new AuthResponseDto("error", e.getMessage(), null, null));
    }
  }

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