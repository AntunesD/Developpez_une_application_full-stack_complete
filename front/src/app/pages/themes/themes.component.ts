import { Component, OnInit } from '@angular/core';
import { Themes } from 'src/app/interfaces/themes.interface';
import { ThemesService } from 'src/app/services/themes.services';

@Component({
  selector: 'app-themes',
  templateUrl: './themes.component.html'
})
export class ThemesComponent {
  themes: Themes[] = [];

  constructor(private themesService: ThemesService) { }

  ngOnInit(): void {
    this.loadArticles();
  }

  private loadArticles(): void {
    this.themesService.getThemes().subscribe({
      next: (themes) => {
        this.themes = themes;
      },
      error: (error) => {
        console.error('Erreur lors du chargement des articles:', error);
      }
    });
  }

  toggleSubscription(themesId: number): void {
    const course = this.themes.find(c => c.id === themesId);
    if (course) {
      course.subscribed = !course.subscribed;
    }
  }

}
