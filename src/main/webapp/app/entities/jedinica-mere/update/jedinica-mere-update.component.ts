import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { JedinicaMereFormService, JedinicaMereFormGroup } from './jedinica-mere-form.service';
import { IJedinicaMere } from '../jedinica-mere.model';
import { JedinicaMereService } from '../service/jedinica-mere.service';

@Component({
  selector: 'jhi-jedinica-mere-update',
  templateUrl: './jedinica-mere-update.component.html',
})
export class JedinicaMereUpdateComponent implements OnInit {
  isSaving = false;
  jedinicaMere: IJedinicaMere | null = null;

  editForm: JedinicaMereFormGroup = this.jedinicaMereFormService.createJedinicaMereFormGroup();

  constructor(
    protected jedinicaMereService: JedinicaMereService,
    protected jedinicaMereFormService: JedinicaMereFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jedinicaMere }) => {
      this.jedinicaMere = jedinicaMere;
      if (jedinicaMere) {
        this.updateForm(jedinicaMere);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const jedinicaMere = this.jedinicaMereFormService.getJedinicaMere(this.editForm);
    if (jedinicaMere.id !== null) {
      this.subscribeToSaveResponse(this.jedinicaMereService.update(jedinicaMere));
    } else {
      this.subscribeToSaveResponse(this.jedinicaMereService.create(jedinicaMere));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJedinicaMere>>): void {
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

  protected updateForm(jedinicaMere: IJedinicaMere): void {
    this.jedinicaMere = jedinicaMere;
    this.jedinicaMereFormService.resetForm(this.editForm, jedinicaMere);
  }
}
