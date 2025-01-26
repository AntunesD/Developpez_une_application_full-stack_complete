import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Profil } from 'src/app/interfaces/profil.interface';
import { AuthResponse } from 'src/app/models/auth-response.model';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-profil',
  templateUrl: './profil.component.html'
})
export class ProfilComponent implements OnInit {

  user: Profil = {
    username: '',
    email: '',
    themes: []
  };

  message: string = ''; // Pour stocker le message à afficher
  messageType: 'success' | 'error' = 'success'; // Pour gérer le type du message

  constructor(private userService: UserService, private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.loadThemes();
  }

  private loadThemes(): void {
    this.userService.getUser().subscribe({
      next: (user) => {
        this.user = user;
      },
      error: (error) => {
        console.error('Erreur lors du chargement du profil :', error);
      }
    });
  }

  saveUserInfo(): void {
    // Appeler updateUser pour sauvegarder les informations de l'utilisateur
    this.userService.updateUser(this.user.username, this.user.email).subscribe({
      next: (response: AuthResponse) => {
        this.authService.saveToken(response.token);
        console.log('Informations utilisateur mises à jour avec succès:', response);

        // Afficher un message de succès
        this.message = 'Informations utilisateur mises à jour avec succès!';
        this.messageType = 'success';

        // Fermer la modale après un délai
        this.autoCloseModal();
      },
      error: (error) => {
        console.error('Erreur lors de la mise à jour des informations utilisateur:', error);

        // Afficher un message d'erreur
        this.message = 'Erreur lors de la mise à jour des informations utilisateur!';
        this.messageType = 'error';

        // Fermer la modale après un délai
        this.autoCloseModal();
      }
    });
  }

  // Fonction pour fermer la modale après un délai
  private autoCloseModal(): void {
    setTimeout(() => {
      this.message = ''; // Réinitialiser le message après un délai
    }, 3000); // 3 secondes
  }

  // Fonction pour fermer la modale manuellement
  closeModal(): void {
    this.message = ''; // Réinitialiser le message immédiatement
  }

  // Méthode pour déconnecter l'utilisateur
  logout(): void {
    this.authService.logout();
    console.log('Utilisateur déconnecté');
    this.router.navigate(['']);
  }
}
