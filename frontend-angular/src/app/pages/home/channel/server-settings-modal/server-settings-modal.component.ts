import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-server-settings-modal',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './server-settings-modal.component.html',
  styleUrl: './server-settings-modal.component.scss'
})
export class ServerSettingsModalComponent {
  @Input() server: { id: number; name: string } | null = null; // The server to edit
  @Output() save = new EventEmitter<{ id: number; name: string }>();
  @Output() close = new EventEmitter<void>();


  closeModal() {
    this.close.emit();
  }

  updateSettings() {
    if (this.server) {
      this.save.emit(this.server);
    }
  }
}
