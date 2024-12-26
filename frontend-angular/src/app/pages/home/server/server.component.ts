import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatIcon } from '@angular/material/icon';
import { ChannelComponent } from '../channel/channel.component';
import { CommonModule } from '@angular/common';
import { AccountModalComponent } from '../account-modal/account-modal.component';
import { ActivatedRoute, Router } from '@angular/router';
import { ServerService } from '../../../services/server.service';
import { CreateServerModalComponent } from '../create-server-modal/create-server-modal.component';

@Component({
  selector: 'app-server',
  standalone: true,
  imports: [CommonModule, MatIcon, ChannelComponent, CreateServerModalComponent, AccountModalComponent],
  templateUrl: './server.component.html',
  styleUrl: './server.component.scss'
})
export class ServerComponent implements OnInit {
  servers: any[] = [];
  @Output() createServer = new EventEmitter<void>();
  selectedServer: any;
  isProfileModalOpen = false;
  isCreateServerModalOpen = false;
  currentUserId = '1'

  constructor(private router: Router, private serverService: ServerService, private route: ActivatedRoute){

  }
  ngOnInit(): void {
    //initialize the data on first load of the component
      this.serverService.getServerDetailsForUser(this.currentUserId).subscribe(
        {
          next: (data:any)=>{
            this.servers = data;


            //gets the current server and channel id from url
            const serveId = this.route.snapshot.paramMap.get('serverid');
            const channelId = this.route.snapshot.paramMap.get('channelid');


            //sets selected server
            this.servers.forEach(
              server=>{
                if(server.id == serveId) this.selectedServer = server;
              }
            )
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
          }
        }
      )
  }


  openModal() {
    this.isCreateServerModalOpen = true;
  }

  closeModal() {
    this.isCreateServerModalOpen = false;
  }

  onSelectServer(server:any){
    this.router.navigate([`/servers/${server.id}/channels/${server.channels[0].id}`])
    this.selectedServer = server;
  }

  onClickAddServer(){
    this.createServer.emit();
  }

  onDeleteServer(id:number){
    if(this.servers.length == 1){
      alert("This is your only server. You can't delete it.")
      return;
    }
    this.serverService.deleteServer(id).subscribe(
      {
        next: (data)=>{
          this.servers = this.servers.filter((server)=> server.id !==id)
          this.selectedServer = this.servers[0]
        }
      }
    ) 
  }

  onServerCreated($event:any){
    this.servers.push($event)
  }

  openProfileModal() {
    this.isProfileModalOpen = true;
  }

  closeProfileModal() {
    this.isProfileModalOpen = false;
  }
}
