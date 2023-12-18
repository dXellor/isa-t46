import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SystemAdminFormComponent } from './system-admin-form.component';

describe('SystemAdminFormComponent', () => {
  let component: SystemAdminFormComponent;
  let fixture: ComponentFixture<SystemAdminFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SystemAdminFormComponent]
    });
    fixture = TestBed.createComponent(SystemAdminFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
