import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ConsignmentService } from '../../services/consignment.service';
import { ConsignmentRequest } from '../../services/models/consignment-request.model';
import { Consignment } from '../../services/models/consignment.model';
import { CommonModule } from '@angular/common';
import { Client } from '../../services/models/client.model';
import { ClientService } from '../../services/client.service';
import { NgSelectModule } from '@ng-select/ng-select';

@Component({
  selector: 'app-consignment-booking',
  imports: [ReactiveFormsModule,CommonModule,FormsModule,NgSelectModule],
  templateUrl: './consignment-booking.html',
  styleUrl: './consignment-booking.css'
})
export class ConsignmentBooking {
 consignmentForm!: FormGroup;
  isLoading = false;
  successMessage = '';
  errorMessage = '';

  clients: Client[] = [];

  serviceTypes = [
    { value: 'standard', label: 'Standard Delivery' },
    { value: 'express', label: 'Express Delivery' },
    { value: 'overnight', label: 'Overnight Delivery' }
  ];

  statusOptions = [
    { value: 'pending', label: 'Pending' },
    { value: 'processing', label: 'Processing' },
    { value: 'shipped', label: 'Shipped' },
    { value: 'delivered', label: 'Delivered' }
  ];

  constructor(
    private fb: FormBuilder,
    private consignmentService: ConsignmentService,
    private clientService: ClientService,
  ) {}

  ngOnInit(): void {
    this.fetchClients();
    this.initializeForm();
  }

  private initializeForm(): void {
    this.consignmentForm = this.fb.group({
      trackingNumber: ['', [Validators.required, Validators.pattern(/^[A-Z0-9]{10,20}$/)]],
      channelPartner: ['', [Validators.required, Validators.minLength(2)]],
      serviceType: ['', Validators.required],
      senderName: ['', [Validators.required, Validators.minLength(2)]],
      senderContact: ['', [Validators.required, Validators.pattern(/^[0-9]{10}$/)]],
      senderAddress: ['', [Validators.required, Validators.minLength(10)]],
      receiverName: ['', [Validators.required, Validators.minLength(2)]],
      receiverAddress: ['', [Validators.required, Validators.minLength(10)]],
      weight: [0, [Validators.required, Validators.min(0.1), Validators.max(50)]],
      dimensions: ['', [Validators.required, Validators.pattern(/^\d+x\d+x\d+$/)]],
      client: [0, [Validators.required, Validators.min(1)]],
      client_id: [0, [Validators.required, Validators.min(1)]],
      totalAmount: [0, [Validators.required, Validators.min(0)]],
      status: ['pending', Validators.required]
    });
  }

  onSubmit(): void {
    if (this.consignmentForm.invalid) {
      this.markFormGroupTouched();
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';
    this.successMessage = '';

    const consignmentData: ConsignmentRequest = this.consignmentForm.value;

    this.consignmentService.saveConsignment(consignmentData).subscribe({
      next: (response: Consignment) => {
        this.successMessage = `Consignment created successfully! Tracking ID: ${response.trackingNumber}`;
        this.consignmentForm.reset();
        this.isLoading = false;
      },
      error: (error) => {
        this.errorMessage = error.message || 'Failed to create consignment. Please try again.';
        this.isLoading = false;
      }
    });
  }

    fetchClients(): void {
    this.clientService.getAllClients().subscribe({
      next: (data) => {
        this.clients = data;
        console.log(this.clients[0].contactNumber);
        console.log(this.clients[1].contactNumber);
        console.log(this.clients[2].contactNumber);
      },
      error: (err) => {
        // handle error
      }
    });
  }

  private markFormGroupTouched(): void {
    Object.keys(this.consignmentForm.controls).forEach(key => {
      this.consignmentForm.get(key)?.markAsTouched();
    });
  }

  // Getter methods for form controls
  get trackingNumber() { return this.consignmentForm.get('trackingNumber'); }
  get channelPartner() { return this.consignmentForm.get('channelPartner'); }
  get serviceType() { return this.consignmentForm.get('serviceType'); }
  get senderName() { return this.consignmentForm.get('senderName'); }
  get senderContact() { return this.consignmentForm.get('senderContact'); }
  get senderAddress() { return this.consignmentForm.get('senderAddress'); }
  get receiverName() { return this.consignmentForm.get('receiverName'); }
  get receiverAddress() { return this.consignmentForm.get('receiverAddress'); }
  get weight() { return this.consignmentForm.get('weight'); }
  get dimensions() { return this.consignmentForm.get('dimensions'); }
  get client() { return this.consignmentForm.get('client'); }
  get client_id() { return this.consignmentForm.get('client_id'); }
  get totalAmount() { return this.consignmentForm.get('totalAmount'); }
  get status() { return this.consignmentForm.get('status'); }
}