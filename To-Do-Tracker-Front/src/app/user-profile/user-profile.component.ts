import { Component, Inject, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from '../model/user';
import { Api } from '../services/api.service';
import { NotificationServiceService } from '../services/notification-service.service';
import { dobValidator} from '../validator/validator';
import { MAT_DIALOG_DATA,MatDialogRef } from '@angular/material/dialog';
import { DashboardComponent } from '../dashboard/dashboard.component';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {
  constructor(private api:Api,private notification:NotificationServiceService,private router:Router
    ,@Inject(MAT_DIALOG_DATA) private editData:any,private matDialogRef: MatDialogRef<DashboardComponent>){}

    user=new User();
    userProfile!:FormGroup;
    ngOnInit() {
    this.userProfile =new FormGroup({
      firstName:new FormControl(this.user.firstName,[Validators.required]),
      lastName:new FormControl(this.user.lastName),
      gender:new FormControl(this.user.gender,[Validators.required]),
      dateOfBirth:new FormControl(this.user.dateOfBirth,[Validators.required,dobValidator]),
      emailId:new FormControl(this.user.emailId),
    });
    if(this.editData){
      this.userProfile.controls["firstName"].setValue(this.editData.firstName);
      this.userProfile.controls["lastName"].setValue(this.editData.lastName);
      this.userProfile.controls["gender"].setValue(this.editData.gender);
      var date=this.editData.dateOfBirth;
      date = date.split('T00:00:00.000+00:00')[0];
      this.userProfile.controls["dateOfBirth"].setValue(date);
      this.userProfile.controls["emailId"].setValue(this.editData.emailId);
    }
  }

  public get firstName() {
    return this.userProfile.get('firstName');
  }
  public get gender() {
    return this.userProfile.get('gender');
  }
  public get userDob() {
    return this.userProfile.get('dateOfBirth');
  }
  public get emailId() {
    return this.userProfile.get('emailId');
  }
  onSubmit() {
    if (this.userProfile.valid) {
      this.api.updateUserDetails(this.api.getUseEmailId(),this.userProfile.value).subscribe(
        d => {
          this.notification.showSuccess(this.api.getUseEmailId(), "Profile Update successfully for !!");
          this.userProfile.reset();
          this.matDialogRef.close('updateprofile');
        },
        e => {
          console.log(e.error.message);
          this.notification.showError(e.error.message, "Something is wrong");
        });
    }
  }

}

