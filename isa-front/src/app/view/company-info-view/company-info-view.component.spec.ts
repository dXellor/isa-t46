import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompanyInfoViewComponent } from './company-info-view.component';

describe('CompanyInfoViewComponent', () => {
  let component: CompanyInfoViewComponent;
  let fixture: ComponentFixture<CompanyInfoViewComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CompanyInfoViewComponent]
    });
    fixture = TestBed.createComponent(CompanyInfoViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
