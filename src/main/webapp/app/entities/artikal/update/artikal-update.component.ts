import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ArtikalFormService, ArtikalFormGroup } from './artikal-form.service';
import { IArtikal } from '../artikal.model';
import { ArtikalService } from '../service/artikal.service';

@Component({
  selector: 'jhi-artikal-update',
  templateUrl: './artikal-update.component.html',
})
export class ArtikalUpdateComponent implements OnInit {
  isSaving = false;
  artikal: IArtikal | null = null;

  editForm: ArtikalFormGroup = this.artikalFormService.createArtikalFormGroup();

  constructor(
    protected artikalService: ArtikalService,
    protected artikalFormService: ArtikalFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ artikal }) => {
      this.artikal = artikal;
      if (artikal) {
        this.updateForm(artikal);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const artikal = this.artikalFormService.getArtikal(this.editForm);
    if (artikal.id !== null) {
      this.subscribeToSaveResponse(this.artikalService.update(artikal));
    } else {
      this.subscribeToSaveResponse(this.artikalService.create(artikal));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IArtikal>>): void {
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

  protected updateForm(artikal: IArtikal): void {
    this.artikal = artikal;
    this.artikalFormService.resetForm(this.editForm, artikal);
  }
}
