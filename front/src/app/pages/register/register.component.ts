import { Component, HostListener } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html'
})

export class RegisterComponent {
  isDesktop: boolean = false;
  registerForm!: FormGroup;
  errorMessage: string | null = null;  // Ajout de la variable pour le message d'erreur

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.checkScreenSize();
    this.createForm();
  }

  // Méthode pour écouter le redimensionnement de la fenêtre
  @HostListener('window:resize', ['$event'])
  onResize() {
    this.checkScreenSize();
  }

  // Méthode pour vérifier la taille de l'écran
  checkScreenSize() {
    this.isDesktop = window.innerWidth >= 768; // Ajustez la largeur selon vos besoins
  }

  // Méthode pour créer le formulaire
  createForm() {
    this.registerForm = this.fb.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, this.passwordValidator]]
    });
  }

  // Validation personnalisée pour le mot de passe
  passwordValidator(control: any) {
    const password = control.value;
    const minLength = 8;
    const regex = {
      number: /[0-9]/,
      lowercase: /[a-z]/,
      uppercase: /[A-Z]/,
      special: /[!@#$%^&*(),.?":{}|<>]/
    };

    if (!password) return null;

    if (password.length < minLength) {
      return { minLength: true };
    }
    if (!regex.number.test(password)) {
      return { number: true };
    }
    if (!regex.lowercase.test(password)) {
      return { lowercase: true };
    }
    if (!regex.uppercase.test(password)) {
      return { uppercase: true };
    }
    if (!regex.special.test(password)) {
      return { special: true };
    }

    return null;
  }
  onSubmit() {
    if (this.registerForm.valid) {
      const formData = this.registerForm.value;
      this.authService.register(formData).subscribe(
        (response) => {
          console.log('Registration successful', response);
          this.authService.saveToken(response.token);
          // Redirection vers la page des articles après une inscription réussie
          this.router.navigate(['/articles']);
        },
        (error) => {
          console.error('Registration failed', error);

          // Vérification de l'erreur spécifique pour l'utilisateur déjà existant
          if (error.status === 400) {
            this.errorMessage = error.error.message;
          }
        }
      );
    } else {
      console.log('Form invalid');
    }
  }

}
