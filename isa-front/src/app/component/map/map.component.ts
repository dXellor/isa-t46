import {
  AfterViewInit,
  Component,
  Input,
  OnChanges,
  OnDestroy,
  ViewEncapsulation
} from '@angular/core';
import * as L from 'leaflet';
import { MapService } from '../../service/map/map.service';
import { Address } from "../../model/address.model";

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css'],
  encapsulation: ViewEncapsulation.None,
})
export class MapComponent implements OnChanges, OnDestroy, AfterViewInit {

  private map: any;
  private markerLayer: L.LayerGroup;

  @Input() address: Address;

  constructor(private mapService: MapService) { }

  ngAfterViewInit(): void {
    this.initMap();
  }
  private initMap(): void {
    this.map = L.map('map', {
      center: [45.2396, 19.8227],
      zoom: 15,
    });

    const tiles = L.tileLayer(
      'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
      {
        maxZoom: 18,
        minZoom: 3,
        attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
      }
    );
    tiles.addTo(this.map);

    this.markerLayer = new L.LayerGroup();
    this.markerLayer.addTo(this.map);

    if (this.address) {
      this.setMarker(this.address);
    }
  }


  ngOnChanges(): void {
    if (this.map && this.address) {
      this.clearMarkers();
      this.setMarker(this.address);
    }
  }

  setMarker(address: Address): void {
    const { street, city, country } = address;
    const fullAddress = `${street}, ${city}, ${country}`;

    this.mapService.search(fullAddress).subscribe({
      next: (result) => {
        const lat = result[0].lat;
        const lon = result[0].lon;

        const marker = L.marker([lat, lon]).addTo(this.markerLayer).bindPopup(fullAddress).openPopup();

        this.map.setView([lat, lon], 15);
      },
      error: () => { },
    });
  }


  clearMarkers(): void {
    this.markerLayer.clearLayers();
  }

  ngOnDestroy(): void {
    if (this.map) {
      this.map.remove();
    }
  }
}
