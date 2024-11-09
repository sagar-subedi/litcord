import { Component, HostListener, Input, OnInit } from '@angular/core';
import { MatFormField } from '@angular/material/form-field';
import { MatIcon } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { ChannelButtonComponent } from './channel-button/channel-button.component';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { CreateChannelModalComponent } from '../create-channel-modal/create-channel-modal.component';

@Component({
  selector: 'app-channel',
  standalone: true,
  imports: [MatFormField, MatInputModule, MatIcon, ChannelButtonComponent, CommonModule, CreateChannelModalComponent],
  templateUrl: './channel.component.html',
  styleUrl: './channel.component.scss'
})
export class ChannelComponent implements OnInit {
  @Input() server!: any;
  textChannels: any[] = [];
  videoChannels: any[] = [];
  selectedChannel: any;
  isDropdownOpen: boolean = false;

  constructor(private route: ActivatedRoute, private router: Router){
    
  }

  ngOnInit(): void {
    this.route.params.subscribe(
      {
        next: (routeMap)=> {
          this.server.channels.forEach((channel: any)=>{
            if(channel.id == routeMap['channelid']) {
              this.selectedChannel = channel;
            }
          })
        }
      }
    )
    this.server.channels.forEach((channel: { type: string; name: string; }) => {
      if(channel.type=='text') this.textChannels.push(channel);
      else if(channel.type=='video') this.videoChannels.push(channel);
    });
  }

  onClickChannelButton(channelId:string){
    this.router.navigate([`/servers/${this.server.id}/channels/${channelId}`])
  }

  // Toggle the dropdown
  toggleDropdown() {
    this.isDropdownOpen = !this.isDropdownOpen;
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
    console.log('Delete Server clicked');
    this.isDropdownOpen = false;
  }
  

}
