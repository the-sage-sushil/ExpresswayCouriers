import { Component, inject, OnInit } from '@angular/core';
import { ClientService } from '../../services/client.service';
import { Client } from '../../services/models/client.model';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-client',
  imports: [CommonModule],
  templateUrl: './client.html',
  styleUrl: './client.css'
})
export class ClientList  implements OnInit {
  clients: Client[] = [];
  loading = false;
  router = inject(Router);

  constructor(private clientService: ClientService) {}

  ngOnInit(): void {
    this.fetchClients();
  }

  fetchClients(): void {
    this.loading = true;
    this.clientService.getAllClients().subscribe({
      next: (data) => {
        this.clients = data;
        console.log(this.clients[0].contactNumber);
        console.log(this.clients[1].contactNumber);
        console.log(this.clients[2].contactNumber);
        this.loading = false;
      },
      error: (err) => {
        this.loading = false;
        // handle error
      }
    });
  }

  editClient(client: Client): void {
    this.router.navigate(['/client/edit', client.id]);
    alert('Edit ' + client.name);
  }

  deleteClient(client: Client): void {
    if (confirm(`Are you sure to delete client "${client.name}"?`)) {
      this.clientService.deleteClient(client.id).subscribe(() => {
        this.fetchClients();
      });
    }
  }

  addClient(): void {
    this.router.navigate(['/client/new']);
  }
}

