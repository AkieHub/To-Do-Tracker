import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ContactComponent } from './contact/contact.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { HomepageComponent } from './homepage/homepage.component';
import { LoginComponent } from './login/login.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { RegisterComponent } from './register/register.component';
import { LoginGuardGuard } from './services/login-guard.guard';



const routes: Routes = [{
  path: '',
  component: HomepageComponent, children: [
    {
      path: '',
      component: LoginComponent
    },
    {
      path: 'forgotpassword',
      component: ForgotPasswordComponent
    },
    {
      path: 'register',
      component: RegisterComponent
    }]
},
{
  path:'dasboard',
  component:DashboardComponent,canActivate:[LoginGuardGuard]
},
{
  path:'**',
  component:PageNotFoundComponent
}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
