import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ClientService } from '../../../services/client.service';
import { Client } from '../../../services/models/client.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-client-create',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './client-create.html',
  styleUrl: './client-create.css'
})
export class ClientCreate {
  clientForm: FormGroup;
  loading = false;
  error: string | null = null;

  constructor(
    private fb: FormBuilder,
    private clientService: ClientService,
    private router: Router
  ) {
    this.clientForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      contactPerson: [''],
      contactNumber: ['', Validators.required],
      standardPricePerKg: [0, [Validators.required, Validators.min(0)]],
      premiumPricePerKg: [0, [Validators.required, Validators.min(0)]],
      surfacePricePerKg: [0, [Validators.required, Validators.min(0)]],
      address: ['', Validators.required]
    });
  }

  submit(): void {
    if (this.clientForm.invalid) return;

    this.loading = true;
    const formValue = this.clientForm.value as Omit<Client, 'id'>;
    this.clientService.saveClient(formValue as Client).subscribe({
      next: () => {
        this.loading = false;
        this.router.navigate(['/client']);
      },
      error: err => {
        this.loading = false;
        this.error = 'Failed to create client.';
      }
    });
  }
}
