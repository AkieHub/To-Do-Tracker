import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegisterComponent } from './register/register.component';
import {MatCardModule} from '@angular/material/card';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatSelectModule} from '@angular/material/select';
import { ReactiveFormsModule,FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { ToastrModule } from 'ngx-toastr';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoginComponent } from './login/login.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { HomepageComponent } from './homepage/homepage.component';
import { ModfifyTaskComponent } from './modfify-task/modfify-task.component';
import{MatButtonModule}from'@angular/material/button'
import{MatSidenavModule}from'@angular/material/sidenav'
import{MatToolbarModule}from'@angular/material/toolbar'
import{MatListModule}from'@angular/material/list'
import{MatIconModule}from'@angular/material/icon'
import{MatMenuModule}from'@angular/material/menu';
import { AddTaskComponent } from './add-task/add-task.component';
import {MatDialogModule} from '@angular/material/dialog';
import {MatInputModule} from '@angular/material/input';
import { DashboardComponent } from './dashboard/dashboard.component';
import { LayoutModule } from '@angular/cdk/layout';
import {MatBadgeModule} from '@angular/material/badge';
import {MatDividerModule} from '@angular/material/divider';
import { AboutUsComponent } from './about-us/about-us.component';
import { ContactComponent } from './contact/contact.component';
import {MatExpansionModule} from '@angular/material/expansion';
import{MatDatepickerModule}from'@angular/material/datepicker';
import {MatTableModule} from '@angular/material/table';
import {MatPaginatorModule} from '@angular/material/paginator';
import{MatSortModule}from'@angular/material/sort';
import { TaskComponent } from './task/task.component'
import{MatTabsModule}from'@angular/material/tabs';
import { FeaturesComponent } from './features/features.component';
import { FilterPipe } from './pipe/filter.pipe';
import {MatTooltipModule} from '@angular/material/tooltip';
import { AllTaskDisplayComponent } from './task-details-display/task-details-display.component';
import { FooterComponent } from './footer/footer.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { OrderByPipe } from './pipe/order-by.pipe';




@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    LoginComponent,
    ForgotPasswordComponent,
    HomepageComponent,
    ModfifyTaskComponent,
    AddTaskComponent,
    DashboardComponent,
    AboutUsComponent,
    ContactComponent,
    TaskComponent,
    FeaturesComponent,FilterPipe,
    AllTaskDisplayComponent,
    FooterComponent,
    UserProfileComponent,
    PageNotFoundComponent,
    OrderByPipe
  ],
  imports: [
    BrowserModule,
    ToastrModule.forRoot(),
    BrowserAnimationsModule,
    AppRoutingModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    FormsModule,
    MatCardModule,
    MatSelectModule,
    HttpClientModule,MatButtonModule,MatSidenavModule,MatToolbarModule,
    MatListModule,
  MatIconModule,
  MatMenuModule,
  MatDialogModule,
  MatInputModule,
  MatDatepickerModule,
  LayoutModule,
  MatBadgeModule,
  MatDividerModule,
  MatExpansionModule,
  MatTableModule,
  MatPaginatorModule,
  MatSortModule,
  MatTabsModule,
  MatTooltipModule

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { 
  
}
