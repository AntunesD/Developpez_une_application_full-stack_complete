package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.ArticleDTO;
import com.openclassrooms.mddapi.dto.UserSimpleDto;
import com.openclassrooms.mddapi.entity.Article;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

  public List<ArticleDTO> getAllArticlesDto() {
    List<Article> articles = articleRepository.findAll();
    return articles.stream()
        .map(this::convertToDto)
        .collect(Collectors.toList());
  }

  private ArticleDTO convertToDto(Article article) {
    ArticleDTO dto = new ArticleDTO();
    dto.setId(article.getId());
    dto.setTitle(article.getTitle());
    dto.setContent(article.getContent());
    dto.setCreatedAt(article.getCreatedAt());

    UserSimpleDto userDto = new UserSimpleDto();
    userDto.setId(article.getUser().getId());
    userDto.setUsername(article.getUser().getUsername());
    dto.setUser(userDto);

    return dto;
  }
}