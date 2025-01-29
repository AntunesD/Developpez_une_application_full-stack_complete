package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.entity.Article;
import com.openclassrooms.mddapi.entity.Comment;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.CommentsRepository;
import com.openclassrooms.mddapi.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentsService {

  @Autowired
  private CommentsRepository commentsRepository;

  @Autowired
  private ArticleRepository articleRepository;

  @Autowired
  private UserRepository userRepository;

  /**
   * Récupère tous les commentaires associés à un article.
   * 
   * @param articleId ID de l'article
   * @return Liste de CommentDTO
   */
  public List<CommentDTO> getCommentsByArticleId(Long articleId) {
    List<Comment> comments = commentsRepository.findByArticleId(articleId);
    return comments.stream()
        .map(this::convertToCommentDto)
        .collect(Collectors.toList());
  }

  /**
   * Crée un nouveau commentaire pour un article.
   * 
   * @param commentDTO Contient les données du commentaire
   * @param username   Nom d'utilisateur de l'auteur du commentaire
   * @return CommentDTO
   */
  public CommentDTO createComment(CommentDTO commentDTO, String username) {
    Comment comment = new Comment();

    // Vérifie l'existence de l'article
    Article article = articleRepository.findById(commentDTO.getArticleId())
        .orElseThrow(() -> new RuntimeException("Article not found"));
    comment.setArticle(article);

    // Vérifie l'existence de l'utilisateur
    com.openclassrooms.mddapi.entity.User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new RuntimeException("User not found");
    }
    comment.setUser(user);

    comment.setContent(commentDTO.getContent());
    comment.setCreatedAt(LocalDate.now());

    Comment savedComment = commentsRepository.save(comment);
    return convertToCommentDto(savedComment);
  }

  /**
   * Met à jour un commentaire existant.
   * 
   * @param commentId  ID du commentaire à mettre à jour
   * @param commentDTO Nouveau contenu du commentaire
   * @return CommentDTO mis à jour
   */
  public CommentDTO updateComment(Long commentId, CommentDTO commentDTO) {
    Comment comment = commentsRepository.findById(commentId)
        .orElseThrow(() -> new RuntimeException("Comment not found"));
    comment.setContent(commentDTO.getContent());

    Comment updatedComment = commentsRepository.save(comment);
    return convertToCommentDto(updatedComment);
  }

  /**
   * Supprime un commentaire.
   * 
   * @param commentId ID du commentaire à supprimer
   */
  public void deleteComment(Long commentId) {
    Comment comment = commentsRepository.findById(commentId)
        .orElseThrow(() -> new RuntimeException("Comment not found"));
    commentsRepository.delete(comment);
  }

  /**
   * Convertit un objet Comment en CommentDTO.
   * 
   * @param comment Comment à convertir
   * @return CommentDTO
   */
  private CommentDTO convertToCommentDto(Comment comment) {
    CommentDTO dto = new CommentDTO();
    dto.setArticleId(comment.getArticle().getId());
    dto.setUsername(comment.getUser().getUsername());
    dto.setContent(comment.getContent());
    dto.setCreatedAt(comment.getCreatedAt());
    return dto;
  }
}
