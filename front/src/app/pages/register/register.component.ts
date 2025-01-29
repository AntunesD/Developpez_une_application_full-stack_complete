import { Component, HostListener } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html'
})
export class RegisterComponent {
  isDesktop: boolean = false;
  registerForm!: FormGroup;

  constructor(private fb: FormBuilder) {
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
      // Traitement du formulaire
      console.log('Form submitted', this.registerForm.value);
    } else {
      console.log('Form invalid');
    }
  }
}
