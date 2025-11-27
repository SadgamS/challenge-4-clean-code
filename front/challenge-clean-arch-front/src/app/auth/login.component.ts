import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../core/auth.service';
import { Component, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  imports: [FormsModule, RouterModule],
  selector: 'app-login',
  templateUrl: './login.component.html',
})
export class LoginComponent {
  username = '';
  password = '';

  constructor(private auth: AuthService, private router: Router) {}

  onSubmit() {
    console.log('Intentando iniciar sesión con', this.username, this.password);
    this.auth.login(this.username, this.password).subscribe({
      next: () => this.router.navigate(['/']),
      error: () => alert('Credenciales inválidas'),
    });
  }
}
