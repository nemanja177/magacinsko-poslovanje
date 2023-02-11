import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRadnik } from '../radnik.model';

@Component({
  selector: 'jhi-radnik-detail',
  templateUrl: './radnik-detail.component.html',
})
export class RadnikDetailComponent implements OnInit {
  radnik: IRadnik | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ radnik }) => {
      this.radnik = radnik;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
