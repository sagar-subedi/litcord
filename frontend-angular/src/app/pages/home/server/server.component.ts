import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatIcon } from '@angular/material/icon';
import { ChannelComponent } from '../channel/channel.component';
import { CommonModule } from '@angular/common';
import { AccountModalComponent } from '../account-modal/account-modal.component';

@Component({
  selector: 'app-server',
  standalone: true,
  imports: [CommonModule, MatIcon, ChannelComponent, AccountModalComponent],
  templateUrl: './server.component.html',
  styleUrl: './server.component.scss'
})
export class ServerComponent implements OnInit {
  @Input() servers!: any[];
  @Output() createServer = new EventEmitter<void>();
  selectedServer: any;
  isProfileModalOpen = false;

  ngOnInit(): void {
    this.selectedServer = this.servers[0];
  }

  onSelectServer(server:any){
    this.selectedServer = server;
  }

  onClickAddServer(){
    this.createServer.emit();
  }

  openProfileModal() {
    this.isProfileModalOpen = true;
  }

  closeProfileModal() {
    this.isProfileModalOpen = false;
  }
}
