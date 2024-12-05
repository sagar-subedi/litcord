import { Component } from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  username = '';
  password = '';

  constructor(private authService: AuthService, private router: Router) {}

  login() {
    // Call your login API and fetch the token
    const fakeToken = 'your.jwt.token'; // Replace with API response
    this.authService.setToken(fakeToken);
    this.router.navigate(['/servers/23/channels/22']);
  }
}
