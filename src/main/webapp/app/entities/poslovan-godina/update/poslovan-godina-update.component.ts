import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { PoslovanGodinaFormService, PoslovanGodinaFormGroup } from './poslovan-godina-form.service';
import { IPoslovanGodina } from '../poslovan-godina.model';
import { PoslovanGodinaService } from '../service/poslovan-godina.service';

@Component({
  selector: 'jhi-poslovan-godina-update',
  templateUrl: './poslovan-godina-update.component.html',
})
export class PoslovanGodinaUpdateComponent implements OnInit {
  isSaving = false;
  poslovanGodina: IPoslovanGodina | null = null;

  editForm: PoslovanGodinaFormGroup = this.poslovanGodinaFormService.createPoslovanGodinaFormGroup();

  constructor(
    protected poslovanGodinaService: PoslovanGodinaService,
    protected poslovanGodinaFormService: PoslovanGodinaFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ poslovanGodina }) => {
      this.poslovanGodina = poslovanGodina;
      if (poslovanGodina) {
        this.updateForm(poslovanGodina);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const poslovanGodina = this.poslovanGodinaFormService.getPoslovanGodina(this.editForm);
    if (poslovanGodina.id !== null) {
      this.subscribeToSaveResponse(this.poslovanGodinaService.update(poslovanGodina));
    } else {
      this.subscribeToSaveResponse(this.poslovanGodinaService.create(poslovanGodina));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPoslovanGodina>>): void {
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

  protected updateForm(poslovanGodina: IPoslovanGodina): void {
    this.poslovanGodina = poslovanGodina;
    this.poslovanGodinaFormService.resetForm(this.editForm, poslovanGodina);
  }
}
