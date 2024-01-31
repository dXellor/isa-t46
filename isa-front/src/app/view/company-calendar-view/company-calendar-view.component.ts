import { Component } from '@angular/core';
import { CalendarEvent, CalendarView } from 'angular-calendar';
import {
  startOfDay,
  endOfDay,
  subDays,
  addDays,
  endOfMonth,
  isSameDay,
  isSameMonth,
  addHours,
  addMinutes
} from 'date-fns';
import { Subject } from 'rxjs';
import { Reservation } from 'src/app/model/reservation.model';
import { ReservationService } from 'src/app/service/reservation/reservation.service';

@Component({
  selector: 'app-company-calendar-view',
  templateUrl: './company-calendar-view.component.html',
  styleUrls: ['./company-calendar-view.component.css']
})
export class CompanyCalendarViewComponent {

  public currentDate: Date;
  public events: CalendarEvent[];
  public view: CalendarView = CalendarView.Month;
  CalendarView = CalendarView;
  refresh = new Subject<void>();
  yearView: boolean;

  constructor(private reservationService: ReservationService) {
    this.yearView = false;
    this.currentDate = new Date();
    this.events = [];
    this.getByMonth();
  }

  getByDay() {
    this.events = [];
    this.reservationService.getByDay(this.currentDate.getFullYear(), this.currentDate.getMonth() + 1, this.currentDate.getDate()).subscribe({
      next: (response: Reservation[]) => {
        if (!response) return;

        this.loadEvents(response);
      }
    });
  }

  getByWeek() {
    this.events = [];
    this.reservationService.getByWeek(this.currentDate.getFullYear(), this.currentDate.getMonth() + 1, this.currentDate.getDate()).subscribe({
      next: (response: Reservation[]) => {
        if (!response) return;

        this.loadEvents(response);
      }
    });
  }

  getByMonth() {
    this.events = [];
    this.reservationService.getByMonth(this.currentDate.getFullYear(), this.currentDate.getMonth() + 1).subscribe({
      next: (response: Reservation[]) => {
        if (!response) return;

        this.loadEvents(response);
      }
    });
  }

  getByYear() {
    this.events = [];
    this.reservationService.getByYear(this.currentDate.getFullYear()).subscribe({
      next: (response: Reservation[]) => {
        if (!response) return;

        this.loadEvents(response);
      }
    });
  }

  loadEvents(reservations: Reservation[]) {
    reservations.forEach((reservation) => {
      let newEvent =
      {
        start: new Date(reservation.dateTime),
        end: addMinutes(new Date(reservation.dateTime), reservation.duration),
        title: reservation.employee ? reservation.employee.firstName + " " + reservation.employee.lastName : "Anonymous",
      }
      this.events.push(newEvent);
    })
    this.refresh.next();
  }

  calendarMoveClicked() {
    this.getDependingOnView(this.yearView ? 'year' : this.view);
  }

  setView(view: CalendarView) {
    this.view = view;
    this.yearView = false;

    this.getDependingOnView(view);
  }

  setYearlyView() {
    this.yearView = true;
  }

  getDependingOnView(view: string) {
    switch (view) {
      case 'year': this.getByYear(); break;
      case 'month': this.getByMonth(); break;
      case 'week': this.getByWeek(); break;
      case 'day': this.getByDay(); break;
    }
  }
}
