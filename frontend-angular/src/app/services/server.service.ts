import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';
import { Membership } from '../types/Membership';

@Injectable({
  providedIn: 'root'
})
export class ServerService {
//   apiUrl: string = 'https://localhost:8081';
  apiUrl: string = 'https://litcord-api.sagar88.com.np';

  constructor(private http: HttpClient, private auth: AuthService) {

  }
  getServerDetailsForUser(userId: string) {
    // Add query parameters
    let params = new HttpParams().append("userId", userId);
    // Perform the GET request
    return this.http.get(`${this.apiUrl}/server/user`, { params });
  }

  getServersByEmail(email: string): Observable<any[]> {
    const url = `${this.apiUrl}/server/from-email/${encodeURIComponent(email)}`;
    return this.http.get<any[]>(url);
  }

  createServer(name: string, userId: number, dp: File): Observable<string> {
    const formData: FormData = new FormData();
    formData.append('name', name);
    formData.append('userId', userId.toString());
    formData.append('dp', dp);

    return this.http.post<string>(`${this.apiUrl}/server`, formData);
  }

  createChannel(newChannel: any): any{
    return this.http.post<any>(`${this.apiUrl}/server/channels`, newChannel )
  }

  deleteServer(id: number){
    return this.http.delete<string>(`${this.apiUrl}/server/${id}`)
  }

  deleteChannel(id: number){
    return this.http.delete<string>(`${this.apiUrl}/channel/${id}`)
  }

  deleteMember(serverId: number, userId: string) {
    const params = {serverId, userId}
    return this.http.delete<string>(`${this.apiUrl}/memberships`, {params});
  }

    /**
   * Fetch all members of a server by serverId.
   * @param serverId - ID of the server
   * @returns Observable of Membership[]
   */
    getMembersByServer(serverId: number): Observable<Membership[]> {
      const url = `${this.apiUrl}/memberships/server/${serverId}`;
      return this.http.get<Membership[]>(url);
    }

      /**
   * Join a server using the invite code.
   * @param inviteCode - Invite code for the server
   * @param userId - User's ID
   * @returns Observable of Membership
   */
  joinServer(inviteCode: string, userId: string): Observable<Membership> {
    const url = `${this.apiUrl}/memberships/join`;
    const params = { inviteCode, userId: userId.toString() };
    return this.http.post<Membership>(url, null, { params });
  }

}
