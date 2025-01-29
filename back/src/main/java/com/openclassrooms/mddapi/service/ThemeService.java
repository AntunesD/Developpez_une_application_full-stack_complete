package com.openclassrooms.mddapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.ThemeDTO;
import com.openclassrooms.mddapi.dto.ThemeRequestDto;
import com.openclassrooms.mddapi.entity.Theme;
import com.openclassrooms.mddapi.entity.User;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ThemeService {

  @Autowired
  private final UserRepository userRepository;

  private final ThemeRepository themeRepository;

  /**
   * Récupère tous les thèmes disponibles et marque ceux auxquels l'utilisateur est abonné.
   * 
   * @param username Le nom d'utilisateur de l'utilisateur connecté
   * @return Liste des objets ThemeDTO avec l'indication d'abonnement
   */
  public List<ThemeDTO> getAllThemes(String username) {

    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new RuntimeException("User not found");
    }

    // Récupérer tous les thèmes et marquer comme "subscribed" si l'utilisateur est
    // abonné
    return themeRepository.findAll().stream()
        .map(theme -> convertToDtoWithSubscription(theme, user))
        .collect(Collectors.toList());
  }

  /**
   * Convertit un thème en DTO en incluant l'état d'abonnement de l'utilisateur.
   * 
   * @param theme Le thème à convertir
   * @param user L'utilisateur pour vérifier son abonnement
   * @return L'objet ThemeDTO avec l'indication d'abonnement
   */
  private ThemeDTO convertToDtoWithSubscription(Theme theme, User user) {
    ThemeDTO dto = new ThemeDTO();
    dto.setId(theme.getId());
    dto.setTitle(theme.getTitle());
    dto.setDescription(theme.getDescription());

    // Vérifier si l'utilisateur est abonné au thème
    boolean isSubscribed = user.getThemes().contains(theme);
    dto.setSubscribed(isSubscribed);

    return dto;
  }

  /**
   * Récupère un thème par son ID sous forme d'entité.
   * 
   * @param id L'identifiant du thème à récupérer
   * @return L'entité Theme
   */
  public Theme getThemeEntityById(Long id) {
    return themeRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Thème non trouvé avec l'id : " + id));
  }

  /**
   * Récupère un thème par son ID sous forme de DTO.
   * 
   * @param id L'identifiant du thème à récupérer
   * @return Le ThemeDTO correspondant au thème trouvé
   */
  public ThemeDTO getThemeById(Long id) {
    Theme theme = themeRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Theme not found"));
    return convertToDto(theme);
  }

  /**
   * Crée un nouveau thème à partir des informations reçues dans le DTO.
   * 
   * @param themeRequestDto Le DTO contenant les informations pour le nouveau thème
   * @return Le ThemeDTO du thème créé
   */
  public ThemeDTO createTheme(ThemeRequestDto themeRequestDto) {
    Theme theme = new Theme();
    theme.setTitle(themeRequestDto.getTitle());
    theme.setDescription(themeRequestDto.getDescription());
    Theme savedTheme = themeRepository.save(theme);
    return convertToDto(savedTheme);
  }

  /**
   * Met à jour un thème existant à partir des informations reçues dans le DTO.
   * 
   * @param id               L'identifiant du thème à mettre à jour
   * @param themeRequestDto Le DTO contenant les nouvelles informations pour le thème
   * @return Le ThemeDTO du thème mis à jour
   */
  public ThemeDTO updateTheme(Long id, ThemeRequestDto themeRequestDto) {
    Theme theme = themeRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Theme not found"));
    theme.setTitle(themeRequestDto.getTitle());
    theme.setDescription(themeRequestDto.getDescription());
    Theme updatedTheme = themeRepository.save(theme);
    return convertToDto(updatedTheme);
  }

  /**
   * Supprime un thème par son ID.
   * 
   * @param id L'identifiant du thème à supprimer
   */
  public void deleteTheme(Long id) {
    themeRepository.deleteById(id);
  }

  /**
   * Convertit un thème en un DTO.
   * 
   * @param theme L'entité Theme à convertir
   * @return Le ThemeDTO correspondant à l'entité Theme
   */
  private ThemeDTO convertToDto(Theme theme) {
    ThemeDTO dto = new ThemeDTO();
    dto.setId(theme.getId());
    dto.setTitle(theme.getTitle());
    dto.setDescription(theme.getDescription());
    return dto;
  }

  /**
   * Permet à un utilisateur de s'abonner ou de se désabonner à un thème.
   * 
   * @param username Le nom d'utilisateur de l'utilisateur
   * @param themeId  L'identifiant du thème
   * @return Un message indiquant l'état de l'abonnement
   */
  public String subscribeToTheme(String username, Long themeId) {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new RuntimeException("User not found");
    }

    Theme theme = themeRepository.findById(themeId)
        .orElseThrow(() -> new RuntimeException("Theme not found"));

    // Vérifie si l'utilisateur est déjà abonné au thème
    if (user.getThemes().contains(theme)) {
      // Si l'utilisateur est déjà abonné, le désabonner
      user.getThemes().remove(theme);
      userRepository.save(user);
      return "User has been unsubscribed from the theme with ID: " + themeId;
    } else {
      // Sinon, l'abonner au thème
      user.getThemes().add(theme);
      userRepository.save(user);
      return "User has been subscribed to the theme with ID: " + themeId;
    }
  }

}
