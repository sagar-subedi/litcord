import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { MatFormField } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { CallComponent } from '../../../components/call/call.component';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-content',
  standalone: true,
  imports: [MatFormField, MatInput, CommonModule, CallComponent, MatIconModule],
  templateUrl: './content.component.html',
  styleUrl: './content.component.scss'
})
export class ContentComponent {
  @Input() channel!:any;

}
