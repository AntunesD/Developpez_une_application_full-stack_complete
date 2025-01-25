package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.ArticleDTO;
import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.dto.ThemeDTO;
import com.openclassrooms.mddapi.dto.UserSimpleDto;
import com.openclassrooms.mddapi.entity.Article;
import com.openclassrooms.mddapi.entity.Theme;
import com.openclassrooms.mddapi.entity.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {

  @Autowired
  private ArticleRepository articleRepository;

  @Autowired
  private UserRepository userRepository; // Nécessaire pour associer un User à un Article

  @Autowired
  private CommentsService commentService;

  @Autowired
  private ThemeService themeService;

  public List<ArticleDTO> getAllArticlesDto() {
    List<Article> articles = articleRepository.findAll();
    return articles.stream()
        .map(this::convertToDto)
        .collect(Collectors.toList());
  }

  public ArticleDTO getArticleById(Long id) {
    Article article = articleRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Article non trouvé avec l'id : " + id));
    // Récupérer et ajouter le thème associé à l'article
    ThemeDTO themeDTO = themeService.getThemeById(article.getTheme().getId()); // Utilisation du ThemeService

    // Récupérer les commentaires associés
    List<CommentDTO> comments = commentService.getCommentsByArticleId(id);

    // Convertir l'article en DTO et ajouter les commentaires
    ArticleDTO articleDTO = convertToDto(article);
    articleDTO.setTheme(themeDTO);
    articleDTO.setComments(comments); // Ajouter la liste des commentaires au DTO

    return articleDTO;
  }

  public ArticleDTO createArticle(ArticleDTO articleDTO, String username) {
    Article article = convertToEntity(articleDTO, username);

    // Associer le thème si présent dans le DTO
    if (articleDTO.getTheme() != null) {
      Theme theme = themeService.getThemeEntityById(articleDTO.getTheme().getId());
      article.setTheme(theme); // Associer le thème à l'article
    }

    Article savedArticle = articleRepository.save(article);
    return convertToDto(savedArticle);
  }

  public ArticleDTO updateArticle(Long id, ArticleDTO articleDTO) {
    Article existingArticle = articleRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Article non trouvé avec l'id : " + id));

    // Mettre à jour les champs de l'entité avec les données du DTO
    existingArticle.setTitle(articleDTO.getTitle());
    existingArticle.setContent(articleDTO.getContent());
    // existingArticle.setTheme(articleDTO.getTheme());

    // Si le user est modifiable dans l'articleDTO, on le met à jour
    if (articleDTO.getUser() != null) {
      User user = userRepository.findById(articleDTO.getUser().getId())
          .orElseThrow(
              () -> new RuntimeException("Utilisateur non trouvé avec l'id : " + articleDTO.getUser().getId()));
      existingArticle.setUser(user);
    }

    Article updatedArticle = articleRepository.save(existingArticle);
    return convertToDto(updatedArticle);
  }

  public void deleteArticle(Long id) {
    Article article = articleRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Article non trouvé avec l'id : " + id));
    articleRepository.delete(article);
  }

  /**
   * Méthode pour convertir une entité Article en DTO ArticleDTO.
   */
  private ArticleDTO convertToDto(Article article) {
    ArticleDTO dto = new ArticleDTO();
    dto.setId(article.getId());
    dto.setTitle(article.getTitle());
    dto.setContent(article.getContent());

    // Convertir Theme en ThemeDTO
    if (article.getTheme() != null) {
      ThemeDTO themeDto = new ThemeDTO();
      themeDto.setId(article.getTheme().getId());
      themeDto.setTitle(article.getTheme().getTitle());
      themeDto.setDescription(article.getTheme().getDescription());
      // Ajouter d'autres champs nécessaires de Theme vers ThemeDTO
      dto.setTheme(themeDto);
    }

    dto.setCreatedAt(article.getCreatedAt());

    UserSimpleDto userDto = new UserSimpleDto();
    userDto.setId(article.getUser().getId());
    userDto.setUsername(article.getUser().getUsername());
    dto.setUser(userDto);

    return dto;
  }

  /**
   * Méthode pour convertir un DTO ArticleDTO en entité Article.
   */
  private Article convertToEntity(ArticleDTO dto, String username) {
    Article article = new Article();
    article.setTitle(dto.getTitle());
    article.setContent(dto.getContent());

    // Vérifie l'existence de l'utilisateur
    com.openclassrooms.mddapi.entity.User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new RuntimeException("User not found");
    }

    // Vérifie l'existence du thème
    if (dto.getTheme() == null || dto.getTheme().getId() == null) {
      throw new RuntimeException("Theme is required");
    }
    Theme theme = themeService.getThemeEntityById(dto.getTheme().getId());
    if (theme == null) {
      throw new RuntimeException("Theme not found with id: " + dto.getTheme().getId());
    }

    // Assigner les valeurs à l'article
    article.setTheme(theme);
    article.setUser(user);
    article.setCreatedAt(LocalDate.now());

    return article;
  }

}
