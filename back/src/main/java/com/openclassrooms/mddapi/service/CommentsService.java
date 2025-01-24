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

  // Méthode pour récupérer les commentaires associés à un article
  public List<CommentDTO> getCommentsByArticleId(Long articleId) {
    List<Comment> comments = commentsRepository.findByArticleId(articleId);
    return comments.stream()
        .map(this::convertToCommentDto)
        .collect(Collectors.toList());
  }

  // Méthode pour ajouter un commentaire
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

  // Méthode pour mettre à jour un commentaire
  public CommentDTO updateComment(Long commentId, CommentDTO commentDTO) {
    Comment comment = commentsRepository.findById(commentId)
        .orElseThrow(() -> new RuntimeException("Comment not found"));
    comment.setContent(commentDTO.getContent());

    Comment updatedComment = commentsRepository.save(comment);
    return convertToCommentDto(updatedComment);
  }

  // Méthode pour supprimer un commentaire
  public void deleteComment(Long commentId) {
    Comment comment = commentsRepository.findById(commentId)
        .orElseThrow(() -> new RuntimeException("Comment not found"));
    commentsRepository.delete(comment);
  }

  // Méthode de conversion d'un Comment en CommentDTO
  private CommentDTO convertToCommentDto(Comment comment) {
    CommentDTO dto = new CommentDTO();
    dto.setArticleId(comment.getArticle().getId());
    dto.setUsername(comment.getUser().getUsername());
    dto.setContent(comment.getContent());
    dto.setCreatedAt(comment.getCreatedAt());
    return dto;
  }
}
