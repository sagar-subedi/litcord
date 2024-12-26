import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ServerService } from '../../../services/server.service';

@Component({
  standalone: true,
  selector: 'app-create-server-modal',
  templateUrl: './create-server-modal.component.html',
  styleUrls: ['./create-server-modal.component.scss'],
  imports: [CommonModule, FormsModule],
})
export class CreateServerModalComponent {
  @Input() isVisible = false; // Controls visibility of the modal
  @Output() close = new EventEmitter<void>();
  @Output() serverCreated = new EventEmitter<any>();

  serverName: string = '';
  serverImage?: File;
  imagePreview: string | null = null; // For holding the preview image URL

  constructor(private serverService: ServerService) {}
  closeModal() {
    this.close.emit();
  }

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
    if (this.serverName && this.serverImage) {
      this.serverService
        .createServer(this.serverName, 1, this.serverImage)
        .subscribe({
          next: (data) => {
            this.serverCreated.emit(data);
            this.closeModal();
          },
        });
    }
  }
}
