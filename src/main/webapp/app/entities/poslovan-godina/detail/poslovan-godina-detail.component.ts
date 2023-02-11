import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPoslovanGodina } from '../poslovan-godina.model';

@Component({
  selector: 'jhi-poslovan-godina-detail',
  templateUrl: './poslovan-godina-detail.component.html',
})
export class PoslovanGodinaDetailComponent implements OnInit {
  poslovanGodina: IPoslovanGodina | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ poslovanGodina }) => {
      this.poslovanGodina = poslovanGodina;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
