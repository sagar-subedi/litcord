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
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { ChannelButtonComponent } from './channel-button/channel-button.component';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { CreateChannelModalComponent } from '../create-channel-modal/create-channel-modal.component';
import { ServerService } from '../../../services/server.service';
import { ContentComponent } from '../content/content.component';
import { InviteDialogComponent } from '../invite-dialog/invite-dialog.component';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { ServerSettingsModalComponent } from './server-settings-modal/server-settings-modal.component';
import { ManageMembersModalComponent } from './manage-members-modal/manage-members-modal.component';
import { Membership } from '../../../types/Membership';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-channel',
  standalone: true,
  imports: [
    MatInputModule,
    MatIconModule,
    ChannelButtonComponent,
    CommonModule,
    CreateChannelModalComponent,
    ServerSettingsModalComponent,
    ManageMembersModalComponent,
    ContentComponent,
    MatDialogModule,
  ],
  templateUrl: './channel.component.html',
  styleUrl: './channel.component.scss',
})
export class ChannelComponent implements OnInit, OnChanges {
  @Input() server!: any;
  @Output() serverDelete = new EventEmitter<any>();
  @Output() serverLeave = new EventEmitter<any>();
  textChannels: any[] = [];
  videoChannels: any[] = [];
  selectedChannel: any;
  isDropdownOpen: boolean = false;
  isSettingsModalVisible = false;

  members: Membership[] = []; // Example members
  isAdmin = false; // Example: current user is admin
  isManageMembersModalVisible = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private serverService: ServerService,
    private dialog: MatDialog,
    private authService: AuthService
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

    //resetting isAdmin flag for newer server, only set to true, if current user is admin of the newly selected server
    this.isAdmin = false;
    //poi - this part may be optimized later, seems little redundant to fetch entire members, just to see if the current
    //user is an ADMIN or not.
    this.serverService.getMembersByServer(this.server.id).subscribe({
      next: (members) => {
        let userId = this.authService.getCurrentUserId();
        this.members = members;
        this.members.forEach((member) => {
          if (member.userId.toString() == userId && member.type == 'ADMIN') {
            this.isAdmin = true;
          }
        });
      },
    });
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

  openInviteDialog(): void {
    const inviteCode = this.server.inviteCode; // Replace with your actual invite code logic
    this.dialog.open(InviteDialogComponent, {
      data: { inviteCode },
      width: '400px',
    });
    this.isDropdownOpen = false;
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

  leaveServer() {
    this.serverLeave.emit(this.server.id);
    this.isDropdownOpen = false;
  }

  openServerSettings(server: { id: number; name: string }) {
    // this.server = { ...server }; // Clone server object to avoid direct mutation
    this.isSettingsModalVisible = true;
    this.isDropdownOpen = false;
  }

  saveServerSettings($event: any) {
    // Update the server on save
    this.server = $event;
    this.isSettingsModalVisible = false;
    this.isDropdownOpen = false;
  }

  openManageMembersDialog() {
    this.serverService.getMembersByServer(this.server.id).subscribe({
      next: (members) => {
        this.members = members;
        this.isManageMembersModalVisible = true;
        this.isDropdownOpen = false;
      },
    });
  }

  closeManageMembersDialog() {
    this.isManageMembersModalVisible = false;
  }

  handleRemoveMember($event: any) {
    this.members = this.members.filter(
      (member) => member.membershipId !== $event
    );
    alert(`Member with ID ${$event} removed.`);
  }
}
