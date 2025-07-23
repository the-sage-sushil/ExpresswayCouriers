import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Consignment } from '../../services/models/consignment.model';
import { ConsignmentService } from '../../services/consignment.service';
import { ConsignmentRequest } from '../../services/models/consignment-request.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-consignment-list',
  templateUrl: './consignment-list.component.html',
  imports: [CommonModule],
  styleUrls: ['./consignment-list.component.scss'],
})
export class ConsignmentList implements OnInit {
  size: number | undefined;
  page: number | undefined = 0;

  bookResponse: Consignment[] = [];

  constructor(
    private consignmentService: ConsignmentService,
    private router: Router
  ) {}
  ngOnInit(): void {
    this.findAllBooks();
  }
  findAllBooks() {
    this.consignmentService.getBookings().subscribe({
      next: (books: Consignment[]): void => {
        this.bookResponse = books;
        console.log(this.bookResponse);
      },
    });
  }
}
