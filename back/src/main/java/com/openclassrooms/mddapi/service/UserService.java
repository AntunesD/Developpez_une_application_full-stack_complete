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

  /**
   * Récupère le profil de l'utilisateur avec ses informations et ses thèmes
   * abonnés.
   * 
   * @param username Le nom d'utilisateur de l'utilisateur dont on veut obtenir le
   *                 profil
   * @return Un objet UserProfilDTO contenant le profil de l'utilisateur
   * @throws RuntimeException Si l'utilisateur n'est pas trouvé
   */
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
          dto.setSubscribed(true);
          return dto;
        })
        .collect(Collectors.toList());

    return new UserProfilDTO(user.getUsername(), user.getEmail(), themeDTOs);
  }

  /**
   * Met à jour le profil de l'utilisateur avec les informations reçues dans le
   * DTO.
   * 
   * @param username Le nom d'utilisateur de l'utilisateur à mettre à jour
   * @param userDTO  Le DTO contenant les nouvelles informations de l'utilisateur
   * @return Un objet AuthResponseDto avec les détails de la modification et le
   *         token JWT
   * @throws RuntimeException Si l'utilisateur n'est pas trouvé
   */
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
