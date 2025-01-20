package com.openclassrooms.mddapi.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private LocalDate createdAt;
  private String content;

  @ManyToOne
  @JoinColumn(name = "article_id", nullable = false)
  private Article article;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
}