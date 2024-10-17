import { Routes } from '@angular/router';
import { RoomComponent } from './room/room.component';

export const routes: Routes = [
    {path:'room/:id', component: RoomComponent}
];
