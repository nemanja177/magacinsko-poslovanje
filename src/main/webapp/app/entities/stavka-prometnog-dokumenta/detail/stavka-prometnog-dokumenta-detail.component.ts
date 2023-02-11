import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStavkaPrometnogDokumenta } from '../stavka-prometnog-dokumenta.model';

@Component({
  selector: 'jhi-stavka-prometnog-dokumenta-detail',
  templateUrl: './stavka-prometnog-dokumenta-detail.component.html',
})
export class StavkaPrometnogDokumentaDetailComponent implements OnInit {
  stavkaPrometnogDokumenta: IStavkaPrometnogDokumenta | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ stavkaPrometnogDokumenta }) => {
      this.stavkaPrometnogDokumenta = stavkaPrometnogDokumenta;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
