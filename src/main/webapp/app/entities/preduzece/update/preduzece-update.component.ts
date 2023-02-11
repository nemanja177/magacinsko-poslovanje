import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { PreduzeceFormService, PreduzeceFormGroup } from './preduzece-form.service';
import { IPreduzece } from '../preduzece.model';
import { PreduzeceService } from '../service/preduzece.service';

@Component({
  selector: 'jhi-preduzece-update',
  templateUrl: './preduzece-update.component.html',
})
export class PreduzeceUpdateComponent implements OnInit {
  isSaving = false;
  preduzece: IPreduzece | null = null;

  editForm: PreduzeceFormGroup = this.preduzeceFormService.createPreduzeceFormGroup();

  constructor(
    protected preduzeceService: PreduzeceService,
    protected preduzeceFormService: PreduzeceFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ preduzece }) => {
      this.preduzece = preduzece;
      if (preduzece) {
        this.updateForm(preduzece);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const preduzece = this.preduzeceFormService.getPreduzece(this.editForm);
    if (preduzece.id !== null) {
      this.subscribeToSaveResponse(this.preduzeceService.update(preduzece));
    } else {
      this.subscribeToSaveResponse(this.preduzeceService.create(preduzece));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPreduzece>>): void {
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

  protected updateForm(preduzece: IPreduzece): void {
    this.preduzece = preduzece;
    this.preduzeceFormService.resetForm(this.editForm, preduzece);
  }
}
