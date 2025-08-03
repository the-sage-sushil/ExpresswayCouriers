import { Component, OnInit } from '@angular/core';
import {
  FormGroup,
  FormBuilder,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ClientService } from '../../../services/client.service';
import { Client, ClientRequest } from '../../../services/models/client.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-client-edit',
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './client-edit.html',
  styleUrl: './client-edit.css',
})
export class ClientEdit implements OnInit {
  clientForm: FormGroup;
  loading = false;
  error: string | null = null;
  clientId : string;

  constructor(
    private fb: FormBuilder,
    private clientService: ClientService,
    private router: Router,
    private route: ActivatedRoute,
  ) {
    
    this.clientId = this.route.snapshot.paramMap.get('id')!;
    this.clientForm = this.fb.group({
      name: ['', Validators.required],
      address: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      contactPerson: [''],
      contactNumber: ['', Validators.required],

      // Pricing Controls
      airLocal250: [25, [Validators.required, Validators.min(0)]],
      airLocal500: [40, [Validators.required, Validators.min(0)]],
      airLocalAdd500: [30, [Validators.required, Validators.min(0)]],

      airNearby250: [40, [Validators.required, Validators.min(0)]],
      airNearby500: [60, [Validators.required, Validators.min(0)]],
      airNearbyAdd500: [50, [Validators.required, Validators.min(0)]],

      airPanIndia250: [60, [Validators.required, Validators.min(0)]],
      airPanIndia500: [80, [Validators.required, Validators.min(0)]],
      airPanIndiaAdd500: [70, [Validators.required, Validators.min(0)]],

      airSpecial250: [80, [Validators.required, Validators.min(0)]],
      airSpecial500: [100, [Validators.required, Validators.min(0)]],
      airSpecialAdd500: [90, [Validators.required, Validators.min(0)]],

      surfaceLocal500: [30, [Validators.required, Validators.min(0)]],
      surfaceNearby500: [40, [Validators.required, Validators.min(0)]],
      surfacePanIndia500: [50, [Validators.required, Validators.min(0)]],
      surfaceSpecial500: [60, [Validators.required, Validators.min(0)]],

      premiumLocal500: [180, [Validators.required, Validators.min(0)]],
      premiumLocalAdd500: [50, [Validators.required, Validators.min(0)]],
      premiumNearby500: [220, [Validators.required, Validators.min(0)]],
      premiumNearbyAdd500: [80, [Validators.required, Validators.min(0)]],
      premiumPanIndia500: [250, [Validators.required, Validators.min(0)]],
      premiumPanIndiaAdd500: [100, [Validators.required, Validators.min(0)]],
      premiumSpecial500: [280, [Validators.required, Validators.min(0)]],
      premiumSpecialAdd500: [120, [Validators.required, Validators.min(0)]],
    });
  }

  ngOnInit(): void {
    this.loading = true;
    this.clientService.getClientById(+this.clientId).subscribe({
      next: (client: Client) => {
        this.clientForm.patchValue(client);
        this.loading = false;
      },
      error: () => {
        this.error = 'Failed to load client data.';
        this.loading = false;
      }
    });
  }
  pricingCategories = [
    {
      label: 'By Air Local',
      items: [
        { label: '250g', controlName: 'airLocal250' },
        { label: '500g', controlName: 'airLocal500' },
        { label: 'Add On 500g', controlName: 'airLocalAdd500' },
      ],
    },
    {
      label: 'By Air (Maharashtra and Gujrat)',
      items: [
        { label: '250g', controlName: 'airNearby250' },
        { label: '500g', controlName: 'airNearby500' },
        { label: 'Add On 500g', controlName: 'airNearbyAdd500' },
      ],
    },
    {
      label: 'By Air Pan India',
      items: [
        { label: '250g', controlName: 'airPanIndia250' },
        { label: '500g', controlName: 'airPanIndia500' },
        { label: 'Add On 500g', controlName: 'airPanIndiaAdd500' },
      ],
    },
    {
      label: 'By Air Special Destination',
      items: [
        { label: '250g', controlName: 'airSpecial250' },
        { label: '500g', controlName: 'airSpecial500' },
        { label: 'Add On 500g', controlName: 'airSpecialAdd500' },
      ],
    },
    {
      label: 'By Surface',
      items: [
        { label: 'Local (500g)', controlName: 'surfaceLocal500' },
        { label: 'Nearby (500g)', controlName: 'surfaceNearby500' },
        { label: 'Pan India (500g)', controlName: 'surfacePanIndia500' },
        {
          label: 'Special Destination (500g)',
          controlName: 'surfaceSpecial500',
        },
      ],
    },
    {
      label: 'By Premium',
      items: [
        { label: 'Local (500g)', controlName: 'premiumLocal500' },
        { label: 'Local Add On (500g)', controlName: 'premiumLocalAdd500' },
        { label: 'Nearby (500g)', controlName: 'premiumNearby500' },
        { label: 'Nearby Add On (500g)', controlName: 'premiumNearbyAdd500' },
        { label: 'Pan India (500g)', controlName: 'premiumPanIndia500' },
        {
          label: 'Pan India Add On (500g)',
          controlName: 'premiumPanIndiaAdd500',
        },
        {
          label: 'Special Destination (500g)',
          controlName: 'premiumSpecial500',
        },
        {
          label: 'Special Destination Add On (500g)',
          controlName: 'premiumSpecialAdd500',
        },
      ],
    },
  ];

  submit(): void {
    if (this.clientForm.invalid) return;

    this.loading = true;
    const formValue = this.clientForm.value as ClientRequest;
    this.clientService.updateClient( +this.clientId , formValue).subscribe({
      next: () => {
        this.loading = false;
        this.router.navigate(['/client']);
      },
      error: (err) => {
        this.loading = false;
        this.error = 'Failed to create client.';
      },
    });
  }
}
