import { Component, signal } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ConsignmentService } from '../../services/consignment.service';
import { ConsignmentRequest } from '../../services/models/consignment-request.model';
import {
  Consignment,
  ServiceableResponse,
  ServiceExplain,
  TatResponse,
} from '../../services/models/consignment.model';
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
  consignmentForm!: FormGroup;
  isLoading = false;
  successMessage = '';
  errorMessage = '';
  isServiceable?: ServiceableResponse;
  locationType = signal<string>('');
  selectedServiceType = signal<string>('');
  calculatedPrice = signal<number>(0);
  isServiceableflag: boolean = false;
  tatResponse?: ServiceExplain;
  selectedClient = signal<Client>({} as Client);
  baseCharge: number = 0;
  addCharge: number = 0;

  clients: Client[] = [];

  serviceTypes = [
    { value: 'air', label: 'Standard Air' },
    { value: 'premium', label: 'Premium Air' },
    { value: 'surface', label: 'Surface' },
    // { value: 'eExpress', label: 'Ecom-Express' },
    // { value: 'eSurface', label: 'Ecom-Surface' },
  ];

  locationTypes = [
    { value: 'PanIndia', label: 'REST OF INDIA' },
    { value: 'PanIndia', label: 'METROS' },
    { value: 'Special', label: 'SPECIAL DESTINATONS' },
    { value: 'Nearby', label: 'WITH IN ZONE' },
    { value: 'Nearby', label: 'WITH IN STATE' },
    { value: 'Local', label: 'WITH IN CITY' },
  ];
  weightInputTimer: any;

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
      senderContact: [null, [, Validators.pattern(/^[0-9]{10}$/)]],
      senderAddress: [null],
      receiverName: [null, [Validators.required, Validators.minLength(2)]],
      receiverAddress: [null, [Validators.required]],
      destPincode: [null, Validators.required],
      weight: [
        ,
        [Validators.required, Validators.min(0.1), Validators.max(50000)],
      ],
      dimensions: [
        '1x1x1',
        [Validators.required, Validators.pattern(/^\d+x\d+x\d+$/)],
      ],
      totalAmount: [
        this.calculatedPrice(),
        [Validators.required, Validators.min(0)],
      ],
      client: [null],
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

    if (this.consignmentForm.get('senderName')?.value.id) {
      this.consignmentForm
        .get('client')
        ?.setValue(this.consignmentForm.get('senderName')?.value);
      this.consignmentForm
        .get('senderName')
        ?.setValue(this.consignmentForm.get('senderName')?.value.name);
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

  onDestPincodeInput(event: Event): void {
    const pincode = (event.target as HTMLInputElement).value;

    // Optionally: check length before API call
    if (pincode.length >= 6) {
      this.dtdc.getServiceable(Number(pincode)).subscribe((resposne) => {
        this.isServiceable = resposne;
        this.isServiceableflag = resposne.data.serviceable;
        this.consignmentForm
          .get('receiverAddress')
          ?.setValue(
            this.isServiceable.data.destinationBranchCity +
              ' (' +
              this.isServiceable.data.state +
              ')',
          );
      });
      this.dtdc.getTatDetails(Number(pincode)).subscribe((resposne) => {
        this.tatResponse = resposne;
        this.locationType.set(
          this.locationTypes.find(
            (key) => key.label === resposne.destinationType.name,
          )?.value || '',
        );
      });
    } else {
      this.isServiceableflag = false; // hide icons again if input is too short
    }
  }
  onServiceTypeSeclet(service: any) {
    const selectedValue = (service.target as HTMLSelectElement).value;
    this.selectedServiceType.set(selectedValue);
  }

  onSenderNameChange(selected: any) {
    if (typeof selected === 'object' && selected !== null) {
      this.selectedClient.set(selected);

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

  onWeightInput(event: Event) {
    const value = Number((event.target as HTMLInputElement).value);

    // Clear previous timer
    clearTimeout(this.weightInputTimer);

    // Start a new debounce timer
    this.weightInputTimer = setTimeout(() => {
      console.log('User stopped typing. Weight:', value);
      this.calculateWeight(value); // Your existing method
    }, 500); // 500ms pause before calling
  }

  calculateWeight(event: any) {
    if (this.selectedClient() && event) {
      let weight = Number(event);
      var baseCharge = this.consignmentService.getCharges(
        this.selectedClient(),
        this.selectedServiceType(),
        this.locationType(),
      );

      if (
        this.selectedServiceType() === 'air' ||
        this.selectedServiceType() === 'premium'
      ) {
        if (weight <= 0.25) {
          this.calculatedPrice.set(baseCharge[0]);
        } else if (weight <= 0.5) {
          this.calculatedPrice.set(baseCharge[1]);
        } else {
          const extraSlabs = Math.ceil((weight - 0.5) / 0.5);
          this.calculatedPrice.set(baseCharge[1] + baseCharge[2] * extraSlabs);
        }
        console.log(this.calculatedPrice());

        this.consignmentForm
          .get('totalAmount')
          ?.setValue(this.calculatedPrice());
      }
      if (this.selectedServiceType() === 'surface') {
        let chargeWeight = Number(weight);

        // Enforce minimum weight slab of 5 kg
        if (chargeWeight < 5) {
          chargeWeight = 5;
        }

        // Calculate extra 500g slabs above 5kg (0 if exactly 5kg)
        const extraSlabs = Math.ceil((chargeWeight - 5) / 0.5);

        // Base price is charge for 5kg minimum: 5 * baseCharge[0]
        // Plus baseCharge[0] for each extra 500g slab
        const price = 5 * baseCharge[0] + extraSlabs * baseCharge[0];

        this.calculatedPrice.set(price);

        console.log(this.calculatedPrice());

        this.consignmentForm
          .get('totalAmount')
          ?.setValue(this.calculatedPrice());
      }
    }
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
