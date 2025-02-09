import { Component, HostListener } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent {
  isDesktop: boolean = false;
  loginForm: FormGroup;
  errorMessage: string = '';

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.checkScreenSize();
    this.loginForm = this.formBuilder.group({
      usernameOrEmail: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  // Méthode pour écouter le redimensionnement de la fenêtre
  @HostListener('window:resize', ['$event'])
  onResize() {
    this.checkScreenSize();
  }

  // Méthode pour vérifier la taille de l'écran
  checkScreenSize() {
    this.isDesktop = window.innerWidth >= 768;
  }

  // Méthode pour soumettre le formulaire de connexion
  onSubmit(): void {
    if (this.loginForm.valid) {
      this.authService.login(this.loginForm.value).subscribe({
        next: (response) => {
          this.authService.saveToken(response.token);
          this.router.navigate(['/articles']); // Redirection vers la page d'accueil
        },
        error: (error) => {
          this.errorMessage = 'Identifiants incorrects';
          console.error('Erreur de connexion:', error);
        }
      });
    }
  }
}