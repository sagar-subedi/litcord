import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ServerService } from '../../../services/server.service';
import { ActivatedRoute, Route } from '@angular/router';

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

  @Output() channelCreated = new EventEmitter<any>;

  constructor(private serverService: ServerService, private route: ActivatedRoute){

  }

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
        type: this.channelType,
        adminId: 1,
        serverId: this.route.snapshot.params['serverid']
      };
//todo: make sure the hardcoded adminId or userId are handled in coming stories

      this.serverService.createChannel(newChannel).subscribe(
        {
          next: (data: string) => {
            this.closeModal();
            this.channelCreated.emit(data);
          }
        }
      );
    }
  }
}
