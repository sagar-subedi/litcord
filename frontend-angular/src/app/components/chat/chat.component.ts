import { CommonModule } from '@angular/common';
import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Message } from '../../types/Message';
import { MessageService } from '../../services/message.service';
import { AuthService } from '../../services/auth.service';
import { SignalingService } from '../../services/signaling-service.service';

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.scss',
})
export class ChatComponent implements OnInit {
  @Input() channel: any;
  messages: Message[] = [];
  messageInput: string = '';
  currentUser: string = 'user1'; // Replace with the actual logged-in user identifier
  currentUserEmail: string = 'user@gmail.com';
  isLoading: boolean = false;

  @ViewChild('chatContainer', { static: true }) chatContainer!: ElementRef;

  currentPage = 0;
  pageSize = 20;

  constructor(
    private messageService: MessageService,
    private authService: AuthService,
    private signalingService: SignalingService
  ) {}

  ngOnInit() {
    this.currentUser = this.authService.getCurrentUserId() ?? '';
    this.currentUserEmail = this.authService.getCurrentUserEmail() ?? '';
    this.loadMessages();
    setTimeout(
      ()=>{
        this.signalingService.subscribe(`messages/${this.channel.id}`, (message: any) => {
          console.log('Received live message', message)
          this.messages.push(JSON.parse(message));
        });
      }
    ,2000)
  }

  loadMessages(): void {
    if (this.isLoading) return;

    this.isLoading = true;
    this.messageService
      .getMessages(this.channel.id, this.currentPage, this.pageSize)
      .subscribe((data) => {
        if (data.length == 0) {
          this.isLoading = false;
          return;
        }
        const scrollTopBefore = this.chatContainer.nativeElement.scrollTop;

        // Append new messages to the beginning of the array
        this.messages = [...data.reverse(), ...this.messages];
        this.currentPage++;

        // Adjust scroll position to maintain the user's current view
        setTimeout(() => {
          this.chatContainer.nativeElement.scrollTop = scrollTopBefore + 50; // Slight offset to allow easier scrolling up again
          this.isLoading = false;
        });
      });
  }

  onScroll(event: Event) {
    const target = event.target as HTMLElement;
    if (target.scrollTop === 0 && !this.isLoading) {
      this.loadMessages();
    }
  }

  sendMessage() {
    if (this.messageInput.trim() !== '') {
      const message: Message = {
        content: this.messageInput,
        senderId: this.currentUser,
        senderEmail: this.currentUserEmail,
        channelId: this.channel.id,
      };
      this.signalingService.publish(`chat/${this.channel.id}`, JSON.stringify(message));
      this.messageInput = '';
      setTimeout(() => this.scrollToBottom(), 0);
    }
  }

  scrollToBottom() {
    const chatArea = document.querySelector('.chat-area');
    if (chatArea) {
      chatArea.scrollTop = chatArea.scrollHeight;
    }
  }
}
