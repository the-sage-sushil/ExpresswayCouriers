import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ConsignmentRequest } from './models/consignment-request.model';
import { Consignment } from './models/consignment.model';
import { environment } from '../../environments/environment';
import { Client } from './models/client.model';

@Injectable({ providedIn: 'root' })
export class ConsignmentService {
  updateConsignment(editedRowId: number, editableConsignment: any) {
    return this.http.put<Consignment>(`${this.apiUrl}booking/${editedRowId}`, editableConsignment, {
    });
  }
  private apiUrl = environment.apiBaseUrl + 'consignments/';

  constructor(private http: HttpClient) {}

  saveConsignment(request: ConsignmentRequest): Observable<Consignment> {
    return this.http.post<Consignment>(`${this.apiUrl}booking`, request, {
    });
  }

  getBookings(filters?: {
    clientId?: number;
    status?: string;
    serviceType?: string;
    channelPartner?: string;
    bookingDateFrom?: string; // ISO date string 'YYYY-MM-DD'
    bookingDateTo?: string;
    minWeight?: number;
    maxWeight?: number;
    paymentMode?: string;
  }): Observable<Consignment[]> {
    let params = new HttpParams();

    if (filters) {
      Object.keys(filters).forEach((key) => {
        const value = (filters as any)[key];
        if (value !== undefined && value !== null && value !== '') {
          params = params.set(key, value);
        }
      });
    }

    return this.http.get<Consignment[]>(`${this.apiUrl}bookings`, {
      params,
    });
  }

  getConsignmentByClientId(clientId: number): Observable<Consignment[]> {
    return this.http.get<Consignment[]>(
      `${this.apiUrl}consignmentbyClientId/${clientId}`,
    );
  }

  getCharges(client: Client, category: string, region: string): number[] {
    const pattern = new RegExp(`^${category}${region}(\\d+|Add\\d+)$`);
    return Object.entries(client)
      .filter(
        ([key, value]) =>
          pattern.test(key) && value !== undefined && typeof value === 'number',
      )
      .map(([, value]) => value as number);
  }
}
