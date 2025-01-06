import { Component, OnInit } from '@angular/core';
import { ServerComponent } from './server/server.component';
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
