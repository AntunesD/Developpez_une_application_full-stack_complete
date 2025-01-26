import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) { }

  canActivate(route: import('@angular/router').ActivatedRouteSnapshot, state: import('@angular/router').RouterStateSnapshot): boolean {
    const isLoggedIn = this.authService.isLoggedIn();

    // Si l'utilisateur est connecté
    if (isLoggedIn) {
      // Si l'utilisateur essaie d'accéder aux pages login ou register, le rediriger vers la page d'accueil
      if (route.routeConfig?.path === 'login' || route.routeConfig?.path === 'register') {
        this.router.navigate(['/']);  // Redirige vers la page d'accueil ou autre page publique
        return false;
      }
      // L'utilisateur peut accéder à des pages protégées
      return true;
    } else {
      // Si l'utilisateur n'est pas connecté, l'empêcher d'accéder aux pages autres que login ou register
      if (route.routeConfig?.path !== 'login' && route.routeConfig?.path !== 'register') {
        this.router.navigate(['/login']); // Rediriger vers la page login
        return false;
      }
      return true; // Permet l'accès à login et register si l'utilisateur n'est pas connecté
    }
  }
}
