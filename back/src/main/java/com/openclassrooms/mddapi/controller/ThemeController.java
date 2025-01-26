package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.SubscribeRequestDTO;
import com.openclassrooms.mddapi.dto.ThemeDTO;
import com.openclassrooms.mddapi.dto.ThemeRequestDto;
import com.openclassrooms.mddapi.service.ThemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/themes")
@RequiredArgsConstructor
public class ThemeController {

  private final ThemeService themeService;

  // Récupérer tous les thèmes
  @GetMapping
  public ResponseEntity<List<ThemeDTO>> getAllThemes(Authentication authentication) {
    String username = authentication.getName();
    List<ThemeDTO> themes = themeService.getAllThemes(username);
    return ResponseEntity.ok(themes);
  }

  // Récupérer un thème par son ID
  @GetMapping("/{id}")
  public ResponseEntity<ThemeDTO> getThemeById(@PathVariable Long id) {
    ThemeDTO theme = themeService.getThemeById(id);
    return ResponseEntity.ok(theme);
  }

  // Créer un nouveau thème
  @PostMapping
  public ResponseEntity<ThemeDTO> createTheme(@RequestBody ThemeRequestDto themeRequestDto) {
    ThemeDTO createdTheme = themeService.createTheme(themeRequestDto);
    return ResponseEntity.status(201).body(createdTheme);
  }

  // Mettre à jour un thème
  @PutMapping("/{id}")
  public ResponseEntity<ThemeDTO> updateTheme(@PathVariable Long id, @RequestBody ThemeRequestDto themeRequestDto) {
    ThemeDTO updatedTheme = themeService.updateTheme(id, themeRequestDto);
    return ResponseEntity.ok(updatedTheme);
  }

  // Supprimer un thème
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTheme(@PathVariable Long id) {
    themeService.deleteTheme(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/subscribe")
  public ResponseEntity<Map<String, String>> subscribeToTheme(@RequestBody SubscribeRequestDTO requestDTO,
      Authentication authentication) {
    String username = authentication.getName();
    try {
      String resultMessage = themeService.subscribeToTheme(username, requestDTO.getThemeId());
      // Emballer le message dans une réponse JSON
      Map<String, String> response = new HashMap<>();
      response.put("message", resultMessage);
      return ResponseEntity.ok(response);
    } catch (RuntimeException e) {
      Map<String, String> errorResponse = new HashMap<>();
      errorResponse.put("error", e.getMessage());
      return ResponseEntity.badRequest().body(errorResponse);
    }
  }

}
