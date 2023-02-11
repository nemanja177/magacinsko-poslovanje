import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IStavkaPopisa } from '../stavka-popisa.model';
import { StavkaPopisaService } from '../service/stavka-popisa.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './stavka-popisa-delete-dialog.component.html',
})
export class StavkaPopisaDeleteDialogComponent {
  stavkaPopisa?: IStavkaPopisa;

  constructor(protected stavkaPopisaService: StavkaPopisaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.stavkaPopisaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
