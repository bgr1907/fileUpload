import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import { UserService } from './user.service'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';
  reactiveForm: any = FormGroup;
  public userFile: any = File;

  constructor(private fb: FormBuilder, private userService: UserService){
    this.reactiveForm = this.fb.group({
      firstName : new FormControl('', [Validators.required, Validators.compose([Validators.pattern('[a-zA-z ]*'), Validators.minLength(3)])]),
      lastName : new FormControl('', [Validators.required, Validators.compose([Validators.pattern('[a-zA-z ]*'), Validators.minLength(2)])]),
      email : new FormControl('', [Validators.required, Validators.compose([Validators.pattern('^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$')])]),
      phoneNumber : new FormControl('', [Validators.required, Validators.compose([Validators.pattern('[0-9+ ]*'), Validators.minLength(10), Validators.maxLength(14)])])
    });
  }

  onSelectFile(event){
    const file = event.target.files[0];
    this.userFile = file;
  }

  saveForm(submitForm: FormGroup){
    if(submitForm.valid){
      const user = submitForm.value;
      const formData = new FormData();
      formData.append('user', JSON.stringify(user));
      formData.append('file', this.userFile);
      this.userService.saveUserProfile(formData).subscribe((response) => {
        console.log(response);
      });
    }else{
      this.validateFormFields(submitForm);
    }
  }

  validateFormFields(submitForm: FormGroup){
    Object.keys(submitForm.controls).forEach(field => {
      const control = submitForm.get(field);
      if(control instanceof FormControl){
        control.markAsTouched({onlySelf: true});
      }else if(control instanceof FormGroup){
        this.validateFormFields(control);
      }
    });
  }

}

