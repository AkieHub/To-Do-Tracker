import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { User } from '../model/user';
import { NotificationServiceService } from '../services/notification-service.service';
import { Api } from '../services/api.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {
  user=new User();
  constructor(private api:Api,private notification:NotificationServiceService,private router:Router) { }

  ngOnInit(): void {
  }
  loginForm=new FormGroup(
    {
    emailId:new FormControl('',[Validators.required,Validators.email]),
    userPassword:new FormControl('',[Validators.required,Validators.minLength(6)])

  }); 
  get emailId(){
    return this.loginForm.get('emailId');
  }

  get userPassword(){
    return this.loginForm.get('userPassword');
  }
  changepassword(){
    console.log(this.loginForm.status);
    console.log(this.loginForm.value);
    this.api.updatePassword(this.loginForm.value).subscribe(
      d=>{console.log(d);
        this.notification.showSuccess(this.user.emailId,"password changed successfully !!");
        this.router.navigate(['/'])
      },
      e=>{console.log(e.error.message);
        this.notification.showError(e.error.message,"Something is wrong");
      }
    );
  }
}
