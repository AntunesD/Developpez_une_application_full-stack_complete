import { Component, HostListener } from '@angular/core';
import { Article } from '../../interfaces/article.interface';
import { ArticlesService } from 'src/app/services/articles.service';

@Component({
  selector: 'app-articles',
  templateUrl: './articlesList.component.html'
})
export class ArticlesComponent {
  articles: Article[] = [];
  sortField: 'title' | 'createdAt' | 'username' = 'createdAt';
  sortDirection: 'asc' | 'desc' = 'desc';


  constructor(private articleService: ArticlesService) { }

  ngOnInit(): void {
    this.loadArticles();
  }

  // Méthode pour charger les articles
  private loadArticles(): void {
    this.articleService.getArticles().subscribe({
      next: (articles) => {
        this.articles = articles;
        this.sortArticles(this.sortField);
      },
      error: (error) => {
        console.error('Erreur lors du chargement des articles:', error);
        // Ici vous pourriez ajouter une gestion d'erreur plus élaborée
      }
    });
  }

  // Méthode pour formater la date
  formatDate(date: string): string {
    return new Date(date).toLocaleDateString('fr-FR', {
      year: 'numeric',
      month: 'long',
      day: 'numeric'
    });
  }

  // Méthode pour trier les articles
  sortArticles(field: 'title' | 'createdAt' | 'username'): void {

    if (this.sortField === field) {
      this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    } else {
      this.sortField = field;
      this.sortDirection = 'asc';
    }

    this.articles.sort((a, b) => {
      let valueA = field === 'username' ? a.user[field] : a[field];
      let valueB = field === 'username' ? b.user[field] : b[field];

      if (this.sortDirection === 'desc') {
        [valueA, valueB] = [valueB, valueA];
      }

      // Comparaison spécifique pour les chaînes
      if (typeof valueA === 'string' && typeof valueB === 'string') {
        return valueA.localeCompare(valueB, 'fr', { sensitivity: 'base' });
      }

      // Comparaison standard pour les autres types
      return valueA > valueB ? 1 : valueA < valueB ? -1 : 0;
    });

  }

  // Méthode pour obtenir l'icône de tri
  getSortIcon(field: 'title' | 'createdAt' | 'username'): string {
    if (this.sortField !== field) return 'fa-sort';
    return this.sortDirection === 'asc' ? 'fa-arrow-up' : 'fa-arrow-down';
  }

  showSortMenu = false;

  sortOptions = [
    { label: 'Titre', value: 'title' as const },
    { label: 'Date de création', value: 'createdAt' as const },
    { label: 'Auteur', value: 'username' as const }
  ];

  // Méthode pour basculer le menu de tri
  toggleSortMenu(): void {
    this.showSortMenu = !this.showSortMenu;
  }

  // Ajoutez cette méthode pour fermer le menu lors d'un clic à l'extérieur
  @HostListener('document:click', ['$event'])
  onDocumentClick(event: MouseEvent): void {
    const target = event.target as HTMLElement;
    if (!target.closest('.relative')) {
      this.showSortMenu = false;
    }
  }
}