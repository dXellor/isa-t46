import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompanyAdminAppointmentsComponent } from './company-admin-appointments.component';

describe('CompanyAdminAppointmentsComponent', () => {
  let component: CompanyAdminAppointmentsComponent;
  let fixture: ComponentFixture<CompanyAdminAppointmentsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CompanyAdminAppointmentsComponent]
    });
    fixture = TestBed.createComponent(CompanyAdminAppointmentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
