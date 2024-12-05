import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatIcon } from '@angular/material/icon';
import { ChannelComponent } from '../channel/channel.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-server',
  standalone: true,
  imports: [CommonModule, MatIcon, ChannelComponent],
  templateUrl: './server.component.html',
  styleUrl: './server.component.scss'
})
export class ServerComponent implements OnInit {
  @Input() servers!: any[];
  @Output() createServer = new EventEmitter<void>();
  selectedServer: any;

  ngOnInit(): void {
    this.selectedServer = this.servers[0];
  }

  onSelectServer(server:any){
    this.selectedServer = server;
  }

  onClickAddServer(){
    this.createServer.emit();
  }
}
