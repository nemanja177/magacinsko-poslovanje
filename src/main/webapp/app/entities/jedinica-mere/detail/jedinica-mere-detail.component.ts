import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJedinicaMere } from '../jedinica-mere.model';

@Component({
  selector: 'jhi-jedinica-mere-detail',
  templateUrl: './jedinica-mere-detail.component.html',
})
export class JedinicaMereDetailComponent implements OnInit {
  jedinicaMere: IJedinicaMere | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jedinicaMere }) => {
      this.jedinicaMere = jedinicaMere;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
