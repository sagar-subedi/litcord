import { Component } from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  username = '';
  password = '';

  constructor(private authService: AuthService, private router: Router) {}

  login() {
    // Call your login API and fetch the token
    this.authService.login(this.username, this.password).subscribe(
      (response: any) => {
        this.authService.setToken(response.token); 
        // Save the token
        this.router.navigate(['/']); // Redirect to the home page
      },
      (error:any) => {
        console.error('Login failed:', error);
        alert('Invalid email or password'); // User feedback
      }
    );
  }
}
