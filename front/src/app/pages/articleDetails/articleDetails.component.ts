import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Article } from 'src/app/interfaces/article.interface';
import { ArticlesService } from 'src/app/services/articles.service';
import { CommentsService } from 'src/app/services/comments.service';

@Component({
  selector: 'app-article-id',
  templateUrl: './articleDetails.component.html'
})
export class ArticleDetailsComponent implements OnInit {
  article!: Article;
  newComment: string = '';

  constructor(
    private route: ActivatedRoute,
    private articlesService: ArticlesService,
    private commentsService: CommentsService
  ) { }

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id')); // Récupérer l'id de l'article depuis l'URL
    this.articlesService.getArticleById(id).subscribe({
      next: (data) => {
        this.article = data;
      },
      error: (err) => {
        console.error('Erreur lors du chargement de l\'article:', err);
      }
    });
  }

  // Méthode pour soumettre un commentaire
  submitComment(): void {
    if (this.newComment.trim()) {
      this.commentsService.postComment(this.article.id, this.newComment).subscribe({
        next: (response) => {
          console.log('Commentaire envoyé avec succès :', response);
          // Réinitialiser le champ après l'envoi
          this.newComment = '';
        },
        error: (err) => {
          console.error('Erreur lors de l\'envoi du commentaire :', err);
        }
      });
    } else {
      console.warn('Le commentaire est vide.');
    }
  }
}
