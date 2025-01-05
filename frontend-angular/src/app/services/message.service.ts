import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Message } from '../types/Message';

@Injectable({
  providedIn: 'root',
})
export class MessageService {
  // private apiUrl = 'https://localhost:8081/messages';
  private apiUrl = 'https://litcord-api.sagar88.com.np/messages';

  constructor(private http: HttpClient) {}

  addMessage(message: Message): Observable<Message> {
    return this.http.post<Message>(this.apiUrl, message);
  }

  getMessages(channelId: number, page: number, size: number): Observable<Message[]> {
    return this.http.get<Message[]>(`${this.apiUrl}/channel/${channelId}`, {
      params: { page: page.toString(), size: size.toString() }
    });
  }
}

