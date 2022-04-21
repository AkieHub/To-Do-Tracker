import { Component, OnInit } from '@angular/core';
import { ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {

  constructor(private router:ActivatedRoute) { }

  ngOnInit(): void {
  }
  
disableLink(){
  let c=(<HTMLInputElement>document.getElementById("link"));
  c.style.display='none';
}



}
