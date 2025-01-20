package com.openclassrooms.mddapi.dto;

import lombok.Data;

@Data
public class ArticleDTO {
  private String title;
  private String content;
  private Long themeId;
}