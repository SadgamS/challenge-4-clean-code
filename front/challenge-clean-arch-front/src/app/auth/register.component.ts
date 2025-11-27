import { Component, signal } from '@angular/core';
import { AuthService } from '../core/auth.service';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  imports: [RouterModule, FormsModule],
  selector: 'app-register',
  templateUrl: './register.component.html',
})
export class RegisterComponent {
  username = signal('');
  password = signal('');

  constructor(private auth: AuthService, private router: Router) {}

  onSubmit() {
    this.auth.register(this.username(), this.password()).subscribe({
      next: () => this.router.navigate(['/login']),
      error: (err) => alert('No se pudo registrar: ' + (err?.message || err)),
    });
  }
}
