import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsignmentBooking } from './consignment-booking';

describe('ConsignmentBooking', () => {
  let component: ConsignmentBooking;
  let fixture: ComponentFixture<ConsignmentBooking>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ConsignmentBooking]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ConsignmentBooking);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
