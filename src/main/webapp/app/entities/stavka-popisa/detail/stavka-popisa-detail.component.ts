import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStavkaPopisa } from '../stavka-popisa.model';

@Component({
  selector: 'jhi-stavka-popisa-detail',
  templateUrl: './stavka-popisa-detail.component.html',
})
export class StavkaPopisaDetailComponent implements OnInit {
  stavkaPopisa: IStavkaPopisa | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ stavkaPopisa }) => {
      this.stavkaPopisa = stavkaPopisa;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
