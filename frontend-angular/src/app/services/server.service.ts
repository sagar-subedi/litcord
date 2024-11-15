import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ServerService {
  // baseUrl: string = 'https://192.168.100.190:8081/test/';
  baseUrl: string = 'https://litcord-api.sagar88.com.np/test/';

  constructor(private http: HttpClient) { 

  }
  getServerDetailsForUser(userId: string) {
    return this.http.get(this.baseUrl+'users/'+userId);
  }


}
