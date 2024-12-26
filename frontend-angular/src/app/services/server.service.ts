import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ServerService {
  baseUrl: string = 'https://localhost:8081';
  // baseUrl: string = 'https://litcord-api.sagar88.com.np/server/';

  constructor(private http: HttpClient, private auth: AuthService) {

  }
  getServerDetailsForUser(userId: string) {
    // Add query parameters
    let params = new HttpParams().append("userId", userId);
    // Perform the GET request
    return this.http.get(`${this.baseUrl}/server/user`, { params });
  }

  createServer(name: string, userId: number, dp: File): Observable<string> {
    const formData: FormData = new FormData();
    formData.append('name', name);
    formData.append('userId', userId.toString());
    formData.append('dp', dp);

    return this.http.post<string>(`${this.baseUrl}/server`, formData);
  }

  createChannel(newChannel: any): any{
    return this.http.post<any>(`${this.baseUrl}/server/channels`, newChannel )
  }

  deleteServer(id: number){
    return this.http.delete<string>(`${this.baseUrl}/server/${id}`)
  }

  deleteChannel(id: number){
    return this.http.delete<string>(`${this.baseUrl}/channel/${id}`)
  }


}
