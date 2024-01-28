import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PositionSimulatorViewComponent } from './position-simulator-view.component';

describe('PositionSimulatorViewComponent', () => {
  let component: PositionSimulatorViewComponent;
  let fixture: ComponentFixture<PositionSimulatorViewComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PositionSimulatorViewComponent]
    });
    fixture = TestBed.createComponent(PositionSimulatorViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
