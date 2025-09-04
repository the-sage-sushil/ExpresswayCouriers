import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class InvoiceService {
  private baseUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  generateInvoice(clientId: number, fromDate: string, toDate: string): Observable<Blob> {
    let params = new HttpParams()
      .set('fromDate', fromDate)
      .set('toDate', toDate);

    return this.http.get(`${this.baseUrl}consignments/invoice/${clientId}`, {
      params: params,
      responseType: 'blob'
    });
  }
}
