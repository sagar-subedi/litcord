import { Component, OnInit } from '@angular/core';
import { MatIconModule } from '@angular/material/icon'; 
import {MatFormFieldModule } from '@angular/material/form-field'
import {MatInputModule } from '@angular/material/input'
import { ContentComponent } from './content/content.component';
import { ServerComponent } from './server/server.component';
import { ServerService } from '../../services/server.service';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { CreateServerModalComponent } from './create-server-modal/create-server-modal.component';
@Component({
  selector: 'app-home',
  standalone: true,
  imports: [MatIconModule, MatFormFieldModule, MatInputModule, ServerComponent, ContentComponent, CreateServerModalComponent, CommonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit {
  currentUserId: string = '1';
  servers: any[] = [];
  selectedServer:any;
  selectedChannel:any;


  isModalVisible = false;

  constructor(private serverService: ServerService, private route: ActivatedRoute){

  }

  openModal() {
    this.isModalVisible = true;
  }

  closeModal() {
    this.isModalVisible = false;
  }

  onServerCreated(event: { name: string; image: File }) {
    console.log('Server Created:', event);
    // Handle the newly created server (e.g., send it to a backend service)
  }

  ngOnInit(): void {

    //initialize the data on first load of the component
      this.serverService.getServerDetailsForUser(this.currentUserId).subscribe(
        {
          next: (data:any)=>{
            this.servers = data.servers;


            //gets the current server and channel id from url
            const serveId = this.route.snapshot.paramMap.get('serverid');
            const channelId = this.route.snapshot.paramMap.get('channelid');


            //sets selected server
            this.servers.forEach(
              server=>{
                if(server.id == serveId) this.selectedServer = server;
              }
            )

            //sets the selected channel
            this.selectedServer.channels.forEach((channel: { id: string | null; })=>{
              if(channel.id == channelId) this.selectedChannel = channel;
            })

          }


        }
      )


      //change selectedServer and selectedChannel in case the route changes
      this.route.params.subscribe(
        {
          next: (routeMap:any)=> {

            //CFI - Candidate For Improvement
            //don't do anything if its the first time i.e server list is empty
            if(this.servers.length==0) return;
            
            this.servers.forEach((server:any)=>{
              if(server.id == routeMap['serverid']){
                this.selectedServer == server;
              }
            })

            this.selectedServer.channels.forEach((channel: any)=>{
              if(channel.id == routeMap['channelid']) {
                this.selectedChannel = channel;
              }
            })

          }
        }
      )

  }

}
