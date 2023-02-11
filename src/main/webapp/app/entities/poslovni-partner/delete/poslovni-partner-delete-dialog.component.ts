import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPoslovniPartner } from '../poslovni-partner.model';
import { PoslovniPartnerService } from '../service/poslovni-partner.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './poslovni-partner-delete-dialog.component.html',
})
export class PoslovniPartnerDeleteDialogComponent {
  poslovniPartner?: IPoslovniPartner;

  constructor(protected poslovniPartnerService: PoslovniPartnerService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.poslovniPartnerService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
