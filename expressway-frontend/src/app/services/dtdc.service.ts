import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { ConsignmentRequest } from './models/consignment-request.model';
import { Consignment } from './models/consignment.model';

@Injectable({
  providedIn: 'root'
})
export class Dtdc {
  private apiUrl = environment.getServicableUrl;

  constructor(private http: HttpClient) {}

  getServiceable(destPincode: number): Observable<Object> {
    return this.http.get<Consignment[]>(`${this.apiUrl}?src=400022&det=${destPincode}`, { withCredentials: true });
  }

}
