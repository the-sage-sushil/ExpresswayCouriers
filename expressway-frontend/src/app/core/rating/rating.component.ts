import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-rating',
  templateUrl: './rating.component.html',
  imports: [CommonModule],
  styleUrls: ['./rating.component.scss']
})
export class RatingComponent {

  @Input() rating : number = 0;
  maxRating: number = 5;

  get fullStar(): number {
    return Math.floor(this.rating);
  }

  get hasHalfstar(): boolean {
     return this.rating % 1 !==0;
  }

  get emptystars(): number {
    return this.maxRating - Math.ceil(this.rating);
  }
}
