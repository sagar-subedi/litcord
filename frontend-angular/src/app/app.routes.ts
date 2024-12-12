import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { TestComponent } from './components/test/test.component';
import { AuthGuard } from './guards/auth.guard';
import { LoginComponent } from './pages/auth/login/login.component';
import { RegisterComponent } from './pages/auth/register/register.component';
import { HomeGuard } from './guards/home.guard';
import { NotFound404Component } from './pages/not-found-404/not-found-404.component';

export const routes: Routes = [
  {
    path: '',
    canActivate: [HomeGuard],
    pathMatch: 'full',
    component: HomeComponent,
  }, // Default redirect to login
  {
    path: 'servers',
    component: HomeComponent,
    canActivate: [HomeGuard],
    pathMatch: 'full',
  },
  {
    path: 'servers/:serverid/channels/:channelid',
    canActivate: [AuthGuard],
    component: HomeComponent,
  },
  { path: 'auth', redirectTo: '/auth/login', pathMatch: 'full' },
  { path: 'auth/login', component: LoginComponent },
  { path: 'auth/register', component: RegisterComponent },
  { path: 'test', component: TestComponent },
  { path: '**', component: NotFound404Component}, // Redirect invalid routes
];
