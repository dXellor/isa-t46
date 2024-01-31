import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmployeeReservationsViewComponent } from './employee-reservations-view.component';

describe('EmployeeReservationsViewComponent', () => {
  let component: EmployeeReservationsViewComponent;
  let fixture: ComponentFixture<EmployeeReservationsViewComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EmployeeReservationsViewComponent]
    });
    fixture = TestBed.createComponent(EmployeeReservationsViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
