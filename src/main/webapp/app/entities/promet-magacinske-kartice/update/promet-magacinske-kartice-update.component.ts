import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { PrometMagacinskeKarticeFormService, PrometMagacinskeKarticeFormGroup } from './promet-magacinske-kartice-form.service';
import { IPrometMagacinskeKartice } from '../promet-magacinske-kartice.model';
import { PrometMagacinskeKarticeService } from '../service/promet-magacinske-kartice.service';

@Component({
  selector: 'jhi-promet-magacinske-kartice-update',
  templateUrl: './promet-magacinske-kartice-update.component.html',
})
export class PrometMagacinskeKarticeUpdateComponent implements OnInit {
  isSaving = false;
  prometMagacinskeKartice: IPrometMagacinskeKartice | null = null;

  editForm: PrometMagacinskeKarticeFormGroup = this.prometMagacinskeKarticeFormService.createPrometMagacinskeKarticeFormGroup();

  constructor(
    protected prometMagacinskeKarticeService: PrometMagacinskeKarticeService,
    protected prometMagacinskeKarticeFormService: PrometMagacinskeKarticeFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ prometMagacinskeKartice }) => {
      this.prometMagacinskeKartice = prometMagacinskeKartice;
      if (prometMagacinskeKartice) {
        this.updateForm(prometMagacinskeKartice);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const prometMagacinskeKartice = this.prometMagacinskeKarticeFormService.getPrometMagacinskeKartice(this.editForm);
    if (prometMagacinskeKartice.id !== null) {
      this.subscribeToSaveResponse(this.prometMagacinskeKarticeService.update(prometMagacinskeKartice));
    } else {
      this.subscribeToSaveResponse(this.prometMagacinskeKarticeService.create(prometMagacinskeKartice));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPrometMagacinskeKartice>>): void {
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

  protected updateForm(prometMagacinskeKartice: IPrometMagacinskeKartice): void {
    this.prometMagacinskeKartice = prometMagacinskeKartice;
    this.prometMagacinskeKarticeFormService.resetForm(this.editForm, prometMagacinskeKartice);
  }
}
