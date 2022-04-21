import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AllTaskDisplayComponent } from '../task-details-display/task-details-display.component';
import { ModfifyTaskComponent } from '../modfify-task/modfify-task.component';
import { Api } from '../services/api.service';


@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.css']
})
export class TaskComponent implements OnInit {

  constructor(private api: Api, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.getAllTask();
  }
  @Input() datareceive: any;
  @Input() typea: any;

  getAllTask() {
    this.api.gelltAllTask(this.api.getUseEmailId()).subscribe(d => { this.datareceive = d; });
  }
  markCompletedTask(taskId: any) {
    this.api.markDeleteOrCompleted(this.api.getUseEmailId(), taskId, "completed").subscribe(d => {
      console.log(d);
      this.getAllTask();
    });
  }
  deleteTask(taskId: any) {
    this.api.markDeleteOrCompleted(this.api.getUseEmailId(), taskId, "del").subscribe(d => {
      this.getAllTask();
  });
  }
  updateTask(task: any) {
    this.dialog.open(ModfifyTaskComponent, {
      width: '500px',
      data: task
    }).afterClosed().subscribe(val => {
      if (val === 'update') {
        this.getAllTask();
      }
    });
  }

  searchText: string = '';
  openDilog(item: any) {
    this.dialog.open(AllTaskDisplayComponent, {
      data: item,
      width: '500px'
    });
    console.log(item);
  }
  key:string='id';
  reverse:boolean=false;
  sort(key:any){
    this.key=key;
    this.reverse=!this.reverse;
    console.log(key);
  }

}
