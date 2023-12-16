import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserAppointmentFormComponent } from './user-appointment-form.component';

describe('UserAppointmentFormComponent', () => {
  let component: UserAppointmentFormComponent;
  let fixture: ComponentFixture<UserAppointmentFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserAppointmentFormComponent]
    });
    fixture = TestBed.createComponent(UserAppointmentFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
