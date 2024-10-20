import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CallingComponent } from '../calling-component/calling-component.component';
import { HttpClient, HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, CallingComponent, HttpClientModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit {
  title = 'litcord';
  apiUrl = 'https://192.168.100.190:8081/';

  constructor(private httpClient: HttpClient){

  }
  ngOnInit(): void {
    this.httpClient.get(this.apiUrl + 'test').subscribe((response)=>{
      console.log("The response:" + response);
    },
  (err)=>{console.log(err)});
  }
}
