import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { MagacinskaKarticaFormService, MagacinskaKarticaFormGroup } from './magacinska-kartica-form.service';
import { IMagacinskaKartica } from '../magacinska-kartica.model';
import { MagacinskaKarticaService } from '../service/magacinska-kartica.service';

@Component({
  selector: 'jhi-magacinska-kartica-update',
  templateUrl: './magacinska-kartica-update.component.html',
})
export class MagacinskaKarticaUpdateComponent implements OnInit {
  isSaving = false;
  magacinskaKartica: IMagacinskaKartica | null = null;

  editForm: MagacinskaKarticaFormGroup = this.magacinskaKarticaFormService.createMagacinskaKarticaFormGroup();

  constructor(
    protected magacinskaKarticaService: MagacinskaKarticaService,
    protected magacinskaKarticaFormService: MagacinskaKarticaFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ magacinskaKartica }) => {
      this.magacinskaKartica = magacinskaKartica;
      if (magacinskaKartica) {
        this.updateForm(magacinskaKartica);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const magacinskaKartica = this.magacinskaKarticaFormService.getMagacinskaKartica(this.editForm);
    if (magacinskaKartica.id !== null) {
      this.subscribeToSaveResponse(this.magacinskaKarticaService.update(magacinskaKartica));
    } else {
      this.subscribeToSaveResponse(this.magacinskaKarticaService.create(magacinskaKartica));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMagacinskaKartica>>): void {
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

  protected updateForm(magacinskaKartica: IMagacinskaKartica): void {
    this.magacinskaKartica = magacinskaKartica;
    this.magacinskaKarticaFormService.resetForm(this.editForm, magacinskaKartica);
  }
}
