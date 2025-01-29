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

/**
 * Contrôleur pour la gestion des thèmes.
 */
@RestController
@RequestMapping("/api/themes")
@RequiredArgsConstructor
public class ThemeController {

  private final ThemeService themeService;

  /**
   * Récupère tous les thèmes disponibles pour l'utilisateur.
   * 
   * @param authentication Informations sur l'utilisateur authentifié.
   * @return Liste des thèmes sous forme de DTO.
   */
  @GetMapping
  public ResponseEntity<List<ThemeDTO>> getAllThemes(Authentication authentication) {
    String username = authentication.getName();
    List<ThemeDTO> themes = themeService.getAllThemes(username);
    return ResponseEntity.ok(themes);
  }

  /**
   * Récupère un thème par son ID.
   * 
   * @param id Identifiant du thème.
   * @return Le thème correspondant.
   */
  @GetMapping("/{id}")
  public ResponseEntity<ThemeDTO> getThemeById(@PathVariable Long id) {
    ThemeDTO theme = themeService.getThemeById(id);
    return ResponseEntity.ok(theme);
  }

  /**
   * Crée un nouveau thème.
   * 
   * @param themeRequestDto Données du thème à créer.
   * @return Le thème créé.
   */
  @PostMapping
  public ResponseEntity<ThemeDTO> createTheme(@RequestBody ThemeRequestDto themeRequestDto) {
    ThemeDTO createdTheme = themeService.createTheme(themeRequestDto);
    return ResponseEntity.status(201).body(createdTheme);
  }

  /**
   * Met à jour un thème existant.
   * 
   * @param id              Identifiant du thème.
   * @param themeRequestDto Nouvelles données du thème.
   * @return Le thème mis à jour.
   */
  @PutMapping("/{id}")
  public ResponseEntity<ThemeDTO> updateTheme(@PathVariable Long id, @RequestBody ThemeRequestDto themeRequestDto) {
    ThemeDTO updatedTheme = themeService.updateTheme(id, themeRequestDto);
    return ResponseEntity.ok(updatedTheme);
  }

  /**
   * Supprime un thème.
   * 
   * @param id Identifiant du thème à supprimer.
   * @return Une réponse sans contenu si la suppression est réussie.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTheme(@PathVariable Long id) {
    themeService.deleteTheme(id);
    return ResponseEntity.noContent().build();
  }

  /**
   * Permet à un utilisateur de s'abonner à un thème.
   * 
   * @param requestDTO     Contient l'ID du thème à suivre.
   * @param authentication Informations sur l'utilisateur authentifié.
   * @return Un message de confirmation ou une erreur.
   */
  @PostMapping("/subscribe")
  public ResponseEntity<Map<String, String>> subscribeToTheme(@RequestBody SubscribeRequestDTO requestDTO,
      Authentication authentication) {
    String username = authentication.getName();
    try {
      String resultMessage = themeService.subscribeToTheme(username, requestDTO.getThemeId());
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
