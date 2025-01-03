import {
  Component,
  EventEmitter,
  HostListener,
  Input,
  OnChanges,
  OnInit,
  Output,
  SimpleChanges,
} from '@angular/core';
import { MatFormField } from '@angular/material/form-field';
import { MatIcon, MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { ChannelButtonComponent } from './channel-button/channel-button.component';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { CreateChannelModalComponent } from '../create-channel-modal/create-channel-modal.component';
import { ServerService } from '../../../services/server.service';
import { ContentComponent } from '../content/content.component';

@Component({
  selector: 'app-channel',
  standalone: true,
  imports: [
    MatFormField,
    MatInputModule,
    MatIconModule,
    ChannelButtonComponent,
    CommonModule,
    CreateChannelModalComponent,
    ContentComponent,
  ],
  templateUrl: './channel.component.html',
  styleUrl: './channel.component.scss',
})
export class ChannelComponent implements OnInit, OnChanges {
  @Input() server!: any;
  @Output() serverDelete = new EventEmitter<any>();
  textChannels: any[] = [];
  videoChannels: any[] = [];
  selectedChannel: any;
  isDropdownOpen: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private serverService: ServerService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe({
      next: (routeMap) => {
        this.setSelectedChannel(routeMap);
      },
    });
    this.populateTextAndVideoChannels();
  }

  setSelectedChannel(routeMap: Params) {
    this.server.channels.forEach((channel: any) => {
      if (channel.id == routeMap['channelid']) {
        this.selectedChannel = channel;
      }
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.populateTextAndVideoChannels();
    this.setSelectedChannel(this.route.snapshot.params);
  }

  populateTextAndVideoChannels() {
    if (!this.server) return;
    this.textChannels = [];
    this.videoChannels = [];
    this.server.channels.forEach((channel: { type: string; name: string }) => {
      if (channel.type == 'TEXT') this.textChannels.push(channel);
      else if (channel.type == 'VIDEO') this.videoChannels.push(channel);
    });
  }

  onClickChannelButton(channelId: string) {
    this.router.navigate([`/servers/${this.server.id}/channels/${channelId}`]);
  }

  onChannelCreate($event: any) {
    console.log($event);
    if ($event.type === 'TEXT') {
      this.textChannels.push($event);
    } else if ($event.type == 'VIDEO') {
      this.videoChannels.push($event);
    }
    this.server.channels.push($event);
  }

  // Toggle the dropdown
  toggleDropdown() {
    this.isDropdownOpen = !this.isDropdownOpen;
  }

  handleDeleteChannel($channelId: number): void {
    if (this.server.channels.length === 1) {
      alert("This this only channel in your server. You can't delete this.");
      return;
    }
    // Call API or service to delete channel
    this.serverService.deleteChannel($channelId).subscribe({
      next: (data) => {
        this.server.channels = this.server.channels.filter(
          (channel: any) => channel.id !== $channelId
        );
        this.populateTextAndVideoChannels();
        this.router.navigate([
          `/servers/${this.server.id}/channels/${this.server.channels[0].id}`,
        ]);
        console.log('Channel deleted successfully');
      },
    });
    console.log(`Deleting channel with ID: ${$channelId}`);
  }

  handleUpdateChannel($updatedChannel: any): void {
    // Call API or service to update channel
    console.log('Updated Channel:', $updatedChannel);
  }

  // Close the dropdown when clicking outside
  @HostListener('document:click', ['$event'])
  onClickOutside(event: Event) {
    const target = event.target as HTMLElement;
    if (!target.closest('.relative')) {
      this.isDropdownOpen = false;
    }
  }

  // Handlers for each operation
  invitePeople() {
    console.log('Invite People clicked');
    this.isDropdownOpen = false; // Close dropdown after action
  }

  serverSettings() {
    console.log('Server Settings clicked');
    this.isDropdownOpen = false;
  }

  manageMembers() {
    console.log('Manage Members clicked');
    this.isDropdownOpen = false;
  }

  createChannel() {
    console.log('Create Channel clicked');
    this.isDropdownOpen = false;
  }

  deleteServer() {
    this.serverDelete.emit(this.server.id);
    this.isDropdownOpen = false;
  }
}
