import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { MagacinFormService, MagacinFormGroup } from './magacin-form.service';
import { IMagacin } from '../magacin.model';
import { MagacinService } from '../service/magacin.service';

@Component({
  selector: 'jhi-magacin-update',
  templateUrl: './magacin-update.component.html',
})
export class MagacinUpdateComponent implements OnInit {
  isSaving = false;
  magacin: IMagacin | null = null;

  editForm: MagacinFormGroup = this.magacinFormService.createMagacinFormGroup();

  constructor(
    protected magacinService: MagacinService,
    protected magacinFormService: MagacinFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ magacin }) => {
      this.magacin = magacin;
      if (magacin) {
        this.updateForm(magacin);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const magacin = this.magacinFormService.getMagacin(this.editForm);
    if (magacin.id !== null) {
      this.subscribeToSaveResponse(this.magacinService.update(magacin));
    } else {
      this.subscribeToSaveResponse(this.magacinService.create(magacin));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMagacin>>): void {
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

  protected updateForm(magacin: IMagacin): void {
    this.magacin = magacin;
    this.magacinFormService.resetForm(this.editForm, magacin);
  }
}
