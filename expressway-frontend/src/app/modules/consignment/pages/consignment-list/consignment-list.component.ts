import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Consignment, ConsignmentResponse } from '../../../../services/models/consignment.model';
import { ConsignmentService } from '../../../../services/consignment.service';
import { ConsignmentRequest } from '../../../../services/models/consignment-request.model';


@Component({
  selector: 'app-consignment-list',
  templateUrl: './consignment-list.component.html',
  standalone: false,
  styleUrls: ['./consignment-list.component.scss']
})
export class ConsignmentListComponent implements OnInit {
  size: number | undefined;
  page: number | undefined = 0;

  bookResponse: ConsignmentResponse | undefined;

  constructor(
    private consignmentService: ConsignmentService,
    private router: Router
  ){
    
  }
  ngOnInit(): void {
    this.findAllBooks();
  }
  findAllBooks() {
  
    this.consignmentService.getBookings().subscribe({
      next: (books:ConsignmentResponse): void => {
        this.bookResponse = books
        console.log(this.bookResponse)
      } 
    })
  }

}
