import { Component } from '@angular/core';
import { Client } from '../../../services/models/client.model';
import { ActivatedRoute } from '@angular/router';
import { ClientService } from '../../../services/client.service';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-client-edit',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './client-edit.html',
  styleUrl: './client-edit.css',
})
export class ClientEdit {
  clientId!: number;
  clientData!: Client;

  constructor(
    private route: ActivatedRoute,
    private clientService: ClientService,

  ) {}

  ngOnInit(): void {
    this.clientId = Number(this.route.snapshot.paramMap.get('id'));
    this.clientService.getClientById(this.clientId).subscribe((client) => {
      this.clientData = client;
      // populate form with clientData
    });
  }
}
