import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Theme } from 'src/app/interfaces/theme.interface';
import { ArticlesService } from 'src/app/services/articles.service';
import { ThemesService } from 'src/app/services/themes.services';

@Component({
  selector: 'app-article-form',
  templateUrl: './article-form.component.html'
})
export class ArticleFormComponent {
  themes: Theme[] = [];
  isSubmitting = false;
  showSuccessMessage = false;

  constructor(
    private themesService: ThemesService,
    private articlesService: ArticlesService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loadThemes();
  }

  private loadThemes(): void {
    this.themesService.getThemes().subscribe({
      next: (themes) => {
        this.themes = themes;
      },
      error: (error) => {
        console.error('Erreur lors du chargement des articles:', error);
      }
    });
  }

  article = {
    theme: '',
    title: '',
    content: ''
  };

  createArticle() {
    if (!this.article.title || !this.article.content || !this.article.theme) {
      console.log('Tous les champs sont obligatoires.');
      return;
    }
    this.isSubmitting = true;
    // Structure de la requête selon ton format
    const articleData = {
      title: this.article.title,
      content: this.article.content,
      theme: {
        id: this.article.theme
      }
    };

    this.articlesService.createArticle(articleData).subscribe({
      next: (createdArticle) => {
        console.log('Article créé avec succès :', createdArticle);
        this.showSuccessMessage = true;  // Afficher le message de succès

        // Rediriger vers /articles après 3 secondes
        setTimeout(() => {
          this.router.navigate(['/articles']);
        }, 3000);
      },
      error: (error) => {
        console.error('Erreur lors de la création de l\'article:', error);
      }
    });
  }
}
