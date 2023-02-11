import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPrometMagacinskeKartice } from '../promet-magacinske-kartice.model';

@Component({
  selector: 'jhi-promet-magacinske-kartice-detail',
  templateUrl: './promet-magacinske-kartice-detail.component.html',
})
export class PrometMagacinskeKarticeDetailComponent implements OnInit {
  prometMagacinskeKartice: IPrometMagacinskeKartice | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ prometMagacinskeKartice }) => {
      this.prometMagacinskeKartice = prometMagacinskeKartice;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
