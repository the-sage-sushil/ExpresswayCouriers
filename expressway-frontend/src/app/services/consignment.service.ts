import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ConsignmentRequest } from './models/consignment-request.model';
import { Consignment } from './models/consignment.model';
import { environment } from '../../environments/environment';
import { Client } from './models/client.model';

@Injectable({ providedIn: 'root' })
export class ConsignmentService {
  private apiUrl = environment.apiBaseUrl + 'consignments/';

  constructor(private http: HttpClient) {}

  saveConsignment(request: ConsignmentRequest): Observable<Consignment> {
    return this.http.post<Consignment>(`${this.apiUrl}booking`, request, { withCredentials: true });
  }

  getBookings(): Observable<Consignment[]> {
    return this.http.get<Consignment[]>(`${this.apiUrl}bookings`, { withCredentials: true });
  }

  getConsignmentByClientId(clientId: number): Observable<Consignment[]> {
    return this.http.get<Consignment[]>(`${this.apiUrl}consignmentbyClientId/${clientId}`, { withCredentials: true });
  }

  getCharges(client: Client, category: string, region: string): number[] {
  const pattern = new RegExp(`^${category}${region}(\\d+|Add\\d+)$`);
  return Object.entries(client)
    .filter(([key, value]) => pattern.test(key) && value !== undefined && typeof value === 'number')
    .map(([, value]) => value as number);
  }


}
