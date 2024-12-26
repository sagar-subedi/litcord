import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatIcon } from '@angular/material/icon';

@Component({
  selector: 'app-channel-button',
  standalone: true,
  imports: [CommonModule, FormsModule, MatIcon],
  templateUrl: './channel-button.component.html',
  styleUrl: './channel-button.component.scss'
})
export class ChannelButtonComponent {

  @Input() channel!:any;
  @Input() isSelected: boolean = false;

  @Output() deleteChannel = new EventEmitter<number>();
  @Output() updateChannelData = new EventEmitter<any>();

  isEditFormVisible = false;
  editChannelData: any = {};

  confirmDeleteChannel(): void {
    const confirmDelete = confirm(`Are you sure you want to delete the channel "${this.channel.name}"?`);
    if (confirmDelete) {
      this.deleteChannel.emit(this.channel.id);
    }
  }

  openEditForm(): void {
    this.isEditFormVisible = true;
    this.editChannelData = { ...this.channel }; // Copy current channel data
  }

  closeEditForm(): void {
    this.isEditFormVisible = false;
  }

  updateChannel(): void {
    this.updateChannelData.emit(this.editChannelData);
    this.closeEditForm();
  }

}
