package com.openclassrooms.mddapi.dto;

import lombok.Data;

@Data
public class ThemeRequestDto {
  private String title;
  private String description;
  private boolean isSubscribed;
}
