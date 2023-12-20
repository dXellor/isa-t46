import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VisitorHomeViewComponent } from './visitor-home-view.component';

describe('VisitorHomeViewComponent', () => {
  let component: VisitorHomeViewComponent;
  let fixture: ComponentFixture<VisitorHomeViewComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VisitorHomeViewComponent]
    });
    fixture = TestBed.createComponent(VisitorHomeViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
