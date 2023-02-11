import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { StavkaPrometnogDokumentaFormService, StavkaPrometnogDokumentaFormGroup } from './stavka-prometnog-dokumenta-form.service';
import { IStavkaPrometnogDokumenta } from '../stavka-prometnog-dokumenta.model';
import { StavkaPrometnogDokumentaService } from '../service/stavka-prometnog-dokumenta.service';

@Component({
  selector: 'jhi-stavka-prometnog-dokumenta-update',
  templateUrl: './stavka-prometnog-dokumenta-update.component.html',
})
export class StavkaPrometnogDokumentaUpdateComponent implements OnInit {
  isSaving = false;
  stavkaPrometnogDokumenta: IStavkaPrometnogDokumenta | null = null;

  editForm: StavkaPrometnogDokumentaFormGroup = this.stavkaPrometnogDokumentaFormService.createStavkaPrometnogDokumentaFormGroup();

  constructor(
    protected stavkaPrometnogDokumentaService: StavkaPrometnogDokumentaService,
    protected stavkaPrometnogDokumentaFormService: StavkaPrometnogDokumentaFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ stavkaPrometnogDokumenta }) => {
      this.stavkaPrometnogDokumenta = stavkaPrometnogDokumenta;
      if (stavkaPrometnogDokumenta) {
        this.updateForm(stavkaPrometnogDokumenta);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const stavkaPrometnogDokumenta = this.stavkaPrometnogDokumentaFormService.getStavkaPrometnogDokumenta(this.editForm);
    if (stavkaPrometnogDokumenta.id !== null) {
      this.subscribeToSaveResponse(this.stavkaPrometnogDokumentaService.update(stavkaPrometnogDokumenta));
    } else {
      this.subscribeToSaveResponse(this.stavkaPrometnogDokumentaService.create(stavkaPrometnogDokumenta));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStavkaPrometnogDokumenta>>): void {
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

  protected updateForm(stavkaPrometnogDokumenta: IStavkaPrometnogDokumenta): void {
    this.stavkaPrometnogDokumenta = stavkaPrometnogDokumenta;
    this.stavkaPrometnogDokumentaFormService.resetForm(this.editForm, stavkaPrometnogDokumenta);
  }
}
