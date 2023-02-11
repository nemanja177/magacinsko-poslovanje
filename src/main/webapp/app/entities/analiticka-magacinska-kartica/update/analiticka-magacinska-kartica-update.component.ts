import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { AnalitickaMagacinskaKarticaFormService, AnalitickaMagacinskaKarticaFormGroup } from './analiticka-magacinska-kartica-form.service';
import { IAnalitickaMagacinskaKartica } from '../analiticka-magacinska-kartica.model';
import { AnalitickaMagacinskaKarticaService } from '../service/analiticka-magacinska-kartica.service';

@Component({
  selector: 'jhi-analiticka-magacinska-kartica-update',
  templateUrl: './analiticka-magacinska-kartica-update.component.html',
})
export class AnalitickaMagacinskaKarticaUpdateComponent implements OnInit {
  isSaving = false;
  analitickaMagacinskaKartica: IAnalitickaMagacinskaKartica | null = null;

  editForm: AnalitickaMagacinskaKarticaFormGroup = this.analitickaMagacinskaKarticaFormService.createAnalitickaMagacinskaKarticaFormGroup();

  constructor(
    protected analitickaMagacinskaKarticaService: AnalitickaMagacinskaKarticaService,
    protected analitickaMagacinskaKarticaFormService: AnalitickaMagacinskaKarticaFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ analitickaMagacinskaKartica }) => {
      this.analitickaMagacinskaKartica = analitickaMagacinskaKartica;
      if (analitickaMagacinskaKartica) {
        this.updateForm(analitickaMagacinskaKartica);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const analitickaMagacinskaKartica = this.analitickaMagacinskaKarticaFormService.getAnalitickaMagacinskaKartica(this.editForm);
    if (analitickaMagacinskaKartica.id !== null) {
      this.subscribeToSaveResponse(this.analitickaMagacinskaKarticaService.update(analitickaMagacinskaKartica));
    } else {
      this.subscribeToSaveResponse(this.analitickaMagacinskaKarticaService.create(analitickaMagacinskaKartica));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnalitickaMagacinskaKartica>>): void {
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

  protected updateForm(analitickaMagacinskaKartica: IAnalitickaMagacinskaKartica): void {
    this.analitickaMagacinskaKartica = analitickaMagacinskaKartica;
    this.analitickaMagacinskaKarticaFormService.resetForm(this.editForm, analitickaMagacinskaKartica);
  }
}
