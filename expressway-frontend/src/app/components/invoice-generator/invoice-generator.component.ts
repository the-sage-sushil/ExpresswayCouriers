import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { InvoiceService } from '../../services/invoice.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatCardModule } from "@angular/material/card";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from "@angular/material/progress-spinner";
import { MatDatepickerModule } from "@angular/material/datepicker";
import { MatNativeDateModule } from '@angular/material/core';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import { NgSelectModule } from '@ng-select/ng-select';
import { ClientService } from '../../services/client.service';
import { Client } from '../../services/models/client.model';

@Component({
  selector: 'app-invoice-generator',
  templateUrl: './invoice-generator.component.html',
  styleUrls: ['./invoice-generator.component.css'],
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    NgSelectModule
  ]
})
export class InvoiceGeneratorComponent {
  invoiceForm!: FormGroup;
  loading = false;
  errorMessage = '';
  successMessage = '';
  clients: Client[] = [];

  constructor(
    private fb: FormBuilder,
    private invoiceService: InvoiceService,
    private clientService: ClientService,
    private snackBar: MatSnackBar
  ) {
    this.initializeForm();
    this.fetchClients();
  }

  private initializeForm(): void {
    this.invoiceForm = this.fb.group({
      client: [null, Validators.required],
      fromDate: ['', Validators.required],
      toDate: ['', Validators.required]
    });
  }

  private fetchClients(): void {
    this.clientService.getAllClients().subscribe({
      next: (data) => {
        this.clients = data;
      },
      error: (err) => {
        this.errorMessage = 'Failed to fetch clients. Please try again.';
      }
    });
  }

  generateInvoice(): void {
    if (this.invoiceForm.invalid) {
      this.markFormGroupTouched();
      return;
    }

    this.loading = true;
    this.errorMessage = '';
    this.successMessage = '';
    const formValue = this.invoiceForm.value;
    
    this.invoiceService.generateInvoice(
      formValue.client.id,
      formValue.fromDate,
      formValue.toDate
    ).subscribe({
      next: (response: Blob) => {
        const downloadURL = window.URL.createObjectURL(response);
        const link = document.createElement('a');
        link.href = downloadURL;
        link.download = `invoice-${formValue.client.name}-${formValue.fromDate}.pdf`;
        link.click();
        window.URL.revokeObjectURL(downloadURL);
        this.successMessage = 'Invoice generated successfully!';
        this.invoiceForm.reset();
      },
      error: (error) => {
        this.errorMessage = error.message || 'Error generating invoice. Please try again.';
      },
      complete: () => {
        this.loading = false;
      }
    });
  }

  private markFormGroupTouched(): void {
    Object.keys(this.invoiceForm.controls).forEach(key => {
      this.invoiceForm.get(key)?.markAsTouched();
    });
  }

  // Getter methods for form controls
  get client() {
    return this.invoiceForm.get('client');
  }

  get fromDate() {
    return this.invoiceForm.get('fromDate');
  }

  get toDate() {
    return this.invoiceForm.get('toDate');
  }
}
