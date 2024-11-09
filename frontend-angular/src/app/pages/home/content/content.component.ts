import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { MatFormField } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { CallComponent } from '../../../components/call/call.component';

@Component({
  selector: 'app-content',
  standalone: true,
  imports: [MatFormField, MatInput, CommonModule, CallComponent],
  templateUrl: './content.component.html',
  styleUrl: './content.component.scss'
})
export class ContentComponent {
  @Input() channel!:any;

}
