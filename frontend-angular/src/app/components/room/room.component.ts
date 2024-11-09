import { Component } from '@angular/core';
import { CallComponent } from '../call/call.component';
@Component({
  selector: 'app-room',
  standalone: true,
  imports: [CallComponent],
  templateUrl: './room.component.html',
  styleUrl: './room.component.scss'
})
export class RoomComponent {

}
