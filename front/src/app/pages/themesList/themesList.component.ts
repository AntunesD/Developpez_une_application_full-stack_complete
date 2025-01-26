import { Component, OnInit } from '@angular/core';
import { Theme } from 'src/app/interfaces/theme.interface';
import { ThemesService } from 'src/app/services/themes.services';

@Component({
  selector: 'app-themes',
  templateUrl: './themesList.component.html'
})
export class ThemesComponent {
  themes: Theme[] = [];

  constructor(private themesService: ThemesService) { }

  ngOnInit(): void {
    this.loadThemes();
  }

  private loadThemes(): void {
    this.themesService.getThemes().subscribe({
      next: (themes) => {
        this.themes = themes;
      },
      error: (error) => {
        console.error('Erreur lors du chargement des themes:', error);
      }
    });
  }

}
