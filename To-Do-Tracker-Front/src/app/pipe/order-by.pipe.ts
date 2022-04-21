import { Pipe, PipeTransform } from '@angular/core';
import * as _ from 'lodash';
import { orderBy } from 'lodash';

@Pipe({
  name: 'orderBy'
})
export class OrderByPipe implements PipeTransform {
  transform(records: Array<any>, args?: any, ty?:boolean): any { 
    
    // return records.sort((a, b) => {
    //   if (a.args < b.args) {
    //     return -1;
    //   } else if (a.args === b.args) {
    //     return 0;
    //   } else if (a.args > b.args) {
    //     return 1;
    //   }
    // });
   console.log(records);
   console.log(args);
   console.log(ty);
  }
    
}
