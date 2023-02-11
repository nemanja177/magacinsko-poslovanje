import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { PrometniDokumentFormService, PrometniDokumentFormGroup } from './prometni-dokument-form.service';
import { IPrometniDokument } from '../prometni-dokument.model';
import { PrometniDokumentService } from '../service/prometni-dokument.service';

@Component({
  selector: 'jhi-prometni-dokument-update',
  templateUrl: './prometni-dokument-update.component.html',
})
export class PrometniDokumentUpdateComponent implements OnInit {
  isSaving = false;
  prometniDokument: IPrometniDokument | null = null;

  editForm: PrometniDokumentFormGroup = this.prometniDokumentFormService.createPrometniDokumentFormGroup();

  constructor(
    protected prometniDokumentService: PrometniDokumentService,
    protected prometniDokumentFormService: PrometniDokumentFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ prometniDokument }) => {
      this.prometniDokument = prometniDokument;
      if (prometniDokument) {
        this.updateForm(prometniDokument);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const prometniDokument = this.prometniDokumentFormService.getPrometniDokument(this.editForm);
    if (prometniDokument.id !== null) {
      this.subscribeToSaveResponse(this.prometniDokumentService.update(prometniDokument));
    } else {
      this.subscribeToSaveResponse(this.prometniDokumentService.create(prometniDokument));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPrometniDokument>>): void {
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

  protected updateForm(prometniDokument: IPrometniDokument): void {
    this.prometniDokument = prometniDokument;
    this.prometniDokumentFormService.resetForm(this.editForm, prometniDokument);
  }
}
