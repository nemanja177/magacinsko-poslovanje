import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMagacin } from '../magacin.model';

@Component({
  selector: 'jhi-magacin-detail',
  templateUrl: './magacin-detail.component.html',
})
export class MagacinDetailComponent implements OnInit {
  magacin: IMagacin | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ magacin }) => {
      this.magacin = magacin;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
