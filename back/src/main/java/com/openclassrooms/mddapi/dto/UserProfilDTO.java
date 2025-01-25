package com.openclassrooms.mddapi.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserProfilDTO {
  private String username;
  private String email;
  private List<ThemeDTO> themes;

}
