import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompanyAdminFormComponent } from './company-admin-form.component';

describe('CompanyAdminFormComponent', () => {
  let component: CompanyAdminFormComponent;
  let fixture: ComponentFixture<CompanyAdminFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CompanyAdminFormComponent]
    });
    fixture = TestBed.createComponent(CompanyAdminFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
