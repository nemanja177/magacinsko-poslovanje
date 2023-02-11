import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IStavkaPrometnogDokumenta } from '../stavka-prometnog-dokumenta.model';
import { StavkaPrometnogDokumentaService } from '../service/stavka-prometnog-dokumenta.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './stavka-prometnog-dokumenta-delete-dialog.component.html',
})
export class StavkaPrometnogDokumentaDeleteDialogComponent {
  stavkaPrometnogDokumenta?: IStavkaPrometnogDokumenta;

  constructor(protected stavkaPrometnogDokumentaService: StavkaPrometnogDokumentaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.stavkaPrometnogDokumentaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
