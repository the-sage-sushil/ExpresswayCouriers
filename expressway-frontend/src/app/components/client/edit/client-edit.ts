import { Component, OnInit } from '@angular/core';
import { Client } from '../../../services/models/client.model';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ClientService } from '../../../services/client.service';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
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

  private clientId!: string; // If id is string, otherwise use number

  constructor(
    private fb: FormBuilder,
    private clientService: ClientService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    // Blank form: will be patched with data after loading
    this.clientForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      contactPerson: [''],
      contactNumber: ['', Validators.required],
      standardPricePerKg: [null, [Validators.required, Validators.min(0)]],
      premiumPricePerKg: [null, [Validators.required, Validators.min(0)]],
      surfacePricePerKg: [null, [Validators.required, Validators.min(0)]],
      address: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.clientId = this.route.snapshot.paramMap.get('id')!;
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

  submit(): void {
    if (this.clientForm.invalid) return;
    this.loading = true;
    const updatedClient: Client = { id: this.clientId, ...this.clientForm.value };

    this.clientService.updateClient(+this.clientId, updatedClient).subscribe({
      next: () => {
        this.loading = false;
        this.router.navigate(['/client']);
      },
      error: () => {
        this.loading = false;
        this.error = 'Failed to update client.';
      }
    });
  }
}

