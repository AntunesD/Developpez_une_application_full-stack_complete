import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
// Assurez-vous d'importer AuthService

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  showButtons: boolean = false;

  constructor(private router: Router, private authService: AuthService) { }

  ngOnInit(): void { }

  start() {
    if (this.authService.isLoggedIn()) {
      // Si l'utilisateur est connecté, redirige vers /articles
      this.router.navigate(['/articles']);
    } else {
      // Sinon, affiche les boutons d'inscription et de connexion
      this.showButtons = true;
    }
  }

  // Méthodes pour rediriger vers les pages de connexion et d'inscription
  login() {
    this.router.navigate(['/login']);
  }

  register() {
    this.router.navigate(['/register']);
  }
}
