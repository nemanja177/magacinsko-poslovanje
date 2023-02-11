import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { StavkaPopisaFormService, StavkaPopisaFormGroup } from './stavka-popisa-form.service';
import { IStavkaPopisa } from '../stavka-popisa.model';
import { StavkaPopisaService } from '../service/stavka-popisa.service';

@Component({
  selector: 'jhi-stavka-popisa-update',
  templateUrl: './stavka-popisa-update.component.html',
})
export class StavkaPopisaUpdateComponent implements OnInit {
  isSaving = false;
  stavkaPopisa: IStavkaPopisa | null = null;

  editForm: StavkaPopisaFormGroup = this.stavkaPopisaFormService.createStavkaPopisaFormGroup();

  constructor(
    protected stavkaPopisaService: StavkaPopisaService,
    protected stavkaPopisaFormService: StavkaPopisaFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ stavkaPopisa }) => {
      this.stavkaPopisa = stavkaPopisa;
      if (stavkaPopisa) {
        this.updateForm(stavkaPopisa);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const stavkaPopisa = this.stavkaPopisaFormService.getStavkaPopisa(this.editForm);
    if (stavkaPopisa.id !== null) {
      this.subscribeToSaveResponse(this.stavkaPopisaService.update(stavkaPopisa));
    } else {
      this.subscribeToSaveResponse(this.stavkaPopisaService.create(stavkaPopisa));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStavkaPopisa>>): void {
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

  protected updateForm(stavkaPopisa: IStavkaPopisa): void {
    this.stavkaPopisa = stavkaPopisa;
    this.stavkaPopisaFormService.resetForm(this.editForm, stavkaPopisa);
  }
}
