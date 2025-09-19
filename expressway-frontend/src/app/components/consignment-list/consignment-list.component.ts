import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Consignment } from '../../services/models/consignment.model';
import { ConsignmentService } from '../../services/consignment.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatNativeDateModule } from '@angular/material/core';
import { ClientService } from '../../services/client.service';
import { Client } from '../../services/models/client.model';

@Component({
  selector: 'app-consignment-list',
  templateUrl: './consignment-list.component.html',
  styleUrls: ['./consignment-list.component.css'],
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatDatepickerModule,
    MatFormFieldModule,
    MatInputModule,
    MatNativeDateModule,
  ],
})
export class ConsignmentList implements OnInit {
  edit(arg0: number) {
    console.log(`button ${arg0} clcick`);
  }
  consignmentResponse?: Consignment[];
  clients?: Client[];
  serviceTypes = [
    { value: 'air', label: 'Standard Air' },
    { value: 'premium', label: 'Premium Air' },
    { value: 'surface', label: 'Surface' },
    // { value: 'eExpress', label: 'Ecom-Express' },
    // { value: 'eSurface', label: 'Ecom-Surface' },
  ];
  filters: any = {
    clientId: '',
    status: '',
    serviceType: '',
    channelPartner: '',
    bookingDateFrom: '',
    bookingDateTo: '',
    minWeight: '',
    maxWeight: '',
    paymentMode: '',
  };

  editedRowId: number | null = null;
  editableConsignment: Consignment | any = {};

  constructor(
    private consignmentService: ConsignmentService,
    private clientService: ClientService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.applyFilters(); // initial load
    this.fetchClients();
  }
  startEdit(consignment: Consignment) {
  this.editedRowId = consignment.id;
  // Deep copy to avoid two-way binding affecting original until save
  this.editableConsignment = { ...consignment };
}

cancelEdit() {
  this.editedRowId = null;
  this.editableConsignment = {};
}

saveEdit() {
  if (!this.editedRowId) return;

  this.consignmentService.updateConsignment(this.editedRowId, this.editableConsignment).subscribe({
    next: (updated) => {
      // Update the row in local array
      const index = this.consignmentResponse?.findIndex(c => c.id === this.editedRowId);
      if (index !== undefined && index >= 0) {
        this.consignmentResponse![index] = updated;
      }
      this.resetFilters();
      this.cancelEdit();
    },
    error: (err) => {
      console.error("Update failed", err);
    }
  });
}
  fetchClients(): void {
    this.clientService.getAllClients().subscribe({
      next: (data) => {
        this.clients = data;
      },
      error: (err) => {
        // handle error
      },
    });
  }
  applyFilters() {
    const filterParams: any = {};

    Object.keys(this.filters).forEach((key) => {
      let value = this.filters[key];

      // Convert Date objects from datepicker to "YYYY-MM-DD"
      if (value instanceof Date) {
        value = value.toISOString().split('T')[0];
      }

      if (value !== '' && value !== null && value !== undefined) {
        filterParams[key] = value;
      }
    });

    this.consignmentService.getBookings(filterParams).subscribe({
      next: (consignments: Consignment[]) => {
        // Sort consignments by bookingDate in descending order (latest first)
        this.consignmentResponse = consignments.sort((a, b) => {
          const dateA = new Date(a.bookingDate).getTime();
          const dateB = new Date(b.bookingDate).getTime();
          return dateB - dateA;
        });
      },
    });
  }

  resetFilters() {
    Object.keys(this.filters).forEach((key) => (this.filters[key] = ''));
    this.applyFilters();
  }
}
