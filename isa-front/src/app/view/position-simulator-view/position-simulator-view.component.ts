import { Component, OnDestroy, OnInit } from '@angular/core';
import { IMqttMessage, IMqttServiceOptions, MqttModule, MqttService } from 'ngx-mqtt';
import { MessageService } from 'primeng/api';
import { Subscription } from 'rxjs';
import { ReservationService } from 'src/app/service/reservation/reservation.service';

@Component({
  selector: 'app-position-simulator-view',
  templateUrl: './position-simulator-view.component.html',
  styleUrls: ['./position-simulator-view.component.css'],
  providers: [MessageService],
})
export class PositionSimulatorViewComponent implements OnInit, OnDestroy {

  private subscribtions: Subscription[] = [];
  public car_position: number[] = [];

  public messages: string[] = [];
  private currentSubscription: Subscription | undefined;
  private readonly topic: string = 'isat46/location/5';
  private readonly connectionOptions : IMqttServiceOptions = {
    hostname: 'localhost',
    port: 9001,
    clean: true,
    connectTimeout: 4000,
    reconnectPeriod: 4000,
    clientId: 'CONSUMER46',
    protocol: 'ws'
  }
  client: MqttService | undefined;

  constructor(private mqttService: MqttService, private reservationService: ReservationService, private messageService: MessageService){
    this.client = mqttService;
  }

  ngOnInit(): void {
    try {
      this.client?.connect(this.connectionOptions)
    } catch (error) {
      console.log('mqtt.connect error', error);
    }

    this.initSubscribtions();
  }

  ngOnDestroy(): void {
    for(let sub of this.subscribtions){
      sub.unsubscribe();
    }
  }

  private initSubscribtions(): void{
    this.subscribtions.push(this.client.onConnect.subscribe(() => {
      console.log('Connection to mqtt broker established');
    }));

    this.subscribtions.push(this.client.onError.subscribe((error) => {
      console.log(`Connection error: ${error}`);
    }));

    this.subscribtions.push(this.client.observe(this.topic, {qos: 2}).subscribe((message: IMqttMessage) =>{
      if(message.payload.toString() === 'END'){
        this.messageService.add({ severity: 'success', summary: 'The simulation has ended'});
      }else{
        const lat = Number(message.payload.toString().split(';')[0]);
        const long = Number(message.payload.toString().split(';')[1]);
        this.car_position = [lat, long];
      }
    }));
  }

  public startPositionSimulator(reservationId: number): void{
    this.reservationService.startPositionSimulator(reservationId).subscribe(() => {
      this.messageService.add({ severity: 'success', summary: 'Position simulator started'});
    });
  }
}
