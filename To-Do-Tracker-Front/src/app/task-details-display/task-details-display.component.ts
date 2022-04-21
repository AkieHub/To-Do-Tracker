import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Api } from '../services/api.service';

@Component({
  selector: 'app-all-task-display',
  templateUrl: './task-details-display.component.html',
  styleUrls: ['./task-details-display.component.css']
})
export class AllTaskDisplayComponent implements OnInit {
  // displayedColumns: string[] = ['taskHeading', 'categoryName', 'taskEndDate', 'priority', 'action'];
  // dataSource!: MatTableDataSource<any>;
  // @ViewChild(MatPaginator) paginator!: MatPaginator;
  // @ViewChild(MatSort) sort!: MatSort;


  constructor(private api: Api, @Inject(MAT_DIALOG_DATA) private dialodDataDisplay: any) { }
  data: any;
  ngOnInit(): void {
    if (this.dialodDataDisplay) {
      this.data = this.dialodDataDisplay;
    }
  }

  // getAllTask() {
  //   this.api.gelltAllTask(this.api.getUseEmailId()).subscribe(d => {
  //     console.log(d);
  //     this.dataSource = new MatTableDataSource(d);
  //     this.dataSource.paginator = this.paginator;
  //     this.dataSource.sort = this.sort;
  //   }, e => { console.log(e); })
  // }

  // editTask() {
  //   this.dialog.open(TaskComponent, {
  //     width: '500px'
  //   })
  // }
  // applyFilter(event: Event) {
  //   const filterValue = (event.target as HTMLInputElement).value;
  //   this.dataSource.filter = filterValue.trim().toLowerCase();

  //   if (this.dataSource.paginator) {
  //     this.dataSource.paginator.firstPage();
  //   }
  // }


}

