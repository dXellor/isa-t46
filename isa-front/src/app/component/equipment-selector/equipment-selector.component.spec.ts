import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EquipmentSelectorComponent } from './equipment-selector.component';

describe('EquipmentSelectorComponent', () => {
  let component: EquipmentSelectorComponent;
  let fixture: ComponentFixture<EquipmentSelectorComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EquipmentSelectorComponent]
    });
    fixture = TestBed.createComponent(EquipmentSelectorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
