import { Component, OnInit } from '@angular/core';
import { FormControl, FormControlName, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent implements OnInit {

  constructor() { }
  contact!:FormGroup;
  ngOnInit(): void {
    this.contact=new FormGroup({
      name:new FormControl('',[Validators.required]),
      email:new FormControl('',[Validators.required,Validators.email]),
      message:new FormControl('',[Validators.required])
    });
  }
  onSubmit(){
console.log(this.contact);
  }

}
