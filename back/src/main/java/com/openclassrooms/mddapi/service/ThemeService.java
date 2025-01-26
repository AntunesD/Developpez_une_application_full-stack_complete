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

  public Theme getThemeEntityById(Long id) {
    return themeRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Thème non trouvé avec l'id : " + id));
  }

  public ThemeDTO getThemeById(Long id) {
    Theme theme = themeRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Theme not found"));
    return convertToDto(theme);
  }

  public ThemeDTO createTheme(ThemeRequestDto themeRequestDto) {
    Theme theme = new Theme();
    theme.setTitle(themeRequestDto.getTitle());
    theme.setDescription(themeRequestDto.getDescription());
    Theme savedTheme = themeRepository.save(theme);
    return convertToDto(savedTheme);
  }

  public ThemeDTO updateTheme(Long id, ThemeRequestDto themeRequestDto) {
    Theme theme = themeRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Theme not found"));
    theme.setTitle(themeRequestDto.getTitle());
    theme.setDescription(themeRequestDto.getDescription());
    Theme updatedTheme = themeRepository.save(theme);
    return convertToDto(updatedTheme);
  }

  public void deleteTheme(Long id) {
    themeRepository.deleteById(id);
  }

  private ThemeDTO convertToDto(Theme theme) {
    ThemeDTO dto = new ThemeDTO();
    dto.setId(theme.getId());
    dto.setTitle(theme.getTitle());
    dto.setDescription(theme.getDescription());
    return dto;
  }

  public String subscribeToTheme(String username, Long themeId) {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new RuntimeException("User not found");
    }

    Theme theme = themeRepository.findById(themeId)
        .orElseThrow(() -> new RuntimeException("Theme not found"));

    // Check if the user is already subscribed to the theme
    if (user.getThemes().contains(theme)) {
      // If the user is already subscribed, remove the theme from the user's list of
      // themes
      user.getThemes().remove(theme);
      userRepository.save(user);
      return "User has been unsubscribed from the theme with ID: " + themeId;
    } else {
      // If the user is not subscribed, add the theme to their list of themes
      user.getThemes().add(theme);
      userRepository.save(user);
      return "User has been subscribed to the theme with ID: " + themeId;
    }
  }

}
