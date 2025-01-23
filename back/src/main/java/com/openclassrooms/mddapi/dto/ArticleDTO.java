package com.openclassrooms.mddapi.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ArticleDTO {
  private Long id;
  private String title;
  private String content;
  private LocalDate createdAt;
  private UserSimpleDto user;
  private Long themeId;
}