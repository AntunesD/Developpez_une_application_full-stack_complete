package com.openclassrooms.mddapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.LoginDto;
import com.openclassrooms.mddapi.dto.RegisterDto;
import com.openclassrooms.mddapi.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private AuthService authService;

  @PostMapping("/login")
  public String login(@RequestBody LoginDto userDto) {
    try {
      return authService.authenticate(userDto.getUsername(), userDto.getPassword());
    } catch (RuntimeException e) {
      return e.getMessage();
    }
  }

  @PostMapping("/register")
  public String register(@RequestBody RegisterDto userDto) {
    try {
      return authService.register(userDto.getUsername(), userDto.getPassword(), userDto.getEmail());
    } catch (RuntimeException e) {
      return e.getMessage();
    }
  }
}