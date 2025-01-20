package com.openclassrooms.mddapi.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.util.List;

@Data
@Entity
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;
  private String email;
  private String password; // Assurez-vous de le hasher avant de le stocker

  @OneToMany(mappedBy = "user")
  private List<Article> articles;

  @OneToMany(mappedBy = "user")
  private List<Comment> comments;
}