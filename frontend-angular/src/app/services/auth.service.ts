import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthUtils } from '../utils/auth-utils';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private tokenKey = 'authToken';

  private readonly apiUrl = 'https://localhost:8081'; // Replace with your backend URL

  constructor(private http: HttpClient) {}

  login(email: string, password: string): Observable<any> {
    const payload = { email, password };
    return this.http.post(`${this.apiUrl}/auth/login`, payload);
  }

  register(payload: { name: string; email: string; password: string }): Observable<any> {
   return this.http.post(`${this.apiUrl}/auth/register`, payload)
  }

  isLoggedIn(): boolean {
    const token = this.getToken();
    return !!token && !this.isTokenExpired(token);
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  setToken(token: string): void {
    localStorage.setItem(this.tokenKey, token);
  }

  logout(): void {
    localStorage.removeItem(this.tokenKey);
  }

  getCurrentUserEmail(){
    let token = this.getToken()
    if(token) {
      return AuthUtils.extractEmailFromToken(token);
    }
    return null;
  }

  getCurrentUserId(){
    let token = this.getToken()
    if(token) {
      return AuthUtils.extractUserIdFromToken(token);
    }
    return null;
  }



  private isTokenExpired(token: string): boolean {
    const payload = JSON.parse(atob(token.split('.')[1]));
    const expiry = payload.exp * 1000; // Convert to milliseconds
    return Date.now() > expiry;
  }
}
