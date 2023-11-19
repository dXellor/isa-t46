import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EquipmentCollectionViewComponent } from './equipment-collection-view.component';

describe('EquipmentCollectionViewComponent', () => {
  let component: EquipmentCollectionViewComponent;
  let fixture: ComponentFixture<EquipmentCollectionViewComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EquipmentCollectionViewComponent]
    });
    fixture = TestBed.createComponent(EquipmentCollectionViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
