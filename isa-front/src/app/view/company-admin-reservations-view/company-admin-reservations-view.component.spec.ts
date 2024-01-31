import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompanyAdminReservationsViewComponent } from './company-admin-reservations-view.component';

describe('CompanyAdminReservationsViewComponent', () => {
  let component: CompanyAdminReservationsViewComponent;
  let fixture: ComponentFixture<CompanyAdminReservationsViewComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CompanyAdminReservationsViewComponent]
    });
    fixture = TestBed.createComponent(CompanyAdminReservationsViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
