import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Task } from '../model/task';
import { NotificationServiceService } from '../services/notification-service.service';
import { Api } from '../services/api.service';
import { MatDialogRef ,MAT_DIALOG_DATA} from '@angular/material/dialog';
import { TaskComponent } from '../task/task.component';

@Component({
  selector: 'app-modfify-task',
  templateUrl: './modfify-task.component.html',
  styleUrls: ['./modfify-task.component.css']
})
export class ModfifyTaskComponent implements OnInit {
  modifyTask!: FormGroup ;


  constructor(private matDialogRef: MatDialogRef<TaskComponent>
    ,private api :Api,private notification:NotificationServiceService
    ,@Inject(MAT_DIALOG_DATA) private editData:any) { }

  task:Task=new Task();

  ngOnInit(): void { 
    this.modifyTask =new FormGroup({
      taskId:new FormControl(''),
      taskHeading:new FormControl('',Validators.required),
      categoryName:new FormControl('',Validators.required),
      taskDescription:new FormControl('',[Validators.required]),
      taskEndDate:new FormControl(null,[Validators.required]),
      taskStartDate:new FormControl(null,[Validators.required]),
      priority:new FormControl('NORMAL'),
      status:new FormControl(false)
    }
    );
    if(this.editData){
      this.modifyTask.controls["taskId"].setValue(this.editData.taskId);
      this.modifyTask.controls["taskHeading"].setValue(this.editData.taskHeading);
      this.modifyTask.controls["categoryName"].setValue(this.editData.categoryName);
      this.modifyTask.controls["taskDescription"].setValue(this.editData.taskDescription);
      var dateStart=this.editData.taskStartDate;
      var dateEnd=this.editData.taskEndDate;
      dateStart = dateStart.split('T00:00:00.000+00:00')[0];
      dateEnd = dateEnd.split('T00:00:00.000+00:00')[0];
      this.modifyTask.controls["taskEndDate"].setValue(dateEnd);
      this.modifyTask.controls["taskStartDate"].setValue(dateStart);
      this.modifyTask.controls["priority"].setValue(this.editData.priority);
      this.modifyTask.controls["status"].setValue(this.editData.status);
    }
    console.log(this.editData) ;
   }
   public get taskId() {
    return this.modifyTask.get('taskId');
  }
  public get taskHeading() {
    return this.modifyTask.get('taskHeading');
  }
  public get categoryName() {
    return this.modifyTask.get('categoryName');
  }
  public get taskDescription() {
    return this.modifyTask.get('taskDescription');
  }
  public get taskEndDate() {
    return this.modifyTask.get('taskEndDate');
  }
  public get taskStartDate() {
    return this.modifyTask.get('taskStartDate');
  }
  public get priority(){
  return this.modifyTask.get('priority');
  }
  public get status(){
    return this.modifyTask.get('status');
    }
  onSubmit(){
    this.task=this.modifyTask.value;
    this.api.modifyTask(this.task,this.api.getUseEmailId()).subscribe(
      d=>{console.log(d);
        this.notification.showSuccess(this.task.taskHeading,"Task updated successfully !!");
        this.modifyTask.reset();
        this.matDialogRef.close('update');
      },
      e=>{console.log(e.error.message);
        this.notification.showError(e.error.message,"Something is wrong");
      }
    );
  }

}
