package com.openclassrooms.mddapi.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.mddapi.config.SecurityConfig;
import com.openclassrooms.mddapi.dto.RegisterDto;
import com.openclassrooms.mddapi.service.AuthService;

@WebMvcTest(AuthController.class)
@Import(SecurityConfig.class)
public class AuthControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private AuthService authService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void testRegisterSuccess() throws Exception {
    // Préparation des données de test
    RegisterDto registerDto = new RegisterDto();
    registerDto.setUsername("testuser");
    registerDto.setPassword("password123");
    registerDto.setEmail("test@example.com");

    // Configuration du mock
    when(authService.register(anyString(), anyString(), anyString()))
        .thenReturn("Inscription réussie");
    // Exécution du test
    mockMvc.perform(post("/api/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(registerDto)))
        .andExpect(status().isOk())
        .andExpect(content().string("Inscription réussie"));
  }

  @Test
  public void testRegisterFailure() throws Exception {
    // Préparation des données de test
    RegisterDto registerDto = new RegisterDto();
    registerDto.setUsername("testuser");
    registerDto.setPassword("password123");
    registerDto.setEmail("test@example.com");

    // Configuration du mock pour simuler une erreur
    when(authService.register(anyString(), anyString(), anyString()))
        .thenThrow(new RuntimeException("L'utilisateur existe déjà"));

    // Exécution du test
    mockMvc.perform(post("/api/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(registerDto)))
        .andExpect(status().isOk())
        .andExpect(content().string("L'utilisateur existe déjà"));
  }
}