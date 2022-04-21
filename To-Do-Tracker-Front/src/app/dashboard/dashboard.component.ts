import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { BreakpointObserver } from '@angular/cdk/layout';
import { MatSidenav } from '@angular/material/sidenav';
import { MatDialog } from '@angular/material/dialog';
import { AddTaskComponent } from '../add-task/add-task.component';
import { Api } from '../services/api.service';
import { Router } from '@angular/router';
import { UserProfileComponent } from '../user-profile/user-profile.component';

@Component({
  selector: 'app-exp',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit, AfterViewInit {
  @ViewChild(MatSidenav)
  sidenav!: MatSidenav;
  userData!: any;
  userName!: string;
  dataShare: any;
  type: any;

  constructor(private observer: BreakpointObserver, private dialog: MatDialog, private api: Api, private router: Router) {
  }
  ngOnInit(): void {
    this.getAllTask();
    this.getUserDetails();
  }
  ngAfterViewInit() {
    this.observer.observe(['(max-width: 800px)']).subscribe((res) => {
      if (res.matches) {
        this.sidenav.mode = 'over';
        this.sidenav.close();
      } else {
        this.sidenav.mode = 'side';
        this.sidenav.open();
      }
    }
      , e => { console.log(e) });
  }

  url: any;
  onSelectFile(event: any) {
    if (event.target.files && event.target.files[0]) {
      var reader = new FileReader();
      reader.readAsDataURL(event.target.files[0]);
      reader.onload = (event) => {
        this.url = event!.target!.result;
      }
    }
  }
  openDialog() {
    this.dialog.open(AddTaskComponent, {
      width: '500px'
    }).afterClosed().subscribe(val => {
      if (val === 'save') {
        this.getAllTask();
      }
    });
  }

  getAllTask() {
    this.api.gelltAllTask(this.api.getUseEmailId()).subscribe(d => {
      this.dataShare = d;
      this.type = false;
    },
      e => { });
  }
  getUserDetails() {
    this.api.getUserDetails(this.api.getUseEmailId()).subscribe(
      d => {
        console.log(d);
        this.userData = d;
        this.userName = this.userData.firstName;
      },
      e => {
        console.log(e);
        console.log("error");
      }
    );
  }


  getPendingTask() {
    this.api.gelltAllTask(this.api.getUseEmailId()).subscribe(d => {
      this.dataShare = d;
      this.type = false;
    });
  }

  getOverDueTask() {
    this.api.getTasksOverDue(this.api.getUseEmailId()).subscribe(d => {
      this.dataShare = d;
      this.type = false;
    });
  }
  getNearDueTask() {
    this.api.getTasksWithNearDueDate(this.api.getUseEmailId()).subscribe(d => {
      console.log(d);
      this.dataShare = d;
      this.type = false;
    });
  }
  getCompletedTask() {
    console.log('getcomple');
    this.api.getAllCompletedTask(this.api.getUseEmailId()).subscribe(d => {
      console.log(d);
      this.dataShare = d;
      this.type = true;
    },
      e => { console.log(e); });
  }
  getAllDeletedTas(){
this.api.getAllDeletedTask(this.api.getUseEmailId()).subscribe(
  d=>{this.dataShare=d;
    console.log(d);
    this.type = true;},
  e=>{alert('error');}
)
  }

  logOut() {
    this.api.setLoginStatus(false);
    this.router.navigateByUrl('/');
  }

  openProfileDilog() {
    this.dialog.open(UserProfileComponent, {
      width: '500px',
      data: this.userData
    }).afterClosed().subscribe(val => {
      if (val === 'updateprofile') {
        this.ngOnInit();
      }
    });
  }
}
