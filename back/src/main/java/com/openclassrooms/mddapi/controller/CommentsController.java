package com.openclassrooms.mddapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.core.Authentication;
import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.service.CommentsService;

@RestController
@RequestMapping("/api/comments")
public class CommentsController {

  @Autowired
  private CommentsService commentsService;

  // Endpoint pour ajouter un commentaire
  @PostMapping("")
  public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO, Authentication authentication) {
    String username = authentication.getName();
    CommentDTO createdComment = commentsService.createComment(commentDTO, username);
    return ResponseEntity.status(201).body(createdComment);
  }

  // Endpoint pour mettre à jour un commentaire
  @PutMapping("/{commentId}")
  public ResponseEntity<CommentDTO> updateComment(@PathVariable Long commentId, @RequestBody CommentDTO commentDTO) {
    CommentDTO updatedComment = commentsService.updateComment(commentId, commentDTO);
    return ResponseEntity.ok(updatedComment);
  }

  // Endpoint pour supprimer un commentaire
  @DeleteMapping("/{commentId}")
  public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
    commentsService.deleteComment(commentId);
    return ResponseEntity.noContent().build();
  }
}
