import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ClientRequest, Client } from './models/client.model';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class ClientService {
  private apiUrl = environment.apiBaseUrl + 'clients/';

  constructor(private http: HttpClient) {}

  saveClient(request: ClientRequest): Observable<Client> {
    return this.http.post<Client>(`${this.apiUrl}`, request, { withCredentials: true });
  }

  getClientById(clientId: number): Observable<Client> {
    return this.http.get<Client>(`${this.apiUrl}${clientId}`, { withCredentials: true });
  }
}
