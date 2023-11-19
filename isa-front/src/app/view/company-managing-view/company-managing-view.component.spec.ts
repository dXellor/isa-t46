import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompanyManagingViewComponent } from './company-managing-view.component';

describe('CompanyManagingViewComponent', () => {
  let component: CompanyManagingViewComponent;
  let fixture: ComponentFixture<CompanyManagingViewComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CompanyManagingViewComponent]
    });
    fixture = TestBed.createComponent(CompanyManagingViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
