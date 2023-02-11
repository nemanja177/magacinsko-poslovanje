import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPoslovniPartner } from '../poslovni-partner.model';

@Component({
  selector: 'jhi-poslovni-partner-detail',
  templateUrl: './poslovni-partner-detail.component.html',
})
export class PoslovniPartnerDetailComponent implements OnInit {
  poslovniPartner: IPoslovniPartner | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ poslovniPartner }) => {
      this.poslovniPartner = poslovniPartner;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
