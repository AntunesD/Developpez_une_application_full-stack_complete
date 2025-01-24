package com.openclassrooms.mddapi.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CommentDTO {
  private Long articleId;
  private String username;
  private String content;
  private LocalDate createdAt;
}