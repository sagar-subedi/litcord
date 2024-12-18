import { Routes } from '@angular/router';
import { AuthComponent } from './pages/auth/auth.component';
import { HomeComponent } from './pages/home/home.component';
import { HomeGuard } from './guards/home.guard';
import { TestComponent } from './components/test/test.component';

export const routes: Routes = [
    { path: '', canActivate: [HomeGuard], pathMatch: 'full', component: HomeComponent},   //canActivate will always return false, so always redirection
    {path:'servers/:serverid/channels/:channelid', component: HomeComponent},
    { path: 'servers', redirectTo: '/servers/23/channels/22', pathMatch: 'full' },
    {path: 'auth', component: AuthComponent},
    {path: 'test', component: TestComponent}
];
