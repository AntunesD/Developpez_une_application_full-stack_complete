package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.ArticleDTO;
import com.openclassrooms.mddapi.entity.Article;
import com.openclassrooms.mddapi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
@CrossOrigin(origins = "*")
public class ArticleController {

  @Autowired
  private ArticleService articleService;

  @GetMapping
  public ResponseEntity<List<ArticleDTO>> getAllArticles() {
    List<ArticleDTO> articles = articleService.getAllArticlesDto();
    return ResponseEntity.ok(articles);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Article> getArticleById(@PathVariable Long id) {
    Article article = articleService.getArticleById(id);
    return ResponseEntity.ok(article);
  }

  @PostMapping
  public ResponseEntity<Article> createArticle(@RequestBody Article article) {
    Article createdArticle = articleService.createArticle(article);
    return ResponseEntity.ok(createdArticle);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Article> updateArticle(@PathVariable Long id, @RequestBody Article article) {
    Article updatedArticle = articleService.updateArticle(id, article);
    return ResponseEntity.ok(updatedArticle);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
    articleService.deleteArticle(id);
    return ResponseEntity.ok().build();
  }
}