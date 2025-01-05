import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { CallComponent } from '../../../components/call/call.component';
import { MatIconModule } from '@angular/material/icon';
import { ChatComponent } from '../../../components/chat/chat.component';

@Component({
  selector: 'app-content',
  standalone: true,
  imports: [CommonModule, CallComponent, ChatComponent, MatIconModule],
  templateUrl: './content.component.html',
  styleUrl: './content.component.scss'
})
export class ContentComponent {
  @Input() channel!:any;
  isInCall: boolean = false;

  startCall(){
    this.isInCall = true;
  }

  endCall(){
    this.isInCall = false;
  }

}
