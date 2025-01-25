package com.openclassrooms.mddapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.AuthResponseDto;
import com.openclassrooms.mddapi.dto.ThemeDTO;
import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.dto.UserProfilDTO;
import com.openclassrooms.mddapi.entity.User;
import com.openclassrooms.mddapi.repository.UserRepository;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private JwtService jwtService;

  public UserProfilDTO getUserProfile(String username) {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      user = userRepository.findByUsername(username);
      if (user == null) {
        throw new RuntimeException("Utilisateur non trouvé");
      }
    }

    // Mapper les thèmes en une liste de ThemeDTO (id, title, description)
    List<ThemeDTO> themeDTOs = user.getThemes().stream()
        .map(theme -> {
          ThemeDTO dto = new ThemeDTO();
          dto.setId(theme.getId());
          dto.setTitle(theme.getTitle());
          dto.setDescription(theme.getDescription());
          return dto;
        })
        .collect(Collectors.toList());

    return new UserProfilDTO(user.getUsername(), user.getEmail(), themeDTOs);
  }

  public AuthResponseDto updateUserProfile(String username, UserDTO userDTO) {
    User user = userRepository.findByUsername(username);

    if (user != null) {
      if (userDTO.getEmail() != null) {
        user.setEmail(userDTO.getEmail());
      }
      if (userDTO.getUsername() != null) {
        user.setUsername(userDTO.getUsername());
      }

      userRepository.save(user);

      String token = jwtService.generateToken(user);
      return new AuthResponseDto("success", "Modification réussie", token, user.getUsername());
    }

    throw new RuntimeException("User not found");
  }
}
