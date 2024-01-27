import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserCompletedReservationsComponent } from './user-completed-reservations.component';

describe('UserCompletedReservationsComponent', () => {
  let component: UserCompletedReservationsComponent;
  let fixture: ComponentFixture<UserCompletedReservationsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserCompletedReservationsComponent]
    });
    fixture = TestBed.createComponent(UserCompletedReservationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
