import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserManagingViewComponent } from './user-managing-view.component';

describe('UserManagingViewComponent', () => {
  let component: UserManagingViewComponent;
  let fixture: ComponentFixture<UserManagingViewComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserManagingViewComponent]
    });
    fixture = TestBed.createComponent(UserManagingViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
