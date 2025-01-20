package com.openclassrooms.mddapi.dto;

import lombok.Data;

@Data
public class CommentDTO {
  private Long articleId;
  private String username;
  private String content;
}