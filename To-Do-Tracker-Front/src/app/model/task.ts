export class Task {
        taskId:number;
    taskHeading :string;
    categoryName:string;
    taskDescription:string;
    taskEndDate:Date;
    taskStartDate:Date;
    status:boolean;
    priority:string;
    constructor(){
      this.taskId=0;
      this.taskHeading='';
      this.categoryName='';
      this.taskDescription='';
      this.taskEndDate=new Date();
      this.taskStartDate=new Date();
      this.status=false;
      this.priority='';
    }
    }

