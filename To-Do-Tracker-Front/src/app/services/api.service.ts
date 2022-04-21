import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class Api {
  private loggedIn!: boolean;
  userEmailId!: string;
  setLoginStatus(data: boolean) {
    this.loggedIn = data;
  }
  getLoginStatus(): boolean {
    return this.loggedIn;
  }

  setUseEmailId(data: string) {
    this.userEmailId = data;
  }
  getUseEmailId(): string {
    return this.userEmailId;
  }
  token: any;
  setToken(tok: any) {
    this.token = tok;
  }
  getTokenWithHeader() {
    let completedToken='Bearer '+this.token.token;
    console.log(completedToken);
    const header=new HttpHeaders().set("Authorization",completedToken);
    return header;
  }
  constructor(private httpclient: HttpClient) { }
  registerUser(user: any) {
    return this.httpclient.post("http://localhost:8084/niit/user/register", user);
  }
  loginUser(user: any) {
    return this.httpclient.post<any>("http://localhost:8083/niit/user/auth/login", user);
  }
  updatePassword(user: any) {
    return this.httpclient.put("http://localhost:8083/niit/user/auth/update/" + user.emailId, user);
  }
  addTask(task: any, emailId: string) {
    return this.httpclient.put("http://localhost:8084/niit/user/task/add/" + emailId, task,{headers:this.getTokenWithHeader()});
  }
  gelltAllTask(userEmailId: any): Observable<any> {
    return this.httpclient.get<any>('http://localhost:8084/niit/user/task/all-task/' + userEmailId,{headers:this.getTokenWithHeader()});
  }
  getTasksOverDue(emailId: string) {
    return this.httpclient.get<any>('http://localhost:8084/niit/user/task/overdue/' + emailId,{headers:this.getTokenWithHeader()});
  }
  getTasksWithNearDueDate(emailId: string) {
    return this.httpclient.get<any>('http://localhost:8084/niit/user/task/near-due/' + emailId,{headers:this.getTokenWithHeader()});
  }

  markDeleteOrCompleted(emailId: string, taskId: number, option: string) {
    return this.httpclient.delete<any>("http://localhost:8084/niit/user/task/remove/" + emailId + "/" + taskId + "/" + option,{headers:this.getTokenWithHeader()});
  }
  modifyTask(task: any, emaiId: string) {
    return this.httpclient.put<any>("http://localhost:8084/niit/user/task/modify/" + emaiId, task,{headers:this.getTokenWithHeader()});
  }

  getAllCompletedTask(emailId: string) {
    return this.httpclient.get<any>("http://localhost:8085/niit/archive/task/get-completedTask/" + emailId);
  }

  getAllDeletedTask(emaiId: string): Observable<any> {
    return this.httpclient.get<any>("http://localhost:8085/niit/archive/task/get-deleted/" + emaiId);
  }

  getUserDetails(emaiId: string) {
    return this.httpclient.get<any>("http://localhost:8084/niit/user/user-profile/" + emaiId,{headers:this.getTokenWithHeader()});
  }
  updateUserDetails(emaiId: string, user: any) {
    return this.httpclient.put<any>("http://localhost:8084/niit/user//update-profile/" + emaiId, user,{headers:this.getTokenWithHeader()});
  }
}
