import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPopis } from '../popis.model';

@Component({
  selector: 'jhi-popis-detail',
  templateUrl: './popis-detail.component.html',
})
export class PopisDetailComponent implements OnInit {
  popis: IPopis | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ popis }) => {
      this.popis = popis;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
