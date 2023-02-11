import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IArtikal } from '../artikal.model';

@Component({
  selector: 'jhi-artikal-detail',
  templateUrl: './artikal-detail.component.html',
})
export class ArtikalDetailComponent implements OnInit {
  artikal: IArtikal | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ artikal }) => {
      this.artikal = artikal;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
