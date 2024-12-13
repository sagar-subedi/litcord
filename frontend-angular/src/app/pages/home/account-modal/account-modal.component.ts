import { Component, EventEmitter, Output } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatIcon } from '@angular/material/icon';
import { Router } from '@angular/router';

@Component({
  selector: 'app-account-modal',
  standalone: true,
  imports: [ReactiveFormsModule, MatIcon],
  templateUrl: './account-modal.component.html',
  styleUrl: './account-modal.component.scss'
})
export class AccountModalComponent {
  @Output() close = new EventEmitter<void>();
  profileForm: FormGroup;

  constructor(private fb: FormBuilder, private router: Router) {
    this.profileForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
    });
  }

  updateProfile() {
    if (this.profileForm.valid) {
      console.log('Profile Updated:', this.profileForm.value);
      // Simulate API call
      alert('Profile updated successfully!');
      this.close.emit(); // Close modal on success
    }
  }

  logout() {
    localStorage.removeItem('token'); // Remove token
    this.router.navigate(['/auth/login']); // Redirect to login
    this.close.emit(); // Close modal
  }

  closeModal(){
    this.close.emit();
  }

}
