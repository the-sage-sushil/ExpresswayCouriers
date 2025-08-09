import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { ConsignmentRequest } from './models/consignment-request.model';
import { Consignment, ServiceableResponse, ServiceExplain, TatRequest, TatResponse } from './models/consignment.model';

@Injectable({
  providedIn: 'root'
})
export class Dtdc {

  private apiUrl = environment.apiBaseUrl + 'consignments/';

  constructor(private http: HttpClient) {}

  getServiceable(destPincode: number): Observable<ServiceableResponse> {
    return this.http.get<ServiceableResponse>(`${this.apiUrl}serviceable/${destPincode}`, { withCredentials: true });
  }

  getTatDetails(destPincode: number): Observable<ServiceExplain> {
    const request: TatRequest = {
    "pickupPincode": "400022",
    "deliveryPincode": `${destPincode}`,
    "weight": "20000",
    "courierType": "Non-Document",
    "isQRBooking": false,
    "length": "10",
    "breadth": "1",
    "height": "10",
    "commodityId": "72",
    "commodityName": "BOOKS",
    "declaredPrice": "450",
    "commodityCode": "BOOKS"
}
    return this.http.post<ServiceExplain>(`${this.apiUrl}getTat`,request ,{ withCredentials: true });
  }

  calculatePrice(weight: number, baseCharge: number[]): number {
    if (weight <= 0.5) {
      return baseCharge[0];
    } else if (weight <= 1) {
      return baseCharge[1];
    } else {
      // For weight >1kg, first slab = baseCharge[1], rest in 500g increments
      const extraSlabs = Math.ceil((weight - 1) / 0.5);
      return baseCharge[1] + baseCharge[2] * extraSlabs;
    }
}

}
