import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAnalitickaMagacinskaKartica } from '../analiticka-magacinska-kartica.model';

@Component({
  selector: 'jhi-analiticka-magacinska-kartica-detail',
  templateUrl: './analiticka-magacinska-kartica-detail.component.html',
})
export class AnalitickaMagacinskaKarticaDetailComponent implements OnInit {
  analitickaMagacinskaKartica: IAnalitickaMagacinskaKartica | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ analitickaMagacinskaKartica }) => {
      this.analitickaMagacinskaKartica = analitickaMagacinskaKartica;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
