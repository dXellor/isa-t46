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
  private car_icon: L.Icon;
  private readonly customIconSize: L.PointExpression = [48, 48];
  private readonly customIconAnchor: L.PointExpression = [24, 46];

  @Input() address: Address;
  @Input() center: number[] = [45.2396, 19.8227];
  @Input() zoom: number = 15;
  @Input() car_position: number[] = [];

  constructor(private mapService: MapService) { 
    this.car_icon = L.icon({
      iconUrl: 'assets/icons/car.png',
      iconSize: this.customIconSize,
      iconAnchor: this.customIconAnchor
    });
  }

  ngAfterViewInit(): void {
    this.initMap();
  }

  private initMap(): void {
    this.map = L.map('map', {
      center: this.center as L.LatLngExpression,
      zoom: this.zoom,
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

    if(this.map && this.car_position){
      this.clearMarkers();
      this.setCarMarker();
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

  private setCarMarker(): void{
    const marker = L.marker(this.car_position as L.LatLngExpression, {icon: this.car_icon}).addTo(this.markerLayer);
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
