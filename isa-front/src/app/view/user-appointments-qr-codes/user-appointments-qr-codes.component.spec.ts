import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserAppointmentsQrCodesComponent } from './user-appointments-qr-codes.component';

describe('UserAppointmentsQrCodesComponent', () => {
  let component: UserAppointmentsQrCodesComponent;
  let fixture: ComponentFixture<UserAppointmentsQrCodesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserAppointmentsQrCodesComponent]
    });
    fixture = TestBed.createComponent(UserAppointmentsQrCodesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
