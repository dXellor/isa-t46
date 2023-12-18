import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompanyInventoryManagementComponent } from './company-inventory-management.component';

describe('CompanyInventoryManagementComponent', () => {
  let component: CompanyInventoryManagementComponent;
  let fixture: ComponentFixture<CompanyInventoryManagementComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CompanyInventoryManagementComponent]
    });
    fixture = TestBed.createComponent(CompanyInventoryManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
