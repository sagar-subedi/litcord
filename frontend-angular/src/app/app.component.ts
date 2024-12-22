import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CallComponent } from './components/call/call.component';
import { provideHttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, CallComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit {
  title = 'litcord';
  apiUrl = 'https://192.168.100.190:8081/';
  private httpClient = provideHttpClient();

  constructor(){

  }
  ngOnInit(): void {
    
  }
}
