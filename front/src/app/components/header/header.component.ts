import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html'
})
export class HeaderComponent {
  // Variable pour gérer l'état de la sidebar (ouverte ou fermée)
  sidebarOpen = false;
  isTransitioning = false;

  constructor(private router: Router, private authService: AuthService) { }

  // Méthode pour vérifier si le lien est actif
  isActive(route: string): boolean {
    return this.router.url === route;
  }

  // Utiliser la méthode isLoggedIn du AuthService pour vérifier l'authentification
  isLoggedIn(): boolean {
    return this.authService.isLoggedIn();
  }

  // Méthode pour ouvrir ou fermer la sidebar
  toggleSidebar() {
    this.sidebarOpen = !this.sidebarOpen;
  }
}
