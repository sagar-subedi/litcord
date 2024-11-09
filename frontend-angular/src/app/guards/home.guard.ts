import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ServerService } from '../services/server.service';

@Injectable({
  providedIn: 'root',
})
export class HomeGuard implements CanActivate {
  constructor(private serverService: ServerService, private router: Router) {}

  canActivate(): Observable<boolean> {
    // Make the API call

    //make the current user dynamic, populated through token or other means
    let currentUserId = '1';
    return this.serverService.getServerDetailsForUser(currentUserId).pipe(
      map((response: any) => {
        // Assuming the response contains the server and channel IDs
        console.log(response);
        const serverId = response.servers[0].id;
        const channelId = response.servers[0].channels[0].id;

        // Navigate to the determined route
        this.router.navigate([`/servers/${serverId}/channels/${channelId}`]);

        // Return false to prevent the original /server route from being activated
        return false;
      })
    );
  }
}
