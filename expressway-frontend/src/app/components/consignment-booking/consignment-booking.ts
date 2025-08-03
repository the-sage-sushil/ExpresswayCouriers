import { Component } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ConsignmentService } from '../../services/consignment.service';
import { ConsignmentRequest } from '../../services/models/consignment-request.model';
import { Consignment } from '../../services/models/consignment.model';
import { CommonModule } from '@angular/common';
import { Client } from '../../services/models/client.model';
import { ClientService } from '../../services/client.service';
import { NgSelectModule } from '@ng-select/ng-select';
import { Router } from '@angular/router';
import { Dtdc } from '../../services/dtdc.service';

@Component({
  selector: 'app-consignment-booking',
  imports: [ReactiveFormsModule, CommonModule, FormsModule, NgSelectModule],
  templateUrl: './consignment-booking.html',
  styleUrl: './consignment-booking.css',
})
export class ConsignmentBooking {
  onSenderNameChange(selected: any) {
    if (typeof selected === 'object' && selected !== null) {
      // It's a client object --> auto-fill contact/address
      this.consignmentForm.patchValue({
        senderContact: selected.contactNumber,
        senderAddress: selected.address,
      });
    } else {
      // It's a custom value (string) --> clear
      this.consignmentForm.patchValue({
        senderContact: '',
        senderAddress: '',
      });
    }
  }
  consignmentForm!: FormGroup;
  isLoading = false;
  successMessage = '';
  errorMessage = '';
  isServiceable = false;

  clients: Client[] = [];

  serviceTypes = [
    { value: 'air', label: 'Standard Air' },
    { value: 'express', label: 'Premium Air' },
    { value: 'surface', label: 'Surface' },
    // { value: 'eExpress', label: 'Ecom-Express' },
    // { value: 'eSurface', label: 'Ecom-Surface' },
  ];

  statusOptions = [
    { value: 'pending', label: 'Pending' },
    { value: 'processing', label: 'Processing' },
    { value: 'shipped', label: 'Shipped' },
    { value: 'delivered', label: 'Delivered' },
  ];
  
  constructor(
    private fb: FormBuilder,
    private router: Router,
    private consignmentService: ConsignmentService,
    private clientService: ClientService,
    private dtdc: Dtdc,
  ) {}
  
  ngOnInit(): void {
    this.fetchClients();
    this.initializeForm();
  }
  
  private initializeForm(): void {
    this.consignmentForm = this.fb.group({
      trackingNumber: [
        null,
        [Validators.required, Validators.pattern(/^[A-Z0-9]{6,20}$/)],
      ],
      channelPartner: ['dtdc', [Validators.required, Validators.minLength(2)]],
      serviceType: [null, Validators.required],
      senderName: [null, [Validators.required, Validators.minLength(2)]],
      senderContact: [
        null,
        [Validators.required, Validators.pattern(/^[0-9]{10}$/)],
      ],
      senderAddress: [null, [Validators.required, Validators.minLength(10)]],
      receiverName: [null, [Validators.required, Validators.minLength(2)]],
      receiverAddress: [null, [Validators.required, Validators.minLength(10)]],
      weight: [
        ,
        [Validators.required, Validators.min(0.1), Validators.max(50000)],
      ],
      dimensions: [
        null,
        [Validators.required, Validators.pattern(/^\d+x\d+x\d+$/)],
      ],
      totalAmount: [null, [Validators.required, Validators.min(0)]],
      destPincode: [null, Validators.required],
      client: [null],
    });
  }

  onSubmit(): void {
    ;
    if (this.consignmentForm.invalid) {
      this.markFormGroupTouched();
      return;
    }
    
    this.isLoading = true;
    this.errorMessage = '';
    this.successMessage = '';
    
    if (this.consignmentForm.get('senderName')?.value.id) {
      this.consignmentForm.get('client')?.setValue(this.consignmentForm.get('senderName')?.value);
      this.consignmentForm.get('senderName')?.setValue(this.consignmentForm.get('senderName')?.value.name);
    }
    const consignmentData: ConsignmentRequest = this.consignmentForm.value;
    
    this.consignmentService.saveConsignment(consignmentData).subscribe({
      next: (response: Consignment) => {
        this.successMessage = `Consignment created successfully! Tracking ID: ${response.trackingNumber}`;
        this.consignmentForm.reset();
        this.isLoading = false;
        this.router.navigate(['/consignments']);
      },
      error: (error) => {
        this.errorMessage =
        error.message || 'Failed to create consignment. Please try again.';
        this.isLoading = false;
      },
    });
  }
  onDestPincodeChange($event: Event) {
    const input = $event.target as HTMLInputElement;
    const value = input.value;

    this.dtdc.getServiceable(Number(value)).subscribe((data) => {
      debugger;
      this.isServiceable = !!data
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

  private markFormGroupTouched(): void {
    Object.keys(this.consignmentForm.controls).forEach((key) => {
      this.consignmentForm.get(key)?.markAsTouched();
    });
  }

  // Getter methods for form controls
  get trackingNumber() {
    return this.consignmentForm.get('trackingNumber');
  }
  get channelPartner() {
    return this.consignmentForm.get('channelPartner');
  }
  get serviceType() {
    return this.consignmentForm.get('serviceType');
  }
  get senderName() {
    return this.consignmentForm.get('senderName');
  }
  get senderContact() {
    return this.consignmentForm.get('senderContact');
  }
  get senderAddress() {
    return this.consignmentForm.get('senderAddress');
  }
  get destPincode() {
    return this.consignmentForm.get('destPincode');
  }
  get receiverName() {
    return this.consignmentForm.get('receiverName');
  }
  get receiverAddress() {
    return this.consignmentForm.get('receiverAddress');
  }
  get weight() {
    return this.consignmentForm.get('weight');
  }
  get dimensions() {
    return this.consignmentForm.get('dimensions');
  }
  get client() {
    return this.consignmentForm.get('client');
  }
  get client_id() {
    return this.consignmentForm.get('client_id');
  }
  get totalAmount() {
    return this.consignmentForm.get('totalAmount');
  }
  get status() {
    return this.consignmentForm.get('status');
  }
}
