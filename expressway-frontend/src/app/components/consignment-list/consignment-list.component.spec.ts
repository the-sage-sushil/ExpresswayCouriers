import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ConsignmentListComponent } from './consignment-list.component';


describe('BookListComponent', () => {
  let component: ConsignmentListComponent;
  let fixture: ComponentFixture<ConsignmentListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ConsignmentListComponent]
    });
    fixture = TestBed.createComponent(ConsignmentListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
