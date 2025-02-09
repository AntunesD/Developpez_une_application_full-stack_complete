package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentsRepository extends JpaRepository<Comment, Long> {
  List<Comment> findByArticleId(Long articleId); // Méthode pour récupérer les commentaires par article
}
