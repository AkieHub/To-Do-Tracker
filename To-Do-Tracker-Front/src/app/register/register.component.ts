import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { User } from '../model/user';
import { NotificationServiceService } from '../services/notification-service.service';
import { Api } from '../services/api.service';
import { dobValidator, passwordMatchValidator } from '../validator/validator';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})

export class RegisterComponent implements OnInit {

  constructor(private api:Api,private notification:NotificationServiceService,private router:Router) { }
  user=new User();
  register =new FormGroup({
    firstName:new FormControl(this.user.firstName,[Validators.required]),
    lastName:new FormControl(this.user.lastName),
    gender:new FormControl(this.user.gender,[Validators.required]),
    dateOfBirth:new FormControl(this.user.dateOfBirth,[Validators.required,dobValidator]),
    emailId:new FormControl(this.user.emailId,[Validators.required,Validators.email]),
    userPassword:new FormControl(this.user.userPassword,[Validators.required,Validators.minLength(6)]),
    confirmPassword:new FormControl('',[Validators.required])
  },{
    validators: [passwordMatchValidator('userPassword', 'confirmPassword')]
  }
  );
  ngOnInit() {
  }
  public get firstName() {
    return this.register.get('firstName');
  }
  public get gender() {
    return this.register.get('gender');
  }
  public get userDob() {
    return this.register.get('dateOfBirth');
  }
  public get emailId() {
    return this.register.get('emailId');
  }
  public get password() {
    return this.register.get('userPassword');
  }
  public get confirmPassword() {
    return this.register.get('confirmPassword');
  }
  onSubmit(){
    this.user=this.register.value;
    console.log(this.user);
    this.api.registerUser(this.user).subscribe(
      d=>{console.log(d);
        this.notification.showSuccess(this.user.emailId,"Register successfully with email!!");
        this.router.navigateByUrl('');
      },
      e=>{console.log(e.error.message);
        this.notification.showError(e.error.message,"Something is wrong");
      }
    );
  }

}


