import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPreduzece } from '../preduzece.model';

@Component({
  selector: 'jhi-preduzece-detail',
  templateUrl: './preduzece-detail.component.html',
})
export class PreduzeceDetailComponent implements OnInit {
  preduzece: IPreduzece | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ preduzece }) => {
      this.preduzece = preduzece;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
