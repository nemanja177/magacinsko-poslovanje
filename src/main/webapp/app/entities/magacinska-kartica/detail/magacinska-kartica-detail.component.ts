import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMagacinskaKartica } from '../magacinska-kartica.model';

@Component({
  selector: 'jhi-magacinska-kartica-detail',
  templateUrl: './magacinska-kartica-detail.component.html',
})
export class MagacinskaKarticaDetailComponent implements OnInit {
  magacinskaKartica: IMagacinskaKartica | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ magacinskaKartica }) => {
      this.magacinskaKartica = magacinskaKartica;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
