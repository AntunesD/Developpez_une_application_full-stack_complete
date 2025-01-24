import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html'
})
export class HeaderComponent {
  constructor(private router: Router) { }

  // Méthode pour vérifier si le lien est actif
  isActive(route: string): boolean {
    return this.router.url === route;
  }

  // TODO: À remplacer par votre véritable logique d'authentification
  isLoggedIn(): boolean {
    return true; // Pour le moment, on retourne toujours true
  }
} 