import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ServerService } from '../services/server.service';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root',
})
export class HomeGuard implements CanActivate {
  constructor(private serverService: ServerService, private router: Router, private authService: AuthService) {}

  canActivate(): Observable<boolean> {
    // Make the API call


    let userId = this.authService.getCurrentUserId();
    let isLoggedIn = this.authService.isLoggedIn();

    if (!userId || !isLoggedIn) {
      // Handle the case where email is null or undefined
      console.error('User email is not available');
      this.router.navigate(['/auth/login'])
      return of(false); // Return an Observable to prevent further execution
    }

    return this.serverService.getServerDetailsForUser(userId).pipe(
      map((response: any) => {
        // Assuming the response contains the server and channel IDs
        console.log(response);
        if(response.length==0){
          this.router.navigate([`/servers/new`])
        }
        const serverId = response[0].id;
        const channelId = response[0].channels[0].id;

        // Navigate to the determined route
        this.router.navigate([`/servers/${serverId}/channels/${channelId}`]);

        // Return false to prevent the original /server route from being activated
        return false;
      })
    );
  }
}
