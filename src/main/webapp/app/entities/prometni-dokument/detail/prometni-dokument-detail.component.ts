import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPrometniDokument } from '../prometni-dokument.model';

@Component({
  selector: 'jhi-prometni-dokument-detail',
  templateUrl: './prometni-dokument-detail.component.html',
})
export class PrometniDokumentDetailComponent implements OnInit {
  prometniDokument: IPrometniDokument | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ prometniDokument }) => {
      this.prometniDokument = prometniDokument;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
