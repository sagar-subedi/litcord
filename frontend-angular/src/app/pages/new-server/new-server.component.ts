import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ServerService } from '../../services/server.service';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-new-server',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './new-server.component.html',
  styleUrl: './new-server.component.scss'
})
export class NewServerComponent {
  serverName: string = '';
  inviteCode: string = '';
  serverImage?: File;
  imagePreview: string | null = null; // For holding the preview image URL

  constructor(private serverService: ServerService, private authService: AuthService, private routerService: Router) {}

  handleImageUpload(event: Event) {
    const target = event.target as HTMLInputElement;
    if (target?.files?.[0]) {
      this.serverImage = target.files[0];

      // Generate a preview using FileReader
      const reader = new FileReader();
      reader.onload = () => {
        this.imagePreview = reader.result as string; // Set the preview URL
      };
      reader.readAsDataURL(this.serverImage);
    }
  }

  createServer() {
    let userId = parseInt(this.authService.getCurrentUserId()!);

    if (this.serverName && this.serverImage) {
      this.serverService
        .createServer(this.serverName, userId, this.serverImage)
        .subscribe({
          next: (data) => {
            this.routerService.navigate(['/servers'])
          },
        });
    }
  }

  joinServer(): void {
    // let userId = this.authService.getCurrentUser().id;
    let userId = this.authService.getCurrentUserId();
    if(!userId){
      return;
    }
    this.serverService.joinServer(this.inviteCode, userId).subscribe({
      next: (server) => {
        console.log('Joined server:', server );
        this.routerService.navigate(['/servers'])
      },
      error: (err) => console.error('Error joining server:', err),
    });
  }
}
