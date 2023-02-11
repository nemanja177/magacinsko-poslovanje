import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { PoslovniPartnerFormService, PoslovniPartnerFormGroup } from './poslovni-partner-form.service';
import { IPoslovniPartner } from '../poslovni-partner.model';
import { PoslovniPartnerService } from '../service/poslovni-partner.service';

@Component({
  selector: 'jhi-poslovni-partner-update',
  templateUrl: './poslovni-partner-update.component.html',
})
export class PoslovniPartnerUpdateComponent implements OnInit {
  isSaving = false;
  poslovniPartner: IPoslovniPartner | null = null;

  editForm: PoslovniPartnerFormGroup = this.poslovniPartnerFormService.createPoslovniPartnerFormGroup();

  constructor(
    protected poslovniPartnerService: PoslovniPartnerService,
    protected poslovniPartnerFormService: PoslovniPartnerFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ poslovniPartner }) => {
      this.poslovniPartner = poslovniPartner;
      if (poslovniPartner) {
        this.updateForm(poslovniPartner);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const poslovniPartner = this.poslovniPartnerFormService.getPoslovniPartner(this.editForm);
    if (poslovniPartner.id !== null) {
      this.subscribeToSaveResponse(this.poslovniPartnerService.update(poslovniPartner));
    } else {
      this.subscribeToSaveResponse(this.poslovniPartnerService.create(poslovniPartner));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPoslovniPartner>>): void {
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

  protected updateForm(poslovniPartner: IPoslovniPartner): void {
    this.poslovniPartner = poslovniPartner;
    this.poslovniPartnerFormService.resetForm(this.editForm, poslovniPartner);
  }
}
