import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { PopisFormService, PopisFormGroup } from './popis-form.service';
import { IPopis } from '../popis.model';
import { PopisService } from '../service/popis.service';

@Component({
  selector: 'jhi-popis-update',
  templateUrl: './popis-update.component.html',
})
export class PopisUpdateComponent implements OnInit {
  isSaving = false;
  popis: IPopis | null = null;

  editForm: PopisFormGroup = this.popisFormService.createPopisFormGroup();

  constructor(
    protected popisService: PopisService,
    protected popisFormService: PopisFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ popis }) => {
      this.popis = popis;
      if (popis) {
        this.updateForm(popis);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const popis = this.popisFormService.getPopis(this.editForm);
    if (popis.id !== null) {
      this.subscribeToSaveResponse(this.popisService.update(popis));
    } else {
      this.subscribeToSaveResponse(this.popisService.create(popis));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPopis>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(popis: IPopis): void {
    this.popis = popis;
    this.popisFormService.resetForm(this.editForm, popis);
  }
}
