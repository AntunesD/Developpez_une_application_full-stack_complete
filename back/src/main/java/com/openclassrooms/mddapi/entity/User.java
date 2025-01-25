package com.openclassrooms.mddapi.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "`user`")
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

  @ManyToMany
  @JoinTable(name = "user_theme", // Nom de la table de jointure
      joinColumns = @JoinColumn(name = "user_id"), // Clé étrangère pour l'utilisateur
      inverseJoinColumns = @JoinColumn(name = "theme_id") // Clé étrangère pour le thème
  )
  private List<Theme> themes;
}