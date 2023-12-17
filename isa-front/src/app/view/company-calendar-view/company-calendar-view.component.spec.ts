import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompanyCalendarViewComponent } from './company-calendar-view.component';

describe('CompanyCalendarViewComponent', () => {
  let component: CompanyCalendarViewComponent;
  let fixture: ComponentFixture<CompanyCalendarViewComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CompanyCalendarViewComponent]
    });
    fixture = TestBed.createComponent(CompanyCalendarViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
