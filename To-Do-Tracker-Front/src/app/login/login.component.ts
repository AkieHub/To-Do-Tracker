import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from '../model/user';
import { NotificationServiceService } from '../services/notification-service.service';
import { Api } from '../services/api.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  user=new User();
  constructor(private router:Router,private api:Api,private notification:NotificationServiceService) { }
  loginForm!:FormGroup;
  ngOnInit(): void {
    this.loginForm=new FormGroup(
      {
      emailId:new FormControl('',[Validators.required,Validators.email]),
      userPassword:new FormControl('',[Validators.required])
  
    });
  }
  
  get emailId(){
    return this.loginForm.get('emailId');
  }

  get userPassword(){
    return this.loginForm.get('userPassword');
  }
  login(){
    this.user=this.loginForm.value;
    this.api.loginUser(this.loginForm.value).subscribe(
      d=>{console.log(d);
        this.api.setUseEmailId(this.user.emailId);
        this.api.setToken(d);
        this.notification.showSuccess(this.user.emailId," successfully loggedin with email!!");
        this.api.setLoginStatus(true);
        this.router.navigateByUrl('/dasboard');
      },
      e=>{console.log(e.error.message);
        this.notification.showError(e.error.message,"Something is wrong");
        this.api.setLoginStatus(false);
      });
  }
}
