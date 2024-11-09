import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-channel-button',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './channel-button.component.html',
  styleUrl: './channel-button.component.scss'
})
export class ChannelButtonComponent {

  @Input() channel!:any;
  @Input() isSelected: boolean = false;

}
