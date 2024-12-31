import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatFormField } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [CommonModule, MatFormField, MatInput, FormsModule],
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.scss',
})
export class ChatComponent implements OnInit {
  messages: { sender: string; text: string }[] = [];
  messageInput: string = '';
  currentUser: string = 'user1'; // Replace with the actual logged-in user identifier
  isLoading: boolean = false;

  ngOnInit() {
    this.loadInitialMessages();
  }

  loadInitialMessages() {
    // Simulate loading initial messages
    this.messages = [
      { sender: 'user1', text: 'Hi there!' },
      { sender: 'user2', text: 'Hello! How can I help you?' },
    ];
  }

  loadOlderMessages() {
    this.isLoading = true;
    setTimeout(() => {
      // Simulate older messages being loaded
      this.messages.unshift(
        { sender: 'user2', text: 'This is an older message.' },
        { sender: 'user1', text: 'Older message from me.' }
      );
      this.isLoading = false;
    }, 1000);
  }

  onScroll(event: Event) {
    const target = event.target as HTMLElement;
    if (target.scrollTop === 0 && !this.isLoading) {
      this.loadOlderMessages();
    }
  }

  sendMessage() {
    if (this.messageInput.trim() !== '') {
      this.messages.push({ sender: this.currentUser, text: this.messageInput });
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
