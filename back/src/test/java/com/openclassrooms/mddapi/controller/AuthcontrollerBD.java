package com.openclassrooms.mddapi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.mddapi.dto.RegisterDto;
import com.openclassrooms.mddapi.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthcontrollerBD {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private UserRepository userRepository;

  @Test
  public void testRegisterSuccess() throws Exception {
    // Nettoyage de la base de données avant le test
    userRepository.deleteAll();

    // Préparation des données de test
    RegisterDto registerDto = new RegisterDto();
    registerDto.setUsername("testuser");
    registerDto.setPassword("password123");
    registerDto.setEmail("test@example.com");

    // Exécution du test
    mockMvc.perform(post("/api/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(registerDto)))
        .andExpect(status().isOk())
        .andExpect(content().string("Utilisateur enregistré avec succès"));
  }

  @Test
  public void testRegisterFailure() throws Exception {
    // Préparation des données de test
    RegisterDto registerDto = new RegisterDto();
    registerDto.setUsername("testuser");
    registerDto.setPassword("password123");
    registerDto.setEmail("test@example.com");

    // Premier enregistrement
    mockMvc.perform(post("/api/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(registerDto)));

    // Tentative d'enregistrement avec les mêmes données
    mockMvc.perform(post("/api/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(registerDto)))
        .andExpect(status().isOk())
        .andExpect(content().string("L'utilisateur existe déjà"));
  }
}