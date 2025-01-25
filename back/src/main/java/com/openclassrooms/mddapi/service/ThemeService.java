package com.openclassrooms.mddapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.ThemeDTO;
import com.openclassrooms.mddapi.dto.ThemeRequestDto;
import com.openclassrooms.mddapi.entity.Theme;
import com.openclassrooms.mddapi.repository.ThemeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ThemeService {

  private final ThemeRepository themeRepository;

  public List<ThemeDTO> getAllThemes() {
    return themeRepository.findAll().stream()
        .map(this::convertToDto)
        .collect(Collectors.toList());
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
    theme.setSubscribed(themeRequestDto.isSubscribed());
    Theme savedTheme = themeRepository.save(theme);
    return convertToDto(savedTheme);
  }

  public ThemeDTO updateTheme(Long id, ThemeRequestDto themeRequestDto) {
    Theme theme = themeRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Theme not found"));
    theme.setTitle(themeRequestDto.getTitle());
    theme.setDescription(themeRequestDto.getDescription());
    theme.setSubscribed(themeRequestDto.isSubscribed());
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
    dto.setSubscribed(theme.isSubscribed());
    return dto;
  }
}
