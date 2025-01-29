package com.openclassrooms.mddapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.openclassrooms.mddapi.dto.ArticleDTO;
import com.openclassrooms.mddapi.service.ArticleService;

/**
 * Contrôleur REST pour gérer les articles.
 */
@RestController
@RequestMapping("/api/articles")
@CrossOrigin(origins = "*")
public class ArticleController {

  @Autowired
  private ArticleService articleService;

  /**
   * Récupère tous les articles.
   * 
   * @return Liste des articles sous forme de DTO.
   */
  @GetMapping
  public ResponseEntity<List<ArticleDTO>> getAllArticles() {
    List<ArticleDTO> articles = articleService.getAllArticlesDto();
    return ResponseEntity.ok(articles);
  }

  /**
   * Récupère un article par son ID.
   * 
   * @param id Identifiant de l'article.
   * @return L'article correspondant.
   */
  @GetMapping("/{id}")
  public ResponseEntity<ArticleDTO> getArticleById(@PathVariable Long id) {
    ArticleDTO article = articleService.getArticleById(id);
    return ResponseEntity.ok(article);
  }

  /**
   * Crée un nouvel article.
   * 
   * @param article        Données de l'article.
   * @param authentication Informations sur l'utilisateur authentifié.
   * @return L'article créé.
   */
  @PostMapping
  public ResponseEntity<ArticleDTO> createArticle(@RequestBody ArticleDTO article, Authentication authentication) {
    String username = authentication.getName();
    ArticleDTO createdArticle = articleService.createArticle(article, username);
    return ResponseEntity.ok(createdArticle);
  }

  /**
   * Met à jour un article existant.
   * 
   * @param id      Identifiant de l'article.
   * @param article Nouvelles données de l'article.
   * @return L'article mis à jour.
   */
  @PutMapping("/{id}")
  public ResponseEntity<ArticleDTO> updateArticle(@PathVariable Long id, @RequestBody ArticleDTO article) {
    ArticleDTO updatedArticle = articleService.updateArticle(id, article);
    return ResponseEntity.ok(updatedArticle);
  }

  /**
   * Supprime un article.
   * 
   * @param id Identifiant de l'article.
   * @return Une réponse vide en cas de succès.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
    articleService.deleteArticle(id);
    return ResponseEntity.ok().build();
  }
}
