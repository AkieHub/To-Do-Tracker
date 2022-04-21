
import { AbstractControl, FormControl, ValidatorFn } from "@angular/forms";

export function dobValidator(controlName: FormControl): { [s: string]: boolean } |null{
  const dob=controlName.value;
  if(!dob){
    return null;
  }
  const dobDate=new Date(dob);
  const todayDate=new Date();
  const currentYear = todayDate.getFullYear();
  const dobYear = dobDate.getFullYear();
    if (Number(currentYear)-Number(dobYear)<18) {
      return { notVailid: true }
    }
    return null;
}

export function passwordMatchValidator(controlName: string, checkControlName: string): ValidatorFn {
  return (controls: AbstractControl) => {
    const control = controls.get(controlName);
    const checkControl = controls.get(checkControlName);
    if (checkControl?.errors && !checkControl?.errors["matching"]) {
      return null;
    }
    if (control?.value !== checkControl?.value) {
      controls?.get(checkControlName)?.setErrors({ passmatching: true });
      return { matching: true };
    } else {
      return null;
    }
  };
}

export function endDateValidator(controlName: string, checkControlName: string): ValidatorFn {
  return (controls: AbstractControl) => {
    const control = controls.get(controlName);
    const checkControl = controls.get(checkControlName);
    if (checkControl?.errors && !checkControl?.errors["matching"]) {
      return null;
    }
    if (control?.value < checkControl?.value) {
      controls?.get(checkControlName)?.setErrors({ passmatching: true });
      return { matching: true };
    } else {
      return null;
    }
  };
}


export function stratDate(control: FormControl): { [s: string]: boolean } |null {

  const fromDate = control.value;
  if (!fromDate) {
    return null;
  }
  const fromDateTime = new Date(fromDate);
  const currentDateTime = new Date();
  if (fromDateTime.setHours(0,0,0,0) > currentDateTime.setHours(0,0,0,0) || fromDateTime.setHours(0,0,0,0) === currentDateTime.setHours(0,0,0,0)) {
    return null;
  }
  return { notvalid: true }

}

