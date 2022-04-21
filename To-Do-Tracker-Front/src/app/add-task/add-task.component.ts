import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Task } from '../model/task';
import { NotificationServiceService } from '../services/notification-service.service';
import { Api } from '../services/api.service';
import { MatDialogRef} from '@angular/material/dialog'
import { DashboardComponent } from '../dashboard/dashboard.component';
import { stratDate } from '../validator/validator';

@Component({
  selector: 'add-app-task',
  templateUrl: './add-task.component.html',
  styleUrls: ['./add-task.component.css']
})
export class AddTaskComponent implements OnInit {
  constructor(private activatedRoute: ActivatedRoute,
     private api: Api, private notification: NotificationServiceService
    , private matDialogRef: MatDialogRef<DashboardComponent>) { }

  task: Task = new Task();
  addTask!: FormGroup;
  ngOnInit(): void {
    this.addTask = new FormGroup({
      taskHeading: new FormControl('', Validators.required),
      categoryName: new FormControl('', Validators.required),
      taskDescription: new FormControl('', [Validators.required]),
      taskEndDate: new FormControl(null, [Validators.required,this.dateRangeValidator]),
      taskStartDate: new FormControl(null, [Validators.required,stratDate]),
      priority: new FormControl('NORMAL'),
      status: new FormControl(false)
    });
  }
  private dateRangeValidator: ValidatorFn = (): {
    [key: string]: any;
  } | null => {
    let invalid = false;
    const from = this.addTask && this.addTask.get("taskStartDate")?.value;
    const to = this.addTask && this.addTask.get("taskEndDate")?.value;
    if (from && to) {
      invalid = new Date(from).valueOf() >= new Date(to).valueOf();
    }
    return invalid ? { invalidRange: { from, to } } : null;
  };


  public get taskHeading() {
    return this.addTask.get('taskHeading');
  }
  public get categoryName() {
    return this.addTask.get('categoryName');
  }
  public get taskDescription() {
    return this.addTask.get('taskDescription');
  }
  public get taskEndDate() {
    return this.addTask.get('taskEndDate');
  }
  public get taskStartDate() {
    return this.addTask.get('taskStartDate');
  }
  public get priority() {
    return this.addTask.get('priority');
  }
  public get status() {
    return this.addTask.get('status');
  }
  getAllTask() {
    this.api.gelltAllTask(this.api.getUseEmailId()).subscribe(d => { });
  }
  onSubmit() {
    this.task = this.addTask.value;
    if (this.addTask.valid) {
      this.api.addTask(this.task,this.api.getUseEmailId()).subscribe(
        d => {
          this.notification.showSuccess(this.task.taskHeading, "Task Added successfully with email!!");
          this.addTask.reset();
          this.matDialogRef.close('save');
         
        },
        e => {
          console.log(e.error.message);
          this.notification.showError(e.error.message, "Something is wrong");
        });
    }
  }
}

