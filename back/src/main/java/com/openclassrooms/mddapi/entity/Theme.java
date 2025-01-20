package com.openclassrooms.mddapi.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.util.List;

@Data
@Entity
public class Theme {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;
  private String description;
  private boolean isSubscribed; // Indique si l'utilisateur est abonné

  @OneToMany(mappedBy = "theme")
  private List<Article> articles;
}