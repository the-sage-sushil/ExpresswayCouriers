import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ClientRequest, Client } from './models/client.model';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class ClientService {
  private apiUrl = environment.apiBaseUrl + 'client';

  constructor(private http: HttpClient) {}

  saveClient(request: ClientRequest): Observable<Client> {
    return this.http.post<Client>(`${this.apiUrl}`, request);
  }

  updateClient(id: number ,request: ClientRequest): Observable<Client> {
    return this.http.put<Client>(`${this.apiUrl}/${id}`, request);
  }

  getClientById(clientId: number): Observable<Client> {
    return this.http.get<Client>(`${this.apiUrl}/${clientId}`);
  }

  getAllClients(): Observable<Client[]> {
    return this.http.get<Client[]>(this.apiUrl);
  }

  deleteClient(clientId: number ): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${clientId}`);
  }
}
