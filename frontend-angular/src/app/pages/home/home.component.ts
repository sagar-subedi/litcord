import { Component, OnInit } from '@angular/core';
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
  imports: [ServerComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent{
// todo: maybe this component is not necessary, maybe rename 'channel component' as server, 'content component' as 'channel component' and 'server component' as 'home component'
}
