import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { RadnikFormService, RadnikFormGroup } from './radnik-form.service';
import { IRadnik } from '../radnik.model';
import { RadnikService } from '../service/radnik.service';

@Component({
  selector: 'jhi-radnik-update',
  templateUrl: './radnik-update.component.html',
})
export class RadnikUpdateComponent implements OnInit {
  isSaving = false;
  radnik: IRadnik | null = null;

  editForm: RadnikFormGroup = this.radnikFormService.createRadnikFormGroup();

  constructor(
    protected radnikService: RadnikService,
    protected radnikFormService: RadnikFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ radnik }) => {
      this.radnik = radnik;
      if (radnik) {
        this.updateForm(radnik);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const radnik = this.radnikFormService.getRadnik(this.editForm);
    if (radnik.id !== null) {
      this.subscribeToSaveResponse(this.radnikService.update(radnik));
    } else {
      this.subscribeToSaveResponse(this.radnikService.create(radnik));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRadnik>>): void {
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

  protected updateForm(radnik: IRadnik): void {
    this.radnik = radnik;
    this.radnikFormService.resetForm(this.editForm, radnik);
  }
}
