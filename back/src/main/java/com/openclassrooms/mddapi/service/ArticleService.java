package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.entity.Article;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

  @Autowired
  private ArticleRepository articleRepository;

  public List<Article> getAllArticles() {
    return articleRepository.findAll();
  }

  public Article getArticleById(Long id) {
    return articleRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Article non trouvé avec l'id : " + id));
  }

  public Article createArticle(Article article) {
    return articleRepository.save(article);
  }

  public Article updateArticle(Long id, Article articleDetails) {
    Article article = getArticleById(id);
    article.setTitle(articleDetails.getTitle());
    article.setContent(articleDetails.getContent());
    article.setTheme(articleDetails.getTheme());
    return articleRepository.save(article);
  }

  public void deleteArticle(Long id) {
    Article article = getArticleById(id);
    articleRepository.delete(article);
  }
}