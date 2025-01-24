package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.entity.Comment;
import com.openclassrooms.mddapi.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

  @Autowired
  private CommentRepository commentRepository;

  // Méthode pour récupérer les commentaires associés à un article
  public List<CommentDTO> getCommentsByArticleId(Long articleId) {
    List<Comment> comments = commentRepository.findByArticleId(articleId);
    return comments.stream()
        .map(this::convertToCommentDto)
        .collect(Collectors.toList());
  }

  // Méthode de conversion d'un Comment en CommentDTO
  private CommentDTO convertToCommentDto(Comment comment) {
    CommentDTO dto = new CommentDTO();
    dto.setArticleId(comment.getArticle().getId());
    dto.setUsername(comment.getUser().getUsername());
    dto.setContent(comment.getContent());
    return dto;
  }
}
