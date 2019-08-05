import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(public http: HttpClient) {
    this.http = http;
  }

  saveUserProfile(formData: FormData): Observable<any>{
    return this.http.post('http://localhost:8000/saveUserProfileInServer', formData);
  }
}
