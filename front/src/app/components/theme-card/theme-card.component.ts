import { Component, Input } from '@angular/core';
import { ThemesService } from 'src/app/services/themes.services';

@Component({
  selector: 'app-theme-card',
  templateUrl: './theme-card.component.html'
})
export class ThemeCardComponent {

  @Input() themes: { id: number; title: string; description: string; subscribed: boolean }[] = [];

  constructor(private themesService: ThemesService) { }

  // Méthode pour basculer l'abonnement d'un thème
  toggleSubscription(themeId: number): void {
    const theme = this.themes.find(t => t.id === themeId);

    if (theme) {
      const action = theme.subscribed ? 'Unsubscribing' : 'Subscribing';

      this.themesService.subscribeToTheme(themeId).subscribe({
        next: (response) => {
          theme.subscribed = !theme.subscribed;
          console.log(`${action} successful: ${response.message}`);
        },
        error: (error) => {
          console.error(`${action} failed`, error);
        }
      });
    }
  }
}
