import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Article } from 'src/app/interfaces/article.interface';
import { ArticlesService } from 'src/app/services/articles.service';

@Component({
  selector: 'app-article-id',
  templateUrl: './articleDetails.component.html'
})
export class ArticleDetailsComponent implements OnInit {
  article!: Article;
  newComment: string = ''; // Nouveau champ pour le commentaire

  constructor(
    private route: ActivatedRoute,
    private articlesService: ArticlesService
  ) { }

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id')); // Récupère l'ID depuis l'URL
    this.articlesService.getArticleById(id).subscribe({
      next: (data) => {
        this.article = data;
      },
      error: (err) => {
        console.error('Erreur lors du chargement de l\'article:', err);
      }
    });
  }

  submitComment(): void {
    if (this.newComment.trim()) {
      console.log('Nouveau commentaire :', this.newComment);
      // Réinitialiser le champ après l'envoi
      this.newComment = '';
    } else {
      console.warn('Le commentaire est vide.');
    }
  }
}
