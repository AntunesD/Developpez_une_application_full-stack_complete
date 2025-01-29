package com.openclassrooms.mddapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;
import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.service.CommentsService;

/**
 * Contrôleur pour la gestion des commentaires.
 */
@RestController
@RequestMapping("/api/comments")
public class CommentsController {

  @Autowired
  private CommentsService commentsService;

  /**
   * Ajoute un nouveau commentaire.
   * 
   * @param commentDTO     Données du commentaire.
   * @param authentication Informations sur l'utilisateur authentifié.
   * @return Le commentaire créé.
   */
  @PostMapping("")
  public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO, Authentication authentication) {
    String username = authentication.getName();
    CommentDTO createdComment = commentsService.createComment(commentDTO, username);
    return ResponseEntity.status(201).body(createdComment);
  }

  /**
   * Met à jour un commentaire existant.
   * 
   * @param commentId  ID du commentaire.
   * @param commentDTO Nouvelles données du commentaire.
   * @return Le commentaire mis à jour.
   */
  @PutMapping("/{commentId}")
  public ResponseEntity<CommentDTO> updateComment(@PathVariable Long commentId, @RequestBody CommentDTO commentDTO) {
    CommentDTO updatedComment = commentsService.updateComment(commentId, commentDTO);
    return ResponseEntity.ok(updatedComment);
  }

  /**
   * Supprime un commentaire.
   * 
   * @param commentId ID du commentaire à supprimer.
   * @return Une réponse sans contenu si la suppression est réussie.
   */
  @DeleteMapping("/{commentId}")
  public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
    commentsService.deleteComment(commentId);
    return ResponseEntity.noContent().build();
  }
}
