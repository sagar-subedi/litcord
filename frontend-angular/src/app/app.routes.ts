import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { TestComponent } from './components/test/test.component';
import { AuthGuard } from './guards/auth.guard';
import { LoginComponent } from './pages/auth/login/login.component';
import { RegisterComponent } from './pages/auth/register/register.component';

export const routes: Routes = [
    { path: '', redirectTo: '/auth/login', pathMatch: 'full' }, // Default redirect to login
    { 
      path: 'servers/:serverid/channels/:channelid', 
      canActivate: [AuthGuard], 
      component: HomeComponent 
    },
    { 
      path: 'servers', 
      redirectTo: '/servers/23/channels/22', 
      pathMatch: 'full' 
    },
    { path: 'auth', redirectTo: '/auth/login', pathMatch: 'full' },
    { path: 'auth/login', component: LoginComponent },
    { path: 'auth/register', component: RegisterComponent },
    { path: 'test', component: TestComponent },
    { path: '**', redirectTo: '/auth/login' } // Redirect invalid routes
];
