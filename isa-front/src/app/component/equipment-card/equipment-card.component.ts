import { Component, Input } from '@angular/core';
import { Equipment } from 'src/app/model/equipment.model';

@Component({
  selector: 'app-equipment-card',
  templateUrl: './equipment-card.component.html',
  styleUrls: ['./equipment-card.component.css']
})
export class EquipmentCardComponent {
  @Input() equipment: Equipment;
}
