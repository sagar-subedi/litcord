import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  standalone: true,
  selector: 'app-create-channel-modal',
  templateUrl: './create-channel-modal.component.html',
  styleUrls: ['./create-channel-modal.component.scss'],
  imports: [FormsModule, CommonModule]
})
export class CreateChannelModalComponent {
  isModalOpen: boolean = false;
  channelName: string = '';
  channelType: string = '';

  openModal() {
    this.isModalOpen = true;
  }

  closeModal() {
    this.isModalOpen = false;
    this.channelName = '';
    this.channelType = '';
  }

  createChannel() {
    if (this.channelName.trim() && this.channelType) {
      const newChannel = {
        name: this.channelName,
        type: this.channelType
      };
      console.log('Channel Created:', newChannel);
      this.closeModal();
    }
  }
}
