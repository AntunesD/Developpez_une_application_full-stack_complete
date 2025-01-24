package com.openclassrooms.mddapi.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class ArticleDTO {
  private Long id;
  private String title;
  private ThemeDTO theme;
  private UserSimpleDto user;
  private LocalDate createdAt;
  private String content;
  private List<CommentDTO> comments;
}