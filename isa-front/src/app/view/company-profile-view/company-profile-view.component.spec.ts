import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompanyProfileViewComponent } from './company-profile-view.component';

describe('CompanyProfileViewComponent', () => {
  let component: CompanyProfileViewComponent;
  let fixture: ComponentFixture<CompanyProfileViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CompanyProfileViewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CompanyProfileViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
